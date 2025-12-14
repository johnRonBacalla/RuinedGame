package entity.placeable.towers;

import gfx.Animate;
import gfx.SpriteLibrary;

public class FireTower2 extends Tower{
    public FireTower2(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites);
        animations.put("fireTower2", new Animate(sprites.get("fireTower2"), 12));
        currentAnimation = animations.get("fireTower2");
    }
}
