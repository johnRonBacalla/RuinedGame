package state;

import core.Game;
import data.SaveManager;
import gfx.LoadSprite;
import gfx.SpriteLibrary;
import input.KeyInput;
import input.MouseInput;
import physics.Position;
import physics.Size;
import ui.SignButton;
import ui.UiButton;
import ui.UiText;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SaveState extends State {

    private BufferedImage ground;
    private BufferedImage skyParalax;
    private BufferedImage cutScene1;
    private double x;
    private double speedX;

    private List<SaveFileButton> saveButtons;
    private UiButton newGameButton;
    private UiText titleText;
    private Game game;

    public SaveState(Game game, SpriteLibrary spriteLibrary, KeyInput keyInput, MouseInput mouse) {
        super(game, spriteLibrary, keyInput, mouse);
        this.game = game;
        ground = LoadSprite.load("/openMenu/ground.png");
        skyParalax = LoadSprite.load("/openMenu/sky.png");

        x = -1024;
        speedX = 0.2;

        saveButtons = new ArrayList<>();

        // Title
        titleText = new UiText("SELECT SAVE FILE",
                new Position(stateSize.getWidth() / 2 - 150, 50), 48, false);

        // Load existing save files
        loadSaveFiles();

        // New Game button
        newGameButton = new SignButton(spriteLibrary, "NEW\nGAME",
                new Position(stateSize.getWidth() / 2 - 86, 450), () -> {
            System.out.println("Starting cutscene...");

            // List all your cutscene images in order
            List<String> cutsceneSlides = List.of(
                    "/cutScenes/1st.png",
                    "/cutScenes/2nd.png",
                    "/cutScenes/3rd.png",
                    "/cutScenes/4th.png"
            );

            // What happens when cutscene ends
            Runnable startNewGame = () -> {
                System.out.println("Cutscene finished. Starting new game.");
                game.setState(new PlayState(game, spriteLibrary, keyInput, mouseInput)); // New game, no save path
            };

            // Switch to cutscene state
            game.setState(new CutsceneState(game, spriteLibrary, keyInput, mouseInput,
                    cutsceneSlides, startNewGame));
        });
    }

    private void loadSaveFiles() {
        File saveDir = new File("res/saves");
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }

        File[] files = saveDir.listFiles((dir, name) -> name.endsWith(".txt"));

        int yOffset = 120;
        int buttonSpacing = 80;

        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                File saveFile = files[i];
                String fileName = saveFile.getName();

                // Load save info
                var saveData = SaveManager.loadGame(saveFile.getAbsolutePath());
                int wave = saveData != null ? (int) saveData.get("wave") : 0;

                int finalI = i;
                SaveFileButton button = new SaveFileButton(
                        spriteLibrary,
                        fileName.replace(".txt", ""),
                        "Wave: " + wave,
                        new Position(stateSize.getWidth() / 2 - 150, yOffset + (i * buttonSpacing)),
                        () -> {
                            System.out.println("Loading save: " + saveFile.getName());
                            // Pass the FULL PATH to PlayState
                            game.setState(new PlayState(game, spriteLibrary, input, mouseInput,
                                    saveFile.getAbsolutePath())); // ✅ Pass the save file path!
                        }
                );

                saveButtons.add(button);
            }
        }
    }

    @Override
    public void update() {
        x += speedX;
        if (x >= 0) {
            x = -skyParalax.getWidth();
        }

        // Update all buttons
        for (SaveFileButton button : saveButtons) {
            button.update(mouseInput.getMouseX(), mouseInput.getMouseY(),
                    mouseInput.isLeftClicked());
        }

        newGameButton.update(mouseInput.getMouseX(), mouseInput.getMouseY(),
                mouseInput.isLeftClicked());
    }

    @Override
    public void render(Graphics2D g) {
        // Background
        int imgWidth = skyParalax.getWidth();
        g.drawImage(skyParalax, (int) x, 0, null);
        g.drawImage(skyParalax, (int) x + imgWidth, 0, null);

        int canvasWidth = stateSize.getWidth();
        int canvasHeight = stateSize.getHeight();
        g.drawImage(ground, 0, canvasHeight - ground.getHeight(),
                canvasWidth, ground.getHeight(), null);

        // Title
        titleText.render(g);

        // Save file buttons
        for (SaveFileButton button : saveButtons) {
            button.render(g);
        }

        // New game button
        newGameButton.render(g);
    }
}

// Simple save file button class
class SaveFileButton extends UiButton {
    private String fileName;
    private String info;
    private UiText fileText;
    private UiText infoText;

    public SaveFileButton(SpriteLibrary sprites, String fileName, String info,
                          Position pos, Runnable onClick) {
        super(pos, new Size(300, 60), onClick);
        this.fileName = fileName;
        this.info = info;

        fileText = new UiText(fileName, new Position(pos.getX() + 20, pos.getY() + 10), 24, false);
        infoText = new UiText(info, new Position(pos.getX() + 20, pos.getY() + 40), 18, false);
    }

    @Override
    public void render(Graphics2D g) {
        // Draw button background
        g.setColor(new Color(139, 69, 19, 200)); // Brown background
        g.fillRect((int) position.getX(), (int) position.getY(), 300, 60);

        g.setColor(Color.WHITE);
        g.drawRect((int) position.getX(), (int) position.getY(), 300, 60);

        fileText.render(g);
        infoText.render(g);
    }
}