package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * Registers Banner Patterns used by the mod.
 */
public class BannerPatternRegistry {

    public static final DeferredRegister<BannerPattern> BANNER_PATTERNS;
    public static final RegistryObject<BannerPattern> BANNER_PATTERN_BUTTERFLY;

    static {
        BANNER_PATTERNS = DeferredRegister.create(Registry.BANNER_PATTERN_REGISTRY, ButterfliesMod.MOD_ID);
        BANNER_PATTERN_BUTTERFLY = BANNER_PATTERNS.register("banner_pattern_butterfly", () -> new BannerPattern("banner_pattern_butterfly"));
    }

    /**
     * Prevent construction.
     */
    private BannerPatternRegistry() {}
}
