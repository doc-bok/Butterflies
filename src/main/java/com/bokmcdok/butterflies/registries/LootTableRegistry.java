package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

public class LootTableRegistry {

    public static final ResourceKey<LootTable> PEACEMAKER_LAIR;

    static {
        PEACEMAKER_LAIR = ResourceKey.create(
                        Registries.LOOT_TABLE,
                        ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "chests/peacemaker_lair"));
    }
}
