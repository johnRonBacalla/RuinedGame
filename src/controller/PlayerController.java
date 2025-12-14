package controller;

import java.awt.event.KeyEvent;
import input.KeyInput;

public class PlayerController implements Controller {

    private KeyInput input;

    public PlayerController(KeyInput input) {
        this.input = input;
    }

    @Override
    public boolean isRequestingUp() {
        return input.isCurrentlyPressed(KeyEvent.VK_W);
    }

    @Override
    public boolean isRequestingDown() {
        return input.isCurrentlyPressed(KeyEvent.VK_S);
    }

    @Override
    public boolean isRequestingLeft() {
        return input.isCurrentlyPressed(KeyEvent.VK_A);
    }

    @Override
    public boolean isRequestingRight() {
        return input.isCurrentlyPressed(KeyEvent.VK_D);
    }


    public boolean isRequestingPlaceItem() {
        return input.isCurrentlyPressed(KeyEvent.VK_P);
    }
}
