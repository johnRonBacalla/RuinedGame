package entity.placeable.towers;

import gfx.Animate;
import gfx.SpriteLibrary;

public class FireTower1 extends Tower {
    public FireTower1(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites);
        animations.put("fireTower1", new Animate(sprites.get("fireTower1"), 12));
        currentAnimation = animations.get("fireTower1");
    }
}
