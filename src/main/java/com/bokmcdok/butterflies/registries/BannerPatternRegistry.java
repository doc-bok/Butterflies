package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.registries.DeferredRegister;

/**
 * Registers Banner Patterns used by the mod.
 */
public class BannerPatternRegistry {

    public static final DeferredRegister<BannerPattern> REGISTER;

    static {
        REGISTER = DeferredRegister.create(Registries.BANNER_PATTERN, ButterfliesMod.MOD_ID);
        REGISTER.register("banner_pattern_butterfly", () -> new BannerPattern("banner_pattern_butterfly"));
    }
}
