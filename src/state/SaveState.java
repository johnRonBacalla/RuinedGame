package state;

import core.Game;
import gfx.LoadSprite;
import gfx.SpriteLibrary;
import input.KeyInput;
import input.MouseInput;
import physics.Position;
import ui.SignButton;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SaveState extends State{

    private BufferedImage ground;
    private BufferedImage skyParalax;
    private double x;
    private double speedX;
    SignButton sign1;


    public SaveState(Game game, SpriteLibrary spriteLibrary, KeyInput keyInput, MouseInput mouse) {
        super( game,  spriteLibrary,  keyInput,  mouse);
        ground = LoadSprite.load("/openMenu/ground.png");
        skyParalax = LoadSprite.load("/openMenu/sky.png");
        sign1 = new SignButton(spriteLibrary, "LOT\nFOR\nSALE", new Position((stateSize.getWidth() / 2) - 86, 262), () -> {
            System.out.println("click");
            game.setState(new PlayState(game, spriteLibrary, keyInput, mouse));
        });

        x = -1024;       // starting offset
        speedX = 0.2;    // you can set any speed, e.g., 0.5px per frame
    }

    @Override
    public void update() {
        x += speedX;  // now fractional movement works

        if (x >= 0) {
            x = -skyParalax.getWidth();
        }
        sign1.update(mouseInput.getMouseX(), mouseInput.getMouseY(), mouseInput.isLeftClicked());
    }

    @Override
    public void render(Graphics2D g) {
        int imgWidth = skyParalax.getWidth();

        // Draw two images side by side for smooth looping
        g.drawImage(skyParalax, (int) x, 0, null);
        g.drawImage(skyParalax, (int) x + imgWidth, 0, null);

        // Draw ground and signs
        int canvasWidth = stateSize.getWidth();
        int canvasHeight = stateSize.getHeight();
        g.drawImage(ground, 0, canvasHeight - ground.getHeight(), canvasWidth, ground.getHeight(), null);
        sign1.render(g);
    }
}
