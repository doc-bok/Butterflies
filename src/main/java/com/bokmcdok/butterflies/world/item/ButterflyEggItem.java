package com.bokmcdok.butterflies.world.item;

import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.ButterflySpeciesList;
import com.bokmcdok.butterflies.world.entity.animal.ButterflyEgg;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * An egg that will eventually hatch into a caterpillar.
 */
public class ButterflyEggItem extends Item implements ButterflyContainerItem {

    //  The name this item is registered under.
    public static String getRegistryId(int butterflyIndex) {
        return ButterflySpeciesList.SPECIES[butterflyIndex] + "_egg";
    }

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

        MutableComponent newComponent = new TranslatableComponent("tooltip.butterflies.place_egg");
        Style style = newComponent.getStyle().withColor(TextColor.fromLegacyFormat(ChatFormatting.GRAY))
                .withItalic(true);
        newComponent.setStyle(style);
        components.add(newComponent);

        super.appendHoverText(stack, level, components, tooltipFlag);
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

                    ButterflyData data = ButterflyData.getEntry(this.butterflyIndex);
                    if (data != null) {
                        ResourceLocation eggEntity = data.getButterflyEggEntity();
                        ButterflyEgg.spawn((ServerLevel) context.getLevel(), eggEntity, clickedPos.relative(clickedFace), clickedFace.getOpposite());
                    }
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
