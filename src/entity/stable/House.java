package entity.stable;

import entity.GameObject;
import gfx.Animate;
import gfx.SpriteLibrary;
import physics.box.Collision;

public class House extends GameObject {

    public House(double x, double y, SpriteLibrary sprites) {
        super(x + 2, y, sprites);

        animations.put("objHouse", new Animate(sprites.get("objHouse"), 1));
        currentAnimation = animations.get("objHouse");
        this.box = new Collision(this, 0, 110, 186, 95);
    }
}
