package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.ButterflyInfo;
import com.bokmcdok.butterflies.world.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Registers the blocks used by the mod.
 */
public class BlockRegistry {

    // An instance of a deferred registry we use to register blocks.
    public static final DeferredRegister<Block> BLOCKS;

    // Bottled creatures.
    public static final List<DeferredHolder<Block, Block>> BOTTLED_BUTTERFLY_BLOCKS;
    public static final List<DeferredHolder<Block, Block>> BOTTLED_CATERPILLAR_BLOCKS;

    // Butterfly Feeder
    public static final DeferredHolder<Block, Block> BUTTERFLY_FEEDER;

    // Butterfly Microscope
    public static final DeferredHolder<Block, Block> BUTTERFLY_MICROSCOPE;

    // Flower Buds
    public static final DeferredHolder<Block, Block> ALLIUM_BUD;
    public static final DeferredHolder<Block, Block> AZURE_BLUET_BUD;
    public static final DeferredHolder<Block, Block> BLUE_ORCHID_BUD;
    public static final DeferredHolder<Block, Block> CORNFLOWER_BUD;
    public static final DeferredHolder<Block, Block> DANDELION_BUD;
    public static final DeferredHolder<Block, Block> LILY_OF_THE_VALLEY_BUD;
    public static final DeferredHolder<Block, Block> ORANGE_TULIP_BUD;
    public static final DeferredHolder<Block, Block> OXEYE_DAISY_BUD;
    public static final DeferredHolder<Block, Block> PINK_TULIP_BUD;
    public static final DeferredHolder<Block, Block> POPPY_BUD;
    public static final DeferredHolder<Block, Block> RED_TULIP_BUD;
    public static final DeferredHolder<Block, Block> WHITE_TULIP_BUD;
    public static final DeferredHolder<Block, Block> WITHER_ROSE_BUD;

    public static final List<DeferredHolder<Block, Block>> FLOWER_BUDS;

    // Origami
    public static final List<DeferredHolder<Block, Block>> BUTTERFLY_ORIGAMI;

    // A list of Butterfly Origami IDs used by the registry.
    private static final String[] ORIGAMI_IDS = {
            "butterfly_origami_black",
            "butterfly_origami_blue",
            "butterfly_origami_brown",
            "butterfly_origami_cyan",
            "butterfly_origami_gray",
            "butterfly_origami_green",
            "butterfly_origami_light_blue",
            "butterfly_origami_light_gray",
            "butterfly_origami_lime",
            "butterfly_origami_magenta",
            "butterfly_origami_orange",
            "butterfly_origami_pink",
            "butterfly_origami_purple",
            "butterfly_origami_red",
            "butterfly_origami_white",
            "butterfly_origami_yellow"
    };

    /**
     * Helper method for the "never" attribute. Used in block properties during
     * block construction.
     * @param ignoredBlockState The current block state.
     * @param ignoredBlockGetter Access to the block.
     * @param ignoredBlockPos The block's position.
     * @param ignoredEntityType The entity type trying to spawn.
     * @return Always FALSE.
     */
    public static boolean alwaysFalse(BlockState ignoredBlockState,
                                      BlockGetter ignoredBlockGetter,
                                      BlockPos ignoredBlockPos,
                                      EntityType<?> ignoredEntityType) {
        return false;
    }

    /**
     * Helper method for the "never" attribute. Used in block properties during
     * block construction.
     * @param ignoredBlockState The current block state.
     * @param ignoredBlockGetter Access to the block.
     * @param ignoredBlockPos The block's position.
     * @return Always FALSE.
     */
    public static boolean alwaysFalse(BlockState ignoredBlockState,
                                      BlockGetter ignoredBlockGetter,
                                      BlockPos ignoredBlockPos) {
        return false;
    }

    private static BlockBehaviour.Properties createPropertiesWithKey(String registryId,
                                                                     BlockBehaviour.Properties baseProperties) {
        ResourceLocation blockId = ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, registryId);
        ResourceKey<Block> resourceKey = ResourceKey.create(Registries.BLOCK, blockId);
        return baseProperties.setId(resourceKey);
    }

    /**
     * Helper method to generate the Registry ID for bottled butterflies.
     * @param butterflyIndex The butterfly index of the species.
     * @return The registry ID.
     */
    private static String getBottledButterflyRegistryId(int butterflyIndex) {
        return "bottled_butterfly_" + ButterflyInfo.SPECIES[butterflyIndex];
    }

    /**
     * Helper method to generate the Registry ID for bottled caterpillars.
     * @param butterflyIndex The butterfly index of the species.
     * @return The registry ID.
     */
    private static String getBottledCaterpillarRegistryId(int butterflyIndex) {
        return "bottled_caterpillar_" + ButterflyInfo.SPECIES[butterflyIndex];
    }

    /**
     * Register a bottled butterfly.
     * @param butterflyIndex The butterfly index to register for.
     * @return The registry object.
     */
    private static DeferredHolder<Block, Block> registerBottledButterfly(int butterflyIndex) {
        String registryId = getBottledButterflyRegistryId(butterflyIndex);
        BlockBehaviour.Properties properties = createPropertiesWithKey(registryId, BottledButterflyBlock.BASE_PROPERTIES);

        // Light Butterflies glow when they are in a bottle.
        if (Arrays.asList(ButterflyInfo.TRAITS[butterflyIndex]).contains(ButterflyData.Trait.GLOW)) {
            return BLOCKS.register(registryId, () -> new BottledButterflyBlock(properties.lightLevel((blockState) -> 15)));
        }

        return BLOCKS.register(registryId, () -> new BottledButterflyBlock(properties));
    }

    /**
     * Register a bottled caterpillar.
     * @param butterflyIndex The butterfly index to register for.
     * @return The registry object.
     */
    private static DeferredHolder<Block, Block> registerBottledCaterpillar(int butterflyIndex) {
        String registryId = getBottledCaterpillarRegistryId(butterflyIndex);
        BlockBehaviour.Properties properties = createPropertiesWithKey(registryId, BottledCaterpillarBlock.BASE_PROPERTIES);
        return BLOCKS.register(registryId, () -> new BottledCaterpillarBlock(properties));
    }

    /**
     * Create a flower bud block.
     * @param registryId The ID to use to register the crop.
     * @param block The flower block it is based on.
     * @return The flower bud block entry.
     */
    private static DeferredHolder<Block, Block> registerFlowerBudBlock(String registryId,
                                                                       Block block) {
        BlockBehaviour.Properties properties = createPropertiesWithKey(registryId, BlockBehaviour.Properties.ofFullCopy(block));
        return BLOCKS.register(registryId, () -> new FlowerCropBlock(block, properties));
    }

    /**
     * Registers an origami block.
     * @param registryId The ID to use to register the block.
     * @return The origami block entry.
     */
    private static DeferredHolder<Block, Block> registerButterflyOrigami(String registryId) {
        BlockBehaviour.Properties properties = createPropertiesWithKey(registryId, ButterflyOrigamiBlock.BASE_PROPERTIES);
        return BLOCKS.register(registryId, () -> new ButterflyOrigamiBlock(properties));
    }

    static {
        BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, ButterfliesMod.MOD_ID);

        // Bottled butterflies.
        List<DeferredHolder<Block, Block>> bottledButterflyBlocks = new ArrayList<>();
        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            bottledButterflyBlocks.add(registerBottledButterfly(i));
        }

        BOTTLED_BUTTERFLY_BLOCKS = Collections.unmodifiableList(bottledButterflyBlocks);

        // Bottled caterpillars.
        List<DeferredHolder<Block, Block>> bottledCaterpillarBlocks = new ArrayList<>();
        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            bottledCaterpillarBlocks.add(registerBottledCaterpillar(i));
        }

        BOTTLED_CATERPILLAR_BLOCKS = Collections.unmodifiableList(bottledCaterpillarBlocks);

        // Butterfly buds
        ALLIUM_BUD = registerFlowerBudBlock("bud_allium", Blocks.ALLIUM);
        AZURE_BLUET_BUD = registerFlowerBudBlock("bud_azure_bluet", Blocks.AZURE_BLUET);
        BLUE_ORCHID_BUD = registerFlowerBudBlock("bud_blue_orchid", Blocks.BLUE_ORCHID);
        CORNFLOWER_BUD = registerFlowerBudBlock("bud_cornflower", Blocks.CORNFLOWER);
        DANDELION_BUD = registerFlowerBudBlock("bud_dandelion", Blocks.DANDELION);
        LILY_OF_THE_VALLEY_BUD = registerFlowerBudBlock("bud_lily_of_the_valley", Blocks.LILY_OF_THE_VALLEY);
        ORANGE_TULIP_BUD = registerFlowerBudBlock("bud_orange_tulip", Blocks.ORANGE_TULIP);
        OXEYE_DAISY_BUD = registerFlowerBudBlock("bud_oxeye_daisy", Blocks.OXEYE_DAISY);
        PINK_TULIP_BUD = registerFlowerBudBlock("bud_pink_tulip", Blocks.PINK_TULIP);
        POPPY_BUD = registerFlowerBudBlock("bud_poppy", Blocks.POPPY);
        RED_TULIP_BUD = registerFlowerBudBlock("bud_red_tulip", Blocks.RED_TULIP);
        WHITE_TULIP_BUD = registerFlowerBudBlock("bud_white_tulip", Blocks.WHITE_TULIP);
        WITHER_ROSE_BUD = registerFlowerBudBlock("bud_wither_rose", Blocks.WITHER_ROSE);

        FLOWER_BUDS = List.of(
                ALLIUM_BUD,
                AZURE_BLUET_BUD,
                BLUE_ORCHID_BUD,
                CORNFLOWER_BUD,
                DANDELION_BUD,
                LILY_OF_THE_VALLEY_BUD,
                ORANGE_TULIP_BUD,
                OXEYE_DAISY_BUD,
                PINK_TULIP_BUD,
                POPPY_BUD,
                RED_TULIP_BUD,
                WHITE_TULIP_BUD,
                WITHER_ROSE_BUD
        );

        // Functional blocks
        BUTTERFLY_FEEDER = BLOCKS.register(ButterflyFeederBlock.ID, ButterflyFeederBlock::new);
        BUTTERFLY_MICROSCOPE = BLOCKS.register( ButterflyMicroscopeBlock.ID, ButterflyMicroscopeBlock::new);

        // Origami
        List<DeferredHolder<Block, Block>> butterflyOrigami = new ArrayList<>();
        for(String id : ORIGAMI_IDS) {
            butterflyOrigami.add(registerButterflyOrigami(id));
        }

        BUTTERFLY_ORIGAMI = Collections.unmodifiableList(butterflyOrigami);
    }

    /**
     * Prevent construction.
     */
    private BlockRegistry() {}
}
