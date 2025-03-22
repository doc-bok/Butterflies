package com.bokmcdok.butterflies.world.inventory;

import com.bokmcdok.butterflies.world.item.ButterflyBookItem;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

/**
 * A slot that limits what can be placed in it to Books and Butterfly Books.
 */
public class ButterflyBookSlot extends Slot {

    /**
     * Construction.
     *
     * @param container The container.
     * @param slotIndex The slot index.
     * @param xPos      The screen coordinates for the slot.
     * @param yPos      The screen coordinates for the slot.
     */
    public ButterflyBookSlot(Container container,
                             int slotIndex,
                             int xPos,
                             int yPos) {
        super(container, slotIndex, xPos, yPos);
    }

    /**
     * Tells the game if the item may be placed in the slot.
     * @param itemStack The item stack to test.
     * @return TRUE if the item can be placed in the slot.
     */
    @Override
    public boolean mayPlace(ItemStack itemStack) {
        return itemStack.getItem() instanceof ButterflyBookItem ||
                itemStack.is(Items.BOOK);
    }
}