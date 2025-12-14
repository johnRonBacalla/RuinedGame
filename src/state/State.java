package state;

import core.Game;
import gfx.SpriteLibrary;
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
    protected SpriteLibrary spriteLibrary;

    public State(Game game, SpriteLibrary spriteLibrary, KeyInput input, MouseInput mouseInput) {
        this.input = input;
        this.mouseInput = mouseInput;
        this.stateSize = game.getWindowSize();
        this.spriteLibrary = spriteLibrary;
    }

    public abstract void update();

    public abstract void render(Graphics2D g);
}
