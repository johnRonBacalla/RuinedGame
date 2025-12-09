package entity.stable;

import entity.GameObject;
import gfx.Animate;
import gfx.SpriteLibrary;
import physics.box.Collision;

public class Tree extends GameObject {
    public Tree(double x, double y, SpriteLibrary sprites) {
        super(x - 64, y - 128, sprites);
        this.box = new Collision(this, 55,110, 75,30);
        animations.put("objTree", new Animate(sprites.get("objTree"), 1));
        currentAnimation = animations.get("objTree");
    }
}
