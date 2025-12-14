package projectile;

import entity.moving.MovingEntity;
import physics.Position;
import physics.Vector;

import java.awt.*;

/**
 * Projectile that moves towards a target and deals damage on hit
 */
public class Projectile {
    private Position position;
    private Vector velocity;
    private int damage;
    private double speed;
    private boolean active;
    private MovingEntity target;

    // Visual properties
    private Color color;
    private int size;
    private String type; // "fire", "ice", "earth", "wind"

    public Projectile(Position start, MovingEntity target, int damage, double speed, String type) {
        this.position = new Position(start.getX(), start.getY());
        this.target = target;
        this.damage = damage;
        this.speed = speed;
        this.active = true;
        this.type = type;

        // Set visual based on type
        switch (type) {
            case "fire":
                this.color = new Color(255, 100, 0);
                this.size = 8;
                break;
            case "ice":
                this.color = new Color(100, 200, 255);
                this.size = 10;
                break;
            case "earth":
                this.color = new Color(139, 90, 43);
                this.size = 12;
                break;
            case "wind":
                this.color = new Color(200, 255, 200);
                this.size = 6;
                break;
            default:
                this.color = Color.WHITE;
                this.size = 8;
        }

        updateVelocity();
    }

    /**
     * Update velocity towards target (for homing projectiles)
     */
    private void updateVelocity() {
        if (target == null || target.isDead()) {
            active = false;
            return;
        }

        // Calculate direction to target center
        double targetX = target.getPosition().getX() + target.getFrame().getWidth() / 2.0;
        double targetY = target.getPosition().getY() + target.getFrame().getHeight() / 2.0;

        double dx = targetX - position.getX();
        double dy = targetY - position.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < 1) {
            active = false;
            return;
        }

        // Normalize and multiply by speed
        velocity = new Vector((dx / distance) * speed, (dy / distance) * speed);
    }

    public void update() {
        if (!active) return;

        // Update velocity to follow target (homing)
        updateVelocity();

        // Move projectile
        if (velocity != null) {
            position.add(velocity);
        }

        // Check if hit target
        if (target != null && !target.isDead()) {
            double targetX = target.getPosition().getX() + target.getFrame().getWidth() / 2.0;
            double targetY = target.getPosition().getY() + target.getFrame().getHeight() / 2.0;

            double dx = targetX - position.getX();
            double dy = targetY - position.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);

            // Hit if close enough
            if (distance < 20) {
                hit();
            }
        }

        // Deactivate if target is dead
        if (target == null || target.isDead()) {
            active = false;
        }
    }

    /**
     * Handle projectile hitting target
     */
    private void hit() {
        if (target != null && !target.isDead()) {
            target.takeDamage(damage);
        }
        active = false;
    }

    public void render(Graphics2D g) {
        if (!active) return;

        // Draw projectile as a circle with glow effect

        // Outer glow
        g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 100));
        g.fillOval(
                (int)(position.getX() - size),
                (int)(position.getY() - size),
                size * 2,
                size * 2
        );

        // Inner core
        g.setColor(color);
        g.fillOval(
                (int)(position.getX() - size/2),
                (int)(position.getY() - size/2),
                size,
                size
        );

        // Bright center
        g.setColor(Color.WHITE);
        g.fillOval(
                (int)(position.getX() - size/4),
                (int)(position.getY() - size/4),
                size/2,
                size/2
        );

        // Trail effect (optional)
        if (velocity != null) {
            g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 50));
            g.setStroke(new BasicStroke(3));
            g.drawLine(
                    (int)position.getX(),
                    (int)position.getY(),
                    (int)(position.getX() - velocity.getX() * 2),
                    (int)(position.getY() - velocity.getY() * 2)
            );
        }
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }
}