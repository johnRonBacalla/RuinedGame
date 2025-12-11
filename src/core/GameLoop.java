package core;

import display.Display;

import java.awt.*;

public class GameLoop implements Runnable{

    private Thread gameThread;
    private Game game;

    // FPS | UPS counters
    private int fps;
    private int ups;

    public GameLoop(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        boolean running = true;
        final double UPS_TARGET = 60.0;
        final double UPDATE_INTERVAL = 1_000_000_000 / UPS_TARGET;

        long previousTime = System.nanoTime();
        double lag = 0.0;

        long timer = System.currentTimeMillis();
        int frames = 0;
        int updates = 0;

        while (running) {
            long currentTime = System.nanoTime();
            long elapsed = currentTime - previousTime;
            previousTime = currentTime;

            lag += elapsed;

            // --- FIXED UPDATE LOOP ---
            while (lag >= UPDATE_INTERVAL) {
                update();
                updates++;
                lag -= UPDATE_INTERVAL;
            }

            // --- RENDER ---
            render();
            frames++;

            // --- Sleep to reduce CPU usage ---
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // --- Update FPS/UPS counters every 1 second ---
            if (System.currentTimeMillis() - timer >= 1000) {
                fps = frames;
                ups = updates;
                frames = 0;
                updates = 0;
                timer += 1000;

                System.out.println("FPS: " + fps + " | UPS: " + ups);
            }
        }
    }

    private void update() {
        Display.updateFade();
        game.update();
    }

    private void render() {
        game.render();
    }
}
