package map.location;

import map.Map;

public class MinesMap extends Map {

    public MinesMap(int width, int height){
        super(20, 18);
        extractMap("/mapText/mines.txt");
    }
}
