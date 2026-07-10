package com.bokmcdok.butterflies.client.model.generators;

import java.util.List;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;

/**
 * A model provider that aggregates sub providers for data generation.
 */
public final class ButterflyModelProvider extends ModelProvider {

    // The list of sub-providers.
    private final List<ModelSubProviderFactory> subProviders;

    /**
     * Factory method to create a Model Provider.
     * @param subProviders The list of sub-providers.
     * @return A new instance of a Model Provider.
     */
    public static Factory<DataProvider> create(@NotNull ModelSubProviderFactory... subProviders) {
        return output -> new ButterflyModelProvider(output, List.of(subProviders));
    }

    /**
     * Creation interface for the sub-providers.
     */
    @FunctionalInterface
    public interface ModelSubProviderFactory {
        ModelSubProvider create(BlockModelGenerators blockModels, ItemModelGenerators itemModels);
    }

    /**
     * Construction.
     * @param packOutput The pack to output the models to.
     * @param subProviders The list of sub-providers.
     */
    public ButterflyModelProvider(PackOutput packOutput,
                                  List<ModelSubProviderFactory> subProviders) {
        super(packOutput, ButterfliesMod.MOD_ID);
        this.subProviders = List.copyOf(subProviders);
    }

    /**
     * Goes through each sub-provider and registers their models.
     * @param blockModels The block model generator.
     * @param itemModels The item model generator.
     */
    @Override
    protected void registerModels(@NotNull BlockModelGenerators blockModels,
                                  @NotNull ItemModelGenerators itemModels) {
        for (var subProvider : subProviders) {
            subProvider.create(blockModels, itemModels).register();
        }
    }
}