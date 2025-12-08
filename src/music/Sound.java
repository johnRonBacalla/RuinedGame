package music;

import javax.sound.sampled.*;
import java.io.IOException;

public class Sound {
    private Clip clip;

    // Constructor: loads the sound file
    public Sound(String filePath) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                    getClass().getResourceAsStream(filePath)
            );

            clip = AudioSystem.getClip();
            clip.open(audioStream);
            audioStream.close(); // close stream after loading

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Play once
    public void play() {
        if (clip == null) return;
        stop();                  // stop any currently playing instance
        clip.setFramePosition(0); // rewind to beginning
        clip.start();
    }

    // Loop continuously (perfect for background music)
    public void loop() {
        if (clip == null) return;
        stop();
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    // Stop playing
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    // Optional: close and free resources (call when you no longer need the sound)
    public void close() {
        if (clip != null) {
            stop();
            clip.close();
        }
    }
}