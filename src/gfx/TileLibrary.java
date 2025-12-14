package gfx;

import tile.Tile;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TileLibrary {

    private final ArrayList<Tile> tiles;
    private  final BufferedImage[] grassDirt;
    private  final BufferedImage[] dirtGrass;
    private  final BufferedImage[] wallWater;
    private  final BufferedImage[] battle1;
    private  final BufferedImage[] battle2;
    private  final BufferedImage[] house;
    private  final BufferedImage[] mines;
    private  final BufferedImage[] plantTile;

    public TileLibrary() {
        this.tiles = new ArrayList<>();

        this.grassDirt = CutSprite.cut(LoadSprite.load("/tiles/1grassPath.png"), 64, 64, 11, 0);
        for(BufferedImage img: grassDirt){
            tiles.add(new Tile(img, false));
        }

        this.dirtGrass = CutSprite.cut(LoadSprite.load("/tiles/2pathGrass.png"), 64, 64, 9,0);
        for(BufferedImage img: dirtGrass){
            tiles.add(new Tile(img, false));
        }

        this.wallWater = CutSprite.cut(LoadSprite.load("/tiles/3wall.png"), 64, 64, 41, 0);
        for(BufferedImage img: wallWater){
            tiles.add(new Tile(img, false));
        }

        this.battle1 = CutSprite.cut(LoadSprite.load("/tiles/4.1battle.png"), 64, 64, 3, 0);
        for(BufferedImage img: battle1){
            tiles.add(new Tile(img, false));
        }

        this.battle2 = CutSprite.cut(LoadSprite.load("/tiles/4.2battle.png"), 64, 64, 2, 0);
        for(BufferedImage img: battle2){
            tiles.add(new Tile(img, true));
        }

        this.house = CutSprite.cut(LoadSprite.load("/tiles/5houseTiles.png"), 64, 64, 20, 0);
        for(BufferedImage img: house){
            tiles.add(new Tile(img, false));
        }

        this.mines = CutSprite.cut(LoadSprite.load("/tiles/6mines.png"), 64, 64, 6, 0);
        for(BufferedImage img: mines){
            tiles.add(new Tile(img, false));
        }

        this.plantTile = CutSprite.cut(LoadSprite.load("/tiles/7plantile.png"), 64, 64, 9, 0);
        for(BufferedImage img: plantTile){
            tiles.add(new Tile(img, true));
        }
    }

    public Tile getTile(int id){
        if(id >= 0 && id < tiles.size()){
            return tiles.get(id);
        }
        return null;
    }
}
