package com.bokmcdok.butterflies.common.data;

import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.world.ButterflyData;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * Holds information on special catch advancements.
 * @param species The species to catch.
 * @param localization The localization string that describes the advancement.
 * @param xpReward The XP reward for completing the advancement.
 */
public record SpecialCatchDefinition(String species,
                                     String localization,
                                     int xpReward,
                                     boolean usesBurntNet) {

    /**
     * Returns the item to use as an icon for the advancement.
     * @return The item to use.
     */
    Item iconItem() {
        if (usesBurntNet) {
            return ItemRegistry.BURNT_BUTTERFLY_NET.get();
        }

        int index = ButterflyData.getButterflyIndex(species);
        return ItemRegistry.BUTTERFLY_SCROLLS.get(index).get();
    }

    /**
     * Returns the item to collect for the advancement.
     * @return The correct net item.
     */
    RegistryObject<Item> collectItem() {
        if (usesBurntNet) {
            return ItemRegistry.BURNT_BUTTERFLY_NET;
        }

        int index = ButterflyData.getButterflyIndex(species);
        return ItemRegistry.BUTTERFLY_NETS.get(index);
    }
}
