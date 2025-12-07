package physics;

import controller.Controller;

public class Motion {

    private Vector vector;          // current velocity
    private double speed;           // max speed

    private double acceleration = 0.35;
    private double deceleration = 0.35;
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
            if (inputX  == 0){
                if(velocityX > 0) {
                    velocityX -= deceleration;
                    if(velocityX < 0) velocityX = 0; // clamp
                } else if (velocityX < 0) {
                    velocityX += deceleration;
                    if (velocityX > 0) velocityX = 0; // clamp
                }
            }
        }

        if (inputY != 0) {
            velocityY += inputY * acceleration;
        } else {
            // --- DECELERATION Y ---
            if (inputY == 0) {
                if (velocityY > 0) {
                    velocityY -= deceleration;
                    if (velocityY < 0) velocityY = 0;  // clamp
                } else if (velocityY < 0) {
                    velocityY += deceleration;
                    if (velocityY > 0) velocityY = 0;  // clamp
                }
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
}
