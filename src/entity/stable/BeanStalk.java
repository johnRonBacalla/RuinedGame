package entity.stable;

import entity.GameObject;
import gfx.Animate;
import gfx.SpriteLibrary;
import physics.box.Collision;
import tile.TileScale;

public class BeanStalk extends GameObject {
    public BeanStalk(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites);

        // Load the full animation (14 frames)
        Animate beanAnim = new Animate(sprites.get("objBean"), 1);

        // Store it in GameObject's animation map
        animations.put("objBean", beanAnim);

        // Show only frame 0 (or any frame you want)

        // Set this as the current animation
        currentAnimation = beanAnim;
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
