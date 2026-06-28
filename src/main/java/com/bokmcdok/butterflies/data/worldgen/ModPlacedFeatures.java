package com.bokmcdok.butterflies.data.worldgen;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.*;
import java.util.List;

/**
 * Generates data files for placed features.
 */
public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> PEACEMAKER_LAIR = registerKey("peacemaker_lair");

    /**
     * Entry point.
     * @param context The context for the biome modifier.
     */
    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        context.register(PEACEMAKER_LAIR, new PlacedFeature(
                configuredFeatures.getOrThrow(ModConfiguredFeatures.PEACEMAKER_LAIR),
                List.of(CountPlacement.of(4),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.absolute(0), VerticalAnchor.belowTop(0))))));
    }

    /**
     * Helper to register a key.
     * @param name The name of the key.
     * @return A new ResourceKey.
     */
    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(ButterfliesMod.MOD_ID, name));
    }
}
