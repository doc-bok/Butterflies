package com.bokmcdok.butterflies.data.worldgen;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.registries.FeatureRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/**
 * Generates data for configured features.
 */
public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> PEACEMAKER_LAIR = registerKey("peacemaker_lair");

    /**
     * Entry point.
     * @param context The context for the configured features.
     */
    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        context.register(PEACEMAKER_LAIR, new ConfiguredFeature<>(
                FeatureRegistry.PEACEMAKER_LAIR.get(),
                NoneFeatureConfiguration.INSTANCE));
    }

    /**
     * Helper to register a key.
     * @param name The name of the key.
     * @return A new ResourceKey.
     */
    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, name));
    }
}
