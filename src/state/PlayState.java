package state;

import display.camera.Camera;
import controller.PlayerController;
import core.Game;
import entity.GameObject;
import entity.moving.MovingEntity;
import entity.moving.Player;
import entity.moving.Skele;
import entity.placeable.EarthPlant;
import entity.placeable.FirePlant;
import entity.placeable.IcePlant;
import entity.placeable.WindPlant;
import entity.placeable.towers.EarthTower1;
import entity.placeable.towers.Tower;
import entity.placeable.towers.WindTower1;
import entity.stable.Bridge;
import entity.stable.Chest;
import gfx.SpriteLibrary;
import input.KeyInput;
import input.MouseInput;
import inventory.*;
import map.*;
import physics.Position;
import physics.box.Box;
import spawner.WaveSpawner;
import tile.Tile;
import tile.TileScale;
import ui.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PlayState extends State {
    private final PlayerController controller;
    private final MovingEntity player;
    private String currentSaveFileName;
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

    private List<UiComponent> hud;
    private Point mouseInMap;
    private UiText mouseTile;

    private WaveSpawner waveSpawner;
    private int currentWave = 1;
    private Boolean isWaveCleared = false;

    private PlacementManager placementManager;
    private String saveFilePath;
    public static int Day = 1;
    private UIDialogue dialogue = new UIDialogue(() -> {
        // Optional: do something when closed
    });


    // Constructor for NEW game
    public PlayState(Game game, SpriteLibrary spriteLibrary, KeyInput input, MouseInput mouseInput) {
        this(game, spriteLibrary, input, mouseInput, null); // Call the other constructor
    }

    // Constructor for LOADING a save
    public PlayState(Game game, SpriteLibrary spriteLibrary, KeyInput input, MouseInput mouseInput, String saveFilePath) {
        super(game, spriteLibrary, input, mouseInput);

        this.game = game;
        // Extract filename from path or create new save name
        if (saveFilePath != null) {
            File file = new File(saveFilePath);
            this.currentSaveFileName = file.getName().replace(".txt", "");
        } else {
            // Generate a unique save name for new games
            this.currentSaveFileName = "save_" + System.currentTimeMillis();
        }

        controller = new PlayerController(input);
        inventoryView = new ArrayList<>();
        hud = new ArrayList<>();

        worldObjects = new ArrayList<>();
        worldBoxes = new ArrayList<>();

        currentBox = new ArrayList<>();
        currentObject = new ArrayList<>();

        // Player always separate first
        player = new Player(this, TileScale.of(15), TileScale.of(8), 5, spriteLibrary);
        worldObjects.add(player);
        inventory = new InventoryManager(spriteLibrary, (Player) player);

        // Map Manager - pass the save file path
        mm = new MapManager(spriteLibrary, saveFilePath); // Modified constructor
        debug = new GridMap(36, 15);

        //fetch objects
        currentObject.addAll(mm.getCurrentMapObjects());
        currentBox.addAll(mm.getCurrentMapBoxes());

        currentMap = mm.getCurrentMap();
        worldObjects.addAll(currentObject);
        worldBoxes.addAll(currentBox);
        placementManager = new PlacementManager(spriteLibrary, mm);

        initializeInventory();
        initializeHud();

        // Camera setup
        camera = new Camera(
                game.getWindowSize().getWidth(),
                game.getWindowSize().getHeight(),
                currentMap.getWidthInPx(),
                currentMap.getHeightInPx()
        );

        mouseInMap = mm.getMouseTile(mouseInput, camera);

        inventoryOpen = false;
        inventory.printInventory();

        waveSpawner = new WaveSpawner(spriteLibrary);
    }

    private void initializeHud() {
        mouseTile = new UiText("test", new Position(100, 100), 36, false);
        hud.add(mouseTile);
    }

    private void initializeInventory() {
        UiButton item1 = new ItemButton(spriteLibrary, inventory,
                new Position(InventoryScale.of(1),
                        InventoryScale.of(2)), 1, () -> {

            if(inventory.getItemStack(1).getQuantity() != 0){
                System.out.println(inventory.getItemName(1));
                handleItemEquip(1);
            }
        });

        UiButton item2 = new ItemButton(spriteLibrary, inventory,
                new Position(InventoryScale.of(2),
                        InventoryScale.of(2)), 2, () -> {
            if(inventory.getItemStack(2).getQuantity() != 0){
                System.out.println(inventory.getItemName(2));
                handleItemEquip(2);
            }
        });
        UiButton item3 = new ItemButton(spriteLibrary, inventory,
                new Position(InventoryScale.of(3),
                        InventoryScale.of(2)), 3, () -> {
            if(inventory.getItemStack(3).getQuantity() != 0){
                System.out.println(inventory.getItemName(3));
                handleItemEquip(3);
            }
        });
        UiButton item4 = new ItemButton(spriteLibrary, inventory,
                new Position(InventoryScale.of(4),
                        InventoryScale.of(2)), 4, () -> {
            if(inventory.getItemStack(4).getQuantity() != 0){
                System.out.println(inventory.getItemName(4));
                handleItemEquip(4);
            }
        });

        UiButton item5 = new ItemButton(spriteLibrary, inventory,
                new Position(InventoryScale.of(1),
                        InventoryScale.of(3)), 5, () -> {
            if(inventory.getItemStack(5).getQuantity() != 0){
                System.out.println(inventory.getItemName(5));
                handleItemEquip(5);
            }
        });
        UiButton item6 = new ItemButton(spriteLibrary, inventory,
                new Position(InventoryScale.of(2),
                        InventoryScale.of(3)), 6, () -> {
            if(inventory.getItemStack(6).getQuantity() != 0){
                System.out.println(inventory.getItemName(6));
                handleItemEquip(6);
            }
        });
        UiButton item7 = new ItemButton(spriteLibrary, inventory,
                new Position(InventoryScale.of(3),
                        InventoryScale.of(3)), 7, () -> {
            if(inventory.getItemStack(7).getQuantity() != 0){
                System.out.println(inventory.getItemName(7));
                handleItemEquip(7);
            }
        });
        UiButton item8 = new ItemButton(spriteLibrary, inventory,
                new Position(InventoryScale.of(4),
                        InventoryScale.of(3)), 8, () -> {
            if(inventory.getItemStack(8).getQuantity() != 0){
                System.out.println(inventory.getItemName(8));
                handleItemEquip(8);
            }
        });

        UiButton item9 = new ItemButton(spriteLibrary, inventory,
                new Position(InventoryScale.of(1),
                        InventoryScale.of(4)), 9, () -> {
            if(inventory.getItemStack(9).getQuantity() != 0){
                System.out.println(inventory.getItemName(9));
                handleItemEquip(9);
            }
        });
        UiButton item10 = new ItemButton(spriteLibrary, inventory,
                new Position(InventoryScale.of(2),
                        InventoryScale.of(4)), 10, () -> {
            if(inventory.getItemStack(10).getQuantity() != 0){
                System.out.println(inventory.getItemName(10));
                handleItemEquip(10);
            }
        });
        UiButton item11 = new ItemButton(spriteLibrary, inventory,
                new Position(InventoryScale.of(3),
                        InventoryScale.of(4)), 11, () -> {
            if(inventory.getItemStack(11).getQuantity() != 0){
                System.out.println(inventory.getItemName(11));
                handleItemEquip(11);
            }
        });
        UiButton item12 = new ItemButton(spriteLibrary, inventory,
                new Position(InventoryScale.of(4),
                        InventoryScale.of(4)), 12, () -> {
            if(inventory.getItemStack(12).getQuantity() != 0){
                System.out.println(inventory.getItemName(12));
                handleItemEquip(12);
            }
        });

        UiButton item13 = new ItemButton(spriteLibrary, inventory,
                new Position(InventoryScale.of(1),
                        InventoryScale.of(5)), 13, () -> {
            if(inventory.getItemStack(13).getQuantity() != 0){
                System.out.println(inventory.getItemName(13));
                handleItemEquip(13);
            }
        });
        UiButton item14 = new ItemButton(spriteLibrary, inventory,
                new Position(InventoryScale.of(2),
                        InventoryScale.of(5)), 14, () -> {
            if(inventory.getItemStack(14).getQuantity() != 0){
                System.out.println(inventory.getItemName(14));
                handleItemEquip(14);
            }
        });
        UiButton item15 = new ItemButton(spriteLibrary, inventory,
                new Position(InventoryScale.of(3),
                        InventoryScale.of(5)), 15, () -> {
            if(inventory.getItemStack(15).getQuantity() != 0){
                System.out.println(inventory.getItemName(15));
                handleItemEquip(15);
            }
        });
        UiButton item16 = new ItemButton(spriteLibrary, inventory,
                new Position(InventoryScale.of(4),
                        InventoryScale.of(5)), 16, () -> {
            if(inventory.getItemStack(16).getQuantity() != 0){
                System.out.println(inventory.getItemName(16));
                handleItemEquip(16);
            }
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

    public void handleItemEquip(int itemId){
        if(inventory.getEquippedItem() != inventory.getItem(itemId)){
            inventory.setEquippedItem(itemId);
        } else inventory.removeEquippedItem();
    }

    public void reassignAll(Location location){
        // Filter out removed objects
        for (GameObject obj : mm.getCurrentMapObjects()) {
            if (!placementManager.wasRemoved(location, obj)) {
                currentObject.add(obj);
            }
        }

        for (GameObject obj : currentObject) {
            currentBox.add(obj.getBox());
        }

        worldObjects.add(player);
        worldBoxes.add(player.getBox());

        worldObjects.addAll(currentObject);
        worldBoxes.addAll(currentBox);
    }

    public void saveGame() {
        Day++;

        // ===== GROW PLANTS IN ALL LOCATION LISTS =====
        // Grow plants in MapManager's lists (the ones that get saved)
        mm.growAllPlants();

        // ===== SAVE AFTER GROWTH =====
        mm.saveAllObjects(currentSaveFileName, 1);
        System.out.println("Game saved to: " + currentSaveFileName);
    }

    public MapManager getMapManager() {
        return mm;
    }

    public boolean isWaveCleared() {
        return isWaveCleared;
    }

    private boolean lastPlacePressed = false;

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
    public void update() {
        Location currentLocation = mm.getCurrentLocation();
        mouseInMap = mm.getMouseTile(mouseInput, camera);
        dialogue.update(mouseInput.getMouseX(), mouseInput.getMouseY(), mouseInput.isLeftPressed());

        // ===== PLACEMENT AND REMOVAL SYSTEM =====
        // Only allow placement in FARM and BATTLE
        if (currentLocation == Location.FARM || currentLocation == Location.BATTLE) {

            // SHOVEL EQUIPPED
            if (inventory.getEquippedItem() instanceof WeaponItem &&
                    inventory.getEquippedItem().getId() == 4) {

                // R key = Remove placeable
                if (input.isPressed(KeyEvent.VK_R)) {
                    placementManager.removeObjectAt(
                            mouseInMap.x, mouseInMap.y, currentMap, currentLocation,
                            currentObject, worldObjects, currentBox, worldBoxes
                    );
                }

                // F key = Harvest
                if (input.isPressed(KeyEvent.VK_F)) {
                    System.out.println("harvest");
                }
            }
            // PLACEABLE ITEMS - Left click to place
            else if (inventory.getEquippedItem() instanceof PlaceableItem &&
                    controller.isRequestingPlaceItem() && !lastPlacePressed) {

                PlaceableItem placeable = (PlaceableItem) inventory.getEquippedItem();
                GameObject placedObject = null;

                switch (placeable.getId()) {
                    case 9: // Fire Rune I
                        placedObject = currentLocation == Location.FARM
                                ? placementManager.placeFirePlant(mouseInMap.x, mouseInMap.y, currentMap, currentLocation)
                                : placementManager.placeFireTower1(mouseInMap.x, mouseInMap.y, currentMap, currentLocation);
                        break;

                    case 10: // Ice Rune I
                        placedObject = currentLocation == Location.FARM
                                ? placementManager.placeIcePlant(mouseInMap.x, mouseInMap.y, currentMap, currentLocation)
                                : placementManager.placeIceTower1(mouseInMap.x, mouseInMap.y, currentMap, currentLocation);
                        break;

                    case 11: // Earth Rune I
                        placedObject = currentLocation == Location.FARM
                                ? placementManager.placeEarthPlant(mouseInMap.x, mouseInMap.y, currentMap, currentLocation)
                                : placementManager.placeEarthTower1(mouseInMap.x, mouseInMap.y, currentMap, currentLocation);
                        break;

                    case 12: // Wind Rune I
                        placedObject = currentLocation == Location.FARM
                                ? placementManager.placeWindPlant(mouseInMap.x, mouseInMap.y, currentMap, currentLocation)
                                : placementManager.placeWindTower1(mouseInMap.x, mouseInMap.y, currentMap, currentLocation);
                        break;

                    case 13: // Fire Rune II
                        placedObject = placementManager.placeFireTower2(mouseInMap.x, mouseInMap.y, currentMap, currentLocation);
                        break;

                    case 14: // Ice Rune II
                        placedObject = placementManager.placeIceTower2(mouseInMap.x, mouseInMap.y, currentMap, currentLocation);
                        break;

                    case 15: // Earth Rune II
                        placedObject = placementManager.placeEarthTower2(mouseInMap.x, mouseInMap.y, currentMap, currentLocation);
                        break;

                    case 16: // Wind Rune II
                        placedObject = placementManager.placeWindTower2(mouseInMap.x, mouseInMap.y, currentMap, currentLocation);
                        break;
                }

                // Add placed object to world
                if (placedObject != null) {
                    mm.addObject(currentLocation, placedObject);
                    currentObject.add(placedObject);
                    worldObjects.add(placedObject);
                    currentBox.add(placedObject.getBox());
                    worldBoxes.add(placedObject.getBox());

                    // Decrement quantity
                    ItemStack stack = inventory.getItemStack(placeable.getId());
                    if (stack != null && stack.getQuantity() > 0) {
                        stack.setQuantity(stack.getQuantity() - 1);
                        if (stack.getQuantity() == 0) {
                            inventory.removeEquippedItem();
                        }
                    }
                }

                lastPlacePressed = true;
            }
        }

        // ===== WAVE SPAWNING SYSTEM =====
        if (input.isPressed(KeyEvent.VK_ENTER) &&
                mm.getCurrentLocation() == Location.BATTLE &&
                !waveSpawner.isWaveActive()) {

            isWaveCleared = false; // Reset flag when new wave starts
            waveSpawner.startWave("/spawns/wave" + currentWave + ".txt");
        }

        List<MovingEntity> newSpawns = waveSpawner.update(1.0 / 60.0); // deltaTime
        for (MovingEntity entity : newSpawns) {
            worldObjects.add(entity);
            worldBoxes.add(entity.getBox());
        }

        if (!controller.isRequestingPlaceItem()) {
            lastPlacePressed = false;
        }

        // ===== INVENTORY TOGGLE =====
        if (input.isPressed(KeyEvent.VK_E)) {
            inventoryOpen = !inventoryOpen;
        }

        // ===== TOWER UPDATES =====
        // Update all towers with their specific behaviors
        for (GameObject obj : worldObjects) {
            if (obj instanceof Tower) {
                Tower tower = (Tower) obj;

                // Collect all enemies for tower targeting
                List<MovingEntity> enemies = new ArrayList<>();
                for (GameObject enemy : worldObjects) {
                    if (enemy instanceof MovingEntity && !(enemy instanceof Player)) {
                        enemies.add((MovingEntity) enemy);
                    }
                }

                // Update tower with enemy list (handles targeting and attacking)
                tower.updateTower(enemies);

                // === SPECIAL TOWER BEHAVIORS ===

                // Wind Tower (Support) - Buffs nearby towers
                if (tower instanceof WindTower1) {
                    WindTower1 windTower = (WindTower1) tower;
                    windTower.updateSupport(worldObjects);
                }

                // Earth Tower (Wall) - Add walls to collision and update wall damage
                if (tower instanceof EarthTower1) {
                    EarthTower1 earthTower = (EarthTower1) tower;

                    // Update walls with enemy detection for damage-over-time
                    earthTower.updateWithEnemies(worldObjects);

                    // Add wall collision boxes to worldBoxes so enemies collide with them
                    for (EarthTower1.Wall wall : earthTower.getActiveWalls()) {
                        if (!worldBoxes.contains(wall.getCollisionBox())) {
                            worldBoxes.add(wall.getCollisionBox());
                        }
                    }
                }
            }
        }

        // ===== PLAYER AND ENTITY UPDATES =====
        // Update player motion
        player.getMotion().update(controller);
        player.applyMotion();

        // Update all game objects
        for (GameObject obj : worldObjects) {
            if (obj instanceof Player p) {
                p.update(worldBoxes); // Player needs worldBoxes for collision
            } else if (obj instanceof Skele skele) {
                skele.update(worldBoxes); // Enemies need worldBoxes to detect walls
            } else {
                obj.update(); // Regular update for other objects
            }
        }

        // ===== TOWER RANGE TOGGLE (DEBUG) =====
        // Optional: Toggle tower range display with T key
        if (input.isPressed(KeyEvent.VK_T)) {
            for (GameObject obj : worldObjects) {
                if (obj instanceof Tower) {
                    Tower tower = (Tower) obj;
                    tower.setShowRange(!tower.isShowingRange());
                }
            }
        }

        // ===== WEAPON ATTACK SYSTEM =====
        // Handle player weapon attacks
        if (!inventoryOpen && input.isPressed(KeyEvent.VK_SPACE)) {
            if (inventory.getEquippedItem() instanceof WeaponItem) {
                WeaponItem weapon = (WeaponItem) inventory.getEquippedItem();
                weapon.attack();
            }
        }

        // Check weapon hitbox collisions with enemies
        if (inventory.getEquippedItem() instanceof WeaponItem) {
            WeaponItem weapon = (WeaponItem) inventory.getEquippedItem();

            if (weapon.getHitBox() != null && weapon.getHitBox().isActive()) {
                for (GameObject obj : worldObjects) {
                    // Check if object is a MovingEntity (enemy) and not the player
                    if (obj instanceof MovingEntity && !(obj instanceof Player)) {
                        MovingEntity enemy = (MovingEntity) obj;
                        enemy.checkHitByWeapon(weapon.getHitBox());
                    }
                }
            }
        }

        // ===== UI UPDATES =====
        // Update mouse tile display
        mouseTile.setText(String.valueOf(mouseInMap));

        // Update inventory UI
        if (inventoryOpen) {
            for (UiComponent component : inventoryView) {
                component.update();
                if (component instanceof UiButton button) {
                    button.update(mouseInput.getMouseX(), mouseInput.getMouseY(), mouseInput.isLeftPressed());
                }
            }
        }

        // Update HUD
        for (UiComponent component : hud) {
            component.update();
        }

        // Update equipped item
        if (inventory.getEquippedItem() != null) {
            inventory.getEquippedItem().update();
        }

        // ===== RENDERING PREP =====
        // Sort objects by Y position for proper depth rendering
        sortObjectsByPosition();

        // Update camera to follow player
        camera.update(player);

        // ===== CLEANUP DEAD ENEMIES =====
        // Remove dead enemies from the world
        List<GameObject> toRemove = new ArrayList<>();
        for (GameObject obj : worldObjects) {
            if (obj instanceof MovingEntity && !(obj instanceof Player)) {
                MovingEntity enemy = (MovingEntity) obj;
                if (enemy.isDead()) {
                    toRemove.add(obj);
                }
            }
        }
        worldObjects.removeAll(toRemove);
        worldBoxes.removeIf(box -> toRemove.stream().anyMatch(obj -> obj.getBox() == box));

        // Check if wave is cleared (no spawns left AND no enemies alive)
        if (mm.getCurrentLocation() == Location.BATTLE) {
            boolean anyEnemiesLeft = worldObjects.stream()
                    .anyMatch(obj -> obj instanceof MovingEntity && !(obj instanceof Player));

            // Wave is cleared when spawner has no more spawns AND no enemies are alive
            if (!waveSpawner.hasMoreSpawns() && !anyEnemiesLeft && !isWaveCleared) {
                isWaveCleared = true;
                System.out.println("Wave cleared! You can now rest.");
            }
        }
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

        for (GameObject obj : worldObjects) {
            Rectangle objRect = new Rectangle(
                    (int) obj.getPosition().getX(),
                    (int) obj.getPosition().getY(),
                    obj.getFrame().getWidth(),
                    obj.getFrame().getHeight()
            );

            if (view.intersects(objRect)) {
                obj.render(g);

                if (obj instanceof Player && inventory.getEquippedItem() instanceof WeaponItem) {
                    ((WeaponItem) inventory.getEquippedItem()).render(g);
                }
            }
        }

        Location currentLocation = mm.getCurrentLocation();

        // Only show previews in FARM and BATTLE
        if (!inventoryOpen && (currentLocation == Location.FARM || currentLocation == Location.BATTLE)) {
            // Green/Red preview for placeable items
            if (inventory.getEquippedItem() instanceof PlaceableItem) {
                placementManager.renderPlacementPreview(g, mouseInMap.x, mouseInMap.y, currentMap, currentLocation);
            }
            // Blue outline for shovel
            else if (inventory.getEquippedItem() instanceof WeaponItem && inventory.getEquippedItem().getId() == 4) {
                placementManager.renderShovelOutline(g, mouseInMap.x, mouseInMap.y, currentMap);
            }
        }

        // Render collision boxes (debug)
        for (GameObject obj : worldObjects) {
            Box box = obj.getBox();
            Rectangle boxRect = new Rectangle(
                    (int) box.getX(), (int) box.getY(),
                    (int) box.getWidth(), (int) box.getHeight()
            );

            if (view.intersects(boxRect)) {
                switch (box.getType()) {
                    case "col" -> g.setColor(Color.RED);
                    case "sensor" -> g.setColor(Color.YELLOW);
                    case "event" -> g.setColor(Color.ORANGE);
                    case "hit" -> g.setColor(Color.GREEN);
                }
                obj.renderBox(g);
            }
        }

        camera.reset(g);

        if(inventoryOpen){
            for(UiComponent components: inventoryView){
                components.render(g);
            }
        }

        for(UiComponent component: hud){
            component.render(g);
        }

        dialogue.render(g);
    }

    public void clearAll(){
        currentObject.clear();
        currentBox.clear();
        worldObjects.clear();
        worldBoxes.clear();
    }

    public void showDialogue(String message) {
        dialogue.show(message);
    }

    public void changeCurrentMap(Location type) {
        mm.changeMap(type);
        currentMap = mm.getCurrentMap();

        clearAll();
        switch (type){
            case MINES:
                mm.changeCurrentObjects(type);
                reassignAll(type);
                break;
            case BATTLE:
                mm.changeCurrentObjects(type);
                reassignAll(type);
                break;
            case FARM:
                mm.changeCurrentObjects(type);
                reassignAll(type);
                break;
            case HOUSE:
                mm.changeCurrentObjects(type);
                reassignAll(type);
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
