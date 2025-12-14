package entity.moving.mobs;

import entity.moving.MovingEntity;
import gfx.Animate;
import gfx.SpriteLibrary;
import physics.box.Box;
import physics.box.Sensor;

import java.util.List;

public class Skele extends MovingEntity {

    private Sensor sensor;
    private boolean frozenByWall = false;

    public Skele(double x, double y, SpriteLibrary sprites) {
        super(x, y, 1, sprites);

        // Configure Skele stats
        this.maxHp = 50;
        this.currentHp = 50;

        // Create sensor for collision detection
        this.sensor = new Sensor(this, 8, 8, 48, 56);

        // Configure hurtbox (adjust these values to fit your skeleton sprite)
        setHurtBox(8, 8, 48, 56); // offsetX, offsetY, width, height

        animations.put("skeleAnim", new Animate(sprites.get("skeleAnim"), 8));
        currentAnimation = animations.get("skeleAnim");
    }

    @Override
    public void update(List<Box> boxes) {
        // Don't move or update if dead
        if (isDead) {
            super.update(); // Still update animations/timers
            return;
        }

        // Check for wall collisions
        frozenByWall = false;
        for (Box box : boxes) {
            if (sensor.intersects(box) && box.getSignal().equals("wall")) {
                frozenByWall = true;
                break;
            }
        }

        // Only move if not frozen by wall
        if (!frozenByWall) {
            getMotion().setVelocityX(-0.5); // Move left slowly
            getMotion().setVelocityY(0);
            applyMotion();
        } else {
            // Frozen - stop movement but keep animation running
            getMotion().setVelocityX(0);
            getMotion().setVelocityY(0);
        }

        super.update();
    }

    @Override
    protected void die() {
        super.die();
        // Add death effects here later (death animation, drop items, etc.)
        System.out.println("Skeleton defeated!");
    }
}