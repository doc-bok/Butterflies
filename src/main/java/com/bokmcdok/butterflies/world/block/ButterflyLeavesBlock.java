package com.bokmcdok.butterflies.world.block;

import com.bokmcdok.butterflies.registries.BlockRegistry;
import com.bokmcdok.butterflies.world.ButterflyData;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A class to represent leaves that have a butterfly egg inside them.
 */
public class ButterflyLeavesBlock extends LeavesBlock implements ButterflyEggHolder {

    // An integer representation of the butterfly species.
    public static final IntegerProperty BUTTERFLY_INDEX = IntegerProperty.create("butterfly_index", 0, 15);

    /**
     * Plants an egg on the specified block if it is a leaf block.
     * @param level The current level.
     * @param position The position of the block.
     * @param location The resource location of the butterfly.
     * @return True if the egg was successfully planted, otherwise false.
     */
    public static boolean swapLeavesBlock(Level level,
                                          BlockPos position,
                                          ResourceLocation location) {
        BlockState blockState = level.getBlockState(position);

        if (blockState.getBlock() == Blocks.OAK_LEAVES) {
            swapLeavesBlock(level, position, location, BlockRegistry.BUTTERFLY_OAK_LEAVES.get());
            return true;
        }

        if (blockState.getBlock() == Blocks.SPRUCE_LEAVES) {
            swapLeavesBlock(level, position, location, BlockRegistry.BUTTERFLY_SPRUCE_LEAVES.get());
            return true;
        }

        if (blockState.getBlock() == Blocks.BIRCH_LEAVES) {
            swapLeavesBlock(level, position, location, BlockRegistry.BUTTERFLY_BIRCH_LEAVES.get());
            return true;
        }

        if (blockState.getBlock() == Blocks.JUNGLE_LEAVES) {
            swapLeavesBlock(level, position, location, BlockRegistry.BUTTERFLY_JUNGLE_LEAVES.get());
            return true;
        }

        if (blockState.getBlock() == Blocks.ACACIA_LEAVES) {
            swapLeavesBlock(level, position, location, BlockRegistry.BUTTERFLY_ACACIA_LEAVES.get());
            return true;
        }

        if (blockState.getBlock() == Blocks.DARK_OAK_LEAVES) {
            swapLeavesBlock(level, position, location, BlockRegistry.BUTTERFLY_DARK_OAK_LEAVES.get());
            return true;
        }

        if (blockState.getBlock() == Blocks.AZALEA_LEAVES) {
            swapLeavesBlock(level, position, location, BlockRegistry.BUTTERFLY_AZALEA_LEAVES.get());
            return true;
        }

        if (blockState.getBlock() == Blocks.FLOWERING_AZALEA_LEAVES) {
            swapLeavesBlock(level, position, location, BlockRegistry.BUTTERFLY_FLOWERING_AZALEA_LEAVES.get());
            return true;
        }

        if (blockState.getBlock() == Blocks.CHERRY_LEAVES) {
            swapLeavesBlock(level, position, location, BlockRegistry.BUTTERFLY_CHERRY_LEAVES.get());
            return true;
        }

        if (blockState.getBlock() == Blocks.MANGROVE_LEAVES) {
            swapLeavesBlock(level, position, location, BlockRegistry.BUTTERFLY_MANGROVE_LEAVES.get());
            return true;
        }

        return false;
    }

    /**
     * Removes an egg from the specified block if it is a leaf block.
     *
     * @param level    The current level.
     * @param position The position of the block.
     */
    public static void removeButterflyEgg(Level level,
                                          BlockPos position) {
        BlockState blockState = level.getBlockState(position);

        if (blockState.getBlock() == BlockRegistry.BUTTERFLY_OAK_LEAVES.get()) {
            swapLeavesBlock(level, position, null, Blocks.OAK_LEAVES);
            return;
        }

        if (blockState.getBlock() == BlockRegistry.BUTTERFLY_SPRUCE_LEAVES.get()) {
            swapLeavesBlock(level, position, null, Blocks.SPRUCE_LEAVES);
            return;
        }

        if (blockState.getBlock() == BlockRegistry.BUTTERFLY_BIRCH_LEAVES.get()) {
            swapLeavesBlock(level, position, null, Blocks.BIRCH_LEAVES);
            return;
        }

        if (blockState.getBlock() == BlockRegistry.BUTTERFLY_JUNGLE_LEAVES.get()) {
            swapLeavesBlock(level, position, null, Blocks.JUNGLE_LEAVES);
            return;
        }

        if (blockState.getBlock() == BlockRegistry.BUTTERFLY_ACACIA_LEAVES.get()) {
            swapLeavesBlock(level, position, null, Blocks.ACACIA_LEAVES);
            return;
        }

        if (blockState.getBlock() == BlockRegistry.BUTTERFLY_DARK_OAK_LEAVES.get()) {
            swapLeavesBlock(level, position, null, Blocks.DARK_OAK_LEAVES);
            return;
        }

        if (blockState.getBlock() == BlockRegistry.BUTTERFLY_AZALEA_LEAVES.get()) {
            swapLeavesBlock(level, position, null, Blocks.AZALEA_LEAVES);
            return;
        }

        if (blockState.getBlock() == BlockRegistry.BUTTERFLY_FLOWERING_AZALEA_LEAVES.get()) {
            swapLeavesBlock(level, position, null, Blocks.FLOWERING_AZALEA_LEAVES);
            return;
        }

        if (blockState.getBlock() == BlockRegistry.BUTTERFLY_CHERRY_LEAVES.get()) {
            swapLeavesBlock(level, position, null, Blocks.CHERRY_LEAVES);
            return;
        }

        if (blockState.getBlock() == BlockRegistry.BUTTERFLY_MANGROVE_LEAVES.get()) {
            swapLeavesBlock(level, position, null, Blocks.MANGROVE_LEAVES);
        }

    }

    /**
     * Plants an egg in the leaf block.
     * @param level The current level.
     * @param position The position of the block.
     * @param location The resource location of the butterfly.
     * @param block The block to replace the current one with.
     */
    private static void swapLeavesBlock(Level level,
                                        BlockPos position,
                                        ResourceLocation location,
                                        Block block) {
        BlockState oldBlockState = level.getBlockState(position);

        // Create a new block and copy the old state.
        BlockState newBlockState = block.defaultBlockState();
        newBlockState = newBlockState
                .setValue(LeavesBlock.DISTANCE, oldBlockState.getValue(LeavesBlock.DISTANCE))
                .setValue(LeavesBlock.PERSISTENT, oldBlockState.getValue(LeavesBlock.PERSISTENT))
                .setValue(LeavesBlock.WATERLOGGED, oldBlockState.getValue(LeavesBlock.WATERLOGGED));

        // Try and get the species index and save this state as well.
        if (location != null) {
            int index = ButterflyData.getButterflyIndex(location);
            if (index >= 0) {
                newBlockState = newBlockState.setValue(ButterflyLeavesBlock.BUTTERFLY_INDEX, index);
            }
        }

        // Update the block to the new block.
        level.setBlockAndUpdate(position, newBlockState);
    }

    /**
     * Creates a new butterfly leaves block.
     * @param properties The block properties.
     */
    public ButterflyLeavesBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(DISTANCE, 7)
                .setValue(PERSISTENT, Boolean.FALSE)
                .setValue(WATERLOGGED, Boolean.FALSE)
                .setValue(BUTTERFLY_INDEX, 0));
    }

    /**
     * Add the Butterfly Index to the block state definition.
     * @param builder The block state's builder.
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BUTTERFLY_INDEX);
    }

    /**
     * Add the butterfly egg to any drops from the leaf.
     * @param blockState The block state being looted.
     * @param builder The loot builder.
     * @return The normal loot, plus the butterfly egg held within.
     */
    @NotNull
    @Override
    @SuppressWarnings("deprecation")
    public List<ItemStack> getDrops(@NotNull BlockState blockState,
                                    @NotNull LootParams.Builder builder) {
        return addButterflyEggDrop(blockState, super.getDrops(blockState, builder));
    }

    /**
     * We will need to random tick so that eggs will spawn caterpillars
     * @param blockState The current block state.
     * @return Always TRUE.
     */
    @Override
    public boolean isRandomlyTicking(@NotNull BlockState blockState) {
        return true;
    }

    /**
     * After a certain amount of time, a caterpillar will spawn.
     * @param blockState The current block state.
     * @param level The current level.
     * @param position The position of the block.
     * @param random The random number generator.
     */
    @Override
    public void randomTick(@NotNull BlockState blockState,
                           @NotNull ServerLevel level,
                           @NotNull BlockPos position,
                           @NotNull RandomSource random) {

        trySpawnCaterpillar(blockState, level, position, random);

        // Only run the super's random tick if it would have ticked anyway.
        if (super.isRandomlyTicking(blockState)) {
            super.randomTick(blockState, level, position, random);
        }
    }
}
