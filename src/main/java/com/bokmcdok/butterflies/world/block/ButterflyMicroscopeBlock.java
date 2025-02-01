package com.bokmcdok.butterflies.world.block;

import com.bokmcdok.butterflies.registries.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
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

    // The shape of the block.
    private static final VoxelShape SHAPE;

    /**
     * Construction
     */
    public ButterflyMicroscopeBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.GOLD)
                .isRedstoneConductor(BlockRegistry::never)
                .isSuffocating(BlockRegistry::never)
                .isValidSpawn(BlockRegistry::never)
                .isViewBlocking(BlockRegistry::never)
                .noOcclusion()
                .sound(SoundType.STONE)
                .strength(1.0F));
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
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(@NotNull BlockState blockState,
                               @NotNull BlockGetter blockGetter,
                               @NotNull BlockPos blockPos,
                               @NotNull CollisionContext collisionContext) {
        return SHAPE;
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
