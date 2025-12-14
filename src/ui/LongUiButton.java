package ui;

import gfx.SpriteLibrary;
import physics.Position;
import physics.Size;

public class LongUiButton extends UiButton {

    // store centered base position
    private int textBaseX;
    private int textBaseY;

    public LongUiButton(SpriteLibrary spriteLibrary, Position position, String text, Runnable onClick) {
        super(position, new Size(288, 86), onClick);

        this.image = spriteLibrary.getFrame("longButton", 0);
        this.hoveredImage = spriteLibrary.getFrame("longButton", 1);
        this.currentImage = image;

        // text should NOT handle hover by itself
        this.buttonText = new UiText(text, new Position(0, 0), 36, true);

        // calculate centered position ONCE
        textBaseX = position.intX()
                + (size.getWidth() - buttonText.getSize().getWidth()) / 2;

        textBaseY = (position.intY()
                + (size.getHeight() - buttonText.getSize().getHeight()) / 2) - 13;

        buttonText.setPosition(new Position(textBaseX, textBaseY));
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        super.update(mouseX, mouseY, mousePressed);

        int yOffset = hovered ? + 6 : 0;

        // only apply offset, NEVER recenter again
        buttonText.setPosition(
                new Position(textBaseX, textBaseY + yOffset)
        );

        buttonText.setHovered(hovered);
    }

    public UiText getText() {
        return buttonText;
    }
}
