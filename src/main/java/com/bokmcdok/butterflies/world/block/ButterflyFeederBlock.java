package com.bokmcdok.butterflies.world.block;

import com.bokmcdok.butterflies.registries.BlockEntityTypeRegistry;
import com.bokmcdok.butterflies.registries.MenuTypeRegistry;
import com.bokmcdok.butterflies.world.level.block.entity.ButterflyFeederEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A butterfly feeder block.
 */
public class ButterflyFeederBlock extends BaseEntityBlock {

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
        super(BlockBehaviour.Properties.of());
        this.blockEntityTypeRegistry = blockEntityTypeRegistry;
        this.menuTypeRegistry = menuTypeRegistry;
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
