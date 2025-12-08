package physics.box;

import entity.GameObject;

public class Collision extends Box{

    public Collision(GameObject owner, double offsetX, double offsetY, double width, double height) {
        super(owner, offsetX, offsetY, width, height);
    }

    @Override
    public String getType(){
        return "col";
    }

    @Override
    public void onCollide(Box other) {}
}
