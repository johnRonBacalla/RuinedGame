package core;

import display.Display;
import gfx.SpriteLibrary;
import input.KeyInput;
import input.MouseInput;
import physics.Size;
import state.MenuState;
import state.SaveState;
import state.State;
import state.StateManager;

public class Game {

    public final static int SPRITE_SIZE = 64;

    private Display display;
    private Size windowSize;
    private KeyInput keyInput;
    private MouseInput mouseInput;
    private StateManager state;
    private SpriteLibrary spriteLibrary;

    public Game(int width, int height) {
        this.spriteLibrary = new SpriteLibrary();
        keyInput = new KeyInput();
        mouseInput = new MouseInput();
        display = new Display(width, height, keyInput, mouseInput);
        windowSize = new Size(width, height);
        state = new StateManager();
        state.setCurrent(new MenuState(this, spriteLibrary, keyInput, mouseInput));
    }

    public void update() {
        if(state != null){
            state.update();
        }
    }

    public void setState(State newState) {
        if (state != null) {
            state.setCurrent(newState);
        }
    }


    public void render() {
        display.render(state.getCurrentState());
    }

    public Size getWindowSize() {
        return windowSize;
    }
}
