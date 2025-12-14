package entity.placeable.towers;

import entity.GameObject;
import entity.moving.MovingEntity;
import entity.moving.Player;
import gfx.SpriteLibrary;
import physics.Position;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

public abstract class Tower extends GameObject {

    // Tower stats
    protected int damage;
    protected double range;
    protected int attackSpeed; // frames between attacks
    protected int attackCooldown;

    // Targeting
    protected MovingEntity currentTarget;
    protected List<MovingEntity> enemiesInRange;

    // Tower type
    protected TowerType towerType;

    // Visual
    protected boolean showRange = false; // Toggle with key

    public enum TowerType {
        PROJECTILE,  // Shoots projectiles at enemies
        AOE,         // Area damage around tower
        WALL         // Creates walls/barriers
    }

    public Tower(double x, double y, SpriteLibrary sprites, TowerType type) {
        super(x, y, sprites);
        this.towerType = type;
        this.enemiesInRange = new ArrayList<>();
        this.attackCooldown = 0;

        // Default stats (override in subclasses)
        this.damage = 10;
        this.range = 200.0; // pixels
        this.attackSpeed = 60; // 1 second at 60 FPS
    }

    @Override
    public void update() {
        super.update();

        // Update cooldown
        if (attackCooldown > 0) {
            attackCooldown--;
        }

        // Clear enemies in range (will be repopulated each frame)
        enemiesInRange.clear();
        currentTarget = null;
    }

    /**
     * Check if enemies are in range and perform attack logic
     * Call this from PlayState with the list of all enemies
     */
    public void updateTower(List<MovingEntity> allEnemies) {
        // Find enemies in range
        detectEnemiesInRange(allEnemies);

        // Select target
        if (!enemiesInRange.isEmpty()) {
            currentTarget = selectTarget();
        }

        // Attack if cooldown is ready and has target
        if (attackCooldown <= 0 && currentTarget != null && !currentTarget.isDead()) {
            attack();
            attackCooldown = attackSpeed;
        }
    }

    /**
     * Detect all enemies within tower's range
     */
    protected void detectEnemiesInRange(List<MovingEntity> allEnemies) {
        Position towerCenter = getTowerCenter();

        for (MovingEntity enemy : allEnemies) {
            // Skip player and dead enemies
            if (enemy instanceof Player || enemy.isDead()) continue;

            Position enemyCenter = getEntityCenter(enemy);
            double distance = calculateDistance(towerCenter, enemyCenter);

            if (distance <= range) {
                enemiesInRange.add(enemy);
            }
        }
    }

    /**
     * Select which enemy to target (default: closest)
     * Override in subclasses for different targeting strategies
     */
    protected MovingEntity selectTarget() {
        if (enemiesInRange.isEmpty()) return null;

        // Default: Target closest enemy
        Position towerCenter = getTowerCenter();
        MovingEntity closest = null;
        double closestDist = Double.MAX_VALUE;

        for (MovingEntity enemy : enemiesInRange) {
            Position enemyCenter = getEntityCenter(enemy);
            double dist = calculateDistance(towerCenter, enemyCenter);

            if (dist < closestDist) {
                closestDist = dist;
                closest = enemy;
            }
        }

        return closest;
    }

    /**
     * Attack logic - override in subclasses
     */
    protected abstract void attack();

    /**
     * Get the center position of the tower
     */
    protected Position getTowerCenter() {
        return new Position(
                position.getX() + getFrame().getWidth() / 2.0,
                position.getY() + getFrame().getHeight() / 2.0
        );
    }

    /**
     * Get the center position of an entity
     */
    protected Position getEntityCenter(MovingEntity entity) {
        return new Position(
                entity.getPosition().getX() + entity.getFrame().getWidth() / 2.0,
                entity.getPosition().getY() + entity.getFrame().getHeight() / 2.0
        );
    }

    /**
     * Calculate distance between two positions
     */
    protected double calculateDistance(Position p1, Position p2) {
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Check if a position is within range
     */
    public boolean isInRange(Position targetPos) {
        Position towerCenter = getTowerCenter();
        return calculateDistance(towerCenter, targetPos) <= range;
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);

        // Render range circle (debug or when selected)
        if (showRange) {
            renderRange(g);
        }

        // Render targeting line to current target
        if (currentTarget != null && !currentTarget.isDead()) {
            renderTargetLine(g);
        }
    }

    /**
     * Render the tower's range circle
     */
    protected void renderRange(Graphics2D g) {
        Position center = getTowerCenter();

        // Semi-transparent circle
        g.setColor(new Color(255, 255, 0, 50)); // Yellow with transparency
        g.fillOval(
                (int)(center.getX() - range),
                (int)(center.getY() - range),
                (int)(range * 2),
                (int)(range * 2)
        );

        // Circle outline
        g.setColor(new Color(255, 255, 0, 150));
        g.setStroke(new BasicStroke(2));
        g.drawOval(
                (int)(center.getX() - range),
                (int)(center.getY() - range),
                (int)(range * 2),
                (int)(range * 2)
        );
    }

    /**
     * Render line to current target
     */
    protected void renderTargetLine(Graphics2D g) {
        Position towerCenter = getTowerCenter();
        Position targetCenter = getEntityCenter(currentTarget);

        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(2));
        g.drawLine(
                (int)towerCenter.getX(),
                (int)towerCenter.getY(),
                (int)targetCenter.getX(),
                (int)targetCenter.getY()
        );
    }

    // === Getters and Setters ===

    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage; }

    public double getRange() { return range; }
    public void setRange(double range) { this.range = range; }

    public int getAttackSpeed() { return attackSpeed; }
    public void setAttackSpeed(int attackSpeed) { this.attackSpeed = attackSpeed; }

    public TowerType getTowerType() { return towerType; }

    public MovingEntity getCurrentTarget() { return currentTarget; }

    public List<MovingEntity> getEnemiesInRange() { return enemiesInRange; }

    public void setShowRange(boolean show) { this.showRange = show; }
    public boolean isShowingRange() { return showRange; }
}