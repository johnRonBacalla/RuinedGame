package ui.buttons;

import gfx.LoadSprite;
import input.KeyInput;
import input.MouseInput;
import physics.Size;
import ui.UI;

import java.awt.*;
import java.awt.image.BufferedImage;

public class InventoryUI extends UI {

    public BufferedImage image;
    public Size size;
    public boolean wasPressed = false;

    public InventoryUI(KeyInput keyInput, MouseInput mouseInput) {
        super(keyInput, mouseInput);
        image = LoadSprite.load("/assets/inventory.png");
        size = new Size(296, 352, 432, 192);
    }

    public void update(MouseInput mouse){
        if(mouse.isLeftPressed() && !wasPressed && size.contains(mouse.getMouseX(), mouse.getMouseY())){
            System.out.println("test");
        }

        wasPressed = mouse.isLeftPressed();
    }

    public void render(Graphics2D g){
        g.drawImage(image, size.getX(), size.getY(), size.getWidth(), size.getHeight(), null);
    }
}
