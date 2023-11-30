package com.bokmcdok.butterflies.world.block;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyIds;
import com.bokmcdok.butterflies.world.entity.animal.Caterpillar;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ButterflyEggHolder {

    /**
     * Adds the butterfly egg contained in a leaf block to an item list.
     * @param blockState The state of the block containing the egg.
     * @param items The list of items to modify.
     * @return The updated item list.
     */
    @NotNull
    default List<ItemStack> addButterflyEggDrop(
            @NotNull BlockState blockState,
            @NotNull List<ItemStack> items) {
        int index = blockState.getValue(ButterflyLeavesBlock.BUTTERFLY_INDEX);
        ResourceLocation location = ButterflyIds.IndexToButterflyEggLocation(index);
        if (location != null) {
            Item entry = ForgeRegistries.ITEMS.getValue(location);
            if (entry != null) {
                items.add(new ItemStack(entry));
            }
        }

        return items;
    }

    /**
     * Try to spawn a caterpillar.
     * @param blockState The current block state.
     * @param level The current level.
     * @param position The position of the block.
     * @param random The random number generator.
     */
    default void trySpawnCaterpillar(@NotNull BlockState blockState,
                                     @NotNull ServerLevel level,
                                     @NotNull BlockPos position,
                                     @NotNull RandomSource random) {
        if (random.nextInt(15) == 0) {
            int directionIndex = random.nextInt(6);
            BlockPos spawnPosition;
            Direction direction;
            switch (directionIndex) {
                case 0 -> {
                    spawnPosition = position.below();
                    direction = Direction.UP;
                }
                case 1 -> {
                    spawnPosition = position.north();
                    direction = Direction.SOUTH;
                }
                case 2 -> {
                    spawnPosition = position.south();
                    direction = Direction.NORTH;
                }
                case 3 -> {
                    spawnPosition = position.east();
                    direction = Direction.WEST;
                }
                case 4 -> {
                    spawnPosition = position.west();
                    direction = Direction.EAST;
                }
                default -> {
                    spawnPosition = position.above();
                    direction = Direction.DOWN;
                }
            }

            if (level.isEmptyBlock(spawnPosition)) {
                int index = blockState.getValue(
                        ButterflyLeavesBlock.BUTTERFLY_INDEX);

                Caterpillar.spawn(
                        level,
                        ButterflyIds.IndexToCaterpillarLocation(index),
                        spawnPosition,
                        direction);

                ButterflyLeavesBlock.removeButterflyEgg(level, position);
            }
        }
    }
}
