package display;

import input.KeyInput;
import input.MouseInput;
import state.State;
//import ui.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Display extends JFrame {

    private Canvas canvas;
    private static boolean fading = false;
    private static float alpha = 0f;              // 0 = transparent, 1 = opaque
    private static boolean fadeIn = true;         // true = fade-in, false = fade-out
    private static Runnable onFadeComplete;       // callback for map change
    private static final float fadeSpeed = 0.05f; // tweak to control fade speed

    //private UI ui;

    public Display(int width, int height, KeyInput keyInput, MouseInput mouseInput) {
        setTitle(" ");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(true);

        add(canvas);
        canvas.addKeyListener(keyInput);
        canvas.addMouseListener(mouseInput);
        canvas.addMouseMotionListener(mouseInput);

        //ui = new UI(keyInput, mouseInput);

        pack();
        setLocationRelativeTo(null);

        canvas.createBufferStrategy(3);
        setVisible(true);
    }

    public void render(State state) {
        //Set initial window configurations
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();

        g.setColor(Color.darkGray);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // render the currentState
        if(state != null){
            state.render(g);
        }

        if (true) {
            Composite old = g.getComposite();
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            g.setComposite(old);
        }

        g.dispose();
        bufferStrategy.show();
        Toolkit.getDefaultToolkit().sync();
    }

    public static void startFade(Runnable afterFade) {
        if (!fading) {
            fading = true;
            fadeIn = true;
            alpha = 0f;
            onFadeComplete = afterFade;
        }
    }

    public static void updateFade() {
        if (!fading) return;

        if (fadeIn) {
            alpha += fadeSpeed;
            if (alpha >= 1f) {
                alpha = 1f;
                fadeIn = false;

                // Call the callback at full black
                if (onFadeComplete != null) {
                    onFadeComplete.run();
                }
            }
        } else {
            alpha -= fadeSpeed;
            if (alpha <= 0f) {
                alpha = 0f;
                fading = false;
            }
        }
    }
}
