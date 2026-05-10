package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * Registers pottery patterns.
 */
public class DecoratedPotPatternsRegistry {

    // An instance of a deferred registry we use to register items.
    public static final DeferredRegister<String> REGISTER;

    // The butterfly pot pattern.
    public static final RegistryObject<String> BUTTERFLY_POT_PATTERN;

    static {
        REGISTER = DeferredRegister.create(Registries.DECORATED_POT_PATTERNS, ButterfliesMod.MOD_ID);
        BUTTERFLY_POT_PATTERN = REGISTER.register("butterfly_pottery_pattern", () -> "butterfly_pottery_pattern");
    }

    // Prevent construction.
    private DecoratedPotPatternsRegistry() {}
}
