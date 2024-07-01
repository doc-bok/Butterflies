package com.bokmcdok.butterflies.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a crop that grows into a flower.
 */
public class FlowerCropBlock extends CropBlock {

    // The shape of the flower.
    private static final VoxelShape[] SHAPE_BY_AGE;

    // The flower block the crop will grow into.
    private final Block flowerBlock;

    /**
     * Construction - copy the properties from the flower it will grow into.
     * @param block The flower block.
     */
    public FlowerCropBlock(Block block) {
        super(BlockBehaviour.Properties.copy(block));
        this.flowerBlock = block;
    }

    /**
     * Flower crops cannot be cloned or placed, even in creative mode.
     * @param blockGetter The block getter.
     * @param blockPos The block position.
     * @param blockState The block state.
     * @return An empty item stack.
     */
    @NotNull
    @Override
    public ItemStack getCloneItemStack(@NotNull BlockGetter blockGetter,
                                       @NotNull BlockPos blockPos,
                                       @NotNull BlockState blockState) {
        return ItemStack.EMPTY;
    }

    /**
     * Get the shape of the flower crop.
     * @param blockState The block state.
     * @param blockGetter The block getter.
     * @param blockPos The block position.
     * @param collisionContext The collision context.
     * @return The shape of the crop based on its age.
     */
    @NotNull
    @Override
    public VoxelShape getShape(@NotNull BlockState blockState,
                               @NotNull BlockGetter blockGetter,
                               @NotNull BlockPos blockPos,
                               @NotNull CollisionContext collisionContext) {
        Vec3 offset = blockState.getOffset(blockGetter, blockPos);
        return SHAPE_BY_AGE[this.getAge(blockState)].move(offset.x, offset.y, offset.z);
    }

    /**
     * Gets the block state based on the age of the crop. Will convert to a
     * flower when it is fully grown.
     * @param age The age of the crop.
     * @return The updated block state.
     */
    @NotNull
    @Override
    public BlockState getStateForAge(int age) {
        return age == MAX_AGE ? this.flowerBlock.defaultBlockState() : super.getStateForAge(age);
    }

    /**
     * FLower buds can be placed on grass blocks.
     * @param blockState The block state.
     * @param blockGetter The block getter.
     * @param blockPos The block position.
     * @return TRUE if the crop is on a grass block.
     */
    @Override
    protected boolean mayPlaceOn(BlockState blockState,
                                 @NotNull BlockGetter blockGetter,
                                 @NotNull BlockPos blockPos) {
        return blockState.is(Blocks.GRASS_BLOCK);
    }

    // Defines the shape of the crop for each age.
    static {
        SHAPE_BY_AGE = new VoxelShape[] {
                Block.box(5.0, 0.0, 5.0, 11.0, 2.0, 11.0),
                Block.box(5.0, 0.0, 5.0, 11.0, 4.0, 11.0),
                Block.box(5.0, 0.0, 5.0, 11.0, 6.0, 11.0),
                Block.box(5.0, 0.0, 5.0, 11.0, 8.0, 11.0),
                Block.box(5.0, 0.0, 5.0, 11.0, 10.0, 11.0),
                Block.box(5.0, 0.0, 5.0, 11.0, 10.0, 11.0),
                Block.box(5.0, 0.0, 5.0, 11.0, 10.0, 11.0),
                Block.box(5.0, 0.0, 5.0, 11.0, 10.0, 11.0)
        };
    }
}
