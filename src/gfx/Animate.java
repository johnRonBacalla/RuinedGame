package gfx;

import java.awt.image.BufferedImage;

public class Animate {

    private BufferedImage[] frames;
    private int currentFrame;
    private int totalFrames;
    private long lastTime;
    private long delay;       // nanoseconds per frame
    private boolean loop;     // should the animation loop
    private boolean finished; // has the animation finished
    private boolean playing;  // is the animation currently playing

    /**
     * Create animation.
     * @param frames Array of frames.
     * @param fps Frames per second.
     * @param loop Should the animation loop? (true for idle/walk, false for attack)
     */
    public Animate(BufferedImage[] frames, int fps, boolean loop) {
        this.frames = frames;
        this.totalFrames = frames.length;
        this.currentFrame = 0;
        this.delay = 1_000_000_000L / fps;
        this.lastTime = System.nanoTime();
        this.loop = loop;
        this.finished = false;
        this.playing = true; // Start playing by default
    }

    /**
     * Constructor without loop argument. Defaults to looping (for idle/walk animations).
     * @param frames Array of frames.
     * @param fps Frames per second.
     */
    public Animate(BufferedImage[] frames, int fps) {
        this(frames, fps, true); // default to loop
    }

    /**
     * Updates the animation. Should be called every tick.
     */
    public void update() {
        if(finished || !playing) return;

        long currentTime = System.nanoTime();
        if(currentTime - lastTime >= delay) {
            currentFrame++;
            if(currentFrame >= totalFrames) {
                if(loop) {
                    currentFrame = 0;
                } else {
                    currentFrame = totalFrames - 1; // stay on last frame
                    finished = true;
                }
            }
            lastTime = currentTime;
        }
    }

    /**
     * Reset animation to the first frame and mark as not finished.
     */
    public void reset() {
        currentFrame = 0;
        finished = false;
        lastTime = System.nanoTime();
    }

    /**
     * Start/resume playing the animation.
     */
    public void play() {
        playing = true;
        lastTime = System.nanoTime(); // Reset timer to prevent frame skip
    }

    /**
     * Pause the animation.
     */
    public void pause() {
        playing = false;
    }

    /**
     * Stop the animation (pause and reset to first frame).
     */
    public void stop() {
        playing = false;
        currentFrame = 0;
        finished = false;
    }

    /**
     * Check if the animation has finished (useful for attack animations).
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Alias for isFinished() - more readable in some contexts.
     */
    public boolean hasFinished() {
        return finished;
    }

    /**
     * Check if the animation is currently playing.
     */
    public boolean isPlaying() {
        return playing;
    }

    public BufferedImage getCurrentFrame() {
        return frames[currentFrame];
    }

    public void changeFrames(BufferedImage[] newFrames, boolean loop) {
        this.frames = newFrames;
        this.totalFrames = newFrames.length;
        this.loop = loop;
        reset();
    }

    public int getWidth() {
        return frames[0].getWidth();
    }

    public int getHeight() {
        return frames[0].getHeight();
    }
}