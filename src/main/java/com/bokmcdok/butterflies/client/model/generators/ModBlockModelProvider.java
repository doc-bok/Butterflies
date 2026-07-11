package com.bokmcdok.butterflies.client.model.generators;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.client.data.models.model.ButterflyModelTemplates;
import com.bokmcdok.butterflies.client.data.models.model.ButterflyTexturedModels;
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
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Optional;

/**
 * Generates block states, block models, and block item models.
 */
public class ModBlockModelProvider extends ModelSubProvider {

    private static final int[] BUD_STAGE_BY_AGE = {0, 1, 2, 3, 4, 5, 6, 6};

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
        registerBottledCreatures();
        registerFlowerBuds();
        registerFunctionalBlocks();
        registerOrigamiBlocks();
    }

    /**
     * Applies a specific rotation based on the variant's orientation.
     * @param frontAndTop The orientation of the variant.
     * @param variant The variant we are creating.
     * @return The updated variant with the correct rotations.
     */
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
     * Creates a flower bud block.
     * @param block The block to create.
     */
    private void createFlowerBudBlock(DeferredHolder<Block, Block> block) {
        if (BlockStateProperties.AGE_7.getPossibleValues().size() != BUD_STAGE_BY_AGE.length) {
            throw new IllegalArgumentException(String.format("Mismatch between Age values size [%d] and Bud Stage values length [%d].",
                    BlockStateProperties.AGE_7.getPossibleValues().size(),
                    BUD_STAGE_BY_AGE.length));
        } else {
            Int2ObjectMap<ResourceLocation> int2objectmap = new Int2ObjectOpenHashMap<>();
            PropertyDispatch propertydispatch = PropertyDispatch.property(BlockStateProperties.AGE_7).generate((i) -> {
                int age = BUD_STAGE_BY_AGE[i];
                ResourceLocation resourcelocation = int2objectmap.computeIfAbsent(i, (x) -> {

                    String blockPath = block.getId().getPath();
                    Optional<String> flowerName = extractFlowerName(blockPath);

                    if (flowerName.isPresent()) {
                        String textureName = flowerName.get();
                        boolean shouldGenerateBudModel = shouldGenerateBudModel(i, textureName);

                        if (age < 5 && isTulip(textureName)) {
                            textureName = "tulip";
                        }

                        ResourceLocation location = ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "block/flower_buds/" + textureName + "_stage" + age);
                        if (shouldGenerateBudModel) {
                            return ModelTemplates.CROP.create(location, TextureMapping.crop(location),  modelOutput);
                        } else {
                            return location;
                        }
                    } else {
                        throw new IllegalArgumentException(String.format("Block is not a flower bud [%s.]", blockPath));
                    }
                });

                return Variant.variant().with(VariantProperties.MODEL, resourcelocation);
            });

            blockStateOutput.accept(MultiVariantGenerator.multiVariant(block.get()).with(propertydispatch));
        }
    }

    /**
     * Creates a single origami block.
     * @param block The block to create.
     */
    private void createOrigamiBlock(DeferredHolder<Block, Block> block) {
        String path = block.getId().getPath();
        Optional<String> color = extractColor(path);
        if (color.isPresent()) {
            ResourceLocation textureLocation = ResourceLocation.withDefaultNamespace("block/" + color.get() + "_wool");
            TextureMapping texturemapping = (new TextureMapping()).put(TextureSlot.ALL, textureLocation);

            ResourceLocation modelLocation = ButterflyModelTemplates.BUTTERFLY_ORIGAMI.create(block.get(), texturemapping, blockModels.modelOutput);

            blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block.get(), Variant.variant().with(VariantProperties.MODEL, modelLocation))
                    .with(PropertyDispatch.property(BlockStateProperties.ORIENTATION)
                            .generate((x) -> applyRotation(x, Variant.variant()))));
        }
    }

    /**
     * Extracts a color from a block name, if present.
     * @param blockName The name of the block.
     * @return The color of the block, if valid.
     */
    private Optional<String> extractColor(String blockName) {
        for(DyeColor color : DyeColor.values()) {
            if (blockName.contains(color.getName())) {
                return Optional.of(color.getName());
            }
        }

        return Optional.empty();
    }

    /**
     * Extracts a flower name from a flower bud's block name.
     * @param blockName The name of the block.
     * @return The flower name, if present.
     */
    private Optional<String> extractFlowerName(String blockName) {
        if (blockName.length() > 4 ) {
            if (blockName.startsWith("bud_")) {
                return Optional.of(blockName.substring(4));
            }
        }

        return Optional.empty();
    }

    /**
     * Check if a flower is a type of tulip.
     * @param textureName The name of the texture.
     * @return True if the flower is a tulip.
     */
    private boolean isTulip(String textureName) {
        return textureName.contains("tulip");
    }

    /**
     * Registers bottled butterflies and bottled caterpillars.
     */
    private void registerBottledCreatures() {
        for (int i = 0; i < BlockRegistry.BOTTLED_BUTTERFLY_BLOCKS.size(); i++) {
            simpleBlockWithItem(
                    BlockRegistry.BOTTLED_BUTTERFLY_BLOCKS.get(i),
                    ButterflyTexturedModels.BOTTLE);

            simpleBlockWithItem(
                    BlockRegistry.BOTTLED_CATERPILLAR_BLOCKS.get(i),
                    ButterflyTexturedModels.BOTTLE);
        }
    }

    /**
     * Registers all flower bud blocks.
     */
    private void registerFlowerBuds() {
        createFlowerBudBlock(BlockRegistry.ALLIUM_BUD);
        createFlowerBudBlock(BlockRegistry.AZURE_BLUET_BUD);
        createFlowerBudBlock(BlockRegistry.BLUE_ORCHID_BUD);
        createFlowerBudBlock(BlockRegistry.CORNFLOWER_BUD);
        createFlowerBudBlock(BlockRegistry.DANDELION_BUD);
        createFlowerBudBlock(BlockRegistry.LILY_OF_THE_VALLEY_BUD);
        createFlowerBudBlock(BlockRegistry.ORANGE_TULIP_BUD);
        createFlowerBudBlock(BlockRegistry.OXEYE_DAISY_BUD);
        createFlowerBudBlock(BlockRegistry.PINK_TULIP_BUD);
        createFlowerBudBlock(BlockRegistry.POPPY_BUD);
        createFlowerBudBlock(BlockRegistry.RED_TULIP_BUD);
        createFlowerBudBlock(BlockRegistry.WHITE_TULIP_BUD);
        createFlowerBudBlock(BlockRegistry.WITHER_ROSE_BUD);
    }

    /**
     * Registers the functional blocks.
     */
    private void registerFunctionalBlocks() {
        simpleBlockWithItem(
                BlockRegistry.BUTTERFLY_FEEDER,
                ButterflyTexturedModels.BUTTERFLY_FEEDER);

        simpleBlockWithItem(
                BlockRegistry.BUTTERFLY_MICROSCOPE,
                ButterflyTexturedModels.BUTTERFLY_MICROSCOPE);
    }

    /**
     * Registers all our origami blocks.
     */
    private void registerOrigamiBlocks() {
        for(DeferredHolder<Block, Block> origami : BlockRegistry.BUTTERFLY_ORIGAMI) {
            createOrigamiBlock(origami);
        }
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

    /**
     * Check if a bud model should be generated. Prevents duplicate models, and
     * allows for model reuse.
     * @param age The age of the flower bud.
     * @param textureName The name of the flower's texture.
     * @return True if a model needs to be generated.
     */
    private boolean shouldGenerateBudModel(int age,
                                           String textureName) {
        return ((!textureName.contains("tulip") || textureName.contains("orange_tulip")) && age < 7) ||
                (isTulip(textureName) && age > 4 && age < 7);
    }
}
