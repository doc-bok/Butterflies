package com.bokmcdok.butterflies.world.item;

import com.bokmcdok.butterflies.registries.ItemRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * An implementation of the butterfly tab for the creative menu.
 */
public class ButterflyTab extends CreativeModeTab {

    // A reference to the item registry.
    private final ItemRegistry itemRegistry;

    /**
     * Construction.
     * @param label The label to use for the tab.
     * @param itemRegistry A reference to the item registry.
     */
    public ButterflyTab(String label,
                        ItemRegistry itemRegistry) {
        this(-1, label, itemRegistry);
    }

    /**
     * Construction.
     * @param length The length of the tab.
     * @param label The label to use for the tab.
     * @param itemRegistry A reference to the item registry.
     */
    public ButterflyTab(int length,
                        String label,
                        ItemRegistry itemRegistry) {
        super(length, label);

        this.itemRegistry = itemRegistry;
    }

    /**
     * Get the icon to use based on an item.
     * @return The item stack to base the icon from.
     */
    @NotNull
    @Override
    public ItemStack makeIcon() {
        return itemRegistry.getButterflyBook().get().getDefaultInstance();
    }
}
