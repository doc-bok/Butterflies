package com.bokmcdok.butterflies.common.data;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.data.worldgen.ModBiomeModifiers;
import com.bokmcdok.butterflies.data.worldgen.ModConfiguredFeatures;
import com.bokmcdok.butterflies.data.worldgen.ModPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Provides any world generation data.
 */
public class ModWorldGenProvider  extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap);

    /**
     * Constructor.
     * @param output The pack to output to.
     * @param registries Access to the registries.
     */
    public ModWorldGenProvider(PackOutput output,
                               CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(ButterfliesMod.MOD_ID));
    }
}
