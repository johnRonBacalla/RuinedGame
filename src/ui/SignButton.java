package ui;

import gfx.SpriteLibrary;
import physics.Position;
import physics.Size;

public class SignButton extends UiButton{

    public int textBaseX;
    public int textBaseY;

    public SignButton(SpriteLibrary spriteLibrary, String text, Position position, Runnable onClick) {
        super(position, new Size(172, 378), onClick);

        this.image = spriteLibrary.getFrame("fenceButton", 0);
        this.hoveredImage = spriteLibrary.getFrame("fenceButton", 1);
        this.buttonText = new UiText(text, new Position(0, 0), 42, true);
        this.currentImage = image;

        textBaseX = position.intX()
                + (size.getWidth() - buttonText.getSize().getWidth()) / 2;

        textBaseY = (position.intY()
                + (size.getHeight() - buttonText.getSize().getHeight()) / 2) - 64;

        buttonText.setPosition(new Position(textBaseX, textBaseY));
    }



    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        super.update(mouseX, mouseY, mousePressed);

        int yOffset = hovered ? - 6 : 0;

        // only apply offset, NEVER recenter again
        buttonText.setPosition(
                new Position(textBaseX, textBaseY + yOffset)
        );

        buttonText.setHovered(hovered);
    }
}
