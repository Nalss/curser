package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Gamepanel extends JComponent implements KeyListener {

    private final Tiling tile;
    private final Player player;
    JFrame frame = new JFrame("Game");


    public Gamepanel() {
        player = new Player(180, 200, 20, 30, 5);
        tile = new Tiling();
    }

    public void openGUI() {
        //Open GUI
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 428); //Set the size of the window
            frame.setResizable(false); //Disable resizing
            frame.setFocusable(true); //Make the frame focusable
            frame.setLocationRelativeTo(null); //Centers the window on screen
            Gamepanel gamePanel = this;
            frame.add(gamePanel);
            frame.addKeyListener(gamePanel); //Add key listener for player movement
            frame.setVisible(true); //Ensures the frame is displayed


            //Start the game loop
            startGame();
    }

    // Game loop with tick speed
    public void startGame() {
        int tickSpeed = 1000 / 60; //60 FPS
        Timer timer = new Timer(tickSpeed, e -> {
            updateGame(); //Update game state
            repaint();   //Repaint the screen
        });
        timer.start();
    }

    //Update game state
    public void updateGame() {
        player.update(getWidth(), getHeight());
    }

    //Paint the game components
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        tile.draw(g, getWidth(), getHeight());
        player.drawPlayer(g, getWidth(), getHeight());
        player.drawCursor(g, frame.getContentPane());
    }

    //KeyListener methods
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
        //Null
    }
}
