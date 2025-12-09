package physics.box;

import entity.GameObject;

public abstract class Box {

    protected GameObject owner; // the object this physics.box belongs to
    protected double offsetX, offsetY; // relative to parent
    protected double width, height;

    public Box(GameObject owner, double offsetX, double offsetY, double width, double height) {
        this.owner = owner;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
    }

    // Absolute position in world
    public double getX() { return owner.getPosition().getX() + offsetX; }
    public double getY() { return owner.getPosition().getY() + offsetY; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }

    // Simple AABB intersection
    public boolean intersects(Box other) {
        return getX() < other.getX() + other.getWidth() &&
                getX() + width > other.getX() &&
                getY() < other.getY() + other.getHeight() &&
                getY() + height > other.getY();
    }

    // Abstract method for behavior on collision
    public abstract void onCollide(Box other);

    public abstract String getType();

    public abstract String getSignal();
}
