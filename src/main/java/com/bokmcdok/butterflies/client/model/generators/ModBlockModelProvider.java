package com.bokmcdok.butterflies.client.model.generators;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.client.data.models.model.ButterflyModelTemplates;
import com.bokmcdok.butterflies.client.data.models.model.ButterflyTexturedModel;
import com.bokmcdok.butterflies.registries.BlockRegistry;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.blockstates.Variant;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.core.FrontAndTop;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * Registers block states.
 */
public class ModBlockModelProvider extends ModelSubProvider {

    /**
     * Construction
     */
    public ModBlockModelProvider(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        super(blockModels, itemModels);
    }

    /**
     * Entry point.
     */
    @Override
    protected void register() {

        // Bottled items
        for (int i = 0; i < BlockRegistry.BOTTLED_BUTTERFLY_BLOCKS.size(); i++) {
            simpleBlockWithItem(
                    BlockRegistry.BOTTLED_BUTTERFLY_BLOCKS.get(i),
                    ButterflyTexturedModel.BOTTLE);

            simpleBlockWithItem(
                    BlockRegistry.BOTTLED_CATERPILLAR_BLOCKS.get(i),
                    ButterflyTexturedModel.BOTTLE);
        }

        // Flower buds
        createFlowerBud(BlockRegistry.ALLIUM_BUD);
        createFlowerBud(BlockRegistry.AZURE_BLUET_BUD);
        createFlowerBud(BlockRegistry.BLUE_ORCHID_BUD);
        createFlowerBud(BlockRegistry.CORNFLOWER_BUD);
        createFlowerBud(BlockRegistry.DANDELION_BUD);
        createFlowerBud(BlockRegistry.LILY_OF_THE_VALLEY_BUD);
        createFlowerBud(BlockRegistry.ORANGE_TULIP_BUD);
        createFlowerBud(BlockRegistry.OXEYE_DAISY_BUD);
        createFlowerBud(BlockRegistry.PINK_TULIP_BUD);
        createFlowerBud(BlockRegistry.POPPY_BUD);
        createFlowerBud(BlockRegistry.RED_TULIP_BUD);
        createFlowerBud(BlockRegistry.WHITE_TULIP_BUD);
        createFlowerBud(BlockRegistry.WITHER_ROSE_BUD);

        // Functional Blocks
        simpleBlockWithItem(
                BlockRegistry.BUTTERFLY_FEEDER,
                ButterflyTexturedModel.BUTTERFLY_FEEDER);

        simpleBlockWithItem(
                BlockRegistry.BUTTERFLY_MICROSCOPE,
                ButterflyTexturedModel.BUTTERFLY_MICROSCOPE);

        // Origami
        for(DeferredHolder<Block, Block> origami : BlockRegistry.BUTTERFLY_ORIGAMI) {
            registerButterflyOrigami(origami);
        }
    }

    private void registerButterflyOrigami(DeferredHolder<Block,Block> block) {

        String path = block.getId().getPath();
        ResourceLocation textureLocation = ResourceLocation.withDefaultNamespace("block/" + path.substring(18) + "_wool");
        TextureMapping texturemapping = (new TextureMapping()).put(TextureSlot.ALL, textureLocation);

        ResourceLocation modelLocation = ButterflyModelTemplates.BUTTERFLY_ORIGAMI.create(block.get(), texturemapping, blockModels.modelOutput);

        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block.get(), Variant.variant().with(VariantProperties.MODEL, modelLocation))
                .with(PropertyDispatch.property(BlockStateProperties.ORIENTATION)
                        .generate((x ) -> applyRotation(x, Variant.variant()))));
    }

    private Variant applyRotation(FrontAndTop frontAndTop,
                                  Variant variant) {
        return switch (frontAndTop) {
            case DOWN_NORTH ->
                    variant.with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180);
            case DOWN_SOUTH -> variant.with(VariantProperties.X_ROT, VariantProperties.Rotation.R180);
            case DOWN_WEST ->
                    variant.with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90);
            case DOWN_EAST ->
                    variant.with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270);
            case UP_NORTH -> variant.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180);
            case UP_SOUTH -> variant;
            case UP_WEST -> variant.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90);
            case UP_EAST -> variant.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270);
            case NORTH_UP ->
                    variant.with(VariantProperties.X_ROT, VariantProperties.Rotation.R270).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180);
            case SOUTH_UP -> variant.with(VariantProperties.X_ROT, VariantProperties.Rotation.R270);
            case WEST_UP ->
                    variant.with(VariantProperties.X_ROT, VariantProperties.Rotation.R270).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90);
            case EAST_UP ->
                    variant.with(VariantProperties.X_ROT, VariantProperties.Rotation.R270).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270);
        };
    }

    /**
     * Create a simple block with a custom model.
     * @param block The block to generate a model for.
     * @param model The base model for the block.
     */
    private void simpleBlockWithItem(DeferredHolder<Block, Block> block,
                                     TexturedModel.Provider model) {
        blockModels.createTrivialBlock(block.get(), model);
    }

    private void createFlowerBud(DeferredHolder<Block, Block> block) {
        if (BlockStateProperties.AGE_7.getPossibleValues().size() != new int[]{0, 1, 2, 3, 4, 5, 6, 6}.length) {
            throw new IllegalArgumentException();
        } else {
            Int2ObjectMap<ResourceLocation> int2objectmap = new Int2ObjectOpenHashMap<>();
            PropertyDispatch propertydispatch = PropertyDispatch.property(BlockStateProperties.AGE_7).generate((i) -> {
                int age = new int[]{0, 1, 2, 3, 4, 5, 6, 6}[i];
                ResourceLocation resourcelocation = int2objectmap.computeIfAbsent(i, (x) -> {

                    String textureName = block.getId().getPath().substring(4);

                    boolean shouldCreateModel =
                            ((!textureName.contains("tulip") || textureName.contains("orange_tulip")) && i < 7) ||
                            (textureName.contains("tulip") && i > 4 && i < 7);

                    if (age < 5 && textureName.contains("tulip")) {
                        textureName = "tulip";
                    }

                    ResourceLocation location = ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "block/flower_buds/" + textureName +"_stage" + age);
                    if (shouldCreateModel) {
                        return ModelTemplates.CROP.create(location, TextureMapping.crop(location), modelOutput);
                    } else {
                        return location;
                    }
                });

                return Variant.variant().with(VariantProperties.MODEL, resourcelocation);
            });

            blockStateOutput.accept(MultiVariantGenerator.multiVariant(block.get()).with(propertydispatch));
        }
    }
}
