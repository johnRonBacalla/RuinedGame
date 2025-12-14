package inventory;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlaceableItem extends Item{
    public PlaceableItem(int id, String name, BufferedImage icon) {
        super(id, name, icon);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g) {

    }
}
