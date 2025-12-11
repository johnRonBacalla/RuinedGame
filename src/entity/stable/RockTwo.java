package entity.stable;

import entity.GameObject;
import gfx.Animate;
import gfx.SpriteLibrary;
import physics.box.Collision;

public class RockTwo extends GameObject {
    public RockTwo(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites);
//        this.box = new Collision(this, 55,110, 75,30);
        animations.put("objRockTwo", new Animate(sprites.get("objRockTwo"), 1));
        currentAnimation = animations.get("objRockTwo");
    }
}
