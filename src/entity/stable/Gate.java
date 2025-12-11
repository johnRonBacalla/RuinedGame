package entity.stable;

import entity.GameObject;
import gfx.Animate;
import gfx.SpriteLibrary;
import physics.box.Collision;
import physics.box.Event;
import tile.TileScale;

public class Gate extends GameObject {


    public Gate( double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites);
        animations.put("invisible", new Animate(sprites.get("invisible"), 1));
        currentAnimation = animations.get("invisible");
    }

    public void setEvent(String signal, int offsetX, int offSetY, int width, int height){
        this.box = new Event(
                this,
                signal,
                offsetX,
                offSetY,
                TileScale.of(width),
                TileScale.of(height));
    }
}
