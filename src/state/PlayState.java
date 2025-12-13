package state;

import data.GameLoader;
import display.camera.Camera;
import controller.PlayerController;
import core.Game;
import entity.Chest;
import entity.GameObject;
import entity.moving.MovingEntity;
import entity.moving.Player;
import entity.stable.Bridge;
import gfx.SpriteLibrary;
import input.KeyInput;
import input.MouseInput;
import map.*;
import physics.Size;
import physics.box.Box;
import tile.TileScale;
import ui.*;
import ui.buttons.ButtonUI;
import ui.buttons.InventoryUI;

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

    private final UI playUi;
    private final ButtonUI hotBar;
    private final InventoryUI inventory;

    private final List<GameObject> worldObjects;
    private final List<GameObject> currentObject;

    private final List<Box> worldBoxes;
    private final List<Box> currentBox;

    private boolean inventoryOpen;

    private Game game;

    public PlayState(Game game, KeyInput input, MouseInput mouseInput) {
        super(game, input, mouseInput);

        this.game = game;
        playUi = new PlayUI(input, mouseInput);
        controller = new PlayerController(input);
        sprites = new SpriteLibrary();

        worldObjects = new ArrayList<>();
        worldBoxes = new ArrayList<>();

        currentBox = new ArrayList<>();
        currentObject = new ArrayList<>();

        // Player always separate first
        player = new Player(this, TileScale.of(15), TileScale.of(8), 5, sprites);
        worldObjects.add(player);

        hotBar = new ButtonUI(sprites.getFrame("uiHotBar", 0), new Size(296, 560, 432, 48), () -> {
            System.out.println("clicked");
        });

        inventory = new InventoryUI(input, mouseInput);

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

        // Camera setup
        camera = new Camera(
                game.getWindowSize().getWidth(),
                game.getWindowSize().getHeight(),
                currentMap.getWidthInPx(),
                currentMap.getHeightInPx()
        );

        inventoryOpen = false;
        initializeUI();
    }

    private void initializeUI() {
        UIContainer container = new UIContainer();
        container.setMargin(new Spacing(10));
        container.setPadding(12);
        container.setSize(new Size(5, 5));
        uiContainers.add(container);

        UIText text = new UIText("123456789");
        container.add((text));
    }

    @Override
    public void update() {

        uiContainers.forEach(uiContainer -> uiContainer.update());
        if(input.isPressed(KeyEvent.VK_E)){
            inventoryOpen = !inventoryOpen;
            System.out.println("Inventory toggled");
        }

        if(!inventoryOpen){
            // Update player input & motion
            player.getMotion().update(controller);
            player.applyMotion();

            // Update all objects
            for (GameObject obj : worldObjects) {
                if (obj instanceof Player p) {
                    p.update(worldBoxes); // player checks collisions
                } else {
                    obj.update(); // other objects normally
                }
            }
        }

        // Maintain draw order
        sortObjectsByPosition();

        playUi.update();
        hotBar.update(mouseInput);
        if(inventoryOpen){
            inventory.update(mouseInput);
        }
        // Camera follows player
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

        uiContainers.forEach(uiContainer -> g.drawImage(
                uiContainer.getSprite(),
                uiContainer.getPosition().intX(),
                uiContainer.getPosition().intY(),
                null
        ));

        playUi.render(g);
        hotBar.render(g);
        if(inventoryOpen){
            inventory.render(g);
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
        }

        camera = new Camera(
                game.getWindowSize().getWidth(),
                game.getWindowSize().getHeight(),
                currentMap.getWidthInPx(),
                currentMap.getHeightInPx()
        );
    }
}
