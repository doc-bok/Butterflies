package com.bokmcdok.butterflies.world.item;

import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.entity.animal.ButterflyEgg;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
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

    //  The index of the butterfly species.
    private final int butterflyIndex;

    /**
     * Construction
     * @param butterflyIndex The butterfly index of this item.
     * @param properties The item properties.
     */
    public ButterflyEggItem(int butterflyIndex,
                            Item.Properties properties) {
        super(properties);
        this.butterflyIndex = butterflyIndex;
    }

    /**
     * Get the butterfly index.
     * @return The butterfly index.
     */
    @Override
    public int getButterflyIndex() {
        return this.butterflyIndex;
    }

    /**
     * If used on a leaf block will place the egg.
     * @param context The context of the use action.
     * @return The result of the use action.
     */
    @NotNull
    @Override
    public InteractionResult useOn(@NotNull UseOnContext context) {
        Player player = context.getPlayer();
        if (player != null) {

            BlockPos clickedPos = context.getClickedPos();

            Block block = context.getLevel().getBlockState(clickedPos).getBlock();
            if (!(block instanceof LeavesBlock)) {
                return InteractionResult.FAIL;
            } else {
                if (!context.getLevel().isClientSide()) {
                    Direction clickedFace = context.getClickedFace();

                    ResourceLocation eggEntity = ButterflyData.indexToButterflyEggEntity(this.butterflyIndex);
                    ButterflyEgg.spawn((ServerLevel) context.getLevel(), eggEntity, clickedPos.relative(clickedFace), clickedFace.getOpposite());
                } else {
                    player.playSound(SoundEvents.SLIME_SQUISH_SMALL, 1F, 1F);
                }

                context.getItemInHand().shrink(1);

                return InteractionResult.CONSUME;
            }
        }

        return super.useOn(context);
    }
}
