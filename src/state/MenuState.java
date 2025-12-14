package state;

import core.Game;
import gfx.LoadSprite;
import gfx.SpriteLibrary;
import input.KeyInput;
import input.MouseInput;
import physics.Position;
import ui.LongUiButton;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuState extends State {

    private float xf = 0, yf = 0;
    private float speedX = 0.5f, speedY = 0.5f;
    private int x, y;

    private MouseInput mouseInput;
    private BufferedImage parallaxBg;
    private BufferedImage logo;
    private LongUiButton play;

    public MenuState(Game game, SpriteLibrary spriteLibrary, KeyInput keyInput, MouseInput mouse) {
        super(game, spriteLibrary, keyInput, mouse);

        this.mouseInput = mouse;

        parallaxBg = LoadSprite.load("/openMenu/paralax.png");
        logo = spriteLibrary.getFrame("logo", 0);

        play = new LongUiButton(spriteLibrary, new Position(368, 300), "Play", () -> {
            game.setState(new SaveState(game, spriteLibrary, keyInput, mouse));
        });
    }

    @Override
    public void update() {
        xf -= speedX;
        yf -= speedY;

        if (xf <= -stateSize.getWidth()) xf = 0;
        if (yf <= -stateSize.getHeight()) yf = 0;

        x = (int) xf;
        y = (int) yf;

        play.update(mouseInput.getMouseX(), mouseInput.getMouseY(), mouseInput.isLeftClicked());
    }

    @Override
    public void render(Graphics2D g) {
        int width = stateSize.getWidth();
        int height = stateSize.getHeight();

        g.drawImage(parallaxBg, x, y, width, height, null);
        g.drawImage(parallaxBg, x + width, y, width, height, null);
        g.drawImage(parallaxBg, x, y + height, width, height, null);
        g.drawImage(parallaxBg, x + width, y + height, width, height, null);

        g.drawImage(logo, 167, 32, logo.getWidth(), logo.getHeight(), null);

        play.render(g);
    }
}
