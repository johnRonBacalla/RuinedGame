package map.location;

import map.Map;

public class FarmMap extends Map {

    public FarmMap(int width, int height){
        super(26, 15);
        extractMap("/mapText/farm.txt");
    }
}
