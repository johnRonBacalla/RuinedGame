package state;

import camera.Camera;
import controller.PlayerController;
import core.Game;
import entity.Ember;
import entity.GameObject;
import entity.House;
import entity.moving.MovingEntity;
import entity.moving.Player;
import gfx.SpriteLibrary;
import gfx.TileLibrary;
import input.KeyInput;
import map.FarmMap;
import map.Map;
import physics.Collider;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PlayState extends State{

    private PlayerController controller;
    private MovingEntity player;
    private SpriteLibrary sprites;
    private int direction = 0;
    private Map map;
    private TileLibrary tl;
    private Camera camera;
    private GameObject ember;
    private GameObject house;
    private List<GameObject> gameObjects;

    public PlayState(Game game, KeyInput input) {
        super(game, input);
        controller = new PlayerController(input);
        this.sprites = new SpriteLibrary();
        this.gameObjects = new ArrayList<>();
        gameObjects.add(this.player = new Player(0, 0, 5, sprites));
        gameObjects.add(this.ember = new Ember(100, 100, sprites));
        gameObjects.add(this.house = new House(320, 0, sprites));
        this.map = new FarmMap(32, 20);
        this.camera = new Camera(
                game.getWindowSize().getWidth(),
                game.getWindowSize().getHeight(),
                map.getWidthInPx(),
                map.getHeightInPx());
    }

    @Override
    public void update() {
        sortObjectsByPosition();
        player.getMotion().update(controller);
        player.applyMotion();
        gameObjects.forEach(gameObject -> gameObject.update());

        checkCollisions();

        camera.focusOn(
                (float) player.getPosition().getX() + player.getFrame().getWidth() / 2f,
                (float) player.getPosition().getY() + player.getFrame().getHeight() / 2f);
        handleAnimation();
    }

    private void sortObjectsByPosition() {
        gameObjects.sort(Comparator.comparingDouble(gameObject ->
                gameObject.getPosition().getY() + gameObject.getFrame().getHeight() / 1.5
        ));
    }

    private void handleAnimation() {
        //direction val#: 0 right, 1 left;
        if(player.getMotion().isMovingLeft()){
            direction = 1;
        } else if(player.getMotion().isMovingRight()){
            direction = 0;
        }

        if(player.getMotion().isMoving()){
            if(player.getMotion().isMovingRight()) player.changeCurrentAnimation("walkR");
            if(player.getMotion().isMovingLeft()) player.changeCurrentAnimation("walkL");
        } else {
            if(direction == 0) player.changeCurrentAnimation("idleR");
            if(direction == 1) player.changeCurrentAnimation("idleL");
        }
    }

    @Override
    public void render(Graphics2D g) {
        //Shift render according to Camera
        g.translate(-camera.getX(), -camera.getY());

        //World Objects
        map.render(g);
        for(GameObject obj: gameObjects){
            g.drawImage(
                obj.getFrame(),
                obj.getPosition().intX(),
                obj.getPosition().intY(),
        null
            );
        }

        showColliders(g);

        //Reset translate for UI
        g.translate(camera.getX(), camera.getY());
    }

    private void showColliders(Graphics2D g) {
        g.setColor(Color.red);
        for(GameObject obj: gameObjects){
            for(Collider c: obj.getColliders()){
                Rectangle r = c.getBounds();
                g.drawRect(r.x, r.y, r.width, r.height);
            }
        }
    }

    private void checkCollisions() {
        Collider playerCollider = player.getColliders().get(0); // assuming single collider

        for(GameObject obj : gameObjects) {
            if(obj == player) continue; // skip self

            for(Collider c : obj.getColliders()) {
                if(playerCollider.intersects(c)) {
                    System.out.println("Player collided with " + obj);
                    // Handle collision here (stop movement, trigger event, etc.)
                }
            }
        }
    }
}
