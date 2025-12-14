package entity.placeable;

import entity.GameObject;
import gfx.Animate;
import gfx.SpriteLibrary;

public class FirePlant extends GameObject {
    private int growthStage; // 0 = seed, 1 = sprout, 2 = mature, 3 = harvestable

    public FirePlant(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites);
        this.growthStage = 0; // Start as seed
        updateAnimation();
    }

    // Constructor for loading from save
    public FirePlant(double x, double y, int stage, SpriteLibrary sprites) {
        super(x, y, sprites);
        this.growthStage = stage;
        updateAnimation();
    }

    private void updateAnimation() {
        if(growthStage == 0){
            animations.put("firePlant", new Animate(sprites.get("firePlant"), 12));
            currentAnimation = animations.get("firePlant");
        } else {
            animations.put("fireReady", new Animate(sprites.get("fireReady"), 12));
            currentAnimation = animations.get("fireReady");
        }
    }

    public void grow() {
        if (growthStage < 1) {
            growthStage++;
            updateAnimation();
        }
    }

    public int getGrowthStage() {
        return growthStage;
    }

    public void setGrowthStage(int stage) {
        this.growthStage = stage;
        updateAnimation();
    }

    public boolean isHarvestable() {
        return growthStage >= 3;
    }
}