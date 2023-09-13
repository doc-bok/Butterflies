package com.bokmcdok.butterflies.world.block;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyIds;
import com.bokmcdok.butterflies.world.entity.ambient.Caterpillar;
import net.minecraft.core.BlockPos;
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
    default List<ItemStack> addButterflyEggDrop(@NotNull BlockState blockState,
                                                @NotNull List<ItemStack> items) {

        int index = blockState.getValue(ButterflyLeavesBlock.BUTTERFLY_INDEX);
        String entityId = ButterflyIds.IndexToEntityId(index);
        if (entityId != null) {
            Item entry = ForgeRegistries.ITEMS.getValue(new ResourceLocation(ButterfliesMod.MODID, entityId + "_egg"));
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
            if (level.isEmptyBlock(position.above())) {
                int index = blockState.getValue(ButterflyLeavesBlock.BUTTERFLY_INDEX);
                Caterpillar.spawn(level, ButterflyIds.IndexToEntityId(index), position.above());
                ButterflyLeavesBlock.removeButterflyEgg(level, position);
            }
        }
    }
}
