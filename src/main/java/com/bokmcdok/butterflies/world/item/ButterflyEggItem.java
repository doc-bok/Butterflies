package com.bokmcdok.butterflies.world.item;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.block.ButterflyLeavesBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
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

    private final ResourceLocation location;

    /**
     * Construction
     * @param properties The item properties.
     */
    public ButterflyEggItem(String entityId, Item.Properties properties) {
        super(properties);
        this.location = new ResourceLocation(ButterfliesMod.MODID, entityId);
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

        if (ButterflyLeavesBlock.swapLeavesBlock(level, position, this.location)) {
            ItemStack itemStack = context.getItemInHand();
            itemStack.shrink(1);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return super.useOn(context);
    }
}
