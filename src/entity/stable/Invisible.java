package entity.stable;

import entity.GameObject;
import gfx.Animate;
import gfx.SpriteLibrary;
import physics.box.Collision;

public class Invisible extends GameObject {
    public Invisible(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites);
        this.box = new Collision(this, 0, 0, 64, 64);
        animations.put("invisible", new Animate(sprites.get("invisible"), 1));
        currentAnimation = animations.get("invisible");
    }
}
