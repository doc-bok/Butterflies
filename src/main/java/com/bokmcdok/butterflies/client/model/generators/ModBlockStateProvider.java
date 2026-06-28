package com.bokmcdok.butterflies.client.model.generators;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.registries.BlockRegistry;
import com.bokmcdok.butterflies.world.block.ButterflyOrigamiBlock;
import com.bokmcdok.butterflies.world.block.FlowerCropBlock;
import net.minecraft.core.FrontAndTop;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.function.Function;

/**
 * Registers block states.
 */
public class ModBlockStateProvider extends BlockStateProvider {

    /**
     * Construction.
     * @param output The pack to output data to.
     * @param existingFileHelper Helper to access existing files.
     */
    public ModBlockStateProvider(PackOutput output,
                                 ExistingFileHelper existingFileHelper) {
        super(output, ButterfliesMod.MOD_ID, existingFileHelper);
    }

    /**
     * Entry point.
     */
    @Override
    protected void registerStatesAndModels() {

        // Bottled items
        for (int i = 0; i < BlockRegistry.BOTTLED_BUTTERFLY_BLOCKS.size(); i++) {
            registerBottledButterfly(i);
            registerBottledCaterpillar(i);
        }

        // Flower buds
        registerFlowerBud(BlockRegistry.ALLIUM_BUD);
        registerFlowerBud(BlockRegistry.AZURE_BLUET_BUD);
        registerFlowerBud(BlockRegistry.BLUE_ORCHID_BUD);
        registerFlowerBud(BlockRegistry.CORNFLOWER_BUD);
        registerFlowerBud(BlockRegistry.DANDELION_BUD);
        registerFlowerBud(BlockRegistry.LILY_OF_THE_VALLEY_BUD);
        registerFlowerBud(BlockRegistry.ORANGE_TULIP_BUD);
        registerFlowerBud(BlockRegistry.OXEYE_DAISY_BUD);
        registerFlowerBud(BlockRegistry.PINK_TULIP_BUD);
        registerFlowerBud(BlockRegistry.POPPY_BUD);
        registerFlowerBud(BlockRegistry.RED_TULIP_BUD);
        registerFlowerBud(BlockRegistry.WHITE_TULIP_BUD);
        registerFlowerBud(BlockRegistry.WITHER_ROSE_BUD);

        // Functional Blocks
        simpleBlockWithItem(BlockRegistry.BUTTERFLY_FEEDER.get(), models().getExistingFile(BlockRegistry.BUTTERFLY_FEEDER.getId()));
        simpleBlockWithItem(BlockRegistry.BUTTERFLY_MICROSCOPE.get(), models().getExistingFile(BlockRegistry.BUTTERFLY_MICROSCOPE.getId()));

        // Origami
        for(DeferredHolder<Block, Block> origami : BlockRegistry.BUTTERFLY_ORIGAMI) {
            registerButterflyOrigami(origami);
        }
    }

    /**
     * Registers a bottled butterfly.
     * @param index The butterfly index.
     */
    private void registerBottledButterfly(int index) {
        registerBottledEntity(index, BlockRegistry.BOTTLED_BUTTERFLY_BLOCKS);
    }

    /**
     * Registers a bottled caterpillar.
     * @param index The butterfly index.
     */
    private void registerBottledCaterpillar(int index) {
        registerBottledEntity(index, BlockRegistry.BOTTLED_CATERPILLAR_BLOCKS);
    }

    /**
     * Registers a bottled entity.
     * @param index The butterfly index.
     * @param blocks The block list to register from.
     */
    private void registerBottledEntity(int index,
                                       List<DeferredHolder<Block, Block>> blocks) {
        final ModelFile bottleModel = models().getExistingFile(new ResourceLocation(ButterfliesMod.MOD_ID, "bottle"));
        simpleBlock(blocks.get(index).get(), bottleModel);
    }

    /**
     * Registers a flower bud block state.
     * @param block The block to create a block state for.
     */
    private void registerFlowerBud(DeferredHolder<Block, Block> block) {

        if (block.get() instanceof FlowerCropBlock flowerBud) {

            String textureName = block.getId().getPath().substring(4);

            Function<BlockState, ConfiguredModel[]> function =
                    state -> generateCropState(state, flowerBud, textureName);

            getVariantBuilder(flowerBud).forAllStates(function);
        }
    }

    private void registerButterflyOrigami(DeferredHolder<Block,Block> block) {

        Function<BlockState, ConfiguredModel[]> function =
                state -> generateOrientationState(state, block);

        getVariantBuilder(block.get()).forAllStates(function);
    }

    /**
     * Generates a block state variant for a specific age.
     * @param state The current block state.
     * @param block The block to generate.
     * @param textureName The name of the texture to use.
     * @return A new variant of the block state.
     */
    private ConfiguredModel[] generateCropState(BlockState state,
                                                CropBlock block,
                                                String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        int age = Math.min(state.getValue(((FlowerCropBlock) block).getAgeProperty()), 6);
        if (age < 5 && textureName.contains("tulip")) {
            textureName = "tulip";
        }

        models[0] = new ConfiguredModel(models().cross("block/flower_buds/" + textureName +"_stage" + age,
                new ResourceLocation(ButterfliesMod.MOD_ID, "block/flower_bud/" + textureName +"_stage" + age)).renderType("cutout"));

        return models;
    }

    /**
     * Generates a block state variant based on orientation.
     * @param state The current block state.
     * @param block The block to generate.
     * @return A new variant of the block state.
     */
    private ConfiguredModel[] generateOrientationState(BlockState state,
                                                       DeferredHolder<Block, Block> block) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        FrontAndTop orientation = state.getValue(ButterflyOrigamiBlock.ORIENTATION);
        int x = 0;
        int y = 0;
        switch (orientation) {
            case DOWN_EAST:
                x = 180;
                y = 270;
                break;

            case DOWN_NORTH:
                x = 180;
                y = 180;
                break;

            case DOWN_SOUTH:
                x = 180;
                break;

            case DOWN_WEST:
                x = 180;
                y = 90;
                break;

            case EAST_UP:
                x = 270;
                y = 270;
                break;

            case NORTH_UP:
                x = 270;
                y = 180;
                break;

            case SOUTH_UP:
                x = 270;
                break;

            case UP_EAST:
                y = 270;
                break;

            case UP_NORTH:
                y = 180;
                break;

            case UP_SOUTH:
                break;

            case UP_WEST:
                y = 90;
                break;

            case WEST_UP:
                x = 270;
                y = 90;
                break;
        }

        final ResourceLocation parent = new ResourceLocation(ButterfliesMod.MOD_ID, "block/butterfly_origami");
        String path = block.getId().getPath();
        models[0] = new ConfiguredModel(
                models().withExistingParent("block/" + path, parent).texture("all", new ResourceLocation("block/" + path.substring(18) + "_wool")),
                x, y, false);

        simpleBlockItem(block.get(), models[0].model);

        return models;
    }
}
