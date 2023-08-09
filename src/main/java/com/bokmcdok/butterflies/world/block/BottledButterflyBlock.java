package com.bokmcdok.butterflies.world.block;

import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.world.block.entity.ButterflyBlockEntity;
import com.bokmcdok.butterflies.world.entity.ambient.Butterfly;
import com.bokmcdok.butterflies.world.item.BottledButterflyItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BottledButterflyBlock extends BaseEntityBlock {

    //  The name this item is registered under.
    public static final String NAME = "bottled_butterfly";

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

        AABB aabb = new AABB(position);
        List<Butterfly> butterflies = level.getEntitiesOfClass(Butterfly.class, aabb);
        for(Butterfly i : butterflies) {
            i.remove(Entity.RemovalReason.DISCARDED);
        }
    }

    /**
     * Transfer the NBT data to the drops when the block is destroyed.
     * @param blockState The current block state.
     * @param builder The loot drop builder.
     * @return The loot dropped by this block.
     */
    @NotNull
    @Override
    @SuppressWarnings("deprecation")
    public List<ItemStack> getDrops(@NotNull BlockState blockState,
                                    @NotNull LootParams.Builder builder) {
        BlockEntity blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof ButterflyBlockEntity butterflyBlockEntity) {
            ItemStack stack = new ItemStack(ItemRegistry.BOTTLED_BUTTERFLY.get());
            BottledButterflyItem.setButterfly(stack, butterflyBlockEntity.getEntityId());

            List<ItemStack> result = Lists.newArrayList();
            result.add(stack);
            return result;
        }

        return super.getDrops(blockState, builder);
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
     * Create the block's entity data.
     * @param position The block's position.
     * @param blockState The current block state.
     * @return The new block entity.
     */
    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos position,
                                      @NotNull BlockState blockState) {
        return new ButterflyBlockEntity(position, blockState);
    }
}
