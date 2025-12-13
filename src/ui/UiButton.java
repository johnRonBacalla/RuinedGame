package ui;

import physics.Position;
import physics.Size;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class UiButton extends UiComponent{

    protected BufferedImage image;
    protected BufferedImage hoveredImage;
    protected BufferedImage currentImage;
    protected Runnable onClick;
    protected boolean hovered;
    protected boolean pressed;
    protected boolean wasPressedInside;

    public UiButton(Position position, Size size, Runnable onClick) {
        super(position, size);
        this.onClick = onClick;
    }

    public void update(int mouseX, int mouseY, boolean mousePressed) {
        hovered = contains(mouseX, mouseY);

        currentImage = hovered ? hoveredImage : image;

        // Mouse button just pressed
        if (hovered && mousePressed && !wasPressedInside) {
            wasPressedInside = true;
        }

        // Mouse button just released
        if (!mousePressed && wasPressedInside) {
            if (hovered && onClick != null) {
                onClick.run(); // ✅ fires ONCE
            }
            wasPressedInside = false;
        }
    }

    @Override
    public void render(Graphics2D g) {
        if(currentImage != null){
            g.drawImage(currentImage,
                    getPosition().intX(),
                    getPosition().intY(),
                    getSize().getWidth(),
                    getSize().getHeight(), null);
        } else {
            g.setColor(Color.ORANGE);
            g.drawRect(getPosition().intX(),
                    getPosition().intY(),
                    getSize().getWidth(),
                    getSize().getHeight());
        }
    }
}
