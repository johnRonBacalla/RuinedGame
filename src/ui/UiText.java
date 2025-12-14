package ui;

import physics.Position;
import physics.Size;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.IOException;

public class UiText extends UiComponent {

    private String text;
    private Font font;
    private Color color = Color.WHITE;
    private Color hoverColor = Color.RED;
    private Color shadowColor = Color.darkGray;

    private boolean hoverable = false;
    private boolean hovered = false;

    private int shadowOffsetX = 2;
    private int shadowOffsetY = 2;

    public UiText(String text, Position position, int fontSize, boolean hoverable) {
        super(position, new Size(0, 0)); // width will be calculated dynamically
        this.text = text;
        this.hoverable = hoverable;
        loadFont("/fonts/joystixMonospace.otf", fontSize);
        calculateSize();
    }

    public void setHovered(boolean is){
        hovered = is;
    }

    private void loadFont(String fontPath, int fontSize) {
        try {
            // Load font as a resource stream
            Font loadedFont = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream(fontPath)
            );
            font = loadedFont.deriveFont(Font.PLAIN, fontSize);
        } catch (FontFormatException | IOException | NullPointerException e) {
            e.printStackTrace();
            font = new Font("Arial", Font.PLAIN, fontSize); // fallback
        }
    }

    private void calculateSize() {
        if (font == null || text == null) return;

        String[] lines = text.split("\n");
        int maxWidth = 0;
        FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);

        for (String line : lines) {
            int lineWidth = (int) font.getStringBounds(line, frc).getWidth();
            if (lineWidth > maxWidth) maxWidth = lineWidth;
        }

        int lineHeight = (int) font.getStringBounds("Ay", frc).getHeight(); // approx height
        size = new Size(maxWidth + shadowOffsetX, lineHeight * lines.length + shadowOffsetY);
    }


    public void setText(String text) {
        this.text = text;
        calculateSize();
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    public void setShadowColor(Color shadowColor) {
        this.shadowColor = shadowColor;
    }

    public void setFontSize(int fontSize) {
        if (font != null) {
            font = font.deriveFont((float) fontSize);
            calculateSize();
        }
    }

    public void setShadowOffset(int x, int y) {
        this.shadowOffsetX = x;
        this.shadowOffsetY = y;
        calculateSize();
    }

    public void update(int mouseX, int mouseY, boolean mousePressed) {
        if (hoverable) {
            hovered = contains(mouseX, mouseY);
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (text == null || font == null) return;

        g.setFont(font);

        int lineHeight = g.getFontMetrics().getHeight();

        String[] lines = text.split("\n");

        for (int i = 0; i < lines.length; i++) {
            int yPos = getPosition().intY() + lineHeight * (i + 1);

            // Draw shadow
            g.setColor(shadowColor);
            g.drawString(lines[i], getPosition().intX() + shadowOffsetX, yPos + shadowOffsetY);

            // Draw main text
            g.setColor(hoverable && hovered ? hoverColor : color);
            g.drawString(lines[i], getPosition().intX(), yPos);
        }
    }

}
