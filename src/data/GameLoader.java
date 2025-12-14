package data;

import java.util.*;
import java.util.Map;

import entity.*;
import entity.stable.Chest;
import gfx.SpriteLibrary;
import tile.TileScale;


public class GameLoader {


    public static GameState loadFromSave(String savePath, SpriteLibrary sprites) {
        java.util.Map<String, Object> saveData = SaveManager.loadGame(savePath);

        if (saveData == null) {
            System.out.println("Failed to load save file");
            return null;
        }

        int wave = (int) saveData.get("wave");
        @SuppressWarnings("unchecked")
        java.util.Map<String, List<String>> sections = (java.util.Map<String, List<String>>) saveData.get("sections");

        GameState state = new GameState();
        state.waveNumber = wave;
        state.sections = sections;
        state.towers = loadTowers(sections.get("TOWERS"), sprites);
        // Don't call loadSeeds anymore, we'll handle it separately
        state.chestItems = loadChestItems(sections.get("CHEST_ITEMS"));
        state.playerInventory = loadPlayerInventory(sections.get("PLAYER_INVENTORY"));

        return state;
    }

    // Load placable objects
    // Change this method to organize by location
    public static Map<String, List<GameObject>> loadPlacablesByLocation(List<String> lines, SpriteLibrary sprites) {
        Map<String, List<GameObject>> objectsByLocation = new HashMap<>();
        objectsByLocation.put("FARM", new ArrayList<>());
        objectsByLocation.put("BATTLE", new ArrayList<>());
        objectsByLocation.put("MINES", new ArrayList<>());
        objectsByLocation.put("HOUSE", new ArrayList<>());

        if (lines == null || lines.isEmpty()) {
            return objectsByLocation;
        }

        for (int i = 1; i < lines.size(); i++) {
            String[] parts = SaveManager.parseLine(lines.get(i));
            String type = parts[0];
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            String location = parts[3];

            switch (type) {
                case "chest" -> {
                    GameObject chest = new Chest(TileScale.of(x), TileScale.of(y), sprites);
                    objectsByLocation.get(location).add(chest);
                }
                default -> System.out.println("Unknown placable type: " + type);
            }
        }

        return objectsByLocation;
    }

    // Load towers
    public static List<GameObject> loadTowers(List<String> lines, SpriteLibrary sprites) {
        List<GameObject> towers = new ArrayList<>();

        if (lines == null || lines.isEmpty()) {
            return towers;
        }

        // Skip header line
        for (int i = 1; i < lines.size(); i++) {
            String[] parts = SaveManager.parseLine(lines.get(i));
            String towerType = parts[0];
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int tier = Integer.parseInt(parts[3]);

            switch (towerType) {
                case "arrow" -> {
                    // GameObject tower = new ArrowTower(TileScale.of(x), TileScale.of(y), tier, sprites);
                    // towers.add(tower);
                    System.out.println("TODO: Create arrow tower at (" + x + "," + y + ") tier " + tier);
                }
                case "cannon" -> {
                    // GameObject tower = new CannonTower(TileScale.of(x), TileScale.of(y), tier, sprites);
                    // towers.add(tower);
                    System.out.println("TODO: Create cannon tower at (" + x + "," + y + ") tier " + tier);
                }
                default -> System.out.println("Unknown tower type: " + towerType);
            }
        }

        return towers;
    }

    // Load seeds
    // Load seeds
    public static Map<String, List<GameObject>> loadSeedsByLocation(List<String> lines, SpriteLibrary sprites) {
        Map<String, List<GameObject>> seedsByLocation = new HashMap<>();
        seedsByLocation.put("FARM", new ArrayList<>());
        seedsByLocation.put("BATTLE", new ArrayList<>());
        seedsByLocation.put("MINES", new ArrayList<>());
        seedsByLocation.put("HOUSE", new ArrayList<>());

        if (lines == null || lines.isEmpty()) {
            return seedsByLocation;
        }

        // Skip header line (plant_type,x,y,location,stage)
        for (int i = 1; i < lines.size(); i++) {
            String[] parts = SaveManager.parseLine(lines.get(i));
            String plantType = parts[0];  // fire, ice, earth, wind
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            String location = parts[3];  // FARM, BATTLE, etc.
            int stage = Integer.parseInt(parts[4]);  // growth stage

            GameObject plant = null;

            switch (plantType) {
                case "fire" -> {
                    plant = new entity.placeable.FirePlant(TileScale.of(x), TileScale.of(y), stage, sprites);
                }
                case "ice" -> {
                    plant = new entity.placeable.IcePlant(TileScale.of(x), TileScale.of(y), stage, sprites);
                }
                case "earth" -> {
                    plant = new entity.placeable.EarthPlant(TileScale.of(x), TileScale.of(y), stage, sprites);
                }
                case "wind" -> {
                    plant = new entity.placeable.WindPlant(TileScale.of(x), TileScale.of(y), stage, sprites);
                }
                default -> System.out.println("Unknown plant type: " + plantType);
            }

            if (plant != null) {
                seedsByLocation.get(location).add(plant);
            }
        }

        return seedsByLocation;
    }

    // Load chest items (returns map of chest ID -> list of items)
    public static java.util.Map<String, List<String>> loadChestItems(List<String> lines) {
        java.util.Map<String, List<String>> chestItemsMap = new HashMap<>();

        if (lines == null || lines.isEmpty()) {
            return chestItemsMap;
        }

        // Skip header line
        for (int i = 1; i < lines.size(); i++) {
            String[] parts = SaveManager.parseLine(lines.get(i));
            String chestId = parts[0];

            // Collect all items (skip the first part which is chest ID)
            List<String> items = new ArrayList<>();
            for (int j = 1; j < parts.length; j++) {
                if (!parts[j].isEmpty()) {
                    items.add(parts[j]);
                }
            }

            chestItemsMap.put(chestId, items);
        }

        return chestItemsMap;
    }

    // Load player inventory (returns list of inventory items with positions)
    public static List<InventoryItem> loadPlayerInventory(List<String> lines) {
        List<InventoryItem> inventory = new ArrayList<>();

        if (lines == null || lines.isEmpty()) {
            return inventory;
        }

        // Skip header line
        for (int i = 1; i < lines.size(); i++) {
            String[] parts = SaveManager.parseLine(lines.get(i));
            String itemType = parts[0];
            int row = Integer.parseInt(parts[1]);
            int col = Integer.parseInt(parts[2]);

            inventory.add(new InventoryItem(itemType, row, col));
        }

        return inventory;
    }

    // Container for all game data
    public static class GameState {
        public int waveNumber;
        public Map<String, List<String>> sections;
        public List<GameObject> placables = new ArrayList<>();
        public List<GameObject> towers = new ArrayList<>();
        public List<GameObject> seeds = new ArrayList<>();
        public Map<String, List<String>> chestItems = new HashMap<>();
        public List<InventoryItem> playerInventory = new ArrayList<>();

        public void print() {
            System.out.println("=== GAME STATE ===");
            System.out.println("Wave: " + waveNumber);
            System.out.println("Placables: " + placables.size());
            System.out.println("Towers: " + towers.size());
            System.out.println("Seeds: " + seeds.size());
            System.out.println("Chests with items: " + chestItems.size());
            System.out.println("Inventory items: " + playerInventory.size());
        }

        public void addTower(String type, Integer x, Integer y ) {

        }
    }

    // Inventory Container
    public static class InventoryItem {
        public String itemType;
        public int row;
        public int col;

        public InventoryItem(String itemType, int row, int col) {
            this.itemType = itemType;
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            return itemType + " at [" + row + "," + col + "]";
        }
    }
}