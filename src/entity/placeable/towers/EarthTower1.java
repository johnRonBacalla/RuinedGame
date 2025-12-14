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
        List<Wall> toRemove = new ArrayList<>();
        for (Wall wall : activeWalls) {
            wall.update();
            if (wall.isBroken()) {
                toRemove.add(wall);
            }
        }
        activeWalls.removeAll(toRemove);

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
        private boolean broken;

        // Time-based system instead of HP
        private double lifetime; // How long the wall lasts (seconds)
        private double timer; // Current time alive (seconds)
        private static final double DEFAULT_LIFETIME = 5.0; // 5 seconds default

        private static final int WALL_WIDTH = TileScale.of(1);
        private static final int WALL_HEIGHT = TileScale.of(1);

        public Wall(double x, double y, SpriteLibrary sprites) {
            super(x, y, sprites);

            this.lifetime = DEFAULT_LIFETIME;
            this.timer = 0;
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

            // Increment timer (assuming 60 FPS)
            timer += 1.0 / 60.0;

            // Check if wall lifetime expired
            if (timer >= lifetime && !broken) {
                broken = true;
                System.out.println("Wall expired after " + lifetime + " seconds!");
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

            // Draw timer bar above wall (shows remaining time)
            renderTimerBar(g);
        }

        /**
         * Render timer bar showing remaining wall lifetime
         */
        private void renderTimerBar(Graphics2D g) {
            int barWidth = WALL_WIDTH;
            int barHeight = 4;
            int barX = (int) position.getX();
            int barY = (int) position.getY() - 8;

            // Background (dark gray)
            g.setColor(Color.DARK_GRAY);
            g.fillRect(barX, barY, barWidth, barHeight);

            // Foreground (cyan) based on time remaining
            double timePercent = 1.0 - (timer / lifetime);
            g.setColor(Color.CYAN);
            g.fillRect(barX, barY, (int)(barWidth * timePercent), barHeight);

            // Border
            g.setColor(Color.BLACK);
            g.drawRect(barX, barY, barWidth, barHeight);
        }

        // Getters
        public Box getCollisionBox() { return box; }
        public boolean isBroken() { return broken; }
        public double getLifetime() { return lifetime; }
        public double getTimeRemaining() { return Math.max(0, lifetime - timer); }

        // Setters
        public void setLifetime(double seconds) {
            this.lifetime = seconds;
        }
    }
}