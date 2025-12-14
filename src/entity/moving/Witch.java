package entity.moving;

import gfx.Animate;
import gfx.SpriteLibrary;

public class Witch extends MovingEntity{
    public Witch(double x, double y, SpriteLibrary sprites) {
        super(x, y, 1, sprites);

        // Configure Skele stats
        this.maxHp = 50;
        this.currentHp = 50;

        // Configure hurtbox (adjust these values to fit your skeleton sprite)
        setHurtBox(8, 8, 48, 56); // offsetX, offsetY, width, height

        animations.put("witchAnim", new Animate(sprites.get("witchAnim"), 8));
        currentAnimation = animations.get("witchAnim");
    }

    @Override
    public void update(){
        // Don't move or update if dead
        if (isDead) {
            super.update(); // Still update animations/timers
            return;
        }

        getMotion().setVelocityX(-0.5); // Move left slowly
        getMotion().setVelocityY(0);
        super.update();
        applyMotion();
    }

    @Override
    protected void die() {
        super.die();
        // Add death effects here later (death animation, drop items, etc.)
        System.out.println("Skeleton defeated!");
    }
}
