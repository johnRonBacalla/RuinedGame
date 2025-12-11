package entity.stable;

import entity.GameObject;
import gfx.Animate;
import gfx.SpriteLibrary;
import physics.box.Event;

public class Bridge extends GameObject {
    public Bridge(double x, double y, SpriteLibrary sprites) {
        super(x, y + 20, sprites);
        this.box = new Event(this, "bridge", 0, 0, 261, 132);
        animations.put("objBridge", new Animate(sprites.get("objBridge"), 1));
        currentAnimation = animations.get("objBridge");
    }
}
