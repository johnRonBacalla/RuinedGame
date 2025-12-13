package gfx;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CutSprite {

    public static BufferedImage[] cut(BufferedImage spriteSheet, int width, int height, int frameCount, int row){

        int x = 0;
        int y = row * height;

        BufferedImage[] frames = new BufferedImage[frameCount];

        for(int i = 0; i < frameCount; i++) {

            // Extract subimage (still software)
            BufferedImage sub = spriteSheet.getSubimage(x, y, width, height);

            // Convert to GPU-friendly
            frames[i] = makeCompatible(sub);

            x += width;
        }

        return frames;
    }

    private static BufferedImage makeCompatible(BufferedImage image) {
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
}
