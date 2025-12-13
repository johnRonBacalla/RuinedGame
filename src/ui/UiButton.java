package ui;

import physics.Position;
import physics.Size;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class UiButton extends UiComponent {

    protected BufferedImage image;
    protected BufferedImage hoveredImage;
    protected BufferedImage currentImage;
    protected BufferedImage itemImage;
    protected Runnable onClick;
    protected boolean hovered;
    protected boolean pressed;
    protected boolean wasPressedInside;

    // Optional text for button (like quantity)
    protected UiText buttonText;

    public UiButton(Position position, Size size, Runnable onClick) {
        super(position, size);
        this.onClick = onClick;
        this.buttonText = null;
    }

    public void update(int mouseX, int mouseY, boolean mousePressed) {
        hovered = contains(mouseX, mouseY);

        currentImage = hovered ? hoveredImage : image;

        if (hovered && mousePressed && !wasPressedInside) {
            wasPressedInside = true;
        }

        if (!mousePressed && wasPressedInside) {
            if (hovered && onClick != null) {
                onClick.run(); // fires once
            }
            wasPressedInside = false;
        }

        // Update the text if present (optional hover or animations)
        if (buttonText != null) {
            buttonText.update(mouseX, mouseY, mousePressed);
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
        if (buttonText != null) {
            buttonText.render(g);
        }
    }
}
