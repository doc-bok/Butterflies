package com.bokmcdok.butterflies.world.item;

import com.bokmcdok.butterflies.client.gui.screens.ButterflyScrollScreen;
import com.bokmcdok.butterflies.world.CompoundTagId;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ButterflyScrollItem extends Item implements ButterflyContainerItem {

    public static final String NAME = "butterfly_scroll";

    /**
     * The species of butterfly on this page
     */
    private String entityId;

    /**
     * Comstruction
     * @param itemProperties The properties of the item.
     */
    public ButterflyScrollItem(Item.Properties itemProperties) {
        super(itemProperties);
    }

    /**
     * Adds some helper text that tells us what butterfly is on the page (if any).
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
        appendButterflyNameToHoverText(stack, components);
        super.appendHoverText(stack, level, components, tooltipFlag);
    }

    /**
     * Open the GUI when the item is used.
     * @param level The current level.
     * @param player The current player.
     * @param hand The hand holding the item.
     * @return The interaction result (always SUCCESS).
     */
    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> use(Level level,
                                                  Player player,
                                                  @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (level.isClientSide()) {
            CompoundTag tag = itemstack.getTag();
            if (tag != null && tag.contains(CompoundTagId.CUSTOM_MODEL_DATA)) {
                Minecraft.getInstance().setScreen(new ButterflyScrollScreen(tag.getInt(CompoundTagId.CUSTOM_MODEL_DATA)));
            }
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
