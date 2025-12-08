package map.location;

import map.Map;

public class BattleMap extends Map {

    public BattleMap(int width, int height) {
        super(20, 15);
        extractMap("/mapText/battle.txt");
    }
}
