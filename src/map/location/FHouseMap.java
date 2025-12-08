package map.location;

import map.Map;

public class FHouseMap extends Map {

    public FHouseMap(int width, int height){
        super(20, 18);
        extractMap("/mapText/fhouse.txt");
    }
}
