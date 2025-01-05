package com.bokmcdok.butterflies.world.inventory;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

/**
 * A slot for the butterfly feeder that limits what can be placed in it.
 */
public class ButterflyFeederSlot extends Slot {

    /**
     * Construction.
     * @param container The container.
     * @param slotIndex The slot index.
     * @param xPos The screen coordinates for the slot.
     * @param yPos The screen coordinates for the slot.
     */
    public ButterflyFeederSlot(Container container,
                               int slotIndex,
                               int xPos,
                               int yPos) {
        super(container, slotIndex, xPos, yPos);
    }

    /**
     * Tells the game if the item may be placed in the slot.
     * @param itemStack The item stack to test.
     * @return TRUE if the item is some kind of butterfly food.
     */
    @Override
    public boolean mayPlace(ItemStack itemStack) {
        return itemStack.is(ItemTags.SMALL_FLOWERS) ||
                itemStack.is(Items.APPLE) ||
                itemStack.is(Items.SWEET_BERRIES) ||
                itemStack.is(Items.MELON_SLICE);
    }
}
