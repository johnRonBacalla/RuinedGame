package tile;

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
}
