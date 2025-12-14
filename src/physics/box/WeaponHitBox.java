package physics.box;

import entity.GameObject;

public class WeaponHitBox {
    private GameObject owner;
    private double offsetX;
    private double offsetY;
    private double width;
    private double height;
    private int damage;
    private boolean active;
    private int activeTimer;

    public WeaponHitBox(GameObject owner, int damage, double offsetX, double offsetY, double width, double height) {
        this.owner = owner;
        this.damage = damage;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
        this.active = false;
        this.activeTimer = 0;
    }

    public void activate(int duration) {
        this.active = true;
        this.activeTimer = duration;
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

    // Get absolute position in world
    public double getX() {
        return owner.getPosition().getX() + offsetX;
    }

    public double getY() {
        return owner.getPosition().getY() + offsetY;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public boolean isActive() {
        return active;
    }

    public int getDamage() {
        return damage;
    }

    // Setters for configuration
    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }

    public void setOffsetY(double offsetY) {
        this.offsetY = offsetY;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    // Check collision with another box (for hitting enemies)
    public boolean intersects(Box other) {
        return getX() < other.getX() + other.getWidth() &&
                getX() + width > other.getX() &&
                getY() < other.getY() + other.getHeight() &&
                getY() + height > other.getY();
    }
}