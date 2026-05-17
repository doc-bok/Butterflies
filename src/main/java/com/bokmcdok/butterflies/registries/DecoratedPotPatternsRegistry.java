package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * Registers pottery patterns.
 */
public class DecoratedPotPatternsRegistry {

    // An instance of a deferred registry we use to register patterns.
    public static final DeferredRegister<String> DECORATED_POT_PATTERNS;

    // The butterfly pot pattern.
    public static final RegistryObject<String> BUTTERFLY_POT_PATTERN;

    static {
        DECORATED_POT_PATTERNS = DeferredRegister.create(Registries.DECORATED_POT_PATTERNS, ButterfliesMod.MOD_ID);
        BUTTERFLY_POT_PATTERN = DECORATED_POT_PATTERNS.register("butterfly_pottery_pattern", () -> "butterfly_pottery_pattern");
    }

    /**
     * Prevent construction.
     */
    private DecoratedPotPatternsRegistry() {}
}
