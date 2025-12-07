package entity;

import gfx.Animate;
import gfx.SpriteLibrary;
import physics.Collider;

public class Ember extends GameObject {
    public Ember(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites);

        animations.put("emberL", new Animate(sprites.get("emberIdleL"), 12));
        currentAnimation = animations.get("emberL");
        addCollider(new Collider(this, 16.0, 30.0, 32, 32));
    }
}
