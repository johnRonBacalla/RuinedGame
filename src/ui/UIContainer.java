package ui;

import physics.Position;
import physics.Size;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class UIContainer extends UIComponents {

    private Color bgColor;
    private List<UIComponents> children;

    public UIContainer(){
        super();
        this.bgColor = new Color(180, 0, 0);  // slightly darker red
        this.children = new ArrayList<>();
        calculateSize();
        calculatePosition();
    }

    public void add(UIComponents component){
        children.add(component);
        update();
    }

    @Override
    public void update() {
        calculateSize();
        calculatePosition();
    }

    /** AUTO-RESIZE TO FIT CONTENTS */
    private void calculateSize(){
        int totalWidth = 0;
        int totalHeight = 0;

        for(UIComponents child : children){
            BufferedImage sprite = child.getSprite();

            if (sprite != null) {
                totalWidth = Math.max(totalWidth, sprite.getWidth());
                totalHeight = Math.max(totalHeight, sprite.getHeight());
            }
        }

        // Add padding
        totalWidth += padding.getHorizontal();
        totalHeight += padding.getVertical();

        if (totalWidth <= 0) totalWidth = 1;
        if (totalHeight <= 0) totalHeight = 1;

        size = new Size(totalWidth, totalHeight);
    }

    private void calculatePosition(){
        position = new Position(margin.getLeft(), margin.getTop());
    }

    @Override
    public BufferedImage getSprite() {
        BufferedImage image = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        // BG
        g.setColor(bgColor);
        g.fillRect(0, 0, size.getWidth(), size.getHeight());

        // DRAW CHILDREN
        int x = padding.getLeft();
        int y = padding.getTop();

        for (UIComponents child : children){
            BufferedImage sprite = child.getSprite();
            if (sprite != null){
                g.drawImage(sprite, x, y, null);
                y += sprite.getHeight(); // stacked vertically
            }
        }

        g.dispose();
        return image;
    }
}
