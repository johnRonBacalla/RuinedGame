package inventory;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Item {
    protected int id;
    protected String name;
    protected BufferedImage icon;

    public Item(int id, String name, BufferedImage icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public String getName() { return name; }
    public BufferedImage getIcon() { return icon; }

    public abstract void update();

    public abstract void render(Graphics2D g);
}

