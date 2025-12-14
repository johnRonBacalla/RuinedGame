package ui;

import gfx.SpriteLibrary;
import inventory.InventoryManager;
import physics.Position;
import physics.Size;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ItemButton extends UiButton{

    public InventoryManager inventory;
    public int itemId;
    public BufferedImage equipedImage;

    public ItemButton(SpriteLibrary spriteLibrary, InventoryManager inventory, Position position,int itemId, Runnable onClick) {
        super(position, new Size(76, 76), onClick);
        this.image = spriteLibrary.getFrame("itemButton", 0);
        this.hoveredImage = spriteLibrary.getFrame("itemButton", 1);
        this.equipedImage = spriteLibrary.getFrame("itemButton", 2);
        this.currentImage = image;
        this.inventory = inventory;
        this.itemImage = inventory.getItemImage(itemId);
        this.itemId = itemId;

        this.buttonText = new UiText("x", new Position(this.getPosition().getX() + 44, this.getPosition().getY() + 49), 15, false);
        buttonText.setText(String.valueOf(inventory.getItemStack(itemId).getQuantity()));
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        super.update(mouseX, mouseY, mousePressed);

        if(inventory.getEquippedItem() == inventory.getItem(itemId)){
            currentImage = equipedImage;
        }
    }

    @Override
    public void render(Graphics2D g) {
        // Draw button background
        if (currentImage != null) {
            g.drawImage(currentImage,
                    getPosition().intX(),
                    getPosition().intY(),
                    getSize().getWidth(),
                    getSize().getHeight(), null);
        } else {
            g.setColor(Color.ORANGE);
            g.fillRect(getPosition().intX(),
                    getPosition().intY(),
                    getSize().getWidth(),
                    getSize().getHeight());
        }

        // Draw item image inside button
        if (itemImage != null) {
            g.drawImage(itemImage,
                    getPosition().intX() + 6,
                    getPosition().intY() + 6,
                    getSize().getWidth() - 12,
                    getSize().getHeight() - 12, null);
        }

        // Draw button text on top
        if (buttonText != null && !(inventory.getItemStack(itemId).getQuantity() <= 1)) {

            g.setColor(new Color(68, 44, 23));
            g.fillRect(getPosition().intX() + 42,
                    getPosition().intY() + 57,
                    getSize().getWidth() - 48,
                    getSize().getHeight() - 62
            );

            buttonText.render(g);
        }

        if(inventory.getItemStack(itemId).getQuantity() == 0){
            g.setColor(new Color(0f, 0f, 0f, 0.9f));
            g.fillRect(getPosition().intX() + 6,
                    getPosition().intY() + 6,
                    getSize().getWidth() - 12,
                    getSize().getHeight() - 12);
        }
    }
}
