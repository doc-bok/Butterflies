package com.bokmcdok.butterflies.world.item;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.ButterflyInfo;
import com.bokmcdok.butterflies.world.entity.animal.Caterpillar;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * A class to represent a caterpillar in the player's inventory.
 */
public class CaterpillarItem extends Item {

    //  The name this item is registered under.
    public static String getRegistryId(int butterflyIndex) {
        return "caterpillar_" + ButterflyInfo.SPECIES[butterflyIndex];
    }

    private final ResourceLocation species;

    /**
     * Construction
     * @param properties The properties to apply to the item.
     * @param species The species of the caterpillar
     */
    public CaterpillarItem(Properties properties,
                           String species) {
        super(properties);

        this.species = new ResourceLocation(ButterfliesMod.MOD_ID, species);
    }

    /**
     * Adds some tooltips.
     * @param stack The item stack.
     * @param level The current level.
     * @param components The current text components.
     * @param tooltipFlag Is this a tooltip?
     */
    @Override
    public void appendHoverText(@NotNull ItemStack stack,
                                @Nullable Level level,
                                @NotNull List<Component> components,
                                @NotNull TooltipFlag tooltipFlag) {

        MutableComponent newComponent = new TranslatableComponent("tooltip.butterflies.place_caterpillar");
        Style style = newComponent.getStyle().withColor(TextColor.fromLegacyFormat(ChatFormatting.GRAY))
                .withItalic(true);
        newComponent.setStyle(style);
        components.add(newComponent);

        super.appendHoverText(stack, level, components, tooltipFlag);
    }

    /**
     * Overridden so we can use a single localisation string for all instances.
     * @param itemStack The stack to get the name for.
     * @return The description ID, which is a reference to the localisation
     *         string.
     */
    @NotNull
    @Override
    public Component getName(@NotNull ItemStack itemStack) {
        return new TranslatableComponent("entity." + species.toString().replace(":", "."));
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

            BlockState blockState = context.getLevel().getBlockState(clickedPos);
            if (!(ButterflyData.getEntry(this.species).isValidLandingBlock(blockState))) {
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
