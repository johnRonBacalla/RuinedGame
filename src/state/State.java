package state;

import core.Game;
import input.MouseInput;
import physics.Size;
import input.KeyInput;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class State {

    protected KeyInput input;
    protected MouseInput mouseInput;
    protected Size stateSize;

    public State(Game game, KeyInput input, MouseInput mouseInput) {
        this.input = input;
        this.mouseInput = mouseInput;
        this.stateSize = game.getWindowSize();
    }

    public abstract void update();

    public abstract void render(Graphics2D g);
}
