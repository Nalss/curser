package src;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Tiling {
    private final java.util.List<String> mapLines = new java.util.ArrayList<>();

    public Tiling() {
        loadMap();
    }

    // Load the tilemap once from resources into memory
    private void loadMap() {
        try (InputStream is = getClass().getResourceAsStream("/src/tilemap")) {
            if (is == null) {
                System.err.println("tilemap resource not found at /src/tilemap");
                return;
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // remove spaces and store the cleaned line
                    mapLines.add(line.replace(" ", ""));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g, int panelWidth, int panelHeight) {
        if (mapLines.isEmpty())
            return;

        int tileSize = Math.max(1, panelWidth / 50); // Size of each tile

        int y = 0;
        for (String line : mapLines) {
            int x = 0;
            for (int i = 0; i < line.length(); i++) {
                char tile = line.charAt(i);
                switch (tile) {
                    case '1' -> {
                        g.setColor(Color.green);
                        g.fillRect(x, y, tileSize, tileSize);
                    }
                    case '2' -> {
                        g.setColor(Color.cyan);
                        g.fillRect(x, y, tileSize, tileSize);
                    }
                    case '3' -> {
                        g.setColor(Color.gray);
                        g.fillRect(x, y, tileSize, tileSize);
                    }
                    default -> {
                    }
                }
                x += tileSize;
            }
            y += tileSize;
        }
    }
}