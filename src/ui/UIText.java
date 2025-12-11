package ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UIText extends UIComponents {

    protected String text;
    protected int fontSize;
    protected int fontStyle;
    protected String fontFamily;
    protected Color color;

    protected boolean dropShadow;
    protected int dropShadowOffset;
    protected Color shadowColor;

    protected Font font; // actual Font object

    public UIText(String text) {
        this.text = text;
        this.fontSize = 12;
        this.fontStyle = Font.PLAIN;
        this.fontFamily = "joystixMonospace"; // name of your TTF file in res/fonts/
        this.color = Color.WHITE;

        this.dropShadow = true;
        this.dropShadowOffset = 0;
        this.shadowColor = new Color(140, 140, 140);

        loadFont();
    }

    private void loadFont() {
        try {
            // Load TTF from res/fonts/
            File fontFile = new File("res/fonts/" + fontFamily + ".otf");
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(fontStyle, (float) fontSize);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            // fallback to system font
            font = new Font("Arial", fontStyle, fontSize);
        }
    }

    @Override
    public BufferedImage getSprite() {
        if (text == null || text.isEmpty()) {
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        }

        // Temporary image to get size
        BufferedImage tmp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = tmp.createGraphics();
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        int w = fm.stringWidth(text);
        int h = fm.getHeight();
        g.dispose();

        // Create final image
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        g = img.createGraphics();
        g.setFont(font);

        if (dropShadow) {
            g.setColor(shadowColor);
            g.drawString(text, dropShadowOffset, fm.getAscent() + dropShadowOffset);
        }

        g.setColor(color);
        g.drawString(text, 0, fm.getAscent());

        g.dispose();
        return img;
    }

    @Override
    public void update() {
        // Nothing to update here, but could recalc size if needed
    }
}
