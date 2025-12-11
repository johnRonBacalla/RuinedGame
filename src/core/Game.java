package core;

import display.Display;
import input.KeyInput;
import input.MouseInput;
import physics.Size;
import state.PlayState;
import state.StateManager;

public class Game {

    public final static int SPRITE_SIZE = 64;

    private Display display;
    private Size windowSize;
    private KeyInput keyInput;
    private MouseInput mouseInput;
    private StateManager state;

    public Game(int width, int height) {
        keyInput = new KeyInput();
        mouseInput = new MouseInput();
        display = new Display(width, height, keyInput, mouseInput);
        windowSize = new Size(width, height);
        state = new StateManager();
        state.setCurrent(new PlayState(this, keyInput, mouseInput));
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
