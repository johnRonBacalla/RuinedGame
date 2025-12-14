package tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {

    private BufferedImage sprite;
    private boolean solid;

    public Tile(BufferedImage sprite, boolean solid) {
        this.sprite = sprite;
        this.solid = solid;
    }

    public BufferedImage getTile(){
        return sprite;
    }

    public boolean isSolid(){
        return solid;
    }

    public void render(Graphics2D g, int x, int y, int size) {
        g.drawImage(sprite, x, y, size, size, null);
    }

    // Add to Tile class
    public void setSolid(boolean solid) {
        this.solid = solid;
    }
}
