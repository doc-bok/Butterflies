package com.bokmcdok.butterflies.data.event;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.registries.BannerPatternRegistry;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Lets datagen know what entries are available for tags.
 */
public final class ModBuiltInEntriesProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder REGISTRY_SET_BUILDER = new RegistrySetBuilder()
            .add(Registries.BANNER_PATTERN, BannerPatternRegistry::bootstrap);

    /**
     * Construction.
     * @param output The pack to output to.
     * @param provider The registry provider.
     */
    public ModBuiltInEntriesProvider(PackOutput output,
                                     CompletableFuture<Provider> provider) {
        super(output, provider, REGISTRY_SET_BUILDER, Set.of(ButterfliesMod.MOD_ID));
    }
}