package src;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class Player {

    // Player attributes
    private int x;
    private int y;
    private final int width;
    private final int height;
    private final int speed;

    // Gravity attributes
    private double velocityY = 0;
    private final double gravity = 0.7;
    private final double jumpStrength = -12;
    private boolean isOnGround = false;
    private boolean setToJump = false;
    private int jumpWindow = 0;

    // Keyboard flags
    private boolean isMovingLeft = false;
    private boolean isMovingRight = false;
    Set<Integer> keys = new HashSet<>();


    // Mouse tracking (updated by MouseMotionListener)
    private int mouseX = -1;
    private int mouseY = -1;

    // Constructor
    public Player(int x, int y, int width, int height, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    // Handle key press
    public void keyPressed(int newKey) {
        keys.add(newKey);
    }
    // Handle key release
    public void keyReleased(int releasedKey) {
        keys.remove(releasedKey);
    }

    // Update player's position
    public void update(int panelWidth, int panelHeight) {
        // Horizontal key update press
        if (keys.contains(KeyEvent.VK_A))
            isMovingLeft = true;
        if (keys.contains(KeyEvent.VK_D))
            isMovingRight = true;
        if (keys.contains(KeyEvent.VK_W)) {
            setToJump = true;        // remember that the player wants to jump
            jumpWindow = 2;         // allow jump buffering for 10 ticks
        }
        // Horizontal key update release
        if (!keys.contains(KeyEvent.VK_A))
            isMovingLeft = false;
        if (!keys.contains(KeyEvent.VK_D))
            isMovingRight = false;

        
        // Horizontal movement
        if (isMovingLeft)
            x -= speed;
        if (isMovingRight)
            x += speed;

        // Gravity
        velocityY += gravity;
        y += (int) velocityY;

        /* Collision */
        // Clamp to panel bounds
        if (x < 0) {
            x = 0;
        } else if (x > panelWidth - width) {
            x = panelWidth - width;
        }
        // Ground collision
        if (y >= panelHeight - height) {
            y = panelHeight - height;
            velocityY = 0;
            isOnGround = true;
        } else {
            isOnGround = false;
        }

        if (y < 0) {
            y = 0;
            velocityY = 0;
        }

        // Jump logic with jump buffering
        if (jumpWindow > 0) {
            jumpWindow--;
        }
        if (setToJump) {
            if (isOnGround) {
                // Perform the jump
                velocityY = jumpStrength;
                isOnGround = false;
                setToJump = false;
                jumpWindow = 0;
            } else if (jumpWindow <= 0) { // Jump window expired without touching the ground
                setToJump = false;
            }
        }
    }

    // Draw player
    public void drawPlayer(Graphics g, int panelWidth, int panelHeight) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
    }

    // Update mouse coordinates (called from Gamepanel's MouseMotionListener)
    public void setMousePosition(int mx, int my) {
        this.mouseX = mx;
        this.mouseY = my;
    }

    // Draw cursor using the last-known mouse coordinates
    public boolean drawCursor(Graphics g) {
        if (mouseX < 0 || mouseY < 0)
            return false; // no mouse data yet

        // Calculate differences relative to player's center
        double dx = mouseX - (x + width / 2.0);
        double dy = mouseY - (y + height / 2.0);
        double angleRadians = Math.atan2(dy, dx);

        int cursorX = (int) (x + width / 2.0 + Math.cos(angleRadians) * 30);
        int cursorY = (int) (y + height / 2.0 + Math.sin(angleRadians) * 30);

        g.setColor(Color.RED);
        g.fillRect(cursorX - 5, cursorY - 8, 14, 14);

        return true;
    }
}
