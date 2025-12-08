package physics.box;

import entity.GameObject;

public class Event extends Box{
    public Event(GameObject owner, double offsetX, double offsetY, double width, double height) {
        super(owner, offsetX, offsetY, width, height);
    }

    @Override
    public void onCollide(Box other) {}

    @Override
    public String getType() {
        return "event";
    }
}
