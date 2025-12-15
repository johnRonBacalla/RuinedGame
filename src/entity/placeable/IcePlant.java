package entity.placeable;

import entity.GameObject;
import gfx.Animate;
import gfx.SpriteLibrary;

public class IcePlant extends GameObject {

    // 0 = planted, 1 = ready
    private int growthStage;

    public IcePlant(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites);
        this.growthStage = 0;
        updateAnimation();
    }

    // Constructor for loading from save
    public IcePlant(double x, double y, int stage, SpriteLibrary sprites) {
        super(x, y, sprites);
        this.growthStage = stage;
        updateAnimation();
    }

    private void updateAnimation() {
        String animKey = (growthStage == 0) ? "icePlant" : "iceReady";

        if (!animations.containsKey(animKey)) {
            animations.put(animKey, new Animate(sprites.get(animKey), 12));
        }

        currentAnimation = animations.get(animKey);
    }

    public void grow() {
        if (growthStage == 0) {
            growthStage = 1;
            updateAnimation();
        }
    }

    public boolean isHarvestable() {
        return growthStage == 1;
    }

    public void harvest() {
        if (!isHarvestable()) return;

        // TODO: give ice item

        growthStage = 0;
        updateAnimation();
    }

    public int getGrowthStage() {
        return growthStage;
    }

    public void setGrowthStage(int stage) {
        this.growthStage = stage;
        updateAnimation();
    }
}
