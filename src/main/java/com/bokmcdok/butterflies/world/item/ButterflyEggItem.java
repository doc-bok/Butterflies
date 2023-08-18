package com.bokmcdok.butterflies.world.item;

import com.bokmcdok.butterflies.registries.BlockRegistry;
import com.bokmcdok.butterflies.world.ButterflyIds;
import com.bokmcdok.butterflies.world.block.ButterflyLeavesBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

/**
 * An egg that will eventually hatch into a caterpillar.
 */
public class ButterflyEggItem extends Item implements ButterflyContainerItem {

    //  The name this item is registered under.
    public static final String ADMIRAL_NAME = "admiral_egg";
    public static final String BUCKEYE_NAME = "buckeye_egg";
    public static final String CABBAGE_NAME = "cabbage_egg";
    public static final String CHALKHILL_NAME = "chalkhill_egg";
    public static final String CLIPPER_NAME = "clipper_egg";
    public static final String COMMON_NAME = "common_egg";
    public static final String EMPEROR_NAME = "emperor_egg";
    public static final String FORESTER_NAME = "forester_egg";
    public static final String GLASSWING_NAME = "glasswing_egg";
    public static final String HAIRSTREAK_NAME = "hairstreak_egg";
    public static final String HEATH_NAME = "heath_egg";
    public static final String LONGWING_NAME = "longwing_egg";
    public static final String MONARCH_NAME = "monarch_egg";
    public static final String MORPHO_NAME = "morpho_egg";
    public static final String RAINBOW_NAME = "rainbow_egg";
    public static final String SWALLOWTAIL_NAME = "swallowtail_egg";

    private final String entityId;

    /**
     * Construction
     * @param properties The item properties.
     */
    public ButterflyEggItem(String entityId, Item.Properties properties) {
        super(properties);
        this.entityId = entityId;
    }

    /**
     * If used on a leaf block will place the egg.
     * @param context The context of the use action.
     * @return The result of the use action.
     */
    @NotNull
    @Override
    public InteractionResult useOn(@NotNull UseOnContext context) {
        Level level = context.getLevel();
        BlockPos position = context.getClickedPos();
        BlockState blockState = level.getBlockState(position);
        ItemStack itemStack = context.getItemInHand();

        if (blockState.getBlock() == Blocks.OAK_LEAVES) {
            plantEgg(itemStack, level, position, BlockRegistry.BUTTERFLY_OAK_LEAVES);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        if (blockState.getBlock() == Blocks.SPRUCE_LEAVES) {
            plantEgg(itemStack, level, position, BlockRegistry.BUTTERFLY_SPRUCE_LEAVES);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        if (blockState.getBlock() == Blocks.BIRCH_LEAVES) {
            plantEgg(itemStack, level, position, BlockRegistry.BUTTERFLY_BIRCH_LEAVES);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        if (blockState.getBlock() == Blocks.JUNGLE_LEAVES) {
            plantEgg(itemStack, level, position, BlockRegistry.BUTTERFLY_JUNGLE_LEAVES);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        if (blockState.getBlock() == Blocks.ACACIA_LEAVES) {
            plantEgg(itemStack, level, position, BlockRegistry.BUTTERFLY_ACACIA_LEAVES);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        if (blockState.getBlock() == Blocks.DARK_OAK_LEAVES) {
            plantEgg(itemStack, level, position, BlockRegistry.BUTTERFLY_DARK_OAK_LEAVES);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        if (blockState.getBlock() == Blocks.AZALEA_LEAVES) {
            plantEgg(itemStack, level, position, BlockRegistry.BUTTERFLY_AZALEA_LEAVES);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        if (blockState.getBlock() == Blocks.FLOWERING_AZALEA_LEAVES) {
            plantEgg(itemStack, level, position, BlockRegistry.BUTTERFLY_FLOWERING_AZALEA_LEAVES);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return super.useOn(context);
    }

    /**
     * Plants an egg in the leaf block.
     * @param itemStack The item stack with the eggs.
     * @param level The current level.
     * @param position The position of the block.
     * @param block The block to replace the current one with.
     */
    private void plantEgg(ItemStack itemStack,
                          Level level,
                          BlockPos position,
                          RegistryObject<Block> block) {
        BlockState newBlockState = block.get().defaultBlockState();
        int index = ButterflyIds.EntityIdToIndex(entityId);
        if (index >= 0) {
            newBlockState = newBlockState.setValue(ButterflyLeavesBlock.BUTTERFLY_INDEX, index);
        }

        level.setBlockAndUpdate(position, newBlockState);
        itemStack.shrink(1);
    }
}
