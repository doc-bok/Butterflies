package com.bokmcdok.butterflies.world.entity.npc;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an offer to buy an item.
 */
public class BuyingItemTrade implements VillagerTrades.ItemListing {
    private final Item wantedItem;
    private final int count;
    private final int maxUses;
    private final int xpValue;
    private final float priceMultiplier;

    /**
     * Construction.
     * @param wantedItem The item the trader wants.
     * @param countIn The number of items the trader wants.
     * @param maxUsesIn The maximum number of trades.
     * @param xpValueIn The XP awarded for completing the trade.
     */
    public BuyingItemTrade(ItemLike wantedItem,
                           int countIn,
                           int maxUsesIn,
                           int xpValueIn) {
        this.wantedItem = wantedItem.asItem();
        this.count = countIn;
        this.maxUses = maxUsesIn;
        this.xpValue = xpValueIn;
        this.priceMultiplier = 0.05F;
    }

    @Override
    public MerchantOffer getOffer(@NotNull Entity trader,
                                  @NotNull RandomSource rand) {
        ItemStack stack = new ItemStack(this.wantedItem, this.count);
        return new MerchantOffer(stack,
                new ItemStack(Items.EMERALD),
                this.maxUses,
                this.xpValue,
                this.priceMultiplier);
    }
}