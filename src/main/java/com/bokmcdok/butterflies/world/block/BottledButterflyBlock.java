package com.bokmcdok.butterflies.world.block;

import com.bokmcdok.butterflies.registries.BlockRegistry;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BottledButterflyBlock extends Block {

    private static final String NAME = "block.butterflies.bottled_butterfly";

    //  The bottle's "model".
    private static final VoxelShape SHAPE = Shapes.or(
            Block.box(5.0, 0.0, 5.0, 10.0, 1.0, 10.0),
            Block.box(4.0, 1.0, 4.D, 11.0, 2.0, 11.0),
            Block.box(3.0, 2.0, 3.0, 12.0, 6.0, 12.0),
            Block.box(4.0, 6.0, 4.D, 11.0, 7.0, 11.0),
            Block.box(5.0, 7.0, 5.0, 10.0, 8.0, 10.0),
            Block.box(6.0, 8.0, 6.0, 9.0, 10.0, 9.0),
            Block.box(5.0, 10.0, 5.0, 10.0, 12.0, 10.0),
            Block.box(6.0, 12.0, 6.0, 9.0, 13.0, 9.0));

    /**
     * Create a butterfly block
     */
    public BottledButterflyBlock(Properties properties) {
        super(properties);
    }

    /**
     * Ensure we remove the butterfly when the block is destroyed.
     * @param level Allow access to the current level.
     * @param position The position of the block.
     * @param state The current block state.
     */
    @Override
    public void destroy(@NotNull LevelAccessor level,
                        @NotNull BlockPos position,
                        @NotNull BlockState state) {
        super.destroy(level, position, state);

        removeButterfly(level, position, Entity.RemovalReason.DISCARDED);
    }

    /**
     * Overridden, so we can use a single localisation string for all instances.
     * @return The description ID, which is a reference to the localisation
     *         string.
     */
    @NotNull
    @Override
    public MutableComponent getName() {
        return Component.translatable(NAME);
    }

    /**
     * Get the shape of the block.
     * @param blockState The current block state.
     * @param blockGetter Access to the block.
     * @param position The block's position.
     * @param collisionContext The collision context we are fetching for.
     * @return The block's bounding box.
     */
    @NotNull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(@NotNull BlockState blockState,
                               @NotNull BlockGetter blockGetter,
                               @NotNull BlockPos position,
                               @NotNull CollisionContext collisionContext) {
        return SHAPE;
    }

    /**
     * Tell this block to render as a normal block.
     * @param blockState The current block state.
     * @return Always MODEL.
     */
    @Override
    @NotNull
    @SuppressWarnings("deprecation")
    public RenderShape getRenderShape(@NotNull BlockState blockState) {
        return RenderShape.MODEL;
    }

    /**
     * Called when the block is replaced with another block
     * @param oldBlockState The original block state.
     * @param level The current level.
     * @param position The block's position.
     * @param newBlockState The new block state.
     * @param flag Unknown.
     */
    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(@NotNull BlockState oldBlockState,
                         @NotNull Level level,
                         @NotNull BlockPos position,
                         @NotNull BlockState newBlockState,
                         boolean flag) {
        super.onRemove(oldBlockState, level, position, newBlockState,flag);

        removeButterfly(level, position, Entity.RemovalReason.KILLED);
    }

    /**
     * Removes a butterfly for the specified reason.
     * @param level The current level.
     * @param position The block's position.
     * @param reason The removal reason.
     */
    private void removeButterfly(LevelAccessor level, BlockPos position, Entity.RemovalReason reason) {
        AABB aabb = new AABB(position);
        List<Butterfly> butterflies = level.getEntitiesOfClass(Butterfly.class, aabb);
        for(Butterfly i : butterflies) {
            i.remove(reason);
        }
    }
}
