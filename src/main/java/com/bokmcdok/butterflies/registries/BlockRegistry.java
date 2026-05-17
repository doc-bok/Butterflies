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
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
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
    // The base properties for bottled butterflies.
    private static final BlockBehaviour.Properties BOTTLED_BUTTERFLY_PROPERTIES =
            BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)
                    .isRedstoneConductor(BlockRegistry::alwaysFalse)
                    .isSuffocating(BlockRegistry::alwaysFalse)
                    .isValidSpawn(BlockRegistry::alwaysFalse)
                    .isViewBlocking(BlockRegistry::alwaysFalse)
                    .noOcclusion()
                    .sound(SoundType.GLASS)
                    .strength(0.3F);

    private static final BlockBehaviour.Properties GLOWING_BOTTLED_BUTTERFLY_PROPERTIES =
            BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)
                    .isRedstoneConductor(BlockRegistry::alwaysFalse)
                    .isSuffocating(BlockRegistry::alwaysFalse)
                    .isValidSpawn(BlockRegistry::alwaysFalse)
                    .isViewBlocking(BlockRegistry::alwaysFalse)
                    .noOcclusion()
                    .sound(SoundType.GLASS)
                    .strength(0.3F)
                    .lightLevel((blockstate) -> 15);
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

    static {
        BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, ButterfliesMod.MOD_ID);

        // Bottled butterflies.
        List<DeferredHolder<Block, Block>> bottledButterflyBlocks = new ArrayList<>();
        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            String registryId = getBottledButterflyRegistryId(i);

            // Light Butterflies glow when they are in a bottle.
            DeferredHolder<Block, Block> newBlock;
            if (Arrays.asList(ButterflyInfo.TRAITS[i]).contains(ButterflyData.Trait.GLOW)) {
                newBlock = BLOCKS.register(registryId, () -> new BottledButterflyBlock(GLOWING_BOTTLED_BUTTERFLY_PROPERTIES));
            } else {
                newBlock = BLOCKS.register(registryId, () -> new BottledButterflyBlock(BOTTLED_BUTTERFLY_PROPERTIES));
            }

            bottledButterflyBlocks.add(newBlock);
        }

        BOTTLED_BUTTERFLY_BLOCKS = Collections.unmodifiableList(bottledButterflyBlocks);

        // Bottled caterpillars.
        List<DeferredHolder<Block, Block>> bottledCaterpillarBlocks = new ArrayList<>();
        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            DeferredHolder<Block, Block> newBlock = BLOCKS.register(getBottledCaterpillarRegistryId(i), BottledCaterpillarBlock::new);
            bottledCaterpillarBlocks.add(newBlock);
        }

        BOTTLED_CATERPILLAR_BLOCKS = Collections.unmodifiableList(bottledCaterpillarBlocks);

        // Butterfly buds
        ALLIUM_BUD = BLOCKS.register("bud_allium", () -> new FlowerCropBlock(Blocks.ALLIUM));
        AZURE_BLUET_BUD = BLOCKS.register("bud_azure_bluet", () -> new FlowerCropBlock(Blocks.AZURE_BLUET));
        BLUE_ORCHID_BUD = BLOCKS.register("bud_blue_orchid", () -> new FlowerCropBlock(Blocks.BLUE_ORCHID));
        CORNFLOWER_BUD = BLOCKS.register("bud_cornflower", () -> new FlowerCropBlock(Blocks.CORNFLOWER));
        DANDELION_BUD = BLOCKS.register("bud_dandelion", () -> new FlowerCropBlock(Blocks.DANDELION));
        LILY_OF_THE_VALLEY_BUD = BLOCKS.register("bud_lily_of_the_valley", () -> new FlowerCropBlock(Blocks.LILY_OF_THE_VALLEY));
        ORANGE_TULIP_BUD = BLOCKS.register("bud_orange_tulip", () -> new FlowerCropBlock(Blocks.ORANGE_TULIP));
        OXEYE_DAISY_BUD = BLOCKS.register("bud_oxeye_daisy", () -> new FlowerCropBlock(Blocks.OXEYE_DAISY));
        PINK_TULIP_BUD = BLOCKS.register("bud_pink_tulip", () -> new FlowerCropBlock(Blocks.PINK_TULIP));
        POPPY_BUD = BLOCKS.register("bud_poppy", () -> new FlowerCropBlock(Blocks.POPPY));
        RED_TULIP_BUD = BLOCKS.register("bud_red_tulip", () -> new FlowerCropBlock(Blocks.RED_TULIP));
        WHITE_TULIP_BUD = BLOCKS.register("bud_white_tulip", () -> new FlowerCropBlock(Blocks.WHITE_TULIP));
        WITHER_ROSE_BUD = BLOCKS.register("bud_wither_rose", () -> new FlowerCropBlock(Blocks.WITHER_ROSE));

        // Functional blocks
        BUTTERFLY_FEEDER = BLOCKS.register( "butterfly_feeder",ButterflyFeederBlock::new);
        BUTTERFLY_MICROSCOPE = BLOCKS.register( "butterfly_microscope", ButterflyMicroscopeBlock::new);

        // Origami
        List<DeferredHolder<Block, Block>> butterflyOrigami = new ArrayList<>();
        for(String id : ORIGAMI_IDS) {
            butterflyOrigami.add(BLOCKS.register(id, ButterflyOrigamiBlock::new));
        }

        BUTTERFLY_ORIGAMI = Collections.unmodifiableList(butterflyOrigami);
    }

    /**
     * Prevent construction.
     */
    private BlockRegistry() {}
}
