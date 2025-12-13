package physics;

import tile.TileScale;

public class Size {

    private int width;
    private int height;

    private int x;
    private int y;

    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Size(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean contains(int mx, int my) {
        return mx >= x && mx <= x + width &&
                my >= y && my <= y + height;
    }

}
