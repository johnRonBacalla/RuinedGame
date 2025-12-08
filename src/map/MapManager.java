package map;

import map.location.BattleMap;
import map.location.FHouseMap;
import map.location.FarmMap;
import map.location.MinesMap;

public class MapManager {

    private Map farm, battle, mines, house;
    private Map currentMap;

    public MapManager() {
        this.farm = new FarmMap(0,0);
        this.battle = new BattleMap(0, 0);
        this.mines = new MinesMap(0,0);
        this.house = new FHouseMap(0, 0);
        this.currentMap = farm;
    }

    public void changeMap(Location type) {
        switch(type) {
            case BATTLE -> currentMap = battle;
            case FARM   -> currentMap = farm;
            case MINES  -> currentMap = mines;
            case HOUSE  -> currentMap = house;
        }
    }

    public Map getCurrentMap() {
        return currentMap;
    }
}
