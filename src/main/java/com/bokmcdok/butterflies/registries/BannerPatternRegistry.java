package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
        BANNER_PATTERN_BUTTERFLY = BANNER_PATTERNS.register("banner_pattern_butterfly", () ->
                new BannerPattern(ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "banner_pattern_butterfly"),
                        "block.minecraft.banner.butterflies.banner_pattern_butterfly"));
    }

    public static void bootstrap(BootstrapContext<BannerPattern> context) {
        register(context, BANNER_PATTERN_BUTTERFLY.getKey());
    }

    public static void register(BootstrapContext<BannerPattern> context, ResourceKey<BannerPattern> resourceKey) {
        context.register(resourceKey, new BannerPattern(resourceKey.location(), "block.minecraft.banner." + resourceKey.location().toShortLanguageKey()));
    }

    /**
     * Prevent construction.
     */
    private BannerPatternRegistry() {}
}
