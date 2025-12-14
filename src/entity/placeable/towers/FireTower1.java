package entity.placeable.towers;

import gfx.Animate;
import gfx.SpriteLibrary;
import projectile.Projectile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FireTower1 extends Tower {

    private List<Projectile> projectiles;

    public FireTower1(double x, double y, SpriteLibrary sprites) {
        super(x, y, sprites, TowerType.PROJECTILE);

        animations.put("fireTower1", new Animate(sprites.get("fireTower1"), 12));
        currentAnimation = animations.get("fireTower1");

        this.projectiles = new ArrayList<>();

        // Configure Fire Tower stats
        this.damage = 20;
        this.range = 250.0;
        this.attackSpeed = 30; // Attack every 0.5 seconds
    }

    @Override
    protected void attack() {
        if (currentTarget == null || currentTarget.isDead()) return;

        System.out.println("Fire Tower shooting at " + currentTarget.getClass().getSimpleName());

        // Create projectile from tower center to target
        Projectile projectile = new Projectile(
                getTowerCenter(),
                currentTarget,
                damage,
                5.0, // projectile speed
                "fire"
        );

        projectiles.add(projectile);
    }

    @Override
    public void update() {
        super.update();

        // Update all projectiles
        Iterator<Projectile> iterator = projectiles.iterator();
        while (iterator.hasNext()) {
            Projectile proj = iterator.next();
            proj.update();

            // Remove inactive projectiles
            if (!proj.isActive()) {
                iterator.remove();
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);

        // Render all active projectiles
        for (Projectile proj : projectiles) {
            proj.render(g);
        }
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }
}