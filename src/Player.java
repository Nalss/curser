package src;

import java.awt.*;
import java.awt.event.KeyEvent;

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

    // Movement flags
    private boolean isMovingLeft = false;
    private boolean isMovingRight = false;

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
    public void keyPressed(int key) {
        if (key == KeyEvent.VK_A)
            isMovingLeft = true;
        if (key == KeyEvent.VK_D)
            isMovingRight = true;
        if (key == KeyEvent.VK_W && isOnGround) {
            velocityY = jumpStrength;
            isOnGround = false;
        }
    }

    // Handle key release
    public void keyReleased(int key) {
        if (key == KeyEvent.VK_A)
            isMovingLeft = false;
        if (key == KeyEvent.VK_D)
            isMovingRight = false;
    }

    // Update player's position
    public void update(int panelWidth, int panelHeight) {
        // Horizontal movement
        if (isMovingLeft)
            x -= speed;
        if (isMovingRight)
            x += speed;

        // Gravity
        velocityY += gravity;
        y += (int) velocityY;

        // Clamp to panel bounds (ground collision)
        if (x < 0) {
            x = 0;
        } else if (x > panelWidth - width) {
            x = panelWidth - width;
        }

        // Ground collision and isOnGround logic
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
    public void drawCursor(Graphics g) {
        if (mouseX < 0 || mouseY < 0)
            return; // no mouse data yet

        // Calculate differences relative to player's center
        double dx = mouseX - (x + width / 2.0);
        double dy = mouseY - (y + height / 2.0);
        double angleRadians = Math.atan2(dy, dx);

        int cursorX = (int) (x + width / 2.0 + Math.cos(angleRadians) * 30);
        int cursorY = (int) (y + height / 2.0 + Math.sin(angleRadians) * 30);

        g.setColor(Color.RED);
        g.fillRect(cursorX - 5, cursorY - 8, 14, 14);
    }
}
