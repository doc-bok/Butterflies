package com.bokmcdok.butterflies.common.data;

import com.bokmcdok.butterflies.butterfly_data.ButterflyRegistry;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

/**
 * Holds information on special catch advancements.
 * @param species The species to catch.
 * @param localization The localization string that describes the advancement.
 * @param xpReward The XP reward for completing the advancement.
 */
public record SpecialCatchDefinition(String species,
                                     String localization,
                                     int xpReward,
                                     boolean usesBurntNet,
                                     boolean usesFlameproofNet) {

    /**
     * Returns the item to use as an icon for the advancement.
     * @return The item to use.
     */
    Item iconItem() {
        if (usesBurntNet) {
            return ItemRegistry.BURNT_BUTTERFLY_NET.get();
        }

        if (usesFlameproofNet) {
            return ItemRegistry.FIREPROOF_BUTTERFLY_NETS.get(0).get();
        }

        int index = ButterflyRegistry.getButterflyIndex(species);
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

        int index = ButterflyRegistry.getButterflyIndex(species);
        if (usesFlameproofNet) {
            return ItemRegistry.FIREPROOF_BUTTERFLY_NETS.get(index);
        }

        return ItemRegistry.BUTTERFLY_NETS.get(index);
    }

    /**
     * Returns the item to collect for the advancement.
     * @return The correct net item.
     */
    RegistryObject<Item> fireproofCollectItem() {
        if (usesBurntNet) {
            return ItemRegistry.BURNT_BUTTERFLY_NET;
        }

        int index = ButterflyRegistry.getButterflyIndex(species);
        return ItemRegistry.FIREPROOF_BUTTERFLY_NETS.get(index);
    }
}
