package com.bokmcdok.butterflies.world.item;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.entity.animal.Caterpillar;
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
 * A class to represent a caterpillar in the player's inventory.
 */
public class CaterpillarItem extends Item {
    
    public static final String ADMIRAL_NAME = "caterpillar_admiral";    
    public static final String BUCKEYE_NAME = "caterpillar_buckeye";
    public static final String CABBAGE_NAME = "caterpillar_cabbage";
    public static final String CHALKHILL_NAME = "caterpillar_chalkhill";
    public static final String CLIPPER_NAME = "caterpillar_clipper";
    public static final String COMMON_NAME = "caterpillar_common";
    public static final String EMPEROR_NAME = "caterpillar_emperor";
    public static final String FORESTER_NAME = "caterpillar_forester";
    public static final String GLASSWING_NAME = "caterpillar_glasswing";
    public static final String HAIRSTREAK_NAME = "caterpillar_hairstreak";
    public static final String HEATH_NAME = "caterpillar_heath";
    public static final String LONGWING_NAME = "caterpillar_longwing";
    public static final String MONARCH_NAME = "caterpillar_monarch";
    public static final String MORPHO_NAME = "caterpillar_morpho";
    public static final String RAINBOW_NAME = "caterpillar_rainbow";
    public static final String SWALLOWTAIL_NAME = "caterpillar_swallowtail";

    private final ResourceLocation species;

    /**
     * Construction
     * @param species The species of the caterpillar
     */
    public CaterpillarItem(String species) {
        super(new Item.Properties());

        this.species = new ResourceLocation(ButterfliesMod.MODID, species);
    }

    /**
     * Places the butterfly scroll on a block.
     * @param context Contains information about the block the user clicked on.
     * @return The result of the interaction.
     */
    @Override
    @NotNull
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
                    Caterpillar.spawn((ServerLevel) context.getLevel(), this.species, clickedPos.relative(clickedFace), clickedFace.getOpposite(), false);
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
