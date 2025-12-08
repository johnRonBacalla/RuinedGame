package entity;

import gfx.Animate;
import gfx.SpriteLibrary;
import physics.box.Event;

import java.awt.*;

public class Ember extends GameObject {
    public Ember(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites);

        animations.put("emberL", new Animate(sprites.get("emberIdleL"), 12));
        currentAnimation = animations.get("emberL");
        this.box = new Event(this, 0, 0, 64, 64);
    }
}
