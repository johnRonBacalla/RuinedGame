package physics;

import entity.GameObject;

import java.awt.*;

public class Collider {

    private GameObject gameObject;
    private double offsetX, offsetY;
    private double width, height;

    public Collider(GameObject entity, double offsetX, double offsetY, double width, double height) {
        this.gameObject = entity;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
    }

    public double getLeft()   { return gameObject.getPosition().getX() + offsetX; }
    public double getTop()    { return gameObject.getPosition().getY() + offsetY; }
    public double getRight()  { return getLeft() + width;  }
    public double getBottom() { return getTop() + height; }

    public boolean intersects(Collider other) {
        return !(getRight() < other.getLeft() ||
                getLeft() > other.getRight() ||
                getBottom() < other.getTop() ||
                getTop() > other.getBottom());
    }

    public Rectangle getBounds(){
        return new Rectangle(
                (int)getLeft(),
                (int)getTop(),
                (int)width,
                (int)height);
    }
}
