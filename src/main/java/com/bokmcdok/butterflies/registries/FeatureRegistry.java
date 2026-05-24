package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.level.levelgen.feature.PeacemakerLair;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FeatureRegistry {

    // An instance of a deferred registry we use to register blocks.
    public static final DeferredRegister<Feature<?>> FEATURES;

    // Peacemaker Lair
    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> PEACEMAKER_LAIR;

    static {
        FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, ButterfliesMod.MOD_ID);

        PEACEMAKER_LAIR = FEATURES.register("peacemaker_lair", () -> new PeacemakerLair(NoneFeatureConfiguration.CODEC));
    }
}
