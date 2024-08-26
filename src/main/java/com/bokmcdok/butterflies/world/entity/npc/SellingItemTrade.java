package com.bokmcdok.butterflies.world.entity.npc;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

/**
 * Creates a trade for selling.
 */
public class SellingItemTrade implements VillagerTrades.ItemListing {
    private final ItemStack givenItem;
    private final int emeraldCount;
    private final int sellingItemCount;
    private final int maxUses;
    private final int xpValue;
    private final float priceMultiplier;

    /**
     * Constructor
     * @param givenItem The item to sell.
     * @param emeraldCount The base cost in emeralds.
     * @param sellingItemCount The number of items to sell.
     * @param xpValue The XP awarded to the villager on a successful trade.
     */
    public SellingItemTrade(ItemLike givenItem,
                            int emeraldCount,
                            int sellingItemCount,
                            int xpValue) {
        this(new ItemStack(givenItem), emeraldCount, sellingItemCount, 12, xpValue);
    }

    /**
     * Constructor
     * @param givenItem The item to sell.
     * @param emeraldCount The base cost in emeralds.
     * @param sellingItemCount The number of items to sell.
     * @param maxUses The maximum number of trades.
     * @param xpValue The XP awarded to the villager on a successful trade.
     */
    public SellingItemTrade(ItemStack givenItem,
                            int emeraldCount,
                            int sellingItemCount,
                            int maxUses,
                            int xpValue) {
        this(givenItem, emeraldCount, sellingItemCount, maxUses, xpValue, 0.2F);
    }

    /**
     * Constructor
     * @param givenItem The item to sell.
     * @param emeraldCount The base cost in emeralds.
     * @param sellingItemCount The number of items to sell.
     * @param maxUses The maximum number of trades.
     * @param xpValue The XP awarded to the villager on a successful trade.
     * @param priceMultiplier The amount the price is modified by annoying or
     *                        helping the villager.
     */
    public SellingItemTrade(ItemStack givenItem,
                            int emeraldCount,
                            int sellingItemCount,
                            int maxUses,
                            int xpValue,
                            float priceMultiplier) {
        this.givenItem = givenItem;
        this.emeraldCount = emeraldCount;
        this.sellingItemCount = sellingItemCount;
        this.maxUses = maxUses;
        this.xpValue = xpValue;
        this.priceMultiplier = priceMultiplier;
    }

    /**
     * Get an offer for the trader to use.
     * @param trader The trader requesting the offer.
     * @param rand The RNG.
     * @return The offer to the player.
     */
    @Override
    public MerchantOffer getOffer(@NotNull Entity trader,
                                  @NotNull RandomSource rand) {
        return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCount),
                new ItemStack(this.givenItem.getItem(), this.sellingItemCount),
                this.maxUses,
                this.xpValue,
                this.priceMultiplier);
    }
}