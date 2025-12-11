package state;

import core.Game;
import input.MouseInput;
import physics.Size;
import input.KeyInput;
import ui.UIContainer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class State {

    protected KeyInput input;
    protected MouseInput mouseInput;
    protected Size stateSize;
    protected List<UIContainer> uiContainers;

    public State(Game game, KeyInput input, MouseInput mouseInput) {
        this.input = input;
        this.mouseInput = mouseInput;
        this.uiContainers = new ArrayList<>();
        this.stateSize = game.getWindowSize();
    }

    public abstract void update();

    public abstract void render(Graphics2D g);

    public List<UIContainer> getUiContainers() {
        return uiContainers;
    }
}
