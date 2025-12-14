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
        loadSheet("objChestClosed", "/objects/chestClosed.png", 1, 58, 41, 0);
        loadSheet("objDeadPineTree", "/objects/deadPineTree.png", 1, 72, 129, 0);
        loadSheet("objPineTree", "/objects/pineTree.png", 1, 108, 178, 0);
        loadSheet("objStump", "/objects/stump.png", 1, 112, 82, 0);
        loadSheet("objRockOne", "/objects/rockOne.png", 1, 54, 48, 0);
        loadSheet("objRockTwo", "/objects/rockTwo.png", 1, 64, 56, 0);
        loadSheet("objBed", "/objects/bed.png", 1, 96, 128, 0);

        loadSheet("itemButton", "/assets/itemButton.png", 3, 76, 76, 0);
        loadSheet("longButton", "/assets/longButton.png", 2, 288, 86, 0);
        loadSheet("midButton", "/assets/midButton.png", 2, 164, 86, 0);
        loadSheet("shortButton", "/assets/shortButton.png", 2, 90, 86, 0);
        loadSheet("fenceButton", "/assets/fenceButton.png", 2, 172, 378, 0);
        loadSheet("giantButton", "/assets/giantButton.png", 2, 576, 172, 0);
        loadSheet("logo", "/assets/logo.png", 1, 690, 214, 0);

        loadSheet("catalog", "/assets/catalog.png", 16, 64, 64, 0);

        loadSheet("swordIdle", "/weapons/rightSword.png", 9, 192, 192, 0);
        loadSheet("swordAttack", "/weapons/rightSword.png", 9, 192, 192, 1);
        loadSheet("hammerIdle", "/weapons/rightHammer.png", 9, 192, 192, 0);
        loadSheet("hammerAttack", "/weapons/rightHammer.png", 9, 192, 192, 1);
        loadSheet("spearIdle", "/weapons/rightSpear.png", 9, 192, 192, 0);
        loadSheet("spearAttack", "/weapons/rightSpear.png", 9, 192, 192, 1);

        loadSheet("firePlant", "/placeable/firePlant.png", 9, 64, 64, 0);
        loadSheet("icePlant", "/placeable/icePlant.png", 9, 64, 64, 0);
        loadSheet("earthPlant", "/placeable/earthPlant.png", 9, 64, 64, 0);
        loadSheet("windPlant", "/placeable/windPlant.png", 9, 64, 64, 0);

        loadSheet("fireReady", "/placeable/fireReady.png", 9, 64, 64, 0);
        loadSheet("iceReady", "/placeable/iceReady.png", 9, 64, 64, 0);
        loadSheet("earthReady", "/placeable/earthReady.png", 9, 64, 64, 0);
        loadSheet("windReady", "/placeable/windReady.png", 9, 64, 64, 0);

        loadSheet("fireTower1", "/placeable/fireTower1.png", 15, 64, 64, 0);
        loadSheet("iceTower1", "/placeable/iceTower1.png", 15, 64, 64, 0);
        loadSheet("earthTower1", "/placeable/earthTower1.png", 15, 64, 64, 0);
        loadSheet("windTower1", "/placeable/windTower1.png", 15, 64, 64, 0);

        loadSheet("fireTower2", "/placeable/fireTower2.png", 15, 64, 64, 0);
        loadSheet("iceTower2", "/placeable/iceTower2.png", 15, 64, 64, 0);
        loadSheet("earthTower2", "/placeable/earthTower2.png", 15, 64, 64, 0);
        loadSheet("windTower2", "/placeable/windTower2.png", 15, 64, 64, 0);

        loadSheet("skeleAnim", "/mobs/skele.png", 4, 64, 64, 0);
        loadSheet("batAnim", "/mobs/bat.png", 3, 64, 64, 0);
        loadSheet("goblinAnim", "/mobs/goblin.png", 4, 64, 64, 0);
        loadSheet("golemAnim", "/mobs/golem.png", 8, 64, 64, 0);
        loadSheet("magmaBomberAnim", "/mobs/magmaBomber.png", 3, 64, 64, 0);
        loadSheet("ogreAnim", "/mobs/ogre.png", 8, 64, 64, 0);
        loadSheet("succubusAnim", "/mobs/succubus.png", 4, 64, 64, 0);
        loadSheet("witchAnim", "/mobs/witch.png", 4, 64, 64, 0);



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
