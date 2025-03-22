package com.bokmcdok.butterflies.world.block;

import com.bokmcdok.butterflies.registries.DataComponentRegistry;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.registries.MenuTypeRegistry;
import com.bokmcdok.butterflies.world.inventory.ButterflyMicroscopeMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

/**
 * Butterfly Microscope
 *  By placing a Butterfly Scroll in this block, players can learn more about
 *  the butterfly. It can also be used to create Butterfly Books and append
 *  pages to it.
 */
public class ButterflyMicroscopeBlock extends Block {

    // The translation string for the title.
    private static final Component CONTAINER_TITLE =
            Component.translatable("container.butterfly_microscope");

    // The shape of the block.
    private static final VoxelShape SHAPE;

    // Access to data components.
    private final DataComponentRegistry dataComponentRegistry;

    // Access to items.
    private final ItemRegistry itemRegistry;

    // Access to menu types.
    private final MenuTypeRegistry menuTypeRegistry;

    /**
     * Construction
     */
    public ButterflyMicroscopeBlock(DataComponentRegistry dataComponentRegistry,
                                    ItemRegistry itemRegistry,
                                    MenuTypeRegistry menuTypeRegistry,
                                    Properties properties) {
        super(properties);

        this.dataComponentRegistry = dataComponentRegistry;
        this.itemRegistry = itemRegistry;
        this.menuTypeRegistry = menuTypeRegistry;
    }

    /**
     * Get the shape of the block.
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
        return SHAPE;
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
     * Open the menu when the block is interacted with.
     * @param state The block's state.
     * @param level The current level.
     * @param pos The block's position.
     * @param player The player using the block.
     * @param hitResult The result of the collision detection.
     * @return The result of the interaction (usually consumed).
     */
    @NotNull
    @Override
    public InteractionResult useWithoutItem(@NotNull BlockState state,
                                            Level level,
                                            @NotNull BlockPos pos,
                                            @NotNull Player player,
                                            @NotNull BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            player.openMenu(state.getMenuProvider(level, pos));
            return InteractionResult.CONSUME;
        }
    }

    /**
     * Provides the menu for this block.
     * @param blockState The current block state.
     * @param level The current level.
     * @param blockPos The position of the block.
     * @return A new menu provider.
     */
    @Override
    public MenuProvider getMenuProvider(@NotNull BlockState blockState,
                                        @NotNull Level level,
                                        @NotNull BlockPos blockPos) {
        return new SimpleMenuProvider((containerId,
                                       inventory,
                                       title) ->
                new ButterflyMicroscopeMenu(
                        dataComponentRegistry,
                        itemRegistry,
                        menuTypeRegistry.getButterflyMicroscopeMenu().get(),
                        containerId,
                        inventory,
                        ContainerLevelAccess.create(level, blockPos)),
                CONTAINER_TITLE);
    }

    // Defines the shape of the block.
    static {
        SHAPE = Shapes.or(
                Block.box( 0.0,  0.0,  0.0, 16.0,  3.0, 16.0),
                Block.box( 2.0,  3.0,  2.0,  5.0,  9.0,  5.0),
                Block.box( 8.0,  5.0,  8.0, 10.0, 16.0, 10.0),
                Block.box( 3.0,  9.0,  3.0,  4.0, 10.0,  4.0),
                Block.box( 4.0, 10.0,  4.0,  5.0, 11.0,  5.0),
                Block.box( 5.0, 11.0,  5.0,  6.0, 12.0,  6.0),
                Block.box( 6.0, 12.0,  6.0,  7.0, 13.0,  7.0),
                Block.box( 7.0, 13.0,  7.0,  8.0, 14.0,  8.0)
        );
    }
}
