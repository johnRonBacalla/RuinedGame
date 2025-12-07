package state;

import java.awt.*;

public class StateManager{

    private State currentState;

    public void setCurrent(State state){
        this.currentState = state;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void update() {
        currentState.update();
    }

    public void render(Graphics2D g) {
        currentState.render(g);
    }
}
