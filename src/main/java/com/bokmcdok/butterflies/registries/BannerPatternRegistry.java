package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BannerPatternRegistry {

    // An instance of a deferred registry we use to register items.
    private final DeferredRegister<BannerPattern> deferredRegister;


    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public BannerPatternRegistry(IEventBus modEventBus) {
        this.deferredRegister = DeferredRegister.create(Registries.BANNER_PATTERN, ButterfliesMod.MOD_ID);
        this.deferredRegister.register(modEventBus);
    }

    /**
     * Register the banner patterns.
     */
    public void initialise() {
        // Register the banner pattern itself.
        deferredRegister.register("banner_pattern_butterfly", () -> new BannerPattern("banner_pattern_butterfly"));
    }
}
