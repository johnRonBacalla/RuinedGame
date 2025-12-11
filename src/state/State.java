package state;

import core.Game;
import input.MouseInput;
import physics.Size;
import input.KeyInput;

import java.awt.*;

public abstract class State {

    private KeyInput input;
    private MouseInput mouseInput;
    private Size stateSize;

    public State(Game game, KeyInput input, MouseInput mouseInput) {
        this.input = input;
        this.mouseInput = mouseInput;
        this.stateSize = game.getWindowSize();
    }

    public abstract void update();

    public abstract void render(Graphics2D g);
}
