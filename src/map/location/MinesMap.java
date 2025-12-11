package map.location;

import map.Map;

public class MinesMap extends Map {

    public MinesMap(int width, int height){
        super(16, 10);
        extractMap("/mapText/mines.txt");
    }
}
