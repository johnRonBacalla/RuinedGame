package entity.stable;

import entity.GameObject;
import gfx.Animate;
import gfx.SpriteLibrary;
import physics.box.Collision;

public class PineTree extends GameObject {
    public PineTree(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites);
//        this.box = new Collision(this, 55,110, 75,30);
        animations.put("objPineTree", new Animate(sprites.get("objPineTree"), 1));
        currentAnimation = animations.get("objPineTree");
    }
}
