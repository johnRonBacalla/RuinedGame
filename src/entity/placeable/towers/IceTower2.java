package entity.placeable.towers;

import gfx.Animate;
import gfx.SpriteLibrary;

public class IceTower2 extends Tower{
    public IceTower2(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites, TowerType.AOE);
        animations.put("iceTower2", new Animate(sprites.get("iceTower2"), 12));
        currentAnimation = animations.get("iceTower2");
    }

    @Override
    protected void attack() {

    }
}
