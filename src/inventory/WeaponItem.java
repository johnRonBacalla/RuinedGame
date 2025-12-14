package inventory;

import entity.moving.MovingEntity;
import entity.moving.Player;
import gfx.Animate;
import physics.box.HitBox;
import weapon.WeaponData;
import java.awt.*;
import java.awt.image.BufferedImage;

public class WeaponItem extends Item {
    private MovingEntity owner;
    private HitBox hitBox;
    private double attackAngle;
    private boolean isAttacking;
    private int cooldownTimer;

    private WeaponData weaponData; // Store weapon data here
    private Animate currentAnimation;

    public WeaponItem(int id, String name, BufferedImage icon, WeaponData weaponData) {
        super(id, name, icon);
        this.weaponData = weaponData;
        this.currentAnimation = weaponData.getIdleAnimation();
        this.cooldownTimer = 0;
        this.attackAngle = 0;
        this.isAttacking = false;
    }

    public void setOwner(MovingEntity owner) {
        this.owner = owner;
        this.hitBox = new HitBox(owner, weaponData.getDamage(), 0, 0, weaponData.getRange(), weaponData.getRange());
    }

    @Override
    public void update() {
        if (cooldownTimer > 0) {
            cooldownTimer--;
        }

        // Update current animation
        if (currentAnimation != null) {
            currentAnimation.update();
        }

        // Check if attack animation has finished
        if (isAttacking && currentAnimation != null && currentAnimation.isFinished()) {
            System.out.println("Attack animation finished!");
            isAttacking = false;
            currentAnimation = weaponData.getIdleAnimation();
            currentAnimation.reset();
        }

        // Update hitbox separately
        if (hitBox != null) {
            hitBox.update();
        }
    }

    @Override
    public void render(Graphics2D g) {
        // Don't render if weapon is not renderable (like shovel)
        if (!weaponData.isRenderable()) return;

        if (owner == null || currentAnimation == null) return;

        // Get current frame
        BufferedImage frame = currentAnimation.getCurrentFrame();

        // Check if player is facing left (assuming Player has a getDirection() method)
        boolean facingLeft = false;
        if (owner instanceof Player) {
            Player player = (Player) owner;
            facingLeft = player.getDirection() == 1; // 1 = left, 0 = right
        }

        // Different offsets based on direction
        int offsetX, offsetY;
        if (facingLeft) {
            // Offsets when facing LEFT
            offsetX = 64; // Negative to move left
            offsetY = -64;  // Adjust as needed
        } else {
            // Offsets when facing RIGHT
            offsetX = owner.getFrame().getWidth() - 64; // Original offset
            offsetY = owner.getFrame().getHeight() - 128;
        }

        int weaponX = (int)(owner.getPosition().getX() + offsetX);
        int weaponY = (int)(owner.getPosition().getY() + offsetY);

        // Draw the weapon (flipped if facing left)
        if (facingLeft) {
            // Flip horizontally
            g.drawImage(frame,
                    weaponX, weaponY, // Start from right edge
                    -192, 192, // Negative width flips it
                    null);
            g.drawRect(weaponX - 128, weaponY, 192, 192);
        } else {
            // Normal rendering
            g.drawImage(frame, weaponX, weaponY, null);
            g.drawRect(weaponX, weaponY, 192, 192);
        }

        // Debug text
        g.setColor(Color.WHITE);
        g.drawString("WEAPON: " + (facingLeft ? "LEFT" : "RIGHT"), weaponX - 128, weaponY - 10);
    }

    public void attack() {
        System.out.println("Attack called!");

        // Can't attack with non-renderable items (like shovel)
        if (!weaponData.isRenderable()) {
            System.out.println("Weapon not renderable");
            return;
        }

        if (owner == null) {
            System.out.println("Owner is null");
            return;
        }

        if (cooldownTimer > 0) {
            System.out.println("On cooldown: " + cooldownTimer);
            return;
        }

        System.out.println("ATTACKING with " + getName());

        // Switch to attack animation
        isAttacking = true;
        currentAnimation = weaponData.getAttackAnimation();
        currentAnimation.reset();

        // Activate hitbox so the attack animation stays active
        if (hitBox != null) {
            hitBox.activate(weaponData.getAttackDuration());
        }

        cooldownTimer = weaponData.getAttackSpeed();
    }

    public HitBox getHitBox() { return hitBox; }
    public boolean isAttacking() { return isAttacking; }
}