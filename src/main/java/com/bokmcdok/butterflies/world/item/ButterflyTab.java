package com.bokmcdok.butterflies.world.item;

import com.bokmcdok.butterflies.registries.ItemRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * An implementation of the butterfly tab for the creative menu.
 */
public class ButterflyTab extends CreativeModeTab {

    /**
     * Construction.
     * @param label The label to use for the tab.
     */
    public ButterflyTab(String label) {
        this(-1, label);
    }

    /**
     * Construction.
     * @param length The length of the tab.
     * @param label The label to use for the tab.
     */
    public ButterflyTab(int length,
                        String label) {
        super(length, label);
    }

    /**
     * Get the icon to use based on an item.
     * @return The item stack to base the icon from.
     */
    @NotNull
    @Override
    public ItemStack makeIcon() {
        return ItemRegistry.BUTTERFLY_BOOK.get().getDefaultInstance();
    }
}
