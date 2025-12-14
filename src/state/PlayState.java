package state;

import display.camera.Camera;
import controller.PlayerController;
import core.Game;
import entity.GameObject;
import entity.moving.MovingEntity;
import entity.moving.Player;
import entity.stable.Bridge;
import entity.stable.Chest;
import gfx.SpriteLibrary;
import input.KeyInput;
import input.MouseInput;
import inventory.InventoryManager;
import inventory.InventoryScale;
import map.*;
import physics.Position;
import physics.box.Box;
import tile.TileScale;
import ui.ItemButton;
import ui.UiButton;
import ui.UiComponent;
import ui.UiText;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class PlayState extends State {

    private final PlayerController controller;
    private final MovingEntity player;
    private final SpriteLibrary sprites;

    private final MapManager mm;
    private Map currentMap;
    private final Map debug;

    private Camera camera;

    private final List<GameObject> worldObjects;
    private final List<GameObject> currentObject;

    private final List<Box> worldBoxes;
    private final List<Box> currentBox;

    private boolean inventoryOpen;
    private List<UiComponent> inventoryView;

    private Game game;

    private InventoryManager inventory;

    public PlayState(Game game, KeyInput input, MouseInput mouseInput) {
        super(game, input, mouseInput);

        this.game = game;
        controller = new PlayerController(input);
        sprites = new SpriteLibrary();
        inventory = new InventoryManager(sprites);
        inventoryView = new ArrayList<>();

        worldObjects = new ArrayList<>();
        worldBoxes = new ArrayList<>();

        currentBox = new ArrayList<>();
        currentObject = new ArrayList<>();

        // Player always separate first
        player = new Player(this, TileScale.of(15), TileScale.of(8), 5, sprites);
        worldObjects.add(player);

        // Map Manager + debug map
        mm = new MapManager(sprites);
        debug = new GridMap(36, 15);

        //fetch objects
        currentObject.addAll(mm.getCurrentMapObjects());
        currentBox.addAll(mm.getCurrentMapBoxes());

        currentMap = mm.getCurrentMap();
        worldObjects.addAll(currentObject);
        worldBoxes.addAll(currentBox);
//        debug = new GridMap(26, 15);

//        GameLoader.GameState state = GameLoader.loadFromSave("res/saves/game_save.txt", sprites);
//
//        if (state != null) {
//            System.out.println("Loading Chest ");
//
//            worldObjects.addAll(state.placables);
//
//            for (GameObject obj : state.placables) {
//                if (obj instanceof Chest chest) {
//                    String chestId = chest.getId();
//                    if (state.chestItems.containsKey(chestId)) {
//                        chest.setItems(state.chestItems.get(chestId));
//                        System.out.println("Chest " + chestId + " loaded with items: " + chest.getItems());
//                    }
//                }
//            }
//        }

        // Load map objects from CSV
//        List<GameObject> mapObjects = SpawnObjects.loadObjects("/mapText/farmObjs.csv", sprites);
//        worldObjects.addAll(mapObjects);

        // Add their collision boxes to worldBoxes
//        for (GameObject obj : mapObjects) {
//            worldBoxes.add(obj.getBox());
//        }
        initializeInventory();

        // Camera setup
        camera = new Camera(
                game.getWindowSize().getWidth(),
                game.getWindowSize().getHeight(),
                currentMap.getWidthInPx(),
                currentMap.getHeightInPx()
        );

        inventoryOpen = true;
        inventory.printInventory();
    }

    private void initializeInventory() {
        UiButton item1 = new ItemButton(sprites, inventory,
                new Position(InventoryScale.of(1),
                        InventoryScale.of(2)), 1, () -> {
            System.out.println(inventory.getItemName(1));
        });

        UiButton item2 = new ItemButton(sprites, inventory,
                new Position(InventoryScale.of(2),
                        InventoryScale.of(2)), 2, () -> {
            System.out.println(inventory.getItemName(2));
        });
        UiButton item3 = new ItemButton(sprites, inventory,
                new Position(InventoryScale.of(3),
                        InventoryScale.of(2)), 3, () -> {
            System.out.println(inventory.getItemName(3));
        });
        UiButton item4 = new ItemButton(sprites, inventory,
                new Position(InventoryScale.of(4),
                        InventoryScale.of(2)), 4, () -> {
            System.out.println(inventory.getItemName(4));
        });

        UiButton item5 = new ItemButton(sprites, inventory,
                new Position(InventoryScale.of(1),
                        InventoryScale.of(3)), 5, () -> {
            System.out.println(inventory.getItemName(5));
        });
        UiButton item6 = new ItemButton(sprites, inventory,
                new Position(InventoryScale.of(2),
                        InventoryScale.of(3)), 6, () -> {
            System.out.println(inventory.getItemName(6));
        });
        UiButton item7 = new ItemButton(sprites, inventory,
                new Position(InventoryScale.of(3),
                        InventoryScale.of(3)), 7, () -> {
            System.out.println(inventory.getItemName(7));
        });
        UiButton item8 = new ItemButton(sprites, inventory,
                new Position(InventoryScale.of(4),
                        InventoryScale.of(3)), 8, () -> {
            System.out.println(inventory.getItemName(8));
        });

        UiButton item9 = new ItemButton(sprites, inventory,
                new Position(InventoryScale.of(1),
                        InventoryScale.of(4)), 9, () -> {
            System.out.println(inventory.getItemName(9));
        });
        UiButton item10 = new ItemButton(sprites, inventory,
                new Position(InventoryScale.of(2),
                        InventoryScale.of(4)), 10, () -> {
            System.out.println(inventory.getItemName(10));
        });
        UiButton item11 = new ItemButton(sprites, inventory,
                new Position(InventoryScale.of(3),
                        InventoryScale.of(4)), 11, () -> {
            System.out.println(inventory.getItemName(11));
        });
        UiButton item12 = new ItemButton(sprites, inventory,
                new Position(InventoryScale.of(4),
                        InventoryScale.of(4)), 12, () -> {
            System.out.println(inventory.getItemName(12));
        });

        UiButton item13 = new ItemButton(sprites, inventory,
                new Position(InventoryScale.of(1),
                        InventoryScale.of(5)), 13, () -> {
            System.out.println(inventory.getItemName(13));
        });
        UiButton item14 = new ItemButton(sprites, inventory,
                new Position(InventoryScale.of(2),
                        InventoryScale.of(5)), 14, () -> {
            System.out.println(inventory.getItemName(14));
        });
        UiButton item15 = new ItemButton(sprites, inventory,
                new Position(InventoryScale.of(3),
                        InventoryScale.of(5)), 15, () -> {
            System.out.println(inventory.getItemName(15));
        });
        UiButton item16 = new ItemButton(sprites, inventory,
                new Position(InventoryScale.of(4),
                        InventoryScale.of(5)), 16, () -> {
            System.out.println(inventory.getItemName(16));
        });

        inventoryView.add(item1);
        inventoryView.add(item2);
        inventoryView.add(item3);
        inventoryView.add(item4);
        inventoryView.add(item5);
        inventoryView.add(item6);
        inventoryView.add(item7);
        inventoryView.add(item8);
        inventoryView.add(item9);
        inventoryView.add(item10);
        inventoryView.add(item11);
        inventoryView.add(item12);
        inventoryView.add(item13);
        inventoryView.add(item14);
        inventoryView.add(item15);
        inventoryView.add(item16);
    }

    public void placeChest(int tileX, int tileY) {
        Chest chest = new Chest(TileScale.of(tileX), TileScale.of(tileY), sprites);

        // Add to MapManager (persists across map changes)
        mm.addObject(mm.getCurrentLocation(), chest);

        currentObject.add(chest);
        worldObjects.add(chest);

        currentBox.add(chest.getBox());
        worldBoxes.add(chest.getBox());

        System.out.println("Placed chest at (" + tileX + ", " + tileY + ")");

    }

    public void saveGame() {
        mm.saveAllObjects(1); // Pass wave number
        System.out.println("Game saved!");
    }

    public MapManager getMapManager() {
        return mm;
    }
    private boolean lastPlacePressed = false;

    @Override
    public void update() {
        if (controller.isRequestingPlaceItem() && !lastPlacePressed) {
            int playerTileX = TileScale.in(player.getPosition().getX());
            int playerTileY = TileScale.in(player.getPosition().getY());
            placeChest(playerTileX, playerTileY);
            lastPlacePressed = true;


        }

        if (!controller.isRequestingPlaceItem()) {
            lastPlacePressed = false;
        }
        if (input.isPressed(KeyEvent.VK_E)) {
            inventoryOpen = !inventoryOpen;
        }

        player.getMotion().update(controller);
        player.applyMotion();
        for (GameObject obj : worldObjects) {
            if (obj instanceof Player p) p.update(worldBoxes);
            else obj.update();
        }

        if (inventoryOpen) {
            for (UiComponent component : inventoryView) {

                // Call default update first (safe for all)
                component.update();

                // Only buttons receive mouse input
                if (component instanceof UiButton button) {
                    button.update(
                            mouseInput.mouseX,
                            mouseInput.mouseY,
                            mouseInput.isLeftPressed()
                    );
                }
            }
        }

        sortObjectsByPosition();
        camera.update(player);
    }

    private void sortObjectsByPosition() {
        worldObjects.sort((a, b) -> {
            boolean aBridge = a instanceof Bridge;
            boolean bBridge = b instanceof Bridge;

            // Bridges always come first
            if (aBridge && !bBridge) return -1;
            if (!aBridge && bBridge) return 1;

            // Otherwise sort by Y depth
            double ay = a.getPosition().getY() + a.getFrame().getHeight() / 1.5;
            double by = b.getPosition().getY() + b.getFrame().getHeight() / 1.5;

            return Double.compare(ay, by);
        });
    }

    @Override
    public void render(Graphics2D g) {
        camera.apply(g);

        // --- Frustum culling: only render objects inside camera view ---
        Rectangle view = new Rectangle(
                (int) camera.getX(),
                (int) camera.getY(),
                camera.getWidth(),
                camera.getHeight()
        );

        mm.render(g, view);

        debug.render(g);

        for (GameObject obj : worldObjects) {
            Rectangle objRect = new Rectangle(
                    (int) obj.getPosition().getX(),
                    (int) obj.getPosition().getY(),
                    obj.getFrame().getWidth(),
                    obj.getFrame().getHeight()
            );

            if (view.intersects(objRect)) {
                obj.render(g);
            }
        }

        // Render collision boxes (optional)
        for (GameObject obj : worldObjects) {
            Box box = obj.getBox();
            Rectangle boxRect = new Rectangle(
                    (int) box.getX(),
                    (int) box.getY(),
                    (int) box.getWidth(),
                    (int) box.getHeight()
            );

            if (view.intersects(boxRect)) {
                switch (box.getType()) {
                    case "col" -> g.setColor(Color.RED);
                    case "sensor" -> g.setColor(Color.YELLOW);
                    case "event" -> g.setColor(Color.ORANGE);
                }
                obj.renderBox(g);
            }
        }

        camera.reset(g);

        if(inventoryOpen){
        }
    }

    public void clearAll(){
        currentObject.clear();
        currentBox.clear();
        worldObjects.clear();
        worldBoxes.clear();
    }

    public void reassignAll(){
        currentObject.addAll(mm.getCurrentMapObjects());
        currentBox.addAll(mm.getCurrentMapBoxes());

        worldObjects.add(player);
        worldBoxes.add(player.getBox());

        worldObjects.addAll(currentObject);
        worldBoxes.addAll(currentBox);
    }

    public void changeCurrentMap(Location type) {
        mm.changeMap(type);
        currentMap = mm.getCurrentMap();

        clearAll();
        switch (type){
            case MINES:
                mm.changeCurrentObjects(type);
                reassignAll();
                break;
            case BATTLE:
                mm.changeCurrentObjects(type);
                reassignAll();
                break;
            case FARM:
                mm.changeCurrentObjects(type);
                reassignAll();
                break;
            case HOUSE:
                mm.changeCurrentObjects(type);
                reassignAll();
                break;
        }

        camera = new Camera(
                game.getWindowSize().getWidth(),
                game.getWindowSize().getHeight(),
                currentMap.getWidthInPx(),
                currentMap.getHeightInPx()
        );
    }
}
