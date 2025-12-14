package entity.placeable.towers;

import gfx.Animate;
import gfx.SpriteLibrary;

public class WindTower2 extends Tower{
    public WindTower2(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites);
        animations.put("windTower2", new Animate(sprites.get("windTower2"), 12));
        currentAnimation = animations.get("windTower2");
    }
}
