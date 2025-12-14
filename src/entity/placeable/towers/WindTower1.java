package entity.placeable.towers;

import entity.GameObject;
import gfx.Animate;
import gfx.SpriteLibrary;
import physics.Position;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WindTower1 extends Tower {

    private double buffRange;
    private double attackSpeedMultiplier;
    private List<Tower> buffedTowers;
    private int buffPulseTimer;
    private static final int BUFF_PULSE_DURATION = 30; // Visual pulse effect

    public WindTower1(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites, TowerType.SUPPORT);

        animations.put("windTower1", new Animate(sprites.get("windTower1"), 12));
        currentAnimation = animations.get("windTower1");

        this.buffedTowers = new ArrayList<>();
        this.buffPulseTimer = 0;

        // Configure Wind Tower stats
        this.damage = 0; // Doesn't attack, only buffs
        this.range = 0; // Doesn't need enemy detection range
        this.buffRange = 192.0; // 3 tiles (64 * 3)
        this.attackSpeed = 1; // Not used for support
        this.attackSpeedMultiplier = 0.5; // 50% faster attack speed (reduces cooldown by half)
    }

    @Override
    protected void attack() {
        // Support towers don't attack enemies
        // They continuously buff nearby towers
    }

    /**
     * Update support tower and apply buffs to nearby towers
     * Call this with list of all towers in the world
     */
    public void updateSupport(List<GameObject> allObjects) {
        buffedTowers.clear();

        Position windCenter = getTowerCenter();

        // Find all towers within buff range
        for (GameObject obj : allObjects) {
            if (obj instanceof Tower && obj != this) {
                Tower tower = (Tower) obj;
                Position towerCenter = tower.getTowerCenter();

                double distance = calculateDistance(windCenter, towerCenter);

                if (distance <= buffRange) {
                    buffedTowers.add(tower);
                }
            }
        }

        // Update buff pulse animation
        if (buffPulseTimer > 0) {
            buffPulseTimer--;
        }

        // Trigger pulse effect periodically
        if (buffedTowers.size() > 0 && buffPulseTimer == 0) {
            buffPulseTimer = BUFF_PULSE_DURATION;
        }
    }

    /**
     * Apply attack speed buff to a tower's cooldown
     * Call this when a buffed tower attacks
     */
    public int applyAttackSpeedBuff(int baseCooldown) {
        return (int)(baseCooldown * (1.0 - attackSpeedMultiplier));
    }

    /**
     * Check if a tower is currently buffed by this wind tower
     */
    public boolean isBuffing(Tower tower) {
        return buffedTowers.contains(tower);
    }

    @Override
    public void update() {
        super.update();

        if (buffPulseTimer > 0) {
            buffPulseTimer--;
        }
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);

        // Render buff range circle
        renderBuffRange(g);

        // Render buff lines to towers
        renderBuffLines(g);

        // Render pulse effect
        if (buffPulseTimer > 0) {
            renderBuffPulse(g);
        }
    }

    /**
     * Render the buff range circle
     */
    private void renderBuffRange(Graphics2D g) {
        Position center = getTowerCenter();

        // Semi-transparent green circle
        g.setColor(new Color(100, 255, 100, 30));
        g.fillOval(
                (int)(center.getX() - buffRange),
                (int)(center.getY() - buffRange),
                (int)(buffRange * 2),
                (int)(buffRange * 2)
        );

        // Circle outline
        g.setColor(new Color(150, 255, 150, 100));
        g.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                0, new float[]{10, 5}, 0)); // Dashed line
        g.drawOval(
                (int)(center.getX() - buffRange),
                (int)(center.getY() - buffRange),
                (int)(buffRange * 2),
                (int)(buffRange * 2)
        );
    }

    /**
     * Render buff connection lines to buffed towers
     */
    private void renderBuffLines(Graphics2D g) {
        Position windCenter = getTowerCenter();

        for (Tower tower : buffedTowers) {
            Position towerCenter = tower.getTowerCenter();

            // Green wavy line (simplified as straight with dashes)
            g.setColor(new Color(100, 255, 100, 150));
            g.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                    0, new float[]{8, 4}, 0)); // Dashed line
            g.drawLine(
                    (int)windCenter.getX(),
                    (int)windCenter.getY(),
                    (int)towerCenter.getX(),
                    (int)towerCenter.getY()
            );

            // Small buff icon on buffed tower
            g.setColor(new Color(150, 255, 150, 200));
            g.fillOval(
                    (int)towerCenter.getX() - 8,
                    (int)towerCenter.getY() - 40,
                    16, 16
            );

            // Draw speed arrows
            g.setColor(Color.WHITE);
            g.setStroke(new BasicStroke(2));
            // Up arrow
            int iconX = (int)towerCenter.getX();
            int iconY = (int)towerCenter.getY() - 32;
            g.drawLine(iconX - 3, iconY + 2, iconX, iconY - 2);
            g.drawLine(iconX + 3, iconY + 2, iconX, iconY - 2);
            g.drawLine(iconX, iconY - 2, iconX, iconY + 4);
        }
    }

    /**
     * Render pulsing buff wave effect
     */
    private void renderBuffPulse(Graphics2D g) {
        Position center = getTowerCenter();

        float progress = 1.0f - ((float)buffPulseTimer / BUFF_PULSE_DURATION);
        int pulseRadius = (int)(buffRange * progress);
        int alpha = (int)(150 * (1.0f - progress));

        // Expanding ring
        g.setColor(new Color(150, 255, 150, alpha));
        g.setStroke(new BasicStroke(3));
        g.drawOval(
                (int)(center.getX() - pulseRadius),
                (int)(center.getY() - pulseRadius),
                pulseRadius * 2,
                pulseRadius * 2
        );
    }

    public List<Tower> getBuffedTowers() {
        return buffedTowers;
    }

    public double getBuffRange() {
        return buffRange;
    }

    public double getAttackSpeedMultiplier() {
        return attackSpeedMultiplier;
    }

    public void setAttackSpeedMultiplier(double multiplier) {
        this.attackSpeedMultiplier = multiplier;
    }
}