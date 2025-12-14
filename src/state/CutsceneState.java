package state;

import core.Game;
import gfx.LoadSprite;
import gfx.SpriteLibrary;
import input.KeyInput;
import input.MouseInput;
import physics.Position;
import ui.SignButton;
import ui.UiButton;
import ui.UiText;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class CutsceneState extends State {

    private final List<String> slidePaths;
    private int currentSlideIndex = 0;
    private BufferedImage currentSlide;

    private final Game game;
    private final Runnable onFinish;

    private SignButton nextButton;
    private SignButton skipButton;
    private UiText startPrompt;  // Only shown on last slide

    private boolean wasLeftPressedLastFrame = false;

    public CutsceneState(Game game, SpriteLibrary spriteLibrary, KeyInput keyInput,
                         MouseInput mouseInput, List<String> slidePaths, Runnable onFinish) {
        super(game, spriteLibrary, keyInput, mouseInput);
        this.game = game;
        this.slidePaths = slidePaths;
        this.onFinish = onFinish;

        loadCurrentSlide();
        setupUI();
    }

    private void loadCurrentSlide() {
        if (currentSlideIndex < slidePaths.size()) {
            currentSlide = LoadSprite.load(slidePaths.get(currentSlideIndex));
        }
    }

    private void setupUI() {
        // "NEXT" button - always says "NEXT" (uses your beautiful fence sign)
        nextButton = new SignButton(
                spriteLibrary,
                "NEXT",
                new Position(stateSize.getWidth() / 2 - 86, stateSize.getHeight() - 200),
                this::advanceSlide
        );

        // "SKIP" button - smaller or positioned in corner
        skipButton = new SignButton(
                spriteLibrary,
                "SKIP",
                new Position(stateSize.getWidth() - 200, 40),
                onFinish
        );

        // Big prompt only for the final slide
        startPrompt = new UiText(
                "CLICK TO START",
                new Position(stateSize.getWidth() / 2 - 200, stateSize.getHeight() / 2 + 100),
                48,
                true
        );
        startPrompt.setColor(Color.WHITE);
        // We'll control visibility manually
    }

    private void advanceSlide() {
        currentSlideIndex++;

        if (currentSlideIndex >= slidePaths.size()) {
            // Cutscene over → start new game
            onFinish.run();
        } else {
            loadCurrentSlide();
        }
    }

    @Override
    public void update() {
        int mx = mouseInput.getMouseX();
        int my = mouseInput.getMouseY();

        // Detect ONLY the moment the button goes from not-pressed → pressed
        boolean justClicked = mouseInput.isLeftPressed() && !wasLeftPressedLastFrame;

        // Update buttons (they need the full pressed state for hover+click logic)
        nextButton.update(mx, my, mouseInput.isLeftPressed());
        skipButton.update(mx, my, mouseInput.isLeftPressed());

        // --- Only advance on the RISING EDGE of the click ---
        if (justClicked) {
            boolean overSkip = mx >= skipButton.getPosition().getX()
                    && mx <= skipButton.getPosition().getX() + skipButton.getSize().getWidth()
                    && my >= skipButton.getPosition().getY()
                    && my <= skipButton.getPosition().getY() + skipButton.getSize().getHeight();

            if (!overSkip) {
                advanceSlide();
            }
            // If over skip → skipButton's own click handler already ran
        }



        // Update for next frame
        wasLeftPressedLastFrame = mouseInput.isLeftPressed();
    }

    @Override
    public void render(Graphics2D g) {
        // Draw scaled slide
        if (currentSlide != null) {
            int imgW = currentSlide.getWidth();
            int imgH = currentSlide.getHeight();

            double scale = Math.min(
                    (double) stateSize.getWidth() / imgW,
                    (double) stateSize.getHeight() / imgH
            );

            int newW = (int) (imgW * scale);
            int newH = (int) (imgH * scale);
            int offsetX = (stateSize.getWidth() - newW) / 2;
            int offsetY = (stateSize.getHeight() - newH) / 2;

            g.drawImage(currentSlide, offsetX, offsetY, newW, newH, null);
        }

        // Optional subtle dark overlay at bottom for button visibility
        g.setColor(new Color(0, 0, 0, 100));
        g.fillRect(0, stateSize.getHeight() - 250, stateSize.getWidth(), 250);

        // Always render NEXT and SKIP buttons

        // On the LAST slide, show big "CLICK TO START" text instead of relying on button text
        if (currentSlideIndex == slidePaths.size() - 1) {
            // Optional: add shadow for readability
            g.setColor(new Color(0, 0, 0, 180));
            g.fillRect(0, stateSize.getHeight() / 2 + 80, stateSize.getWidth(), 100);

            startPrompt.render(g);
        }
    }
}