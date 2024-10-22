package com.bokmcdok.butterflies.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

/**
 * A class to represent a placed butterfly origami block.
 */
public class ButterflyOrigamiBlock extends Block {

    // The block's facing property.
    public static final EnumProperty<FrontAndTop> ORIENTATION;

    // The various shapes.
    private final VoxelShape northAabb;
    private final VoxelShape southAabb;
    private final VoxelShape eastAabb;
    private final VoxelShape westAabb;
    private final VoxelShape upAabb;
    private final VoxelShape downAabb;

    /**
     * Construction
     */
    public ButterflyOrigamiBlock() {
        super(Properties.of()
                .noCollission()
                .strength(0.5F, 2.5F)
                .sound(SoundType.PINK_PETALS));

        this.registerDefaultState(this.defaultBlockState().setValue(ORIENTATION, FrontAndTop.NORTH_UP));

        this.upAabb = Block.box(1.0, 0.0, 1.0, 15.0, 4.0, 15.0);
        this.downAabb = Block.box(1.0, 12.0, 1.0, 15.0, 16.0, 15.0);
        this.northAabb = Block.box(1.0, 1.0, 12.0, 15.0, 15.0, 16.0);
        this.southAabb = Block.box(1.0, 1.0, 0.0, 15.0, 15.0, 4.0);
        this.eastAabb = Block.box(0.0, 1.0, 1.0, 4.0, 15.0, 15.0);
        this.westAabb = Block.box(12.0, 1.0, 1.0, 16.0, 15.0, 15.0);
    }

    /**
     * Determines whether the block can stay placed.
     * @param blockState The current block state.
     * @param levelReader Access to the level.
     * @param pos The block's position.
     * @return TRUE if the block can survive, FALSE otherwise.
     */
    @Override
    @SuppressWarnings("deprecation")
    public boolean canSurvive(BlockState blockState,
                              LevelReader levelReader,
                              BlockPos pos) {
        Direction direction = blockState.getValue(ORIENTATION).front();
        BlockPos blockPos = pos.relative(direction.getOpposite());
        return levelReader.getBlockState(blockPos).isFaceSturdy(levelReader, blockPos, direction);
    }

    /**
     * Get the shape of an origami block for collision detection.
     * @param blockState The block state.
     * @param blockGetter Accessor to world blocks.
     * @param pos The position of the block.
     * @param collisionContext The context for this collision.
     * @return The shape of the block.
     */
    @NotNull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(@NotNull BlockState blockState,
                               @NotNull BlockGetter blockGetter,
                               @NotNull BlockPos pos,
                               @NotNull CollisionContext collisionContext) {
        FrontAndTop direction = blockState.getValue(ORIENTATION);
        return switch (direction) {
            case NORTH_UP -> this.northAabb;
            case SOUTH_UP -> this.southAabb;
            case EAST_UP -> this.eastAabb;
            case WEST_UP -> this.westAabb;
            case DOWN_EAST, DOWN_NORTH, DOWN_SOUTH, DOWN_WEST -> this.downAabb;
            default -> this.upAabb;
        };
    }

    /**
     * Get the default direction depending on where the player placed the origami.
     * @param blockPlaceContext The context for the block place action.
     * @return The new block state.
     */
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        FrontAndTop orientation;

        Direction direction = blockPlaceContext.getClickedFace();
        switch (direction) {
            case NORTH -> orientation = FrontAndTop.NORTH_UP;
            case SOUTH -> orientation = FrontAndTop.SOUTH_UP;
            case EAST -> orientation = FrontAndTop.EAST_UP;
            case WEST -> orientation = FrontAndTop.WEST_UP;

            default -> {
                Direction horizontalDirection = blockPlaceContext.getHorizontalDirection();
                switch (horizontalDirection) {
                    case EAST -> orientation = direction == Direction.UP ? FrontAndTop.UP_EAST : FrontAndTop.DOWN_EAST;
                    case WEST -> orientation = direction == Direction.UP ? FrontAndTop.UP_WEST : FrontAndTop.DOWN_WEST;


                    case SOUTH -> orientation = direction == Direction.UP ? FrontAndTop.UP_SOUTH : FrontAndTop.DOWN_SOUTH;

                    default -> orientation = direction == Direction.UP ? FrontAndTop.UP_NORTH : FrontAndTop.DOWN_NORTH;
                }
            }
        }
        return defaultBlockState().setValue(ORIENTATION, orientation);
    }

    /**
     * Called on a block update.
     * @param thisBlockState The current block state.
     * @param direction The direction of the block update.
     * @param otherBlockState The state of the block that caused the update.
     * @param levelAccessor The current level.
     * @param thisBlockPos The current block position.
     * @param otherBlockPos The position of the block that caused the update.
     * @return The updated block state.
     */
    @NotNull
    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState thisBlockState,
                                  Direction direction,
                                  @NotNull BlockState otherBlockState,
                                  @NotNull LevelAccessor levelAccessor,
                                  @NotNull BlockPos thisBlockPos,
                                  @NotNull BlockPos otherBlockPos) {
        return direction.getOpposite() ==
                thisBlockState.getValue(ORIENTATION).front() && !thisBlockState.canSurvive(levelAccessor, thisBlockPos) ?
                Blocks.AIR.defaultBlockState() :
                super.updateShape(thisBlockState, direction, otherBlockState, levelAccessor, thisBlockPos, otherBlockPos);
    }

    /**
     * Create the basic definition for the block state.
     * @param blockStateBuilder The builder being used to create the block state.
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockStateBuilder) {
        blockStateBuilder.add(ORIENTATION);
    }

    static {
        ORIENTATION = BlockStateProperties.ORIENTATION;
    }
}
