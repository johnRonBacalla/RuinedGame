package core;

import display.Display;
import input.KeyInput;
import physics.Size;
import state.PlayState;
import state.StateManager;

public class Game {

    public final static int SPRITE_SIZE = 64;

    private Display display;
    private Size windowSize;
    private KeyInput input;
    private StateManager state;

    public Game(int width, int height) {
        input = new KeyInput();
        display = new Display(width, height, input);
        windowSize = new Size(width, height);
        state = new StateManager();
        state.setCurrent(new PlayState(this, input));
    }

    public void update() {
        if(state != null){
            state.update();
        }
    }

    public void render() {
        display.render(state.getCurrentState());
    }

    public Size getWindowSize() {
        return windowSize;
    }
}
