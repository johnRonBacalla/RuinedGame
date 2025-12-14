package input;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

public class MouseInput implements MouseListener, MouseMotionListener {

    private int mouseX, mouseY;           // Current mouse coordinates
    private boolean leftPressed;          // Left button state
    private boolean rightPressed;         // Right button state
    private boolean leftLast, rightLast;  // Previous frame state for edge detection
    private boolean clicked;              // Detect a click event (any button)

    public MouseInput() {
        mouseX = 0;
        mouseY = 0;
        leftPressed = false;
        rightPressed = false;
        leftLast = false;
        rightLast = false;
        clicked = false;
    }

    // ---- MouseListener methods ----
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = true;
            clicked = true; // optional: detect click immediately
            // Debug tile coordinates
            System.out.println("Left click: " + getTileX() + ", " + getTileY());
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            rightPressed = true;
            clicked = true;
            System.out.println("Right click");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) leftPressed = false;
        if (e.getButton() == MouseEvent.BUTTON3) rightPressed = false;
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    // ---- MouseMotionListener methods ----
    @Override
    public void mouseDragged(MouseEvent e) { updateMousePosition(e); }

    @Override
    public void mouseMoved(MouseEvent e) { updateMousePosition(e); }

    private void updateMousePosition(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    // ---- Call this at the end of each update cycle ----
    public void update() {
        leftLast = leftPressed;
        rightLast = rightPressed;
        clicked = false;
    }

    // ---- Just pressed detection ----
    public boolean isLeftClicked() {
        return leftPressed && !leftLast;
    }

    public boolean isRightClicked() {
        return rightPressed && !rightLast;
    }

    // ---- Getters ----
    public boolean isLeftPressed() { return leftPressed; }
    public boolean isRightPressed() { return rightPressed; }
    public boolean isClicked() { return clicked; }
    public int getMouseX() { return mouseX; }
    public int getMouseY() { return mouseY; }

    // ---- Optional: tile-based debug ----
    public int getTileX() {
        return mouseX >= 0 ? (int) Math.ceil(mouseX / 64.0) : (int) (mouseX / 64.0);
    }

    public int getTileY() {
        return mouseY >= 0 ? (int) Math.ceil(mouseY / 64.0) : (int) (mouseY / 64.0);
    }
}
