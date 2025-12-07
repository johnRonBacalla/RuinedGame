package gfx;

import java.awt.image.BufferedImage;

public class Animate {

    private BufferedImage[] frames;
    private int currentFrame;
    private int totalFrames;
    private long lastTime;
    private long delay;

    public Animate(BufferedImage[] frames, int fps) {
        this.frames = frames;
        this.totalFrames = frames.length;
        this.currentFrame = 0;
        this.delay = 1_000_000_000 / fps;
        this.lastTime = System.nanoTime();
    }

    public void update() {
        long currentTime = System.nanoTime();

        if(currentTime - lastTime >= delay) {
            currentFrame++;

            if(currentFrame >= totalFrames) currentFrame = 0;
            lastTime = currentTime;
        }
    }

    public void changeAnimation(BufferedImage[] frames){
        this.frames = frames;
    }

    public BufferedImage getCurrentFrame() {
        return frames[currentFrame];
    }

    public int getWidth() {
        return frames[0].getWidth();
    }

    public int getHeight() {
        return frames[0].getHeight();
    }
}
