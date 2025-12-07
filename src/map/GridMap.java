package map;

import java.awt.*;

public class GridMap extends Map{

    public GridMap(int width, int height) {
        super(width, height);
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.GRAY);

        // vertical lines
        for (int x = 0; x <= widthInPx; x += cellSize) {
            g.drawLine(x, 0, x, heightInPx);
        }

        // horizontal lines
        for (int y = 0; y <= heightInPx; y += cellSize) {
            g.drawLine(0, y, widthInPx, y);
        }
    }
}
