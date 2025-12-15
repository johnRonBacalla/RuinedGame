package ui;

import physics.Position;
import physics.Size;

import java.awt.*;

public class UIDialogue extends UiComponent {

    private String message;
    private UiText messageText;

    private boolean visible = false;
    private Runnable onClose;

    // OK button bounds
    private Rectangle okButtonBounds;
    private boolean hoveringOk = false;

    public UIDialogue(Runnable onClose) {
        super(new Position(0, 0), new Size(0, 0));
        this.onClose = onClose;

        messageText = new UiText("", new Position(0, 0), 28, false);
        messageText.setColor(Color.WHITE);
        messageText.setShadowColor(new Color(0, 0, 0, 180));
    }

    public void show(String message) {
        this.message = message;
        messageText.setText(message);

        int screenW = 1280;
        int screenH = 720;

        int padding = 60;
        int boxWidth = Math.max(600, messageText.getSize().getWidth() + padding * 2);
        int boxHeight = messageText.getSize().getHeight() + 180;

        int boxX = screenW / 2 - boxWidth / 2;
        int boxY = screenH / 2 - boxHeight / 2;

        position = new Position(boxX, boxY);
        size = new Size(boxWidth, boxHeight);

        // Center text
        int textX = screenW / 2 - messageText.getSize().getWidth() / 2;
        int textY = boxY + 60;
        messageText.setPosition(new Position(textX, textY));

        // OK button
        int buttonW = 160;
        int buttonH = 50;
        int buttonX = screenW / 2 - buttonW / 2;
        int buttonY = boxY + boxHeight - 90;

        okButtonBounds = new Rectangle(buttonX, buttonY, buttonW, buttonH);

        visible = true;
    }

    public void hide() {
        visible = false;
        if (onClose != null) {
            onClose.run();
        }
    }

    public boolean isVisible() {
        return visible;
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        if (!visible) return;

        hoveringOk = okButtonBounds.contains(mouseX, mouseY);

        if (hoveringOk && mousePressed) {
            hide();
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (!visible) return;

        int boxX = (int) position.getX();
        int boxY = (int) position.getY();
        int boxW = size.getWidth();
        int boxH = size.getHeight();

        // Background
        g.setColor(new Color(0, 0, 0, 220));
        g.fillRoundRect(boxX, boxY, boxW, boxH, 30, 30);

        // Border
        g.setColor(new Color(200, 150, 50));
        g.setStroke(new BasicStroke(6));
        g.drawRoundRect(boxX, boxY, boxW, boxH, 30, 30);

        // Message
        messageText.render(g);

        // OK Button
        g.setColor(hoveringOk ? new Color(220, 180, 80) : new Color(180, 140, 60));
        g.fillRoundRect(
                okButtonBounds.x,
                okButtonBounds.y,
                okButtonBounds.width,
                okButtonBounds.height,
                16,
                16
        );

        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(3));
        g.drawRoundRect(
                okButtonBounds.x,
                okButtonBounds.y,
                okButtonBounds.width,
                okButtonBounds.height,
                16,
                16
        );

        // OK text
        g.setFont(new Font("Arial", Font.BOLD, 22));
        FontMetrics fm = g.getFontMetrics();
        int textX = okButtonBounds.x + okButtonBounds.width / 2 - fm.stringWidth("OK") / 2;
        int textY = okButtonBounds.y + okButtonBounds.height / 2 + fm.getAscent() / 2 - 4;

        g.drawString("OK", textX, textY);
    }
}
