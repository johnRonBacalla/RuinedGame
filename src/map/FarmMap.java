package map;

import core.Game;

import java.awt.*;

public class FarmMap extends Map{

    public FarmMap(int width, int height){
        super(width, height);
        extractMap("/mapText/farm.txt");
    }

    @Override
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
