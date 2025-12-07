package state;

import core.Game;
import physics.Size;
import input.KeyInput;

import java.awt.*;

public abstract class State {

    private KeyInput input;
    private Size stateSize;

    public State(Game game, KeyInput input) {
        this.input = input;
        this.stateSize = game.getWindowSize();
    }

    public abstract void update();

    public abstract void render(Graphics2D g);
}
