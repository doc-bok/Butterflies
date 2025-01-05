package com.bokmcdok.butterflies.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * The menu for a butterfly feeder.
 */
public class ButterflyFeederMenu extends AbstractContainerMenu {

    // The container the menu is interfacing with.
    private final Container feeder;

    /**
     * Client constructor.
     * @param menuType The type of this menu.
     * @param containerId The ID of the container.
     * @param playerInventory The player's inventory.
     */
    public ButterflyFeederMenu(MenuType<?> menuType,
                               int containerId,
                               Inventory playerInventory) {
        this(menuType, containerId, playerInventory, new SimpleContainer(1));
    }

    /**
     * Server constructor.
     * @param menuType The type of this menu.
     * @param containerId The ID of the container.
     * @param playerInventory The player's inventory.
     * @param container The container for the feeder.
     */
    public ButterflyFeederMenu(MenuType<?> menuType,
                               int containerId,
                               Inventory playerInventory,
                               Container container) {
        super(menuType, containerId);

        this.feeder = container;
        checkContainerSize(container, 1);
        container.startOpen(playerInventory.player);

        this.addSlot(new ButterflyFeederSlot(container, 0, 80, 17));

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, i * 18 + 47));
            }
        }

        for(int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 105));
        }
    }

    /**
     * Called when a stack is shift-clicked.
     * @param player The player interacting with the menu.
     * @param slotIndex The slot that was shift-clicked.
     * @return The item stack that was moved.
     */
    @NotNull
    @Override
    public ItemStack quickMoveStack(@NotNull Player player,
                                    int slotIndex) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if (slot.hasItem()) {
            ItemStack item = slot.getItem();
            result = item.copy();

            if (slotIndex < this.feeder.getContainerSize()) {
                if (!this.moveItemStackTo(item, this.feeder.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(item, 0, this.feeder.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (item.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return result;
    }

    /**
     * Called when the block entity is removed.
     * @param player The player interacting with the menu.
     */
    @Override
    public void removed(@NotNull Player player) {
        super.removed(player);
        this.feeder.stopOpen(player);
    }

    /**
     * Check the block entity is still valid.
     * @param player The player interacting with the menu.
     * @return TRUE if the block is still valid.
     */
    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.feeder.stillValid(player);
    }
}
