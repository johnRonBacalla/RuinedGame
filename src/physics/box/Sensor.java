package physics.box;

import entity.GameObject;
import entity.moving.MovingEntity;

public class Sensor extends Box {

    public Sensor(GameObject owner, double offsetX, double offsetY, double width, double height) {
        super(owner, offsetX, offsetY, width, height);
    }

    @Override
    public void onCollide(Box other) {
        String type = other.getType();
        System.out.println("Hit by a " + type);

        switch (type) {
            case "hit":
                takeDamage(other);
                break;

            case "event":
                triggerEvent(other);
                break;

            case "col":
                handleCollision((Collision) other);
                break;

            // Sensors should not stop motion
            default:
                break;
        }
    }

    @Override
    public String getType() {
        return "sensor";
    }

    private void triggerEvent(Box other) {
        // TODO: trigger event logic
    }

    private void takeDamage(Box other) {
        // TODO: damage logic
    }

    private void handleCollision(Collision other) {
        if (!(owner instanceof MovingEntity movingEntity)) return;

        double ownerX = owner.getPosition().getX();
        double ownerY = owner.getPosition().getY();
        double ownerWidth = owner.getFrame().getWidth();
        double ownerHeight = owner.getFrame().getHeight();

        double otherX = other.getX();
        double otherY = other.getY();
        double otherWidth = other.getWidth();
        double otherHeight = other.getHeight();

        double dx = (ownerX + ownerWidth / 2) - (otherX + otherWidth / 2);
        double dy = (ownerY + ownerHeight / 2) - (otherY + otherHeight / 2);

        double combinedHalfWidth = (ownerWidth / 2) + (otherWidth / 2);
        double combinedHalfHeight = (ownerHeight / 2) + (otherHeight / 2);

        double overlapX = combinedHalfWidth - Math.abs(dx);
        double overlapY = combinedHalfHeight - Math.abs(dy);

        if (overlapX > 0 && overlapY > 0) {
            if (overlapX < overlapY) {
                // Horizontal collision
                movingEntity.getMotion().stopX();
                if (dx > 0) owner.getPosition().setX(ownerX + 1);
                else owner.getPosition().setX(ownerX - 1);
            } else {
                // Vertical collision
                movingEntity.getMotion().stopY();
                if (dy > 0) owner.getPosition().setY(ownerY + 1);
                else owner.getPosition().setY(ownerY - 1);
            }
        }
    }
}
