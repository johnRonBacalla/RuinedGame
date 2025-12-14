package entity.moving;

import gfx.Animate;
import gfx.SpriteLibrary;

public class Skele extends MovingEntity{
    public Skele(double x, double y, SpriteLibrary sprites) {
        super(x, y, 0.5, sprites); // This is max speed
        animations.put("skeleAnim", new Animate(sprites.get("skeleAnim"), 8));
        currentAnimation = animations.get("skeleAnim");
    }

    @Override
    public void update(){
        // Set velocity to exactly the speed (0.5) moving left
        double moveSpeed = -0.1; // Negative = left, adjust this value
        getMotion().setVelocityX(moveSpeed);
        getMotion().setVelocityY(0);
        super.update();
        applyMotion();
    }
}