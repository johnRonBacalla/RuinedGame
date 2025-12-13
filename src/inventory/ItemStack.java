package inventory;

public class ItemStack {
    private final Item item;
    private int quantity;

    public ItemStack(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() { return item; }
    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) {
        this.quantity = Math.min(quantity, 64); // cap at 64
        if(this.quantity < 0) this.quantity = 0;
    }

    public int increment(int amount) {
        int newQty = quantity + amount;
        if(newQty > 64) {
            quantity = 64;
            return newQty - 64; // leftover discarded
        } else {
            quantity = newQty;
            return 0;
        }
    }

    public void decrement(int amount) {
        setQuantity(quantity - amount);
    }
}

