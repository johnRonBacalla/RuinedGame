package entity.moving;

import gfx.Animate;
import gfx.SpriteLibrary;
import physics.Collider;

public class Player extends MovingEntity {

    public Player(double x, double y, double speed, SpriteLibrary sprites) {
        super(x, y, speed, sprites);

        animations.put("idleL", new Animate(sprites.get("playerIdleL"), 12));
        animations.put("idleR", new Animate(sprites.get("playerIdleR"), 12));
        animations.put("walkL", new Animate(sprites.get("playerWalkL"), 12));
        animations.put("walkR", new Animate(sprites.get("playerWalkR"), 12));
        this.currentAnimation = animations.get("idleR");
        addCollider(new Collider(this, 16.0, 30.0, 32, 32));
    }
}
