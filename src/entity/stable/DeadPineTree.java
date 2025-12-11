package entity.stable;

import entity.GameObject;
import gfx.Animate;
import gfx.SpriteLibrary;
import physics.box.Collision;

public class DeadPineTree extends GameObject {
    public DeadPineTree(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites);
        animations.put("objDeadPineTree", new Animate(sprites.get("objDeadPineTree"), 1));
        currentAnimation = animations.get("objDeadPineTree");
    }
}
