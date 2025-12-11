package map.location;

import map.Map;

public class FarmMap extends Map {

    public FarmMap(int width, int height){
        super(36, 15);
        extractMap("/mapText/farm.txt");
    }
}
