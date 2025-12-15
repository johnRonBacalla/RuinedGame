package entity.moving;

import entity.GameObject;
import physics.Motion;
import physics.box.HurtBox;
import physics.box.Sensor;
import physics.box.WeaponHitBox;
import gfx.Animate;
import gfx.SpriteLibrary;
import java.util.List;
import physics.box.Box;
import javax.swing.*;
import java.awt.*;

public class MovingEntity extends GameObject {

    protected final Motion motion;
    public boolean restartCamera;

    // HP System
    protected int maxHp;
    protected int currentHp;
    protected boolean isDead;

    // HurtBox
    protected HurtBox hurtBox;

    // Damage feedback
    protected boolean wasHit;
    protected int hitFlashTimer;
    protected static final int HIT_FLASH_DURATION = 10; // frames to flash when hit

    public MovingEntity(double x, double y, double speed, SpriteLibrary sprites) {
        super(x, y, sprites);
        this.motion = new Motion(speed);

        // Default HP values (can be overridden in subclasses)
        this.maxHp = 100;
        this.currentHp = maxHp;
        this.isDead = false;

        // Default hurtbox (centered on entity)
        this.hurtBox = new HurtBox(this, 0, 0, 64, 64);

        this.wasHit = false;
        this.hitFlashTimer = 0;
    }

    public void update(List<Box> boxes) {
        // default movement integration
        applyMotion();
        super.update();
    }

    @Override
    public void update(){
        double dt = 1.0 / 60.0;

        if(waitingToSwitch) {
            switchTimer += dt;
            if(switchTimer >= SWITCH_DELAY) {
                currentAnimation = pendingAnimation;
                pendingAnimation = null;
                waitingToSwitch = false;
                switchTimer = 0;
            }
        }

        currentAnimation.update();

        // Update hit flash timer
        if (hitFlashTimer > 0) {
            hitFlashTimer--;
            if (hitFlashTimer == 0) {
                wasHit = false;
            }
        }
    }

    /**
     * Check if this entity's hurtbox intersects with a weapon hitbox
     * Call this from PlayState to check collisions
     */
    public void checkHitByWeapon(WeaponHitBox weaponHitBox) {
        if (isDead || hurtBox == null || weaponHitBox == null) return;

        if (hurtBox.intersects(weaponHitBox)) {
            takeDamage(weaponHitBox.getDamage());
        }
    }

    /**
     * Apply damage to this entity
     */
    public void takeDamage(int damage) {
        if (isDead) return;

        currentHp -= damage;
        wasHit = true;
        hitFlashTimer = HIT_FLASH_DURATION;

        System.out.println(this.getClass().getSimpleName() + " took " + damage +
                " damage! HP: " + currentHp + "/" + maxHp);

        if (currentHp <= 0) {
            currentHp = 0;
            die();
        }
    }

    /**
     * Called when HP reaches 0
     */
    protected void die() {
        isDead = true;
        System.out.println(this.getClass().getSimpleName() + " died!");
    }

    /**
     * Heal this entity
     */
    public void heal(int amount) {
        if (isDead) return;

        currentHp = Math.min(currentHp + amount, maxHp);
        System.out.println(this.getClass().getSimpleName() + " healed " + amount +
                " HP! HP: " + currentHp + "/" + maxHp);
    }

    @Override
    public void render(Graphics2D g) {
        // Flash red when hit
//        if (wasHit && hitFlashTimer % 4 < 2) {
//            // Draw red tint
//            Composite oldComposite = g.getComposite();
//            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
//            g.setColor(Color.RED);
//            g.fillRect(
//                    (int) position.getX(),
//                    (int) position.getY(),
//                    currentAnimation.getCurrentFrame().getWidth(),
//                    currentAnimation.getCurrentFrame().getHeight()
//            );
//            g.setComposite(oldComposite);
//        }

        super.render(g);

        // Render HP bar
        renderHpBar(g);

        // Debug: Render hurtbox
//        renderHurtBox(g);
    }

    /**
     * Render HP bar above entity
     */
    protected void renderHpBar(Graphics2D g) {
        if (isDead) return;

        int barWidth = 60;
        int barHeight = 6;
        int barX = (int) position.getX() + 2;
        int barY = (int) position.getY() - 10;

        // Background (black)
        g.setColor(Color.BLACK);
        g.fillRect(barX, barY, barWidth, barHeight);

        // HP fill (green to red based on HP percentage)
        float hpPercent = (float) currentHp / maxHp;
        int fillWidth = (int) (barWidth * hpPercent);

        if (hpPercent > 0.5f) {
            g.setColor(Color.GREEN);
        } else if (hpPercent > 0.25f) {
            g.setColor(Color.YELLOW);
        } else {
            g.setColor(Color.RED);
        }

        g.fillRect(barX, barY, fillWidth, barHeight);

        // Border (white)
        g.setColor(Color.WHITE);
        g.drawRect(barX, barY, barWidth, barHeight);
    }

    /**
     * Debug: Render hurtbox
     */
    protected void renderHurtBox(Graphics2D g) {
        if (hurtBox == null) return;

        // Semi-transparent blue fill
        Color hurtBoxColor = new Color(0, 0, 255, 50);
        g.setColor(hurtBoxColor);
        g.fillRect(
                (int) hurtBox.getX(),
                (int) hurtBox.getY(),
                (int) hurtBox.getWidth(),
                (int) hurtBox.getHeight()
        );

        // Blue border
        g.setColor(Color.BLUE);
        g.setStroke(new BasicStroke(2));
        g.drawRect(
                (int) hurtBox.getX(),
                (int) hurtBox.getY(),
                (int) hurtBox.getWidth(),
                (int) hurtBox.getHeight()
        );
    }

    public void changeCurrentAnimation(String key){
        Animate anim = animations.get(key);

        if(anim != currentAnimation && anim != pendingAnimation) {
            pendingAnimation = anim;
            waitingToSwitch = true;
            switchTimer = 0;
        }
    }

    public void applyMotion() {
        position.add(motion.getVector());
    }

    // === Getters and Setters ===

    public Motion getMotion() { return motion; }

    public int getMaxHp() { return maxHp; }

    public int getCurrentHp() { return currentHp; }

    public boolean isDead() { return isDead; }

    public HurtBox getHurtBox() { return hurtBox; }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
        this.currentHp = Math.min(currentHp, maxHp);
    }

    public void setCurrentHp(int hp) {
        this.currentHp = Math.max(0, Math.min(hp, maxHp));
        if (currentHp == 0 && !isDead) {
            die();
        }
    }

    /**
     * Configure the hurtbox for this entity
     */
    public void setHurtBox(double offsetX, double offsetY, double width, double height) {
        this.hurtBox = new HurtBox(this, offsetX, offsetY, width, height);
    }
}