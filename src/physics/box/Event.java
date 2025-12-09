package physics.box;

import entity.GameObject;

public class Event extends Box{

    public String signal;

    public Event(GameObject owner, String signal, double offsetX, double offsetY, double width, double height) {
        super(owner, offsetX, offsetY, width, height);
        this.signal = signal;
    }

    @Override
    public void onCollide(Box other) {}

    @Override
    public String getType() {
        return "event";
    }

    @Override
    public String getSignal() {
        return signal;
    }
}
