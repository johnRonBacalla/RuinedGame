package display.camera;

import entity.GameObject;

import java.awt.*;

public class Camera {

    private float x, y;
    private int width, height;
    private int worldWidth, worldHeight;
    private float deadZoneX;
    private float deadZoneY;

    public Camera(int width, int height, int worldWidth, int worldHeight) {
        this.width = width;
        this.height = height;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;

        this.deadZoneX = width / 2f;
        this.deadZoneY = height / 2f;
    }

    public void focusOn(float targetX, float targetY){
        float targetCamX = x;
        float targetCamY = y;

        if (targetX < x + deadZoneX) targetCamX = targetX - deadZoneX;
        if (targetX > x + width - deadZoneX) targetCamX = targetX - (width - deadZoneX);

        if (targetY < y + deadZoneY) targetCamY = targetY - deadZoneY;
        if (targetY > y + height - deadZoneY) targetCamY = targetY - (height - deadZoneY);

        // Smooth follow
        float lerp = 0.3f;
        x += (targetCamX - x) * lerp;
        y += (targetCamY - y) * lerp;

        clamp();
    }

    private void clamp() {
        // Horizontal
        if(worldWidth > width) {          // only clamp if map is wider than viewport
            if(x < 0) x = 0;
            if(x + width > worldWidth) x = worldWidth - width;
        } else {
            x = (worldWidth - width) / 2f; // center display.camera if map is smaller
        }

        // Vertical
        if(worldHeight > height) {        // only clamp if map is taller than viewport
            if(y < 0) y = 0;
            if(y + height > worldHeight) y = worldHeight - height;
        } else {
            y = (worldHeight - height) / 2f; // center display.camera if map is smaller
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setWorldSize(int widthInPx, int heightInPx) {
        this.worldWidth = widthInPx;
        this.worldHeight = heightInPx;
    }

    public void update(GameObject entity){
        this.focusOn(
                (float) entity.getPosition().getX() + entity.getFrame().getWidth() / 2f,
                (float) entity.getPosition().getY() + entity.getFrame().getHeight() / 2f);
    }

    public void apply(Graphics2D g){
        g.translate(-x, -y);
    }

    public void reset(Graphics2D g){
        g.translate(x, y);
    }
}
