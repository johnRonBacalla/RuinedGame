package entity.placeable;

import entity.GameObject;
import gfx.Animate;
import gfx.SpriteLibrary;

public class FirePlant extends GameObject {
    public FirePlant(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites);

        animations.put("firePlant", new Animate(sprites.get("firePlant"), 12));
        currentAnimation = animations.get("firePlant");
    }
}
