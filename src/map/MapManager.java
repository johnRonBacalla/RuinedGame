package map;

import data.GameLoader;
import data.SaveManager;
import entity.GameObject;
import entity.stable.*;
import gfx.SpriteLibrary;
import map.location.BattleMap;
import map.location.FHouseMap;
import map.location.FarmMap;
import map.location.MinesMap;
import physics.box.Box;
import tile.Tile;
import tile.TileScale;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapManager {

    private final Map farm, battle, mines, house;
    private SpriteLibrary sprites;

    private Map currentMap;
    private Location currentLocation;
    private List<GameObject> currentMapObjects;
    private List<Box> currentMapBoxes;

    private List<GameObject> farmObjects;
    private List<GameObject> battleObjects;
    private List<GameObject> minesObjects;
    private List<GameObject> houseObjects;

    // Save File Data Loader
    private GameLoader.GameState savedFileObjects;


    public MapManager(SpriteLibrary sprites) {
        this.sprites = sprites;

        this.farm = new FarmMap(0,0);
        this.battle = new BattleMap(0,0);
        this.mines = new MinesMap(0,0);
        this.house = new FHouseMap(0,0);

        this.currentMapObjects = new ArrayList<>();
        this.currentMapBoxes = new ArrayList<>();

        //load map objects and boxes
        this.farmObjects = SpawnObjects.loadObjects("/mapText/objects/farmObjs.csv", sprites);

        this.battleObjects = SpawnObjects.loadObjects("/mapText/objects/battleObjs.csv", sprites);

        this.minesObjects = SpawnObjects.loadObjects("/mapText/objects/minesObjs.csv", sprites);

        this.houseObjects = SpawnObjects.loadObjects("/mapText/objects/houseObjs.csv", sprites);

        this.savedFileObjects = GameLoader.loadFromSave("res/saves/game_save.txt", sprites);

        loadFarmAssets();
        loadMinesAssets();
        loadBattleAssets();
        loadHouseAssets();

        // Ari mo load ang gikan sa saved file

        if(savedFileObjects != null){
            java.util.Map<String, List<GameObject>> loadedObjects =
                    GameLoader.loadPlacablesByLocation(savedFileObjects.sections.get("PLACABLES"), sprites);

            farmObjects.addAll(loadedObjects.get("FARM"));
            battleObjects.addAll(loadedObjects.get("BATTLE"));
            minesObjects.addAll(loadedObjects.get("MINES"));
            houseObjects.addAll(loadedObjects.get("HOUSE"));
        }


        //initial map
        currentMap = farm;
        currentLocation = Location.FARM;
        currentMapObjects.addAll(farmObjects);

        for (GameObject obj : currentMapObjects) {
            currentMapBoxes.add(obj.getBox());
        }
    }

    private void loadFarmAssets() {
        // Load Border Collisions
        Border topBorder =  new Border(TileScale.of(0), TileScale.of(0), sprites);
        topBorder.setCollision(0, -16, 36, 4);

        Border leftBorder = new Border(TileScale.of(0), TileScale.of(0), sprites);
        leftBorder.setCollision(0, 0, 3, 15);

        Border rightBorder = new Border(TileScale.of(36), TileScale.of(0), sprites);
        rightBorder.setCollision(0, 0, 1, 15);

        Border bottomBorder = new Border(TileScale.of(0), TileScale.of(15), sprites);
        bottomBorder.setCollision(0, 0, 36, 1);

        Border topRiver = new Border(TileScale.of(29), TileScale.of(4), sprites);
        topRiver.setCollision(0, -48, 4, 6);

        Border bottomRiver = new Border(TileScale.of(29), TileScale.of(11), sprites);
        bottomRiver.setCollision(0, 16, 4, 4);

        Gate minesGate = new Gate(TileScale.of(21), TileScale.of(3), sprites);
        minesGate.setEvent("toMines", 0, -15, 1, 1);

        Gate battleGate = new Gate(TileScale.of(36), TileScale.of(10), sprites);
        battleGate.setEvent("toBattle", -1, 0, 32, 64);

        Gate houseGate = new Gate(TileScale.of(15), TileScale.of(5), sprites);
        houseGate.setEvent("toHouse", 0, 15, 1, 1);

        BeanStalk beanStalk = new BeanStalk(TileScale.of(4), TileScale.of(-1), sprites);
        beanStalk.setCollision(0, TileScale.of(6), 3, 3);

        farmObjects.add(topBorder);
        farmObjects.add(leftBorder);
        farmObjects.add(rightBorder);
        farmObjects.add(bottomBorder);
        farmObjects.add(bottomRiver);
        farmObjects.add(topRiver);
        farmObjects.add(minesGate);
        farmObjects.add(beanStalk);
        farmObjects.add(battleGate);
        farmObjects.add(houseGate);
    }

    private void loadMinesAssets() {
        Gate fromMineToFarmGate = new Gate(TileScale.of(8), TileScale.of(9), sprites);
        fromMineToFarmGate.setEvent("fromMines", 0, 5, 1, 1);
        minesObjects.add(fromMineToFarmGate);

    }


    private void loadBattleAssets() {
        Gate fromBattleToFarmGate = new Gate(TileScale.of(0), TileScale.of(9), sprites);
        fromBattleToFarmGate.setEvent("fromBattle", 0, 0, 1, 1);
        battleObjects.add(fromBattleToFarmGate);

    }

    private void loadHouseAssets() {


        Border topBorder =  new Border(TileScale.of(4), TileScale.of(2), sprites);
        topBorder.setCollision(0, -25, 30, 1);

        Border leftBorder = new Border(TileScale.of(1), TileScale.of(0), sprites);
        leftBorder.setCollision(0, 0, 3, 15);

        Border rightBorder = new Border(TileScale.of(36), TileScale.of(0), sprites);
        rightBorder.setCollision(0, 0, 1, 15);

        Border bottomBorder = new Border(TileScale.of(0), TileScale.of(15), sprites);
        bottomBorder.setCollision(0, 0, 36, 1);

        Gate fromHouseToFarm = new Gate(TileScale.of(7), TileScale.of(9), sprites);
        fromHouseToFarm.setEvent("fromHouse", 0, 5, 2, 1);
        houseObjects.add(fromHouseToFarm);

        Gate save = new Gate(TileScale.of(6), TileScale.of(3), sprites);
        save.setEvent("saveGame", 0, 5, 2, 2);
        houseObjects.add(save);

        Bed playerBed = new Bed(TileScale.of(10), TileScale.of(3), sprites);
        houseObjects.add(playerBed);

        Bed emberBed = new Bed(TileScale.of(6), TileScale.of(3), sprites);
        houseObjects.add(emberBed);



        houseObjects.add(topBorder);
        houseObjects.add(leftBorder);
        houseObjects.add(rightBorder);
        houseObjects.add(bottomBorder);



    }

    public void addObject(Location location, GameObject obj) {
        switch (location) {
            case FARM -> farmObjects.add(obj);
            case BATTLE -> battleObjects.add(obj);
            case MINES -> minesObjects.add(obj);
            case HOUSE -> houseObjects.add(obj);
        }

        if (location == currentLocation) {
            currentMapObjects.add(obj);
            currentMapBoxes.add(obj.getBox());
        }
    }

    public void saveAllObjects(int waveNumber) {
        java.util.Map<String, List<String>> gameData = new HashMap<>();
        List<String> placables = new ArrayList<>();

        // CSV Header
        placables.add("entity_type,x,y,location");

        // Save objects from all locations
        addObjectsToSave(placables, farmObjects, Location.FARM);
        addObjectsToSave(placables, battleObjects, Location.BATTLE);
        addObjectsToSave(placables, minesObjects, Location.MINES);
        addObjectsToSave(placables, houseObjects, Location.HOUSE);

        gameData.put("PLACABLES", placables);
        SaveManager.saveGame(waveNumber, gameData);

        System.out.println("Saved " + (placables.size() - 1) + " objects across all locations");
    }

    private void addObjectsToSave(List<String> placables, List<GameObject> objects, Location location) {
        for (GameObject obj : objects) {
            if (obj instanceof Chest) {
                Chest chest = (Chest) obj;
                int tileX = TileScale.in(chest.getPosition().getX());
                int tileY = TileScale.in(chest.getPosition().getY());

                placables.add(String.format("chest,%d,%d,%s",
                        tileX, tileY, location.name()));
            }
            // TODO: Add more object types here (Beds, Towers, Seeds, etc.)
        }
    }

    public void changeMap(Location type) {
        currentLocation = type;
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

    public void render(Graphics2D g, Rectangle view) {
        int tileSize = 64;

        int startCol = Math.max(0, view.x / tileSize);
        int endCol = Math.min(getCurrentMap().getTileWidth(), (view.x + view.width) / tileSize + 1);
        int startRow = Math.max(0, view.y / tileSize);
        int endRow = Math.min(getCurrentMap().getTileHeight(), (view.y + view.height) / tileSize + 1);

        for (int row = startRow; row < endRow; row++) {
            for (int col = startCol; col < endCol; col++) {
                int tileNum = currentMap.getMapTiles()[col][row];
                Tile tile = currentMap.getTileLibrary().getTile(tileNum);
                if (tile != null) {
                    tile.render(g, col * tileSize, row * tileSize, tileSize);
                }
            }
        }
    }

    public List<GameObject> getCurrentMapObjects(){
        return currentMapObjects;
    }

    public List<Box> getCurrentMapBoxes(){
        return currentMapBoxes;
    }

    public Location getCurrentLocation(){
        return currentLocation;
    }

    public void changeCurrentObjects(Location type) {
        currentMapObjects.clear();
        currentMapBoxes.clear();

        switch (type){
            case FARM:
                currentMapObjects.addAll(farmObjects);
//                This is for loading the chest items
//                if (savedFileObjects != null) {
//                    System.out.println("Loading Chest ");
//
//                    currentMapObjects.addAll(savedFileObjects.placables);
//
//                    for (GameObject obj : savedFileObjects.placables) {
//                        if (obj instanceof Chest chest) {
//                            String chestId = chest.getId();
//                            if (savedFileObjects.chestItems.containsKey(chestId)) {
//                                chest.setItems(savedFileObjects.chestItems.get(chestId));
//                                System.out.println("Chest " + chestId + " loaded with items: " + chest.getItems());
//                            }
//                        }
//                    }
//                }
                for (GameObject obj : currentMapObjects) {
                    currentMapBoxes.add(obj.getBox());
                }
                break;
            case MINES:
                currentMapObjects.addAll(minesObjects);
                for (GameObject obj : currentMapObjects) {
                    currentMapBoxes.add(obj.getBox());
                }
                break;
            case BATTLE:
                currentMapObjects.addAll(battleObjects);
                for (GameObject obj : currentMapObjects) {
                    currentMapBoxes.add(obj.getBox());
                }
                break;
            case HOUSE:
                currentMapObjects.addAll(houseObjects);
                for (GameObject obj : currentMapObjects) {
                    currentMapBoxes.add(obj.getBox());
                }
                break;
        }
    }
}
