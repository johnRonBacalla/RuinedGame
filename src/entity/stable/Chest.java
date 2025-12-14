package entity.stable;

import entity.GameObject;
import gfx.Animate;
import gfx.SpriteLibrary;
import physics.box.Collision;
import tile.TileScale;

public class Chest extends GameObject {
    public Chest(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites);

        Animate chest = new Animate(sprites.get("objChestClosed"), 9);

        // Store it in GameObject's animation map
        animations.put("objChestClosed", chest);


        // Set this as the current animation
        currentAnimation = chest;
    }

}
