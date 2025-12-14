package entity.placeable.towers;

import entity.GameObject;
import entity.moving.MovingEntity;
import entity.moving.Player;
import gfx.Animate;
import gfx.SpriteLibrary;
import physics.Position;
import physics.box.Box;
import physics.box.Collision;
import tile.TileScale;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class EarthTower1 extends Tower {

    private List<Wall> activeWalls;
    private boolean wallCreated = false;

    public EarthTower1(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites, TowerType.WALL);
        animations.put("earthTower1", new Animate(sprites.get("earthTower1"), 12));
        currentAnimation = animations.get("earthTower1");

        this.activeWalls = new ArrayList<>();
        this.damage = 0; // Wall towers don't do damage directly
        this.range = TileScale.of(1); // Range to place wall (1 tile)
        this.attackSpeed = 1; // Create wall immediately
    }

    @Override
    public void update() {
        super.update();

        // Update all active walls
        List<Wall> toRemove = new ArrayList<>();
        for (Wall wall : activeWalls) {
            wall.update();
            if (wall.isBroken()) {
                toRemove.add(wall);
            }
        }
        activeWalls.removeAll(toRemove);
    }

    /**
     * Update tower with all world objects so walls can detect stuck enemies
     * Call this from PlayState instead of regular update()
     */
    public void updateWithEnemies(List<GameObject> worldObjects) {
        update(); // Call regular update first

        // Pass worldObjects to each wall so they can check for stuck enemies
        for (Wall wall : activeWalls) {
            wall.updateWithEnemies(worldObjects);
        }
    }

    @Override
    protected void attack() {
        // Only create wall once
        if (!wallCreated) {
            createWall();
            wallCreated = true;
        }
    }

    private void createWall() {
        // Calculate position: 1 tile to the right of tower
        double wallX = position.getX() + TileScale.of(1);
        double wallY = position.getY();

        Wall wall = new Wall(wallX, wallY, sprites);
        activeWalls.add(wall);
    }

    public List<Wall> getActiveWalls() {
        return activeWalls;
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);

        // Render all walls
        for (Wall wall : activeWalls) {
            wall.render(g);
        }
    }

    /**
     * Inner Wall class - represents a physical barrier
     * Extends GameObject so it can use the Collision box system
     */
    public static class Wall extends GameObject {
        private int maxHp;
        private int currentHp;
        private boolean broken;

        // Damage over time
        private int damagePerSecond = 10;
        private double damageTimer = 0;
        private static final double DAMAGE_INTERVAL = 1.0; // 1 second

        private static final int WALL_WIDTH = TileScale.of(1);
        private static final int WALL_HEIGHT = TileScale.of(1);

        public Wall(double x, double y, SpriteLibrary sprites) {
            super(x, y, sprites);

            this.maxHp = 100; // Default HP - can be changed
            this.currentHp = maxHp;
            this.broken = false;

            // Use custom Collision box that returns "wall" signal
            this.box = new Collision(this, 0, 0, WALL_WIDTH, WALL_HEIGHT) {
                @Override
                public String getSignal() {
                    return "wall"; // This is what enemies check for
                }
            };

            // Set currentAnimation to null explicitly
            this.currentAnimation = null;
        }

        @Override
        public void update() {
            // Don't call super.update() since we don't have animations

            // Check if wall is broken
            if (currentHp <= 0 && !broken) {
                broken = true;
            }
        }

        /**
         * Update wall with list of all enemies to check for stuck enemies
         * Call this from EarthTower1.update()
         */
        public void updateWithEnemies(List<GameObject> allObjects) {
            if (broken) return;

            // Check how many enemies are stuck to this wall
            int enemiesStuck = 0;
            for (GameObject obj : allObjects) {
                if (obj instanceof MovingEntity && !(obj instanceof Player)) {
                    MovingEntity enemy = (MovingEntity) obj;

                    // Check if enemy is touching this wall
                    if (isEnemyTouchingWall(enemy)) {
                        enemiesStuck++;
                    }
                }
            }

            // Apply damage over time if enemies are stuck
            if (enemiesStuck > 0) {
                damageTimer += 1.0 / 60.0; // deltaTime (assuming 60 FPS)

                if (damageTimer >= DAMAGE_INTERVAL) {
                    // Each enemy does damage per second
                    int totalDamage = damagePerSecond * enemiesStuck;
                    takeDamage(totalDamage);
                    damageTimer = 0;

                    System.out.println("Wall taking " + totalDamage +
                            " damage from " + enemiesStuck + " enemies! HP: " +
                            currentHp + "/" + maxHp);
                }
            } else {
                // Reset timer if no enemies are stuck
                damageTimer = 0;
            }
        }

        /**
         * Check if an enemy is touching this wall
         */
        private boolean isEnemyTouchingWall(MovingEntity enemy) {
            // Get enemy's sensor box (assuming they have one)
            Box enemyBox = enemy.getBox();
            if (enemyBox == null) return false;

            // Check intersection with wall's collision box
            return box.intersects(enemyBox);
        }

        public void takeDamage(int damage) {
            if (!broken) {
                currentHp -= damage;
                if (currentHp < 0) currentHp = 0;
            }
        }

        @Override
        public void render(Graphics2D g) {
            if (broken) return;

            // Render wall sprite
            BufferedImage wallSprite = sprites.getFrame("earthWall", 0); // YOU EDIT THIS KEY

            if (wallSprite != null) {
                g.drawImage(wallSprite,
                        (int) position.getX(),
                        (int) position.getY(),
                        WALL_WIDTH,
                        WALL_HEIGHT,
                        null);
            } else {
                // Fallback to brown box if sprite not found
                g.setColor(new Color(139, 69, 19));
                g.fillRect(
                        (int) position.getX(),
                        (int) position.getY(),
                        WALL_WIDTH,
                        WALL_HEIGHT
                );

                g.setColor(Color.BLACK);
                g.setStroke(new BasicStroke(2));
                g.drawRect(
                        (int) position.getX(),
                        (int) position.getY(),
                        WALL_WIDTH,
                        WALL_HEIGHT
                );
            }

            // Draw HP bar above wall
            renderHpBar(g);
        }

        private void renderHpBar(Graphics2D g) {
            int barWidth = WALL_WIDTH;
            int barHeight = 4;
            int barX = (int) position.getX();
            int barY = (int) position.getY() - 8;

            // Background (red)
            g.setColor(Color.RED);
            g.fillRect(barX, barY, barWidth, barHeight);

            // Foreground (green) based on HP percentage
            double hpPercent = (double) currentHp / maxHp;
            g.setColor(Color.GREEN);
            g.fillRect(barX, barY, (int)(barWidth * hpPercent), barHeight);

            // Border
            g.setColor(Color.BLACK);
            g.drawRect(barX, barY, barWidth, barHeight);
        }

        // Getters
        public Box getCollisionBox() { return box; }
        public boolean isBroken() { return broken; }
        public int getCurrentHp() { return currentHp; }
        public int getMaxHp() { return maxHp; }

        // Setters
        public void setMaxHp(int maxHp) {
            this.maxHp = maxHp;
            this.currentHp = maxHp;
        }

        public void setCurrentHp(int hp) {
            this.currentHp = hp;
            if (this.currentHp > maxHp) this.currentHp = maxHp;
            if (this.currentHp < 0) this.currentHp = 0;
        }

        public void setDamagePerSecond(int dps) {
            this.damagePerSecond = dps;
        }
    }
}