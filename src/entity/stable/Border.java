package entity.stable;

import entity.GameObject;
import gfx.Animate;
import gfx.SpriteLibrary;
import physics.box.Collision;
import tile.TileScale;

public class Border extends GameObject {

    public Border(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites);
        animations.put("invisible", new Animate(sprites.get("invisible"), 1));
        currentAnimation = animations.get("invisible");
    }

    public void setCollision(int offsetX, int offSetY, int width, int height){
        this.box = new Collision(
                this,
                offsetX,
                offSetY,
                TileScale.of(width),
                TileScale.of(height));
    }
}
