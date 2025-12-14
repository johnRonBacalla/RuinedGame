package entity.placeable.towers;

import gfx.Animate;
import gfx.SpriteLibrary;
import entity.moving.MovingEntity;
import physics.Position;

import java.awt.*;

public class IceTower1 extends Tower {

    private int aoeRadius;
    private int visualEffectTimer;
    private static final int EFFECT_DURATION = 15; // frames

    public IceTower1(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites, TowerType.AOE);

        animations.put("iceTower1", new Animate(sprites.get("iceTower1"), 12));
        currentAnimation = animations.get("iceTower1");

        // Configure Ice Tower stats
        this.damage = 10; // Lower damage but hits multiple enemies
        this.range = 200.0;
        this.attackSpeed = 60; // Attack every 1 second
        this.aoeRadius = 200;
        this.visualEffectTimer = 0;
    }

    @Override
    protected void attack() {
        if (enemiesInRange.isEmpty()) return;

        System.out.println("Ice Tower AOE freezing " + enemiesInRange.size() + " enemies");

        // Damage ALL enemies in range
        for (MovingEntity enemy : enemiesInRange) {
            if (!enemy.isDead()) {
                enemy.takeDamage(damage);
                // TODO: Add slow effect here later
            }
        }

        // Trigger visual effect
        visualEffectTimer = EFFECT_DURATION;
    }

    @Override
    public void update() {
        super.update();

        // Update visual effect timer
        if (visualEffectTimer > 0) {
            visualEffectTimer--;
        }
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);

        // Render AOE blast effect when attacking
        if (visualEffectTimer > 0) {
            renderAoeEffect(g);
        }
    }

    /**
     * Render visual AOE ice damage effect
     */
    private void renderAoeEffect(Graphics2D g) {
        Position center = getTowerCenter();

        // Pulsing effect based on timer
        float alpha = (float)visualEffectTimer / EFFECT_DURATION;
        int radius = (int)(aoeRadius * (1.0f - alpha * 0.3f)); // Shrinking effect

        // Ice blue color with particles
        g.setColor(new Color(100, 200, 255, (int)(150 * alpha)));
        g.fillOval(
                (int)(center.getX() - radius),
                (int)(center.getY() - radius),
                radius * 2,
                radius * 2
        );

        // Outer ring
        g.setColor(new Color(150, 220, 255, (int)(200 * alpha)));
        g.setStroke(new BasicStroke(3));
        g.drawOval(
                (int)(center.getX() - radius),
                (int)(center.getY() - radius),
                radius * 2,
                radius * 2
        );

        // Inner ring
        int innerRadius = radius / 2;
        g.setColor(new Color(200, 240, 255, (int)(180 * alpha)));
        g.drawOval(
                (int)(center.getX() - innerRadius),
                (int)(center.getY() - innerRadius),
                innerRadius * 2,
                innerRadius * 2
        );

        // Ice crystals effect (small diamonds around the blast)
        g.setColor(new Color(220, 240, 255, (int)(220 * alpha)));
        for (int i = 0; i < 8; i++) {
            double angle = (i / 8.0) * Math.PI * 2;
            int crystalX = (int)(center.getX() + Math.cos(angle) * radius * 0.8);
            int crystalY = (int)(center.getY() + Math.sin(angle) * radius * 0.8);

            int[] xPoints = {crystalX, crystalX - 5, crystalX, crystalX + 5};
            int[] yPoints = {crystalY - 8, crystalY, crystalY + 8, crystalY};
            g.fillPolygon(xPoints, yPoints, 4);
        }
    }

    @Override
    protected MovingEntity selectTarget() {
        // AOE doesn't need specific targeting, just return first enemy
        return enemiesInRange.isEmpty() ? null : enemiesInRange.get(0);
    }
}