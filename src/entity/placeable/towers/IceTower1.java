package entity.placeable.towers;

import gfx.Animate;
import gfx.SpriteLibrary;

public class IceTower1 extends Tower{
    public IceTower1(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites);
        animations.put("iceTower1", new Animate(sprites.get("iceTower1"), 12));
        currentAnimation = animations.get("iceTower1");
    }
}
