package ui.buttons;

import input.MouseInput;
import physics.Size;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ButtonUI {

    private BufferedImage sprite;
    private Size size;
    private Runnable onClick;

    // Track previous mouse state to prevent spam
    private boolean wasPressed = false;

    public ButtonUI(BufferedImage sprite, Size size, Runnable onClick){
        this.sprite = sprite;
        this.size = size;
        this.onClick = onClick;
    }

    public void update(MouseInput mouse){
        boolean leftPressed = mouse.isLeftPressed();

        // Trigger click only when mouse is newly pressed inside button
        if(leftPressed && !wasPressed && size.contains(mouse.getMouseX(), mouse.getMouseY())){
            if(onClick != null) onClick.run();
        }

        wasPressed = leftPressed; // update for next frame
    }

    public void render(Graphics2D g){
        if(sprite != null){
            g.drawImage(sprite, size.getX(), size.getY(), size.getWidth(), size.getHeight(), null);
        }
    }

}
