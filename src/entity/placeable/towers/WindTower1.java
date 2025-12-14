package entity.placeable.towers;

import gfx.Animate;
import gfx.SpriteLibrary;

public class WindTower1 extends Tower{
    public WindTower1(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites);
        animations.put("windTower1", new Animate(sprites.get("windTower1"), 12));
        currentAnimation = animations.get("windTower1");
    }
}
