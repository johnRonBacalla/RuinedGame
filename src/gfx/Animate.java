package gfx;

import java.awt.image.BufferedImage;

public class Animate {

    private BufferedImage[] frames;
    private int currentFrame;
    private int totalFrames;
    private long lastTime;
    private long delay;
    private boolean lock = false;

    public Animate(BufferedImage[] frames, int fps) {
        this.frames = frames;
        this.totalFrames = frames.length;
        this.currentFrame = 0;
        this.delay = 1_000_000_000 / fps;
        this.lastTime = System.nanoTime();
    }

    public void update() {
        if(!lock){
            long currentTime = System.nanoTime();

            if(currentTime - lastTime >= delay) {
                currentFrame++;

                if(currentFrame >= totalFrames) currentFrame = 0;
                lastTime = currentTime;
            }
        }
    }

    public void showOnly(BufferedImage[] frames, int index) {
        if (frames == null || frames.length == 0) return;

        // Keep frames to be consistent with your system
        this.frames = frames;
        this.totalFrames = frames.length;

        // Clamp the index to avoid crash
        if (index < 0) index = 0;
        if (index >= frames.length) index = frames.length - 1;

        // Force current frame
        this.currentFrame = index;
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

    public void lock() {
        lock = true;
    }
}
