package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registers Banner Patterns used by the mod.
 */
public class BannerPatternRegistry {

    public static final DeferredRegister<BannerPattern> BANNER_PATTERNS;
    public static final DeferredHolder<BannerPattern, BannerPattern> BANNER_PATTERN_BUTTERFLY;

    static {
        BANNER_PATTERNS = DeferredRegister.create(Registries.BANNER_PATTERN, ButterfliesMod.MOD_ID);
        BANNER_PATTERN_BUTTERFLY = BANNER_PATTERNS.register("banner_pattern_butterfly", () -> new BannerPattern("banner_pattern_butterfly"));
    }

    /**
     * Prevent construction.
     */
    private BannerPatternRegistry() {}
}
