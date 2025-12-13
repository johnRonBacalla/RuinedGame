package ui;

import input.KeyInput;
import input.MouseInput;
import java.awt.*;

public class UI {

    private final KeyInput keyInput;
    private final MouseInput mouseInput;

    public UI(KeyInput keyInput, MouseInput mouseInput) {
        this.keyInput = keyInput;
        this.mouseInput = mouseInput;
    }

    public void update() {
    }

    public void render(Graphics2D g){
    }
}
