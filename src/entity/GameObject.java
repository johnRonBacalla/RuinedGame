package entity;

import physics.Position;
import gfx.Animate;
import gfx.SpriteLibrary;
import physics.box.Box;
import physics.box.Collision;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class GameObject {
    protected final Position position;
    protected Position previousPosition;
    protected SpriteLibrary sprites;
    protected HashMap<String, Animate> animations;

    protected Animate currentAnimation;
    protected Animate pendingAnimation;
    protected boolean waitingToSwitch = false;
    protected double switchTimer = 0;
    protected final double SWITCH_DELAY = 0.10;

    protected Box box;

    public GameObject(double x, double y, SpriteLibrary sprites) {
        this.position = new Position(x, y);
        this.previousPosition = new Position(x, y);
        this.sprites = sprites;
        this.animations = new HashMap<>();
        this.box = new Collision(this, 0, 0, 0, 0);
    }

    public BufferedImage getFrame() {
        return currentAnimation.getCurrentFrame();
    }

    public void update(){
        currentAnimation.update();
    }

    public void render(Graphics2D g){
        g.drawImage(
                getFrame(),
                getPosition().intX(),
                getPosition().intY(),
                null
        );
    }

    public void renderBox(Graphics2D g){
        g.drawRect(
                (int)box.getX(),
                (int)box.getY(),
                (int)box.getWidth(),
                (int)box.getHeight());
    }

    public Position getPosition() { return position; }

    public Box getBox() {
        return box;
    }
}