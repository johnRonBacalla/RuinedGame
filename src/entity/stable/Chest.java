package entity.stable;

import entity.GameObject;
import gfx.Animate;
import gfx.SpriteLibrary;

import java.util.ArrayList;
import java.util.List;

public class Chest extends GameObject {
    private final String id;  // Unique identifier for save/load
    private List<String> items = new ArrayList<>();

    public Chest(double x, double y, SpriteLibrary sprites, String id) {
        super(x, y, sprites);
        this.id = id;
//        this.box = new Collision(this, 55, 110, 75, 30);

        animations.put("objChestClosed", new Animate(sprites.get("objChestClosed"), 1));
        currentAnimation = animations.get("objChestClosed");
    }

    // Getter for ID (needed for saving)
    public String getId() {
        return id;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }
}