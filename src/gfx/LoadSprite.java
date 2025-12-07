package gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class LoadSprite {

    public static BufferedImage load(String path) {
        try {
            var stream = LoadSprite.class.getResourceAsStream(path);
            System.out.println("loaded: " + path);
            if (stream == null)
                throw new IllegalArgumentException("Resource not found at: " + path);

            return ImageIO.read(stream);

        } catch (IOException e) {
            throw new RuntimeException("Failed to read image: " + path, e);
        }
    }

    public static BufferedImage get(BufferedImage image){
        return image;
    }
}
