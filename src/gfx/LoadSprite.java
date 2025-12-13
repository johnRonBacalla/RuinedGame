package gfx;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LoadSprite {

    public static BufferedImage load(String path) {
        try {
            var stream = LoadSprite.class.getResourceAsStream(path);
            System.out.println("loaded: " + path);
            if (stream == null)
                throw new IllegalArgumentException("Resource not found at: " + path);

            BufferedImage img = ImageIO.read(stream);
            return toCompatibleImage(img); // <--- IMPORTANT

        } catch (IOException e) {
            throw new RuntimeException("Failed to read image: " + path, e);
        }
    }

    public static BufferedImage toCompatibleImage(BufferedImage image) {
        GraphicsConfiguration gc = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();

        BufferedImage compatible = gc.createCompatibleImage(
                image.getWidth(),
                image.getHeight(),
                image.getTransparency()
        );

        Graphics2D g2d = compatible.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return compatible;
    }

    public static BufferedImage get(BufferedImage image){
        return image;
    }

    public static Font loadFont(String path, float size) {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File(path));
            return font.deriveFont(size);
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("Arial", Font.PLAIN, (int) size);
        }
    }
}
