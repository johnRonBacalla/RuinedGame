package ui;

import physics.Position;
import physics.Size;

import java.awt.*;

public abstract class UiComponent {

    protected Position position;
    protected Size size;

    public UiComponent(Position position, Size size){
        this.position = position;
        this.size = size;
    }

    public boolean contains(int mouseX, int mouseY) {
        int x = position.intX();
        int y = position.intY();
        int w = size.getWidth();
        int h = size.getHeight();

        return mouseX >= x &&
                mouseX <= x + w &&
                mouseY >= y &&
                mouseY <= y + h;
    }

    // Old parameterless update - keep for backward compatibility if needed
    public void update(){}

    // NEW: Add this method - default does nothing
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        // Default behavior: do nothing
        // Subclasses like UiButton, UiText, SignButton will override this
    }

    public abstract void render(Graphics2D g);

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }
}