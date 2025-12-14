package entity.placeable;

import entity.GameObject;
import gfx.Animate;
import gfx.SpriteLibrary;

public class IcePlant extends GameObject {
    public IcePlant(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites);

        animations.put("icePlant", new Animate(sprites.get("icePlant"), 12));
        currentAnimation = animations.get("icePlant");
    }
}