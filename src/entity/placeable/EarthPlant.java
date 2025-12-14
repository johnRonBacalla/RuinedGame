package entity.placeable;

import entity.GameObject;
import gfx.Animate;
import gfx.SpriteLibrary;

public class EarthPlant extends GameObject {
    private int growthStage; // 0 = seed, 1 = sprout, 2 = mature, 3 = harvestable

    public EarthPlant(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites);
        this.growthStage = 0; // Start as seed
        updateAnimation();
    }

    // Constructor for loading from save
    public EarthPlant(double x, double y, int stage, SpriteLibrary sprites) {
        super(x, y, sprites);
        this.growthStage = stage;
        updateAnimation();
    }

    private void updateAnimation() {
        // You can change animation based on stage if you have different sprites
        animations.put("earthPlant", new Animate(sprites.get("earthPlant"), 12));
        currentAnimation = animations.get("earthPlant");
    }

    public void grow() {
        if (growthStage < 3) {
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


