package inventory;

import gfx.SpriteLibrary;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class InventoryManager {

    public SpriteLibrary spriteLibrary;

    private final Map<Integer, ItemStack> inventory; // itemId -> ItemStack
    private final Map<Integer, Item> itemCatalog;   // itemId -> Item object
    private Item equippedItem;

    public InventoryManager(SpriteLibrary spriteLibrary) {
        inventory = new HashMap<>();
        itemCatalog = new HashMap<>();
        this.spriteLibrary = spriteLibrary;
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

        itemCatalog.put(1, new WeaponItem(1, "Sword", swordIcon));
        itemCatalog.put(2, new WeaponItem(2, "Spear", spearIcon));
        itemCatalog.put(3, new WeaponItem(3, "Hammer", hammerIcon));
        itemCatalog.put(4, new WeaponItem(4, "Shovel", shovelIcon));

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
            inventory.put(itemId, new ItemStack(itemCatalog.get(itemId), 64));
        }
    }

    public void give(int itemId, int quantity) {
        ItemStack stack = inventory.get(itemId);
        if (stack == null) return;

        int newQty = Math.min(stack.getQuantity() + quantity, 64);
        stack.setQuantity(newQty);
    }

    public void clickingButton(int itemId) {
        ItemStack stack = inventory.get(itemId);
        if (stack == null || stack.getQuantity() <= 0) return;

        Item item = stack.getItem();

        if (item instanceof PotionItem) {
            // Signal PlayState to apply potion effect
            // some callback to PlayState
            stack.decrement(1);    // reduce quantity
        } else {
            equippedItem = item;
            item.onEquip();
        }
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

    public ItemStack getItemStack(int itemId) {
        return inventory.get(itemId);
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

