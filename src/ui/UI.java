//package ui;
//
//import input.KeyInput;
//import input.MouseInput;
//
//public class UI {
//
//    private final KeyInput keyInput;
//    private final MouseInput mouseInput;
//
//    // Example internal state (e.g., menu open, buttons, etc.)
//    private boolean showInventory = false;
//
//    public UI(KeyInput keyInput, MouseInput mouseInput) {
//        this.keyInput = keyInput;
//        this.mouseInput = mouseInput;
//    }
//
//    public void update() {
//        // --- Handle keyboard shortcuts ---------------------------------
//        //if (keyInput.isKeyPressed('I')) {    // Example: toggle inventory
//            showInventory = !showInventory;
//        }
//
//        // --- Handle mouse clicks --------------------------------------
//        if (mouseInput.isLeftPressed()) {
//            int mx = mouseInput.getMouseX();
//            int my = mouseInput.getMouseY();
//
//            // Example: detect button click area
//            if (mx >= 20 && mx <= 120 && my >= 20 && my <= 60) {
//                System.out.println("UI Button Clicked!");
//            }
//        }
//    }
//
//    p
