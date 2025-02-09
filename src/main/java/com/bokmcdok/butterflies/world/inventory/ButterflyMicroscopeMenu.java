package com.bokmcdok.butterflies.world.inventory;

import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.world.block.ButterflyMicroscopeBlock;
import com.bokmcdok.butterflies.world.item.ButterflyBookItem;
import com.bokmcdok.butterflies.world.item.ButterflyScrollItem;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ButterflyMicroscopeMenu extends AbstractContainerMenu {

    // Used to index slots when moving items around.
    public static final int RESULT_SLOT = 0;
    private static final int CRAFT_SLOT_START = 1;
    private static final int CRAFT_SLOT_END = 3;
    private static final int INV_SLOT_START = 3;
    private static final int INV_SLOT_END = 30;
    private static final int USE_ROW_SLOT_START = 30;
    private static final int USE_ROW_SLOT_END = 39;

    // The item registry.
    private final ItemRegistry itemRegistry;

    // The crafting slot container.
    private final CraftingContainer craftSlots;

    // The result slot container.
    private final ResultContainer resultSlots;

    // The container the menu is interfacing with.
    private final ContainerLevelAccess containerLevelAccess;

    // The player.
    private final Player player;

    /**
     * Client constructor.
     * @param menuType The type of this menu.
     * @param containerId The ID of the container.
     * @param playerInventory The player's inventory.
     */
    public ButterflyMicroscopeMenu(MenuType<?> menuType,
                                   int containerId,
                                   Inventory playerInventory) {
        this(null, menuType, containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    /**
     * Server constructor.
     * @param menuType The type of this menu.
     * @param containerId The ID of the container.
     * @param playerInventory The player's inventory.
     * @param container The container for the feeder.
     */
    public ButterflyMicroscopeMenu(ItemRegistry itemRegistry,
                                   MenuType<?> menuType,
                                   int containerId,
                                   Inventory playerInventory,
                                   ContainerLevelAccess container) {
        super(menuType, containerId);

        this.itemRegistry = itemRegistry;

        this.player = playerInventory.player;

        this.craftSlots = new TransientCraftingContainer(this, 2, 1);
        this.resultSlots = new ResultContainer();
        this.containerLevelAccess = container;

        // TODO: Update positions.
        this.addSlot(new ButterflyBookResultSlot(this.craftSlots, this.resultSlots, 0, 204, 17));
        this.addSlot(new ButterflyBookSlot(craftSlots, 0, 132, 17));
        this.addSlot(new ButterflyScrollSlot(craftSlots, 1, 150, 17));

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 96 + j * 18, i * 18 + 47));
            }
        }

        for(int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 96 + i * 18, 105));
        }
    }

    /**
     * Get the butterfly index of the current scroll. Used to render the
     * correct scroll.
     * @return The butterfly index.
     */
    public int getButterflyScrollIndex() {
        ItemStack scroll = this.craftSlots.getItem(1);
        if (scroll != ItemStack.EMPTY) {
            if (scroll.getItem() instanceof ButterflyScrollItem scrollItem) {
                return scrollItem.getButterflyIndex();
            }
        }

        return -1;
    }

    /**
     * Called when a stack is shift-clicked.
     *
     * @param player    The player interacting with the menu.
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
            ItemStack slotItem = slot.getItem();
            result = slotItem.copy();
            if (slotIndex == RESULT_SLOT) {
                this.containerLevelAccess.execute((level, blockPos) ->
                        slotItem.getItem().onCraftedBy(slotItem, level, player));
                if (!this.moveItemStackTo(slotItem, INV_SLOT_START, USE_ROW_SLOT_END, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(slotItem, result);
            } else if (slotIndex >= INV_SLOT_START && slotIndex < USE_ROW_SLOT_END) {
                if (!this.moveItemStackTo(slotItem, CRAFT_SLOT_START, CRAFT_SLOT_END, false)) {
                    if (slotIndex < USE_ROW_SLOT_START) {
                        if (!this.moveItemStackTo(slotItem, USE_ROW_SLOT_START, USE_ROW_SLOT_END, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(slotItem, INV_SLOT_START, INV_SLOT_END, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(slotItem, INV_SLOT_START, USE_ROW_SLOT_END, false)) {
                return ItemStack.EMPTY;
            }

            if (slotItem.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotItem.getCount() == result.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, slotItem);
            if (slotIndex == RESULT_SLOT) {
                player.drop(slotItem, false);
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
        this.containerLevelAccess.execute((level, blockPos) ->
                this.clearContainer(player, this.craftSlots));
    }

    /**
     * Called when the slots are changed by the player.
     * @param container The current container.s
     */
    @Override
    public void slotsChanged(@NotNull Container container) {
        this.containerLevelAccess.execute((level, blockPos) ->
                onCraftingGridSlotChanged(this, level, this.player, this.craftSlots, this.resultSlots));
    }

    /**
     * Check the block entity is still valid.
     * @param player The player interacting with the menu.
     * @return TRUE if the block is still valid.
     */
    @Override
    public boolean stillValid(@NotNull Player player) {
        return containerLevelAccess.evaluate((level, blockPos) ->
                level.getBlockState(blockPos).getBlock() instanceof ButterflyMicroscopeBlock &&
                        player.distanceToSqr(
                                (double) blockPos.getX() + 0.5,
                                (double) blockPos.getY() + 0.5,
                                (double) blockPos.getZ() + 0.5) <= 64.0, true);
    }

    /**
     * Update the resulting book if there is a valid recipe.
     * @param menu The menu.
     * @param level The current level.
     * @param player The player interacting with the menu.
     * @param craftingContainer The crafting slots.
     * @param resultContainer The result slots.
     */
    private void onCraftingGridSlotChanged(AbstractContainerMenu menu,
                                           Level level,
                                           Player player,
                                           CraftingContainer craftingContainer,
                                           ResultContainer resultContainer) {
        if (!level.isClientSide) {
            ServerPlayer serverPlayer = (ServerPlayer)player;
            ItemStack result = ItemStack.EMPTY;

            ItemStack book = craftingContainer.getItem(0);

            // Get a new book, if any.
            if (!book.isEmpty()) {
                ItemStack scroll = craftingContainer.getItem(1);
                if (scroll != ItemStack.EMPTY) {
                    if (scroll.getItem() instanceof ButterflyScrollItem scrollItem) {
                        if (book.is(itemRegistry.getButterflyBook().get())) {
                            result = book.copy();
                        } else {
                            result = new ItemStack(itemRegistry.getButterflyBook().get());
                        }

                        if (!ButterflyBookItem.addPage(result, scrollItem.getButterflyIndex())) {
                            result = ItemStack.EMPTY;
                        }
                    }
                }
            }

            resultContainer.setItem(0, result);
            menu.setRemoteSlot(0, result);
            serverPlayer.connection.send(new ClientboundContainerSetSlotPacket(menu.containerId, menu.incrementStateId(), 0, result));
        }
    }
}
