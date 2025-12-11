package entity.moving;

import entity.GameObject;
import physics.Motion;
import gfx.Animate;
import gfx.SpriteLibrary;

public class MovingEntity extends GameObject {

    protected final Motion motion;
    public boolean restartCamera;

    public MovingEntity(double x, double y, double speed, SpriteLibrary sprites) {
        super(x, y, sprites);
        this.motion = new Motion(speed);
    }

    @Override
    public void update(){
        double dt = 1.0 / 60.0;

        if(waitingToSwitch) {
            switchTimer += dt;
            if(switchTimer >= SWITCH_DELAY) {
                currentAnimation = pendingAnimation;
                pendingAnimation = null;
                waitingToSwitch = false;
                switchTimer = 0;
            }
        }

        currentAnimation.update();
    }

    public void changeCurrentAnimation(String key){
        Animate anim = animations.get(key);

        if(anim != currentAnimation && anim != pendingAnimation) {
            pendingAnimation = anim;
            waitingToSwitch = true;
            switchTimer = 0;
        }
    }

    public void applyMotion() {
        position.add(motion.getVector());
    }

    public Motion getMotion() { return motion; }
}
