package com.bokmcdok.butterflies.world.block;

import com.bokmcdok.butterflies.registries.BlockRegistry;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.block.entity.ButterflyBlockEntity;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BottledButterflyBlock extends BaseEntityBlock {

    //  The name this item is registered under.

    //  The name this item is registered under.
    public static final String ADMIRAL_NAME = "bottled_butterfly_admiral";
    public static final String BUCKEYE_NAME = "bottled_butterfly_buckeye";
    public static final String CABBAGE_NAME = "bottled_butterfly_cabbage";
    public static final String CHALKHILL_NAME = "bottled_butterfly_chalkhill";
    public static final String CLIPPER_NAME = "bottled_butterfly_clipper";
    public static final String COMMON_NAME = "bottled_butterfly_common";
    public static final String EMPEROR_NAME = "bottled_butterfly_emperor";
    public static final String FORESTER_NAME = "bottled_butterfly_forester";
    public static final String GLASSWING_NAME = "bottled_butterfly_glasswing";
    public static final String HAIRSTREAK_NAME = "bottled_butterfly_hairstreak";
    public static final String HEATH_NAME = "bottled_butterfly_heath";
    public static final String LONGWING_NAME = "bottled_butterfly_longwing";
    public static final String MONARCH_NAME = "bottled_butterfly_monarch";
    public static final String MORPHO_NAME = "bottled_butterfly_morpho";
    public static final String RAINBOW_NAME = "bottled_butterfly_rainbow";
    public static final String SWALLOWTAIL_NAME = "bottled_butterfly_swallowtail";
    
    //  TODO: Remove in future version
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
     */
    public BottledButterflyBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.GLASS)
                .isRedstoneConductor(BlockRegistry::never)
                .isSuffocating(BlockRegistry::never)
                .isValidSpawn(BlockRegistry::never)
                .isViewBlocking(BlockRegistry::never)
                .noOcclusion()
                .sound(SoundType.GLASS)
                .strength(0.3F));
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
     * Transfer the NBT data to the drops when the block is destroyed.
     * TODO: Included only for backward compatibility. This should be removed
     *       in a future version.
     * @param blockState The current block state.
     * @param builder The loot drop builder.
     * @return The loot dropped by this block.
     */

    @NotNull
    @Override
    @SuppressWarnings("deprecation")
    public List<ItemStack> getDrops(@NotNull BlockState blockState,
                                    LootContext.@NotNull Builder builder) {
        BlockEntity blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof ButterflyBlockEntity butterflyBlockEntity) {
            ResourceLocation entity = butterflyBlockEntity.getEntityLocation();
            if (entity != null) {
                int butterflyIndex = ButterflyData.getButterflyIndex(butterflyBlockEntity.getEntityLocation());
                ItemStack stack = new ItemStack(ItemRegistry.getBottledButterflyFromIndex(butterflyIndex).get());
                List<ItemStack> result = new ArrayList<>();
                result.add(stack);
                return result;
            }
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
        return ButterflyBlockEntity.CreateBottledButterflyBlockEntity(position, blockState);
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
