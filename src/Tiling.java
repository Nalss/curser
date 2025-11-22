package src;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Tiling {

    private String tileMap = "";
    private char tile;

    public void draw(Graphics g, int panelWidth, int panelHeight) {

        int tileSize = Math.max(1, panelWidth / 50); // Size of each tile
        int cols = panelWidth / tileSize;
        int rows = panelHeight / tileSize;

        try {
            InputStream is = getClass().getResourceAsStream("/src/tilemap");
            if (is == null) {
                System.err.println("tilemap resource not found at /src/tilemap");
            } else {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

                    for (int row = 0; row < rows; row++) {
                        String line = reader.readLine();
                        if (line == null) {
                            break;
                        }
                        // remove spaces using String replace and append
                        line = line.replace(" ", "");
                        tileMap += line + "n"; // 'n' denotes new line
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int y = 0;
        int x = 0;

        for (int i = 0; i < tileMap.length(); i++) {
            tile = tileMap.charAt(i);
            switch (tile) {
                case 'n' -> {
                    x = 0;
                    y += tileSize;
                }
                case '1' -> {
                    g.setColor(Color.green);
                    g.fillRect(x, y, tileSize, tileSize);
                    x += tileSize;
                }
                case '2' -> {
                    g.setColor(Color.cyan);
                    g.fillRect(x, y, tileSize, tileSize);
                    x += tileSize;
                }
                case '3' -> {
                    g.setColor(Color.gray);
                    g.fillRect(x, y, tileSize, tileSize);
                    x += tileSize;
                }
                default -> {
                }
            }
        }
    }
}