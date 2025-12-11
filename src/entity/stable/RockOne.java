package entity.stable;

import entity.GameObject;
import gfx.Animate;
import gfx.SpriteLibrary;
import physics.box.Collision;

public class RockOne extends GameObject {
    public RockOne(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites);
//        this.box = new Collision(this, 55,110, 75,30);
        animations.put("objRockOne", new Animate(sprites.get("objRockOne"), 1));
        currentAnimation = animations.get("objRockOne");
    }
}
