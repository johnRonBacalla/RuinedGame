package entity.stable;

import entity.GameObject;
import gfx.Animate;
import gfx.SpriteLibrary;
import physics.box.Collision;

public class Stump extends GameObject {
    public Stump(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites);
//        this.box = new Collision(this, 55,110, 75,30);
        animations.put("objStump", new Animate(sprites.get("objStump"), 1));
        currentAnimation = animations.get("objStump");
    }
}
