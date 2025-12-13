package entity.moving;

import display.Display;
import map.Location;
import physics.box.Box;
import gfx.Animate;
import gfx.SpriteLibrary;
import physics.box.Sensor;
import sound.Sound;
import state.PlayState;
import state.State;
import tile.TileScale;

import java.awt.*;
import java.util.List;

public class Player extends MovingEntity {

    private int direction = 0;
    private Box sensor;
    private Sound walkingSound;
    private boolean wasWalking = false;
    private Double bridgeBaseY = null;
    private boolean touchingBridgeThisFrame;
    private PlayState state;

    public Player(State state, double x, double y, double speed, SpriteLibrary sprites) {
        super(x, y, speed, sprites);

        animations.put("idleL", new Animate(sprites.get("playerIdleL"), 12));
        animations.put("idleR", new Animate(sprites.get("playerIdleR"), 12));
        animations.put("walkL", new Animate(sprites.get("playerWalkL"), 12));
        animations.put("walkR", new Animate(sprites.get("playerWalkR"), 12));
        this.currentAnimation = animations.get("idleR");
        this.sensor = new Sensor(this, 16, 42, 32, 25);
        this.walkingSound = new Sound("sounds/sfx/WalkingSFX.wav");
        this.state = (PlayState) state;
    }

    public void update(List<Box> boxes){
        super.update();
        handleAnimation();
        handleWalkingSound();

        touchingBridgeThisFrame = false;

        for(Box box : boxes) {
            if (sensor.intersects(box)) {
                switch(box.getSignal()){
                    case "null":
                        sensor.onCollide(box);
                        break;
                    case "toBattle":
                        Display.startFade(() -> {
                            state.changeCurrentMap(Location.BATTLE);
                            position.setX(TileScale.of(1));
                            position.setY(TileScale.of(9));
                            restartCamera = true;
                        });
                        break;
                    case "toMines":
                        Display.startFade(() -> {
                            state.changeCurrentMap(Location.MINES);
                            position.setX(TileScale.of(8));
                            position.setY(TileScale.of(8));
                            restartCamera = true;
                        });
                        break;
                    case "toHouse":
                        Display.startFade(() -> {
                            state.changeCurrentMap(Location.HOUSE);
                            position.setX(TileScale.of(8));
                            position.setY(TileScale.of(8));
                            restartCamera = true;
                        });
                        break;
                    case "fromBattle":
                        Display.startFade(() -> {
                            state.changeCurrentMap(Location.FARM);
                            position.setX(TileScale.of(34));
                            position.setY(TileScale.of(10));
                            restartCamera = true;
                        });
                        break;
                    case "fromMines":
                        Display.startFade(() -> {
                            state.changeCurrentMap(Location.FARM);
                            position.setX(TileScale.of(21));
                            position.setY(TileScale.of(4));
                            restartCamera = true;
                        });
                        break;
                    case "fromHouse":
                        Display.startFade(() -> {
                            state.changeCurrentMap(Location.FARM);
                            position.setX(TileScale.of(15));
                            position.setY(TileScale.of(6));
                            restartCamera = true;
                        });
                        break;
                    case "bridge":
                        touchingBridgeThisFrame = true;
                        double fullWidth = 264.0;
                        double maxLift = 30.0; // example value

                        double dx = this.position.getX() - box.getX();
                        dx = Math.max(0, Math.min(dx, fullWidth));
                        double half = fullWidth / 2.0;

                        double lift;
                        if (dx <= half) {
                            lift = (dx / half) * maxLift;       // up
                        } else {
                            lift = ((fullWidth - dx) / half) * maxLift; // down
                        }

                        // Store base Y only on FIRST bridge contact
                        if (bridgeBaseY == null) {
                            bridgeBaseY = this.getY();  // <-- no teleporting
                        }

                        // Apply the offset relative to starting Y
                        this.setY(bridgeBaseY - lift);

                        sensor.onCollide(box);
                        break;
                }
            }
        }
        if (!touchingBridgeThisFrame) {
            bridgeBaseY = null;
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

    private void handleWalkingSound() {
        boolean isWalking = getMotion().isMoving();

        if (isWalking && !wasWalking) {
            walkingSound.loop();
        } else if (!isWalking && wasWalking) {
            walkingSound.stop();
        }

        wasWalking = isWalking;
    }

    public double getY() {
        return position.getY();
    }

    public void setY(double y){
        this.position.setY(y);
    }
}