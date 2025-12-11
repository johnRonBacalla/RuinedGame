package entity.stable;

import entity.GameObject;
import gfx.Animate;
import gfx.SpriteLibrary;

public class Mines extends GameObject {
    public Mines(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites);
        animations.put("objMines", new Animate(sprites.get("objMines"), 1));
        currentAnimation = animations.get("objMines");
    }
}
