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

                int tileX;
                int tileY;

                if(!(mouseX / 64 >= 0)){
                    tileX = (int) (mouseX / 64d);
                } else
                    tileX = (int) Math.ceil(mouseX / 64);

                if(!(mouseY / 64 >= 0)){
                    tileY = (int) (mouseY / 64d);
                } else
                    tileY = (int) Math.ceil(mouseY / 64);

                System.out.println(tileX);
                System.out.println(tileY);
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

    public boolean isLeftPressed(){
        return leftPressed;
    }

    public boolean isRightPressed(){
        return rightPressed;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }
}
