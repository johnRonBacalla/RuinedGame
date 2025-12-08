package entity.moving;

import physics.box.Box;
import gfx.Animate;
import gfx.SpriteLibrary;
import physics.box.Sensor;

import java.awt.*;
import java.util.List;

public class Player extends MovingEntity {

    private int direction = 0;
    private Box sensor;

    public Player(double x, double y, double speed, SpriteLibrary sprites) {
        super(x, y, speed, sprites);

        animations.put("idleL", new Animate(sprites.get("playerIdleL"), 12));
        animations.put("idleR", new Animate(sprites.get("playerIdleR"), 12));
        animations.put("walkL", new Animate(sprites.get("playerWalkL"), 12));
        animations.put("walkR", new Animate(sprites.get("playerWalkR"), 12));
        this.currentAnimation = animations.get("idleR");
        this.sensor = new Sensor(this, 16, 42, 32, 25);
    }

    public void update(List<Box> boxes){
        super.update();
        handleAnimation();

        for(Box box : boxes) {
            if (sensor.intersects(box)) {
                sensor.onCollide(box);
            }
        }
    }

    public void render(Graphics2D g){
        super.render(g);
        g.setColor(Color.GREEN);
        g.drawRect(
                (int)sensor.getX(),
                (int)sensor.getY(),
                (int)sensor.getWidth(),
                (int)sensor.getHeight());
    }

    private void handleAnimation() {
        if(getMotion().isMovingLeft()){
            direction = 1;
        } else if(getMotion().isMovingRight()){
            direction = 0;
        }

        if(getMotion().isMoving()){
            if(getMotion().isMovingRight()) changeCurrentAnimation("walkR");
            if(getMotion().isMovingLeft()) changeCurrentAnimation("walkL");
        } else {
            if(direction == 0) changeCurrentAnimation("idleR");
            if(direction == 1) changeCurrentAnimation("idleL");
        }
    }
}
