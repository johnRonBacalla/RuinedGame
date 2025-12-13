package inventory;

import java.awt.image.BufferedImage;

public class PotionItem extends Item{
    public PotionItem(int id, String name, BufferedImage icon) {
        super(id, name, icon);
    }

    @Override
    public void onUse() {
        // Just a signal; actual effect happens in PlayState
        System.out.println("Potion used: " + name);
    }
}
