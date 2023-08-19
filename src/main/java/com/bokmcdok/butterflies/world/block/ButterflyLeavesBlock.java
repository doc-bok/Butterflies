package com.bokmcdok.butterflies.world.block;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.registries.BlockRegistry;
import com.bokmcdok.butterflies.world.ButterflyIds;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A class to represent leaves that have a butterfly egg inside them.
 */
public class ButterflyLeavesBlock extends LeavesBlock {

    // An integer representation of the butterfly species.
    public static final IntegerProperty BUTTERFLY_INDEX = IntegerProperty.create("butterfly_index", 0, 15);

    /**
     * Plants an egg on the specified block it it is a leaf block.
     * @param level The current level.
     * @param position The position of the block.
     * @param entityId The entity ID of the butterfly.
     * @return True if the egg was successfully planted, otherwise false.
     */
    public static boolean plantButterflyEgg(Level level,
                                            BlockPos position,
                                            String entityId) {
        BlockState blockState = level.getBlockState(position);

        if (blockState.getBlock() == Blocks.OAK_LEAVES) {
            plantButterflyEgg(level, position, entityId, BlockRegistry.BUTTERFLY_OAK_LEAVES);
            return true;
        }

        if (blockState.getBlock() == Blocks.SPRUCE_LEAVES) {
            plantButterflyEgg(level, position, entityId, BlockRegistry.BUTTERFLY_SPRUCE_LEAVES);
            return true;
        }

        if (blockState.getBlock() == Blocks.BIRCH_LEAVES) {
            plantButterflyEgg(level, position, entityId, BlockRegistry.BUTTERFLY_BIRCH_LEAVES);
            return true;
        }

        if (blockState.getBlock() == Blocks.JUNGLE_LEAVES) {
            plantButterflyEgg(level, position, entityId, BlockRegistry.BUTTERFLY_JUNGLE_LEAVES);
            return true;
        }

        if (blockState.getBlock() == Blocks.ACACIA_LEAVES) {
            plantButterflyEgg(level, position, entityId, BlockRegistry.BUTTERFLY_ACACIA_LEAVES);
            return true;
        }

        if (blockState.getBlock() == Blocks.DARK_OAK_LEAVES) {
            plantButterflyEgg(level, position, entityId, BlockRegistry.BUTTERFLY_DARK_OAK_LEAVES);
            return true;
        }

        if (blockState.getBlock() == Blocks.AZALEA_LEAVES) {
            plantButterflyEgg(level, position, entityId, BlockRegistry.BUTTERFLY_AZALEA_LEAVES);
            return true;
        }

        if (blockState.getBlock() == Blocks.FLOWERING_AZALEA_LEAVES) {
            plantButterflyEgg(level, position, entityId, BlockRegistry.BUTTERFLY_FLOWERING_AZALEA_LEAVES);
            return true;
        }

        if (blockState.getBlock() == Blocks.CHERRY_LEAVES) {
            plantButterflyEgg(level, position, entityId, BlockRegistry.BUTTERFLY_CHERRY_LEAVES);
            return true;
        }

        if (blockState.getBlock() == Blocks.MANGROVE_LEAVES) {
            plantButterflyEgg(level, position, entityId, BlockRegistry.BUTTERFLY_MANGROVE_LEAVES);
            return true;
        }

        return false;
    }

    /**
     * Plants an egg in the leaf block.
     * @param level The current level.
     * @param position The position of the block.
     * @param block The block to replace the current one with.
     */
    private static void plantButterflyEgg(Level level,
                                          BlockPos position,
                                          String entityId,
                                          RegistryObject<Block> block) {
        BlockState oldBlockState = level.getBlockState(position);

        // Create a new block and copy the old state.
        BlockState newBlockState = block.get().defaultBlockState();
        newBlockState = newBlockState
                .setValue(LeavesBlock.DISTANCE, oldBlockState.getValue(LeavesBlock.DISTANCE))
                .setValue(LeavesBlock.PERSISTENT, oldBlockState.getValue(LeavesBlock.PERSISTENT))
                .setValue(LeavesBlock.WATERLOGGED, oldBlockState.getValue(LeavesBlock.WATERLOGGED));

        // Try and get the species index and save this state as well.
        int index = ButterflyIds.EntityIdToIndex(entityId);
        if (index >= 0) {
            newBlockState = newBlockState.setValue(ButterflyLeavesBlock.BUTTERFLY_INDEX, index);
        }

        // Update the block to the new block.
        level.setBlockAndUpdate(position, newBlockState);
    }

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
