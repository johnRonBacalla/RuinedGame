package entity.placeable;

import entity.GameObject;
import gfx.Animate;
import gfx.SpriteLibrary;

public class EarthPlant extends GameObject {
    public EarthPlant(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites);

        animations.put("earthPlant", new Animate(sprites.get("earthPlant"), 12));
        currentAnimation = animations.get("earthPlant");
    }
}


