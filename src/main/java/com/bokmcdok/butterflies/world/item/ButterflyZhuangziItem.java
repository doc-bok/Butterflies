package com.bokmcdok.butterflies.world.item;

import com.bokmcdok.butterflies.client.gui.screens.ButterflyZhuangziScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ButterflyZhuangziItem extends Item {

    public static final String NAME = "butterfly_zhuangzi";

    /**
     * Construction
     */
    public ButterflyZhuangziItem() {
        super(new Properties().stacksTo(1));
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
            Minecraft.getInstance().setScreen(new ButterflyZhuangziScreen());
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
