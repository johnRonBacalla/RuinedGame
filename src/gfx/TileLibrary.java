package gfx;

import tile.Tile;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TileLibrary {

    private ArrayList<Tile> tiles;
    private BufferedImage[] grassDirt;
    private BufferedImage[] dirtGrass;

    public TileLibrary() {
        this.tiles = new ArrayList<>();

        this.grassDirt = CutSprite.cut(LoadSprite.load("/tiles/grassDirt.png"), 64, 64, 9, 0);
        for(BufferedImage img: grassDirt){
            tiles.add(new Tile(img, false));
        }

        this.dirtGrass = CutSprite.cut(LoadSprite.load("/tiles/dirtGrass.png"), 64, 64, 9,0);
        for(BufferedImage img: dirtGrass){
            tiles.add(new Tile(img, false));
        }
    }

    public Tile getTile(int id){
        if(id >= 0 && id < tiles.size()){
            return tiles.get(id);
        }
        return null;
    }
}
