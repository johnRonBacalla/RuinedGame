package entity;

import physics.Collider;
import physics.Position;
import gfx.Animate;
import gfx.SpriteLibrary;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameObject {
    protected final Position position;
    protected SpriteLibrary sprites;
    protected HashMap<String, Animate> animations;
    protected List<Collider> colliders;

    protected Animate currentAnimation;
    protected Animate pendingAnimation;
    protected boolean waitingToSwitch = false;
    protected double switchTimer = 0;
    protected final double SWITCH_DELAY = 0.10;

    public GameObject(double x, double y, SpriteLibrary sprites) {
        this.position = new Position(x, y);
        this.sprites = sprites;
        this.animations = new HashMap<>();
        this.colliders = new ArrayList<>();
    }

    public void addCollider(Collider collider) {
        colliders.add(collider);
    }

    public List<Collider> getColliders(){
        return colliders;
    }

    public BufferedImage getFrame() {
        return currentAnimation.getCurrentFrame();
    }

    public void update(){
        currentAnimation.update();
    }

    public Position getPosition() { return position; }
}
