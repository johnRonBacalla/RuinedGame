package physics.box;

import entity.GameObject;

public class HitBox extends Box {

    private int damage;
    private boolean active;
    private int activeTimer; // How long the hitbox stays active
    private int maxActiveTime;

    public HitBox(GameObject owner, int damage, double offsetX, double offsetY, double width, double height) {
        super(owner, offsetX, offsetY, width, height);
        this.damage = damage;
        this.active = false;
        this.activeTimer = 0;
        this.maxActiveTime = 10; // Default: active for 10 frames
    }

    public void activate(int duration) {
        this.active = true;
        this.activeTimer = duration;
        this.maxActiveTime = duration;
    }

    public void deactivate() {
        this.active = false;
        this.activeTimer = 0;
    }

    public void update() {
        if (active) {
            activeTimer--;
            if (activeTimer <= 0) {
                deactivate();
            }
        }
    }

    @Override
    public void onCollide(Box other) {
        // Hitbox collision handled in PlayState
    }

    public boolean isActive() {
        return active;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getActiveTimer() {
        return activeTimer;
    }

    @Override
    public String getType() {
        return "hit";
    }

    @Override
    public String getSignal() {
        return "";
    }
}