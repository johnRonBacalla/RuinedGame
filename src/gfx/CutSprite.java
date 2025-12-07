package gfx;

import java.awt.image.BufferedImage;

public class CutSprite {

    public static BufferedImage[] cut(BufferedImage spriteSheet, int width, int height, int frameCount, int row){

        int x = 0;
        int y = row * height;

        BufferedImage[] frames = new BufferedImage[frameCount];

        for(int i = 0; i < frameCount; i++) {
            frames[i] = spriteSheet.getSubimage(x, y, width, height);
            x += width;
        }
        return frames;
    }
}
