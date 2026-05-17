package com.bokmcdok.butterflies.event.village;

import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.registries.VillagerProfessionRegistry;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.ButterflyInfo;
import com.bokmcdok.butterflies.world.entity.npc.BuyingItemTrade;
import com.bokmcdok.butterflies.world.entity.npc.SellingItemTrade;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.Objects;

/**
 * Listens for events based around villagers.
 */
public class VillageEventListener {

    /**
     * Construction
     * @param forgeEventBus The event bus to register with.
     */
    public VillageEventListener(IEventBus forgeEventBus) {
        forgeEventBus.register(this);
        forgeEventBus.addListener(this::onVillagerTrades);
        forgeEventBus.addListener(this::onWandererTrades);
    }

    /**
     * Used to add/modify trades to professions.
     * @param event The event information.
     */
    @SubscribeEvent
    private void onVillagerTrades(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfessionRegistry.LEPIDOPTERIST.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();


            List<VillagerTrades.ItemListing> tradesLevel1 = trades.get(1);
            List<VillagerTrades.ItemListing> tradesLevel2 = trades.get(2);
            List<VillagerTrades.ItemListing> tradesLevel3 = trades.get(3);
            List<VillagerTrades.ItemListing> tradesLevel4 = trades.get(4);
            List<VillagerTrades.ItemListing> tradesLevel5 = trades.get(5);

            tradesLevel1.add(new SellingItemTrade(ItemRegistry.EMPTY_BUTTERFLY_NET.get(), 5, 1, 1));
            tradesLevel3.add(new SellingItemTrade(ItemRegistry.SILK.get(), 32, 8, 10));
            tradesLevel5.add(new SellingItemTrade(ItemRegistry.BUTTERFLY_BANNER_PATTERN.get(), 8, 1, 30));
            tradesLevel5.add(new SellingItemTrade(ItemRegistry.ZHUANGZI_BOOK.get(), 35, 1, 30));

            List<DeferredHolder<Item, Item>> bottledButterflies = ItemRegistry.BOTTLED_BUTTERFLIES;
            List<DeferredHolder<Item, Item>> bottledCaterpillars = ItemRegistry.BOTTLED_CATERPILLARS;
            List<DeferredHolder<Item, Item>> butterflyEggs = ItemRegistry.BUTTERFLY_EGGS;
            List<DeferredHolder<Item, Item>> butterflyScrolls = ItemRegistry.BUTTERFLY_SCROLLS;
            List<DeferredHolder<Item, Item>> caterpillars = ItemRegistry.CATERPILLARS;

            for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
                if (ButterflyInfo.TYPES[i] != ButterflyData.ButterflyType.SPECIAL) {
                    switch (ButterflyInfo.RARITIES[i]) {
                        case COMMON:
                            tradesLevel1.add(new BuyingItemTrade(butterflyEggs.get(i).get(), 15, 16, 2));
                            tradesLevel1.add(new SellingItemTrade(butterflyEggs.get(i).get(), 6, 1, 1));

                            tradesLevel2.add(new SellingItemTrade(bottledCaterpillars.get(i).get(), 10, 1, 5));
                            tradesLevel2.add(new SellingItemTrade(butterflyScrolls.get(i).get(), 15, 1, 5));
                            tradesLevel2.add(new BuyingItemTrade(caterpillars.get(i).get(), 10, 12, 10));
                            tradesLevel2.add(new SellingItemTrade(caterpillars.get(i).get(), 8, 1, 5));

                            tradesLevel3.add(new SellingItemTrade(bottledButterflies.get(i).get(), 15, 1, 10));
                            break;

                        case UNCOMMON:
                            tradesLevel2.add(new BuyingItemTrade(butterflyEggs.get(i).get(), 15, 12, 1));
                            tradesLevel2.add(new SellingItemTrade(butterflyEggs.get(i).get(), 8, 1, 1));

                            tradesLevel3.add(new SellingItemTrade(bottledCaterpillars.get(i).get(), 15, 1, 10));
                            tradesLevel3.add(new SellingItemTrade(butterflyScrolls.get(i).get(), 20, 1, 10));
                            tradesLevel3.add(new BuyingItemTrade(caterpillars.get(i).get(), 8, 12, 20));
                            tradesLevel3.add(new SellingItemTrade(caterpillars.get(i).get(), 10, 1, 10));

                            tradesLevel4.add(new SellingItemTrade(bottledButterflies.get(i).get(), 20, 1, 15));
                            break;

                        case RARE:
                            tradesLevel3.add(new BuyingItemTrade(butterflyEggs.get(i).get(), 10, 1, 20));
                            tradesLevel3.add(new SellingItemTrade(butterflyEggs.get(i).get(), 10, 1, 10));

                            tradesLevel4.add(new SellingItemTrade(bottledCaterpillars.get(i).get(), 20, 1, 15));
                            tradesLevel4.add(new SellingItemTrade(butterflyScrolls.get(i).get(), 32, 1, 15));
                            tradesLevel4.add(new BuyingItemTrade(caterpillars.get(i).get(), 6, 1, 30));
                            tradesLevel4.add(new SellingItemTrade(caterpillars.get(i).get(), 15, 1, 15));

                            tradesLevel5.add(new SellingItemTrade(bottledButterflies.get(i).get(), 32, 1, 30));
                            break;
                    }
                }
            }
        }
    }

    /**
     * Used to add/modify trades to wandering traders.
     * @param event The event information.
     */
    @SubscribeEvent
    private void onWandererTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();
        List<DeferredHolder<Item, Item>> bottledButterflies = ItemRegistry.BOTTLED_BUTTERFLIES;

        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            if (ButterflyInfo.TYPES[i] != ButterflyData.ButterflyType.SPECIAL) {
                if (Objects.requireNonNull(ButterflyInfo.RARITIES[i]) == ButterflyData.Rarity.UNCOMMON) {
                    genericTrades.add(new SellingItemTrade(bottledButterflies.get(i).get(), 20, 1, 30));
                }
            }
        }
    }
}
