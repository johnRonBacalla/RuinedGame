package entity;

import gfx.Animate;
import gfx.SpriteLibrary;
import physics.box.Collision;

import java.awt.*;

public class House extends GameObject{

    public House(double x, double y, SpriteLibrary sprites) {
        super(x + 20, y, sprites);

        animations.put("objHouse", new Animate(sprites.get("objHouse"), 1));
        currentAnimation = animations.get("objHouse");
        this.box = new Collision(this, 0, 110, 186, 95);
    }
}
