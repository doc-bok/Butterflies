package com.bokmcdok.butterflies.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Handles crafting butterfly books with the butterfly microscope.
 */
public class ButterflyBookResultSlot extends Slot {
    private final CraftingContainer craftSlots;

    /**
     * Construction
     * @param craftingContainer The container for the crafting slots.
     * @param container The container for the result.
     * @param slotIndex The index of this result.
     * @param x The x-position of this slot.
     * @param y The y-position of this slot.
     */
    public ButterflyBookResultSlot(CraftingContainer craftingContainer,
                                   Container container,
                                   int slotIndex,
                                   int x,
                                   int y) {
        super(container, slotIndex, x, y);
        this.craftSlots = craftingContainer;
    }

    /**
     * Disallow players putting items in result slots.
     * @param itemStack The stack to place.
     * @return Always FALSE.
     */
    @Override
    public boolean mayPlace(@NotNull ItemStack itemStack) {
        return false;
    }

    /**
     * Remove ingredients when a new book is crafted.
     * @param player The player crafting the boot.
     * @param itemStack The item stack to take.
     */
    @Override
    public void onTake(@NotNull Player player,
                       @NotNull ItemStack itemStack) {
        this.checkTakeAchievements(itemStack);

        for (int i = 0; i < craftSlots.getContainerSize(); ++i) {
            ItemStack craftItem = this.craftSlots.getItem(i);
            if (!craftItem.isEmpty()) {
                this.craftSlots.removeItem(i, 1);
            }
        }
    }
}
