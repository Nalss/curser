package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Gamepanel extends JComponent implements KeyListener, MouseMotionListener {

    private final Tiling tile;
    private final Player player;
    JFrame frame = new JFrame("Game");

    public Gamepanel() {
        player = new Player(180, 200, 20, 30, 5);
        tile = new Tiling();
    }

    public void openGUI() {
        // Open GUI
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 428); // Set the size of the window
        frame.setResizable(false); // Disable resizing
        frame.setFocusable(true); // Make the frame focusable
        frame.setLocationRelativeTo(null); // Centers the window on screen
        Gamepanel gamePanel = this;
        frame.add(gamePanel);
        frame.addKeyListener(gamePanel); // Add key listener for player movement
        gamePanel.addMouseMotionListener(gamePanel); // Track mouse movement
        frame.setVisible(true); // Ensures the frame is displayed

        // Start the game loop
        startGame();
    }

    // Game loop with tick speed
    public void startGame() {
        final int FPS = 60;
        final int TICK_RATE = 60;

        new Thread(() -> {
            long lastTime = System.nanoTime();
            long timer = 0;
            // int frames = 0;

            while (true) {
                long now = System.nanoTime();
                long elapsed = now - lastTime;
                lastTime = now;

                timer += elapsed;
                // waadwdframes++;

                // Fixed timestep for logic
                while (timer >= 1_000_000_000L / TICK_RATE) {
                    updateGame(); // Player movement, AI, etc.
                    timer -= 1_000_000_000L / TICK_RATE;
                }

                repaint();

                /*
                 * Show FPS in window title (optional)
                 * if (timer >= 1_000_000_000L) {
                 * System.out.println("FPS: " + frames);
                 * // frame.setTitle("Game - FPS: " + frames);
                 * frames = 0;
                 * timer = 0;
                 * }
                 */

                // Sleep to avoid 100% CPU
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ignored) {
                }
            }
        }, "GameLoop").start();
    }

    // Update game state
    public void updateGame() {
        player.update(getWidth(), getHeight());
    }

    // Paint the game components
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        tile.draw(g, getWidth(), getHeight());
        player.drawPlayer(g, getWidth(), getHeight());
        player.drawCursor(g);
    }

    // KeyListener methods
    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.keyReleased(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Null
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Update player with mouse position relative to this component
        player.setMousePosition(e.getX(), e.getY());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Treat dragged same as moved for cursor tracking
        player.setMousePosition(e.getX(), e.getY());
    }
}
