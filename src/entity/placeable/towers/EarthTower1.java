package entity.placeable.towers;

import gfx.Animate;
import gfx.SpriteLibrary;

public class EarthTower1 extends Tower{
    public EarthTower1(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites, TowerType.WALL);
        animations.put("earthTower1", new Animate(sprites.get("earthTower1"), 12));
        currentAnimation = animations.get("earthTower1");
    }

    @Override
    protected void attack() {

    }
}
