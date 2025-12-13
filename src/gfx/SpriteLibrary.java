package gfx;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class SpriteLibrary {

    private Map<String, BufferedImage[]> animations;

    public SpriteLibrary() {
        animations = new HashMap<>();

        // player
        loadSheet("playerIdleL", "/sprites/player/stand.png", 9, 64, 64, 1);
        loadSheet("playerIdleR", "/sprites/player/stand.png", 9, 64, 64, 0);
        loadSheet("playerWalkL", "/sprites/player/walk.png", 9, 64, 64, 1);
        loadSheet("playerWalkR", "/sprites/player/walk.png", 9, 64, 64, 0);

        loadSheet("invisible", "/sprites/invisible.png",1, 64, 64, 0);

        loadSheet("emberIdleL", "/sprites/npc/ember.png", 9, 64, 64, 1);

        loadSheet("objHouse", "/objects/house.png", 1, 186, 212, 0);
        loadSheet("objTree", "/objects/tree.png", 1, 192, 192, 0);
        loadSheet("objBridge", "/objects/bridge.png", 1, 264, 132, 0);
        loadSheet("objMines", "/objects/mines.png", 1, 197, 128, 0);
        loadSheet("objBean", "/objects/beanStalk.png", 14, 192, 576, 0);

        loadSheet("itemFrame", "/assets/itemFrame.png", 1, 76, 76, 0);
        loadSheet("itemHighlight", "/assets/itemHighlight.png", 1, 76, 76, 0);
    }

    private void loadSheet(String key, String path, int frameCount, int width, int height, int row) {
        BufferedImage sheet = LoadSprite.load(path);
        BufferedImage[] frames = CutSprite.cut(sheet, width, height, frameCount, row);
        animations.put(key, frames);
    }

    public BufferedImage[] get(String key) {
        return animations.get(key);
    }

    public BufferedImage getFrame(String key, int frameIndex) {
        BufferedImage[] frames = animations.get(key);

        if (frames == null) return null;                   // key not found
        if (frameIndex < 0 || frameIndex >= frames.length) // out-of-bounds safety
            return null;

        return frames[frameIndex];
    }
}
