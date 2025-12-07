package entity;

import gfx.Animate;
import gfx.SpriteLibrary;
import physics.Collider;

public class House extends GameObject{
    public House(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites);

        animations.put("objHouse", new Animate(sprites.get("objHouse"), 1));
        currentAnimation = animations.get("objHouse");
        addCollider(new Collider(this,
                8,
                (double) currentAnimation.getHeight() / 2 + 15,
                currentAnimation.getWidth() - 15,
                (double) currentAnimation.getHeight() / 2 - 25));
    }
}
