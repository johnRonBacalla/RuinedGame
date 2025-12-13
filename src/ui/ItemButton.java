package ui;

import gfx.SpriteLibrary;
import inventory.InventoryManager;
import physics.Position;
import physics.Size;
import java.awt.*;

public class ItemButton extends UiButton{

    public InventoryManager inventory;
    public int itemId;

    public ItemButton(SpriteLibrary spriteLibrary, InventoryManager inventory, Position position,int itemId, Runnable onClick) {
        super(position, new Size(76, 76), onClick);
        this.image = spriteLibrary.getFrame("itemButton", 0);
        this.hoveredImage = spriteLibrary.getFrame("itemButton", 1);
        this.currentImage = image;
        this.inventory = inventory;
        this.itemImage = inventory.getItemImage(itemId);
        this.itemId = itemId;

        this.buttonText = new UiText("x", new Position(this.getPosition().getX() + 49, this.getPosition().getY() + 50), 15, false);
        buttonText.setText(String.valueOf(inventory.getItemStack(itemId).getQuantity()));
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

            g.setColor(Color.darkGray);
            g.fillRect(getPosition().intX() + 48,
                    getPosition().intY() + 55,
                    getSize().getWidth() - 50,
                    getSize().getHeight() - 55
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
