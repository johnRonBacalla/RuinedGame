package map.location;

import map.Map;

public class FHouseMap extends Map {

    public FHouseMap(int width, int height){
        super(16, 10);
        extractMap("/mapText/fhouse.txt");
    }
}
