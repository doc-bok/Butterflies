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

public class ModBuiltInEntriesProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.BANNER_PATTERN, BannerPatternRegistry::bootstrap);

    public ModBuiltInEntriesProvider(PackOutput output, CompletableFuture<Provider> provider) {
        super(output, provider, BUILDER, Set.of(ButterfliesMod.MOD_ID));
    }
}