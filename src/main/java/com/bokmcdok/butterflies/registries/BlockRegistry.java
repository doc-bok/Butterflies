package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflySpeciesList;
import com.bokmcdok.butterflies.world.block.BottledButterflyBlock;
import com.bokmcdok.butterflies.world.block.BottledCaterpillarBlock;
import com.bokmcdok.butterflies.world.block.FlowerCropBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

/**
 * Registers the blocks used by the mod.
 */
public class BlockRegistry {

    // An instance of a deferred registry we use to register items.
    public static final DeferredRegister<Block> INSTANCE =
            DeferredRegister.create(BuiltInRegistries.BLOCK, ButterfliesMod.MOD_ID);

    // Bottled Caterpillars
    private static DeferredHolder<Block, Block> registerBottledButterfly(int butterflyIndex) {
        return INSTANCE.register(BottledButterflyBlock.getRegistryId(butterflyIndex), BottledButterflyBlock::new);
    }

    public static final List<DeferredHolder<Block, Block>> BOTTLED_BUTTERFLY_BLOCKS = new ArrayList<>() {
        {
            for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                add(registerBottledButterfly(i));
            }
        }
    };

    // Bottled Caterpillars
    private static DeferredHolder<Block, Block> registerBottledCaterpillar(int butterflyIndex) {
        return INSTANCE.register(BottledCaterpillarBlock.getRegistryId(butterflyIndex), BlockRegistry::bottledCaterpillarBlock);
    }

    public static final List<DeferredHolder<Block, Block>> BOTTLED_CATERPILLAR_BLOCKS = new ArrayList<>() {
        {
            for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                add(registerBottledCaterpillar(i));
            }
        }
    };

    // Flower Buds
    public static final DeferredHolder<Block, Block> ALLIUM_BUD = INSTANCE.register(
            "bud_allium", () -> new FlowerCropBlock(Blocks.ALLIUM)
    );

    public static final DeferredHolder<Block, Block> AZURE_BLUET_BUD = INSTANCE.register(
            "bud_azure_bluet", () -> new FlowerCropBlock(Blocks.AZURE_BLUET)
    );

    public static final DeferredHolder<Block, Block> BLUE_ORCHID_BUD = INSTANCE.register(
            "bud_blue_orchid", () -> new FlowerCropBlock(Blocks.BLUE_ORCHID)
    );

    public static final DeferredHolder<Block, Block> CORNFLOWER_BUD = INSTANCE.register(
            "bud_cornflower", () -> new FlowerCropBlock(Blocks.CORNFLOWER)
    );

    public static final DeferredHolder<Block, Block> DANDELION_BUD = INSTANCE.register(
            "bud_dandelion", () -> new FlowerCropBlock(Blocks.DANDELION)
    );

    public static final DeferredHolder<Block, Block> LILY_OF_THE_VALLEY_BUD = INSTANCE.register(
            "bud_lily_of_the_valley", () -> new FlowerCropBlock(Blocks.LILY_OF_THE_VALLEY)
    );

    public static final DeferredHolder<Block, Block> ORANGE_TULIP_BUD = INSTANCE.register(
            "bud_orange_tulip", () -> new FlowerCropBlock(Blocks.ORANGE_TULIP)
    );

    public static final DeferredHolder<Block, Block> OXEYE_DAISY_BUD = INSTANCE.register(
            "bud_oxeye_daisy", () -> new FlowerCropBlock(Blocks.OXEYE_DAISY)
    );

    public static final DeferredHolder<Block, Block> PINK_TULIP_BUD = INSTANCE.register(
            "bud_pink_tulip", () -> new FlowerCropBlock(Blocks.PINK_TULIP)
    );

    public static final DeferredHolder<Block, Block> POPPY_BUD = INSTANCE.register(
            "bud_poppy", () -> new FlowerCropBlock(Blocks.POPPY)
    );

    public static final DeferredHolder<Block, Block> RED_TULIP_BUD = INSTANCE.register(
            "bud_red_tulip", () -> new FlowerCropBlock(Blocks.RED_TULIP)
    );

    public static final DeferredHolder<Block, Block> WHITE_TULIP_BUD = INSTANCE.register(
            "bud_white_tulip", () -> new FlowerCropBlock(Blocks.WHITE_TULIP)
    );

    public static final DeferredHolder<Block, Block> WITHER_ROSE_BUD = INSTANCE.register(
            "bud_wither_rose", () -> new FlowerCropBlock(Blocks.WITHER_ROSE)
    );

    /**
     * Gets the flower bud for the specified flower block.
     * @param flowerBlock The flower we are trying to seed.
     * @return The bud block for the specified flower, if any.
     */
    public static Block getFlowerBud(Block flowerBlock) {
        if (flowerBlock == Blocks.ALLIUM) {
            return ALLIUM_BUD.get();
        }

        if (flowerBlock == Blocks.AZURE_BLUET) {
            return AZURE_BLUET_BUD.get();
        }

        if (flowerBlock == Blocks.BLUE_ORCHID) {
            return BLUE_ORCHID_BUD.get();
        }

        if (flowerBlock == Blocks.CORNFLOWER) {
            return CORNFLOWER_BUD.get();
        }

        if (flowerBlock == Blocks.DANDELION) {
            return DANDELION_BUD.get();
        }

        if (flowerBlock == Blocks.LILY_OF_THE_VALLEY) {
            return LILY_OF_THE_VALLEY_BUD.get();
        }

        if (flowerBlock == Blocks.ORANGE_TULIP) {
            return ORANGE_TULIP_BUD.get();
        }

        if (flowerBlock == Blocks.OXEYE_DAISY) {
            return OXEYE_DAISY_BUD.get();
        }

        if (flowerBlock == Blocks.PINK_TULIP) {
            return PINK_TULIP_BUD.get();
        }

        if (flowerBlock == Blocks.POPPY) {
            return POPPY_BUD.get();
        }

        if (flowerBlock == Blocks.RED_TULIP) {
            return RED_TULIP_BUD.get();
        }

        if (flowerBlock == Blocks.TORCHFLOWER) {
            return Blocks.TORCHFLOWER_CROP;
        }

        if (flowerBlock == Blocks.WHITE_TULIP) {
            return WHITE_TULIP_BUD.get();
        }

        if (flowerBlock == Blocks.WITHER_ROSE) {
            return WITHER_ROSE_BUD.get();
        }

        return null;
    }

    /**
     * Helper method for the "never" attribute.
     * @param blockState The current block state.
     * @param blockGetter Access to the block.
     * @param blockPos The block's position.
     * @param entityType The entity type trying to spawn.
     * @return Always FALSE.
     */
    public static boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, EntityType<?> entityType) {
        return false;
    }

    /**
     * Helper method for the "never" attribute.
     * @param blockState The current block state.
     * @param blockGetter Access to the block.
     * @param blockPos The block's position.
     * @return Always FALSE.
     */
    public static boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return false;
    }

    /**
     * Create a bottled caterpillar block.
     * @return A Bottled caterpillar block.
     */
    private static BottledCaterpillarBlock bottledCaterpillarBlock() {
        return new BottledCaterpillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)
                .isRedstoneConductor(BlockRegistry::never)
                .isSuffocating(BlockRegistry::never)
                .isValidSpawn(BlockRegistry::never)
                .isViewBlocking(BlockRegistry::never)
                .noOcclusion()
                .sound(SoundType.GLASS)
                .strength(0.3F));
    }
}
