package com.bokmcdok.butterflies.client.model.generators;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.registries.BlockRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output,
                                 ExistingFileHelper exFileHelper) {
        super(output, ButterfliesMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerBottledButterflies();
    }

    private void registerBottledButterflies() {
        final ModelFile bottleModel = models().getExistingFile(new ResourceLocation(ButterfliesMod.MOD_ID, "bottle"));
        for (RegistryObject<Block> block: BlockRegistry.BOTTLED_BUTTERFLY_BLOCKS) {
            simpleBlock(block.get(), bottleModel);
        }
    }
}
