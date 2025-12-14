package entity.placeable.towers;

import gfx.Animate;
import gfx.SpriteLibrary;

public class EarthTower2 extends Tower{
    public EarthTower2(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites);
        animations.put("earthTower2", new Animate(sprites.get("earthTower2"), 12));
        currentAnimation = animations.get("earthTower2");
    }
}
