package entity.placeable;

import entity.GameObject;
import gfx.Animate;
import gfx.SpriteLibrary;

public class WindPlant extends GameObject {
    public WindPlant(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites);

        animations.put("windPlant", new Animate(sprites.get("windPlant"), 12));
        currentAnimation = animations.get("windPlant");
    }
}
