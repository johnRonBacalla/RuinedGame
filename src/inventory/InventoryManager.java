package inventory;

import entity.moving.MovingEntity;
import entity.moving.Player;
import gfx.SpriteLibrary;
import weapon.WeaponData;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class InventoryManager {

    public SpriteLibrary spriteLibrary;

    private final Map<Integer, ItemStack> inventory; // itemId -> ItemStack
    private final Map<Integer, Item> itemCatalog;   // itemId -> Item object
    private Item equippedItem;
    private MovingEntity owner;

    public InventoryManager(SpriteLibrary spriteLibrary, Player player) {
        inventory = new HashMap<>();
        itemCatalog = new HashMap<>();
        this.spriteLibrary = spriteLibrary;
        owner = player;
        loadItems();
        initializeInventory();
    }

    // Preload all possible items
    private void loadItems() {
        //weapons
        BufferedImage swordIcon = spriteLibrary.getFrame("catalog", 0);
        BufferedImage spearIcon = spriteLibrary.getFrame("catalog", 1);
        BufferedImage hammerIcon = spriteLibrary.getFrame("catalog", 2);
        BufferedImage shovelIcon = spriteLibrary.getFrame("catalog", 3);

        WeaponData swordData = new WeaponData(1, "Sword", spriteLibrary);
        WeaponData spearData = new WeaponData(2, "Spear", spriteLibrary);
        WeaponData hammerData = new WeaponData(3, "Hammer", spriteLibrary);
        WeaponData shovelData = new WeaponData(4, "Shovel", spriteLibrary);

        //potions
        BufferedImage healthPotIcon = spriteLibrary.getFrame("catalog", 4);
        BufferedImage rockPotIcon = spriteLibrary.getFrame("catalog", 5);
        BufferedImage breezePotIcon = spriteLibrary.getFrame("catalog", 6);
        BufferedImage firePotIcon = spriteLibrary.getFrame("catalog", 7);

        //placeable
        BufferedImage fireIRuneIcon = spriteLibrary.getFrame("catalog", 8);
        BufferedImage iceIRuneIcon = spriteLibrary.getFrame("catalog", 9);
        BufferedImage earthIRuneIcon = spriteLibrary.getFrame("catalog", 10);
        BufferedImage windIRuneIcon = spriteLibrary.getFrame("catalog", 11);
        BufferedImage fireIIRuneIcon = spriteLibrary.getFrame("catalog", 12);
        BufferedImage iceIIRuneIcon = spriteLibrary.getFrame("catalog", 13);
        BufferedImage earthIIRuneIcon = spriteLibrary.getFrame("catalog", 14);
        BufferedImage windIIRuneIcon = spriteLibrary.getFrame("catalog", 15);

        // Create weapons
        WeaponItem sword = new WeaponItem(1, "Sword", swordIcon, swordData);
        WeaponItem spear = new WeaponItem(2, "Spear", spearIcon, spearData);
        WeaponItem hammer = new WeaponItem(3, "Hammer", hammerIcon, hammerData);

        // Configure sword hitbox
        sword.setHitboxSize(40, 192);           // Width: 40, Height: 35
        sword.setHitboxOffset(78, -64);          // Right-facing: 35px right, 5px down
        sword.setInvertedHitboxOffset(-64, -64); // Left-facing: 75px left, 5px down
        sword.setHitboxActivationDelay(20);

        // Configure spear hitbox (longer range)
        spear.setHitboxSize(128, 48);           // Longer width for spear reach
        spear.setHitboxOffset(64, 16);          // Further out from player
        spear.setInvertedHitboxOffset(-128, 16);
        spear.setHitboxActivationDelay(20);

        // Configure hammer hitbox (wider area)
        hammer.setHitboxSize(64, 104);          // Square hitbox for hammer slam
        hammer.setHitboxOffset(96, -20);
        hammer.setInvertedHitboxOffset(-96, -24);
        hammer.setHitboxActivationDelay(20);

        itemCatalog.put(1, sword);
        itemCatalog.put(2, spear);
        itemCatalog.put(3, hammer);
        itemCatalog.put(4, new WeaponItem(4, "Shovel", shovelIcon, shovelData));

        itemCatalog.put(5, new PotionItem(5, "Health Potion", healthPotIcon));
        itemCatalog.put(6, new PotionItem(6, "Rock Potion", rockPotIcon));
        itemCatalog.put(7, new PotionItem(7, "Breeze Potion", breezePotIcon));
        itemCatalog.put(8, new PotionItem(8, "Fire Potion", firePotIcon));

        itemCatalog.put(9, new PlaceableItem(9, "Fire Rune I", fireIRuneIcon));
        itemCatalog.put(10, new PlaceableItem(10, "Ice Rune I", iceIRuneIcon));
        itemCatalog.put(11, new PlaceableItem(11, "Earth Rune I", earthIRuneIcon));
        itemCatalog.put(12, new PlaceableItem(12, "Wind Rune I", windIRuneIcon));

        itemCatalog.put(13, new PlaceableItem(13, "Fire Rune II", fireIIRuneIcon));
        itemCatalog.put(14, new PlaceableItem(14, "Ice Rune II", iceIIRuneIcon));
        itemCatalog.put(15, new PlaceableItem(15, "Earth Rune II", earthIIRuneIcon));
        itemCatalog.put(16, new PlaceableItem(16, "Wind Rune II", windIIRuneIcon));
    }

    // Start inventory with all items but 0 quantity
    private void initializeInventory() {
        for (int itemId : itemCatalog.keySet()) {
            inventory.put(itemId, new ItemStack(itemCatalog.get(itemId), 0));
        }
        give(1, 1);
        give(2, 1);
        give(3, 1);
        give(4, 1);
        give(9, 2);
        give(10, 2);
        give(11, 2);
        give(12, 2);

    }

    public void give(int itemId, int quantity) {
        ItemStack stack = inventory.get(itemId);
        if (stack == null) return;

        int newQty = Math.min(stack.getQuantity() + quantity, 64);
        stack.setQuantity(newQty);
    }

    public void printInventory() {
        System.out.println("---- Inventory ----");
        for (int itemId : itemCatalog.keySet()) {
            ItemStack stack = inventory.get(itemId);
            Item item = stack.getItem();
            int qty = stack.getQuantity();
            System.out.println(itemId + " - " + item.getName() + " : " + qty);
        }
        System.out.println("------------------");
    }

    public void setEquippedItem(int itemId){
        equippedItem = itemCatalog.get(itemId);

        // If it's a weapon, set the owner so it can render
        if (equippedItem instanceof WeaponItem weapon) {
            weapon.setOwner(owner);
        }
    }

    public void removeEquippedItem(){
        equippedItem = null;
    }

    public ItemStack getItemStack(int itemId) {
        return inventory.get(itemId);
    }

    public Item getItem(int itemId){
        return itemCatalog.get(itemId);
    }

    public Item getEquippedItem() {
        return equippedItem;
    }

    public BufferedImage getItemImage(int itemId){
        return itemCatalog.get(itemId).getIcon();
    }

    public String getItemName(int itemId){
        return itemCatalog.get(itemId).getName();
    }
}