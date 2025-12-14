package physics.box;

import entity.GameObject;

/**
 * HurtBox - A box that can receive damage from WeaponHitBox
 */
public class HurtBox {
    private GameObject owner;
    private double offsetX;
    private double offsetY;
    private double width;
    private double height;

    public HurtBox(GameObject owner, double offsetX, double offsetY, double width, double height) {
        this.owner = owner;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
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

    // Check collision with a WeaponHitBox
    public boolean intersects(WeaponHitBox hitBox) {
        if (!hitBox.isActive()) return false;

        return getX() < hitBox.getX() + hitBox.getWidth() &&
                getX() + width > hitBox.getX() &&
                getY() < hitBox.getY() + hitBox.getHeight() &&
                getY() + height > hitBox.getY();
    }

    // Setters for adjusting hurtbox
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

    public GameObject getOwner() {
        return owner;
    }
}