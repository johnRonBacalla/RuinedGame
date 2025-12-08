package state;

import display.camera.Camera;
import controller.PlayerController;
import core.Game;
import entity.GameObject;
import entity.moving.MovingEntity;
import entity.moving.Player;
import gfx.SpriteLibrary;
import input.KeyInput;
import map.MapManager;
import map.SpawnObjects;
import map.GridMap;
import map.Map;
import physics.box.Box;
import tile.TileScale;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PlayState extends State {

    private PlayerController controller;
    private MovingEntity player;
    private SpriteLibrary sprites;

    private MapManager mm;
    private Map currentMap;
    private Map debug;

    private Camera camera;
    private List<GameObject> gameObjects;  // All objects including player
    private List<Box> worldBoxes;          // All collision boxes except player sensor

    public PlayState(Game game, KeyInput input) {
        super(game, input);

        controller = new PlayerController(input);
        sprites = new SpriteLibrary();
        gameObjects = new ArrayList<>();
        worldBoxes = new ArrayList<>();

        // Player always separate first
        player = new Player(TileScale.of(8), TileScale.of(8), 5, sprites);
        gameObjects.add(player);

        // Map Manager + map
        mm = new MapManager();
        currentMap = mm.getCurrentMap();
        debug = new GridMap(26, 15);

        // Load map objects from CSV
        List<GameObject> mapObjects = SpawnObjects.loadObjects("/mapText/farmObjs.csv", sprites);
        gameObjects.addAll(mapObjects);

        // Add their collision boxes to worldBoxes
        for (GameObject obj : mapObjects) {
            worldBoxes.add(obj.getBox());
        }

        // Camera setup
        camera = new Camera(
                game.getWindowSize().getWidth(),
                game.getWindowSize().getHeight(),
                currentMap.getWidthInPx(),
                currentMap.getHeightInPx()
        );
    }

    @Override
    public void update() {
        // Update player input & motion
        player.getMotion().update(controller);
        player.applyMotion();

        // Update all objects
        for (GameObject obj : gameObjects) {
            if (obj instanceof Player p) {
                p.update(worldBoxes); // player checks collisions
            } else {
                obj.update(); // other objects normally
            }
        }

        // Maintain draw order
        sortObjectsByPosition();

        // Camera follows player
        camera.update(player);
    }

    private void sortObjectsByPosition() {
        gameObjects.sort(Comparator.comparingDouble(obj ->
                obj.getPosition().getY() + obj.getFrame().getHeight() / 1.5
        ));
    }

    @Override
    public void render(Graphics2D g) {
        camera.apply(g);

        currentMap.render(g);
        debug.render(g);

        for (GameObject obj : gameObjects) {
            obj.render(g);
        }

        // Render collision boxes (optional)
        for (GameObject obj : gameObjects) {
            switch (obj.getBox().getType()) {
                case "col" -> g.setColor(Color.RED);
                case "sensor" -> g.setColor(Color.YELLOW);
                case "event" -> g.setColor(Color.MAGENTA);
            }
            obj.renderBox(g);
        }

        camera.reset(g);
    }
}
