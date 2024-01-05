package com.bokmcdok.butterflies.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MangroveLeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A class to represent leaves that have a butterfly egg inside them.
 */
public class ButterflyMangroveLeavesBlock extends MangroveLeavesBlock implements ButterflyEggHolder {

    // An integer representation of the butterfly species.
    public static final IntegerProperty BUTTERFLY_INDEX = IntegerProperty.create("butterfly_index", 0, 15);

    /**
     * Creates a new butterfly leaves block.
     * @param properties The block properties.
     */
    public ButterflyMangroveLeavesBlock(Properties properties) {
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
                                    @NotNull LootContext.Builder builder) {
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
