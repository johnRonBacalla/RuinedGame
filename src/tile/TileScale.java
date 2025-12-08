package tile;

import core.Game;

public class TileScale {

    public static int of(int tile){
        return tile * Game.SPRITE_SIZE;
    }
}
