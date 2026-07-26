package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Holds all the tags used by the mod.
 */
public class TagRegistry {

    // The available tags for this mod.
    public static final TagKey<Item> FIREPROOF_BUTTERFLY_NETS;

    static {
        FIREPROOF_BUTTERFLY_NETS = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(),
                        new ResourceLocation(ButterfliesMod.MOD_ID, "fireproof_butterfly_nets"));
    }

    // Prevent construction.
    private TagRegistry() {}
}
