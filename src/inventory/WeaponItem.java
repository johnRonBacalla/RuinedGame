package inventory;

import entity.moving.MovingEntity;
import entity.moving.Player;
import gfx.Animate;
import physics.box.WeaponHitBox;
import weapon.WeaponData;
import java.awt.*;
import java.awt.image.BufferedImage;

public class WeaponItem extends Item {
    private MovingEntity owner;
    private WeaponHitBox hitBox;
    private double attackAngle;
    private boolean isAttacking;
    private int cooldownTimer;

    private WeaponData weaponData;
    private Animate currentAnimation;

    // Configurable hitbox properties
    private double hitboxOffsetX;
    private double hitboxOffsetY;
    private double hitboxWidth;
    private double hitboxHeight;

    // Inverted offsets for left-facing
    private double invertedHitboxOffsetX;
    private double invertedHitboxOffsetY;

    private boolean debugHitbox = true; // Toggle to show/hide hitbox

    // NEW: Hitbox activation delay
    private int hitboxActivationDelay = 10; // frames to wait before hitbox activates (adjustable)
    private int hitboxDelayTimer = 0;
    private boolean waitingToActivateHitbox = false;

    public WeaponItem(int id, String name, BufferedImage icon, WeaponData weaponData) {
        super(id, name, icon);
        this.weaponData = weaponData;
        this.currentAnimation = weaponData.getIdleAnimation();
        this.cooldownTimer = 0;
        this.attackAngle = 0;
        this.isAttacking = false;

        // Default hitbox values
        this.hitboxWidth = weaponData.getRange();
        this.hitboxHeight = weaponData.getRange();
        this.hitboxOffsetX = 30;
        this.hitboxOffsetY = 0;
        this.invertedHitboxOffsetX = -30 - hitboxWidth;
        this.invertedHitboxOffsetY = 0;
    }

    public void setOwner(MovingEntity owner) {
        this.owner = owner;
        this.hitBox = new WeaponHitBox(owner, weaponData.getDamage(),
                hitboxOffsetX, hitboxOffsetY, hitboxWidth, hitboxHeight);
    }

    private void updateHitboxOffset() {
        if (hitBox == null || owner == null) return;

        boolean facingLeft = false;
        if (owner instanceof Player) {
            Player player = (Player) owner;
            facingLeft = player.getDirection() == 1;
        }

        if (facingLeft) {
            hitBox.setOffsetX(invertedHitboxOffsetX);
            hitBox.setOffsetY(invertedHitboxOffsetY);
        } else {
            hitBox.setOffsetX(hitboxOffsetX);
            hitBox.setOffsetY(hitboxOffsetY);
        }
    }

    @Override
    public void update() {
        if (cooldownTimer > 0) {
            cooldownTimer--;
        }

        // NEW: Handle hitbox activation delay
        if (waitingToActivateHitbox) {
            hitboxDelayTimer--;
            if (hitboxDelayTimer <= 0) {
                // Time to activate the hitbox!
                if (hitBox != null) {
                    hitBox.activate(weaponData.getAttackDuration());
                    updateHitboxOffset();
                    System.out.println("Hitbox activated after delay!");
                }
                waitingToActivateHitbox = false;
            }
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

        // Update hitbox
        if (hitBox != null) {
            hitBox.update();
            // Update offset if hitbox is active (during attack)
            if (hitBox.isActive()) {
                updateHitboxOffset();
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        // Don't render if weapon is not renderable (like shovel)
        if (!weaponData.isRenderable()) return;

        if (owner == null || currentAnimation == null) return;

        // Get current frame
        BufferedImage frame = currentAnimation.getCurrentFrame();

        // Check if player is facing left
        boolean facingLeft = false;
        if (owner instanceof Player) {
            Player player = (Player) owner;
            facingLeft = player.getDirection() == 1;
        }

        // Different offsets based on direction
        int offsetX, offsetY;
        if (facingLeft) {
            offsetX = 64;
            offsetY = -64;
        } else {
            offsetX = owner.getFrame().getWidth() - 64;
            offsetY = owner.getFrame().getHeight() - 128;
        }

        int weaponX = (int)(owner.getPosition().getX() + offsetX);
        int weaponY = (int)(owner.getPosition().getY() + offsetY);

        // Draw the weapon (flipped if facing left)
        if (facingLeft) {
            g.drawImage(frame,
                    weaponX, weaponY,
                    -192, 192,
                    null);
            g.drawRect(weaponX - 128, weaponY, 192, 192);
        } else {
            g.drawImage(frame, weaponX, weaponY, null);
            g.drawRect(weaponX, weaponY, 192, 192);
        }

        // Debug text
        g.setColor(Color.WHITE);
        g.drawString("WEAPON: " + (facingLeft ? "LEFT" : "RIGHT"), weaponX - 128, weaponY - 10);

        // NEW: Show delay timer
        if (waitingToActivateHitbox) {
            g.setColor(Color.YELLOW);
            g.drawString("Delay: " + hitboxDelayTimer, weaponX - 128, weaponY - 25);
        }

        // Render debug hitbox
        if (debugHitbox && hitBox != null && hitBox.isActive()) {
            renderDebugHitbox(g);
        }
    }

    private void renderDebugHitbox(Graphics2D g) {
        // Semi-transparent red fill
        Color hitboxColor = new Color(255, 0, 0, 100);
        g.setColor(hitboxColor);
        g.fillRect(
                (int) hitBox.getX(),
                (int) hitBox.getY(),
                (int) hitBox.getWidth(),
                (int) hitBox.getHeight()
        );

        // Red border
        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(2));
        g.drawRect(
                (int) hitBox.getX(),
                (int) hitBox.getY(),
                (int) hitBox.getWidth(),
                (int) hitBox.getHeight()
        );
    }

    public void attack() {
        System.out.println("Attack called!");

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

        // NEW: Start the delay timer instead of activating immediately
        waitingToActivateHitbox = true;
        hitboxDelayTimer = hitboxActivationDelay;
        System.out.println("Hitbox will activate in " + hitboxActivationDelay + " frames");

        cooldownTimer = weaponData.getAttackSpeed();
    }

    // === Hitbox Configuration Methods ===

    public void setHitboxSize(double width, double height) {
        this.hitboxWidth = width;
        this.hitboxHeight = height;
        if (hitBox != null) {
            hitBox.setWidth(width);
            hitBox.setHeight(height);
        }
    }

    public void setHitboxOffset(double offsetX, double offsetY) {
        this.hitboxOffsetX = offsetX;
        this.hitboxOffsetY = offsetY;
        if (hitBox != null && owner != null) {
            updateHitboxOffset();
        }
    }

    public void setInvertedHitboxOffset(double offsetX, double offsetY) {
        this.invertedHitboxOffsetX = offsetX;
        this.invertedHitboxOffsetY = offsetY;
        if (hitBox != null && owner != null) {
            updateHitboxOffset();
        }
    }

    public void setDebugHitbox(boolean debug) {
        this.debugHitbox = debug;
    }

    // NEW: Configure hitbox activation delay
    public void setHitboxActivationDelay(int frames) {
        this.hitboxActivationDelay = frames;
    }

    public int getHitboxActivationDelay() {
        return hitboxActivationDelay;
    }

    public WeaponHitBox getHitBox() { return hitBox; }
    public boolean isAttacking() { return isAttacking; }
}