package display;

import input.KeyInput;
import state.State;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Display extends JFrame {

    private Canvas canvas;

    public Display(int width, int height, KeyInput input) {
        setTitle(" ");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(true);

        add(canvas);
        canvas.addKeyListener(input);
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

        g.dispose();
        bufferStrategy.show();
        Toolkit.getDefaultToolkit().sync();
    }
}
