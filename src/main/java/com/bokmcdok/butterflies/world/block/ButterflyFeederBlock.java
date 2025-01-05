package com.bokmcdok.butterflies.world.block;

import com.bokmcdok.butterflies.registries.BlockEntityTypeRegistry;
import com.bokmcdok.butterflies.registries.BlockRegistry;
import com.bokmcdok.butterflies.registries.MenuTypeRegistry;
import com.bokmcdok.butterflies.world.block.entity.ButterflyFeederEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A butterfly feeder block.
 */
public class ButterflyFeederBlock extends BaseEntityBlock {

    private static final MapCodec<ButterflyFeederBlock> CODEC =
            simpleCodec((x) -> new ButterflyFeederBlock(null, null));

    //  The bottle's "model".
    private static final VoxelShape SHAPE = Shapes.or(
            Block.box(14.0,  0.0,  0.0, 16.0, 12.0,  2.0),
            Block.box(14.0,  0.0, 14.0, 16.0, 12.0, 16.0),
            Block.box( 0.0,  0.0, 14.0,  2.0, 12.0, 16.0),
            Block.box( 0.0, 12.0,  0.0, 16.0, 14.0, 16.0),
            Block.box(15.0, 14.0,  0.0, 16.0, 16.0, 16.0),
            Block.box( 0.0, 14.0,  0.0,  1.0, 16.0, 16.0),
            Block.box( 1.0, 14.0, 15.0, 15.0, 16.0, 16.0),
            Block.box( 1.0, 14.0,  0.0, 15.0, 16.0,  1.0),
            Block.box( 0.0,  0.0,  0.0,  2.0, 12.0,  2.0));

    // The registries.
    private final BlockEntityTypeRegistry blockEntityTypeRegistry;
    private final MenuTypeRegistry menuTypeRegistry;

    /**
     * Construction.
     * @param blockEntityTypeRegistry The block entity registry.
     * @param menuTypeRegistry The menu type registry.
     */
    public ButterflyFeederBlock(BlockEntityTypeRegistry blockEntityTypeRegistry,
                                MenuTypeRegistry menuTypeRegistry) {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.SAND)
                .isRedstoneConductor(BlockRegistry::never)
                .isSuffocating(BlockRegistry::never)
                .isValidSpawn(BlockRegistry::never)
                .isViewBlocking(BlockRegistry::never)
                .noOcclusion()
                .sound(SoundType.BAMBOO)
                .strength(0.3F));

        this.blockEntityTypeRegistry = blockEntityTypeRegistry;
        this.menuTypeRegistry = menuTypeRegistry;
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
     * Get the codec whatever that is.
     * @return The codec.
     */
    @NotNull
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
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
     * Drop the container's items when it is destroyed.
     * @param blockState The current block state.
     * @param level The current level.
     * @param blockPos The block's position.
     * @param newBlockState The new block state.
     * @param unknown Unknown flag.
     */
    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(BlockState blockState,
                         @NotNull Level level,
                         @NotNull BlockPos blockPos,
                         BlockState newBlockState,
                         boolean unknown) {
        if (!blockState.is(newBlockState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity instanceof Container) {
                Containers.dropContents(level, blockPos, (Container)blockEntity);
                level.updateNeighbourForOutputSignal(blockPos, this);
            }

            super.onRemove(blockState, level, blockPos, newBlockState, unknown);
        }
    }

    /**
     * Create a block entity for this block.
     * @param blockPos The position of the block.
     * @param blockState The block's state.
     * @return A new block entity.
     */
    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos,
                                      @NotNull BlockState blockState) {
        return new ButterflyFeederEntity(
                this.menuTypeRegistry, blockEntityTypeRegistry.getButterflyFeeder().get(),
                blockPos,
                blockState);
    }

    /**
     * Open the menu when the block is interacted with.
     * @param blockState The block's state.
     * @param level The current level.
     * @param blockPos The block's position.
     * @param player The player using the block.
     * @param interactionHand The hand interacting with the block.
     * @param blockHitResult The result of the collision detection.
     * @return The result of the interaction (usually consumed).
     */
    @NotNull
    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(@NotNull BlockState blockState,
                                 Level level,
                                 @NotNull BlockPos blockPos,
                                 @NotNull Player player,
                                 @NotNull InteractionHand interactionHand,
                                 @NotNull BlockHitResult blockHitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity instanceof ButterflyFeederEntity feederEntity) {
                player.openMenu(feederEntity);
            }

            return InteractionResult.CONSUME;
        }
    }
}
