package com.bokmcdok.butterflies.world.level.block.entity;

import com.bokmcdok.butterflies.registries.MenuTypeRegistry;
import com.bokmcdok.butterflies.world.inventory.ButterflyFeederMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * The block entity for a butterfly feeder.
 */
public class ButterflyFeederEntity extends RandomizableContainerBlockEntity {

    // Reference to the menu type registry.
    private final MenuTypeRegistry menuTypeRegistry;

    // The item(s) contained in the feeder.
    NonNullList<ItemStack> items;

    /**
     * Construction.
     * @param blockEntityType The block entity type.
     * @param blockPos The position of the block.
     * @param blockState The state of the block.
     */
    public ButterflyFeederEntity(MenuTypeRegistry menuTypeRegistry,
                                 BlockEntityType<?> blockEntityType,
                                 BlockPos blockPos,
                                 BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
        this.menuTypeRegistry = menuTypeRegistry;
        this.items = NonNullList.withSize(1, ItemStack.EMPTY);
    }

    /**
     * Create the container menu.
     * @param containerId The ID of this container.
     * @param inventory The player's inventory.
     * @return The menu to open.
     */
    @NotNull
    @Override
    protected AbstractContainerMenu createMenu(int containerId,
                                               @NotNull Inventory inventory) {
        return new ButterflyFeederMenu(this.menuTypeRegistry.getButterflyFeederMenu().get(),
                containerId, inventory, this);
    }

    /**
     * The size of the container.
     * @return This is always 1.
     */
    @Override
    public int getContainerSize() {
        return 1;
    }

    /**
     * Get the default name for the feeder.
     * @return The relevant localisation string.
     */
    @NotNull
    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.butterfly_feeder");
    }

    /**
     * Get the items in this container.
     * @return The item stack.
     */
    @NotNull
    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    /**
     * Set the items in the container.
     * @param items The items to set.
     */
    @Override
    protected void setItems(@NotNull NonNullList<ItemStack> items) {
        this.items = items;
    }
}
