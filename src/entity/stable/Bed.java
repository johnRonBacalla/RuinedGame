package entity.stable;

import entity.GameObject;
import gfx.Animate;
import gfx.SpriteLibrary;
import physics.box.Collision;
import tile.TileScale;

public class Bed extends GameObject {
    public Bed(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites);

        // Load the full animation (14 frames)
        Animate bed = new Animate(sprites.get("objBed"), 1);

        // Store it in GameObject's animation map
        animations.put("objBed", bed);



        // Set this as the current animation
        currentAnimation = bed;

    }
}
