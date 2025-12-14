package weapon;

import gfx.Animate;
import gfx.SpriteLibrary;

public class WeaponData {
    private String name;
    private int damage;
    private int range;
    private int attackSpeed;
    private int attackDuration;
    private Animate idleAnimation;
    private Animate attackAnimation;
    private boolean isRenderable; // Whether this weapon shows on player

    public WeaponData(int weaponId, String name, SpriteLibrary sprites) {
        this.name = name;
        this.isRenderable = true; // Default to renderable

        // Load animations based on weapon ID
        switch(weaponId) {
            case 1: // Sword
                this.damage = 10;
                this.range = 32;
                this.attackSpeed = 30;
                this.attackDuration = 8;
                this.idleAnimation = new Animate(sprites.get("swordIdle"), 8, true);
                this.attackAnimation = new Animate(sprites.get("swordAttack"), 15, false);
                break;
            case 2: // Spear
                this.damage = 8;
                this.range = 48;
                this.attackSpeed = 40;
                this.attackDuration = 6;
                this.idleAnimation = new Animate(sprites.get("spearIdle"), 8, true);
                this.attackAnimation = new Animate(sprites.get("spearAttack"), 15, false);
                break;
            case 3: // Hammer
                this.damage = 15;
                this.range = 28;
                this.attackSpeed = 50;
                this.attackDuration = 10;
                this.idleAnimation = new Animate(sprites.get("hammerIdle"), 8, true);
                this.attackAnimation = new Animate(sprites.get("hammerAttack"), 15, false);
                break;
            case 4: // Shovel - Just a key item, not a weapon
                this.isRenderable = false; // Don't render this
                this.damage = 0;
                this.range = 0;
                this.attackSpeed = 0;
                this.attackDuration = 0;
                // No animations needed
                break;
        }
    }

    // Getters
    public String getName() { return name; }
    public int getDamage() { return damage; }
    public int getRange() { return range; }
    public int getAttackSpeed() { return attackSpeed; }
    public int getAttackDuration() { return attackDuration; }
    public Animate getIdleAnimation() { return idleAnimation; }
    public Animate getAttackAnimation() { return attackAnimation; }
    public boolean isRenderable() { return isRenderable; }
}