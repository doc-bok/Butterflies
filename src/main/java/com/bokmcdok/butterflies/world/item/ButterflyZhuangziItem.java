package com.bokmcdok.butterflies.world.item;

import com.bokmcdok.butterflies.client.gui.screens.ButterflyZhuangziScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
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
    public InteractionResult use(Level level,
                                 @NotNull Player player,
                                 @NotNull InteractionHand hand) {

        if (level.isClientSide()) {
            openScreen();
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResult.SUCCESS;
    }

    /**
     * Open the screen. Kept separate, so it can be excluded from server builds.
     */
    @OnlyIn(Dist.CLIENT)
    private void openScreen() {
        Minecraft.getInstance().setScreen(new ButterflyZhuangziScreen());
    }
}
