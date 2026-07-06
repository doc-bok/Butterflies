package com.bokmcdok.butterflies.client.model.generators;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.blockstates.BlockStateGenerator;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.resources.ResourceLocation;


public abstract class ModelSubProvider {

    protected final BlockModelGenerators blockModels;
    protected final Consumer<BlockStateGenerator> blockStateOutput;
    protected final ItemModelGenerators itemModels;
    protected final BiConsumer<ResourceLocation, ModelInstance> modelOutput;

    public ModelSubProvider(BlockModelGenerators blockModels,
                            ItemModelGenerators itemModels) {
        this.blockModels = blockModels;
        this.itemModels = itemModels;
        this.modelOutput = blockModels.modelOutput;
        this.blockStateOutput = blockModels.blockStateOutput;
    }

    protected abstract void register();
}