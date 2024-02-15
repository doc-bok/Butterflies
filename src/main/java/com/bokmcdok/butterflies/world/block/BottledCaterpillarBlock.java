package com.bokmcdok.butterflies.world.block;

import com.bokmcdok.butterflies.world.entity.animal.Caterpillar;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BottledCaterpillarBlock extends Block {

    //  The name this item is registered under.
    public static final String ADMIRAL_NAME = "bottled_caterpillar_admiral";
    public static final String BUCKEYE_NAME = "bottled_caterpillar_buckeye";
    public static final String CABBAGE_NAME = "bottled_caterpillar_cabbage";
    public static final String CHALKHILL_NAME = "bottled_caterpillar_chalkhill";
    public static final String CLIPPER_NAME = "bottled_caterpillar_clipper";
    public static final String COMMON_NAME = "bottled_caterpillar_common";
    public static final String EMPEROR_NAME = "bottled_caterpillar_emperor";
    public static final String FORESTER_NAME = "bottled_caterpillar_forester";
    public static final String GLASSWING_NAME = "bottled_caterpillar_glasswing";
    public static final String HAIRSTREAK_NAME = "bottled_caterpillar_hairstreak";
    public static final String HEATH_NAME = "bottled_caterpillar_heath";
    public static final String LONGWING_NAME = "bottled_caterpillar_longwing";
    public static final String MONARCH_NAME = "bottled_caterpillar_monarch";
    public static final String MORPHO_NAME = "bottled_caterpillar_morpho";
    public static final String RAINBOW_NAME = "bottled_caterpillar_rainbow";
    public static final String SWALLOWTAIL_NAME = "bottled_caterpillar_swallowtail";

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
     * @param properties The properties of this block
     */
    public BottledCaterpillarBlock(Properties properties) {
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

        removeEntity(level, position, Entity.RemovalReason.DISCARDED);
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

        removeEntity(level, position, Entity.RemovalReason.KILLED);
    }

    /**
     * Removes a butterfly for the specified reason.
     * @param level The current level.
     * @param position The block's position.
     * @param reason The removal reason.
     */
    private void removeEntity(LevelAccessor level, BlockPos position, Entity.RemovalReason reason) {
        AABB aabb = new AABB(position);
        List<Caterpillar> entities = level.getEntitiesOfClass(Caterpillar.class, aabb);
        for(Caterpillar i : entities) {
            i.remove(reason);
        }
    }
}
