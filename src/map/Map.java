package map;

import core.Game;
import physics.Size;
import gfx.TileLibrary;
import tile.TileScale;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class Map {

    protected int widthInPx;
    protected int heightInPx;
    protected int tileWidth;
    protected int tileHeight;
    protected int cellSize;
    protected Size size;
    protected TileLibrary tl;
    protected int[][] mapTiles;

    public Map(int width, int height){
        this.widthInPx = TileScale.of(width);
        this.heightInPx = TileScale.of(height);
        this.tileWidth = width;
        this.tileHeight = height;
        this.cellSize = Game.SPRITE_SIZE;
        size = new Size(width, height);
        tl = new TileLibrary();
        this.mapTiles = new int[tileWidth][tileHeight];
    }

    public void extractMap(String path) {
        try (InputStream is = getClass().getResourceAsStream(path);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            for (int row = 0; row < tileHeight; row++) {
                String line = br.readLine();
                if (line == null) break; // no more lines → stop

                String[] numbers = line.trim().split("\\s+"); // handles multiple spaces

                for (int col = 0; col < tileWidth; col++) {
                    mapTiles[col][row] = Integer.parseInt(numbers[col]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // print real error
        }
    }

    public int getWidthInPx() {
        return widthInPx;
    }

    public int getHeightInPx() {
        return heightInPx;
    }

    public void render(Graphics2D g) {
        for (int row = 0; row < tileHeight; row++) {
            for (int col = 0; col < tileWidth; col++) {

                int tileNum = mapTiles[col][row]; // <-- update per tile
                int x = col * Game.SPRITE_SIZE;
                int y = row * Game.SPRITE_SIZE;

                g.drawImage(tl.getTile(tileNum).getTile(), x, y, null);
            }
        }
    }
}
