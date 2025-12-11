package input;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

public class MouseInput implements MouseListener, MouseMotionListener {

    public int mouseX, mouseY;       // Current mouse coordinates
    public boolean leftPressed;       // Left button state
    public boolean rightPressed;      // Right button state
    public boolean clicked;           // Detect a click event

    public MouseInput() {
        mouseX = 0;
        mouseY = 0;
        leftPressed = false;
        rightPressed = false;
        clicked = false;
    }

    // ---- MouseListener methods ----
    @Override
    public void mousePressed(MouseEvent e) {
        {
            if (e.getButton() == MouseEvent.BUTTON1) {
                leftPressed = true;
                System.out.println("Left click");
            }

            if (e.getButton() == MouseEvent.BUTTON3) {
                rightPressed = true;
                System.out.println("Right click");
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) leftPressed = false;
        if (e.getButton() == MouseEvent.BUTTON3) rightPressed = false;
        clicked = true; // simple click detection
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    // ---- MouseMotionListener methods ----
    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    // Call this at the end of each update cycle to reset click state
    public void update() {
        clicked = false;
    }
}
