package physics;

import controller.Controller;

public class Motion {

    private Vector vector;          // current velocity
    private double speed;           // max speed

    private double acceleration = 0.45;
    private double deceleration = 0.45;
    private double velocityX = 0;
    private double velocityY = 0;

    public Motion(double speed) {
        this.speed = speed;
        this.vector = new Vector(0, 0);
    }

    public void update(Controller controller) {
        int inputX = 0;
        int inputY = 0;

        if (controller.isRequestingUp()) inputY--;
        if (controller.isRequestingDown()) inputY++;
        if (controller.isRequestingLeft()) inputX--;
        if (controller.isRequestingRight()) inputX++;

        // --- ACCELERATION ---
        if (inputX != 0) {
            velocityX += inputX * acceleration;
        } else {
            // --- DECELERATION X ---
            if (velocityX > 0) {
                velocityX -= deceleration;
                if (velocityX < 0) velocityX = 0;
            } else if (velocityX < 0) {
                velocityX += deceleration;
                if (velocityX > 0) velocityX = 0;
            }
        }

        if (inputY != 0) {
            velocityY += inputY * acceleration;
        } else {
            // --- DECELERATION Y ---
            if (velocityY > 0) {
                velocityY -= deceleration;
                if (velocityY < 0) velocityY = 0;
            } else if (velocityY < 0) {
                velocityY += deceleration;
                if (velocityY > 0) velocityY = 0;
            }
        }

        // Snap to zero if near zero to avoid jitter
        if (Math.abs(velocityX) < 0.01) velocityX = 0;
        if (Math.abs(velocityY) < 0.01) velocityY = 0;

        // LIMIT SPEED (diagonal friendly)
        double length = Math.sqrt(velocityX * velocityX + velocityY * velocityY);
        if (length > speed) {
            velocityX = (velocityX / length) * speed;
            velocityY = (velocityY / length) * speed;
        }

        vector = new Vector(velocityX, velocityY);
    }

    public Vector getVector() { return vector; }
    public boolean isMoving() { return vector.length() > 0.01; }
    public boolean isMovingLeft() { return vector.getX() < 0; }
    public boolean isMovingRight() { return vector.getX() > 0; }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
        updateVector();
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
        updateVector();
    }

    // --- Stop movement along one axis ---
    public void stopX() {
        this.velocityX = 0;
        updateVector();
    }

    public void stopY() {
        this.velocityY = 0;
        updateVector();
    }

    // Update the internal vector after velocity changes
    private void updateVector() {
        this.vector = new Vector(velocityX, velocityY);
    }
}
