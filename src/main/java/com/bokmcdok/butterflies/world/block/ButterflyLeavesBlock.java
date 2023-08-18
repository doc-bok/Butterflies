package com.bokmcdok.butterflies.world.block;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyIds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A class to represent leaves that have a butterfly egg inside them.
 */
public class ButterflyLeavesBlock extends LeavesBlock {

    // An integer representation of the butterfly species.
    public static final IntegerProperty BUTTERFLY_INDEX = IntegerProperty.create("butterfly_index", 0, 15);

    /**
     * Creates a new butterfly leaves block.
     * @param properties The block properties.
     */
    public ButterflyLeavesBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(DISTANCE, 7)
                .setValue(PERSISTENT, Boolean.FALSE)
                .setValue(WATERLOGGED, Boolean.FALSE)
                .setValue(BUTTERFLY_INDEX, 0));
    }

    /**
     * Add the Butterfly Index to the block state definition.
     * @param builder The block state's builder.
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BUTTERFLY_INDEX);
    }

    /**
     * Add the butterfly egg to any drops from the leaf.
     * @param blockState The block state being looted.
     * @param builder The loot builder.
     * @return The normal loot, plus the butterfly egg held within.
     */
    @NotNull
    @Override
    @SuppressWarnings("deprecation")
    public List<ItemStack> getDrops(@NotNull BlockState blockState,
                                    @NotNull LootParams.Builder builder) {

        List<ItemStack> result = super.getDrops(blockState, builder);

        int index = blockState.getValue(BUTTERFLY_INDEX);
        String entityId = ButterflyIds.IndexToEntityId(index);
        if (entityId != null) {
            Item entry = ForgeRegistries.ITEMS.getValue(new ResourceLocation(ButterfliesMod.MODID, entityId + "_egg"));
            if (entry != null) {
                result.add(new ItemStack(entry));
            }
        }

        return result;
    }
}
