package com.bokmcdok.butterflies.event.village;

import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.registries.VillagerProfessionRegistry;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.entity.npc.BuyingItemTrade;
import com.bokmcdok.butterflies.world.entity.npc.SellingItemTrade;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Listens for events based around villagers.
 */
public class VillageEventListener {

    // Registries.
    private final ItemRegistry itemRegistry;
    private final VillagerProfessionRegistry villagerProfessionRegistry;

    /**
     * Construction
     * @param forgeEventBus The event bus to register with.
     */
    public VillageEventListener(IEventBus forgeEventBus,
                                ItemRegistry itemRegistry,
                                VillagerProfessionRegistry villagerProfessionRegistry) {
        forgeEventBus.register(this);
        forgeEventBus.addListener(this::onVillagerTrades);
        forgeEventBus.addListener(this::onWandererTrades);

        this.itemRegistry = itemRegistry;
        this.villagerProfessionRegistry = villagerProfessionRegistry;
    }

    /**
     * Used to add/modify trades to professions.
     * @param event The event information.
     */
    private void onVillagerTrades(VillagerTradesEvent event) {
        if (event.getType() == villagerProfessionRegistry.getLepidopterist().get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            Collection<ButterflyData> butterflies = ButterflyData.getButterflyDataCollection();

            List<VillagerTrades.ItemListing> tradesLevel1 = trades.get(1);
            List<VillagerTrades.ItemListing> tradesLevel2 = trades.get(2);
            List<VillagerTrades.ItemListing> tradesLevel3 = trades.get(3);
            List<VillagerTrades.ItemListing> tradesLevel4 = trades.get(4);
            List<VillagerTrades.ItemListing> tradesLevel5 = trades.get(5);

            tradesLevel1.add(new SellingItemTrade(itemRegistry.getEmptyButterflyNet().get(), 5, 1, 1));
            tradesLevel3.add(new SellingItemTrade(itemRegistry.getSilk().get(), 32, 8, 10));
            tradesLevel5.add(new SellingItemTrade(itemRegistry.getZhuangziBook().get(), 35, 1, 30));

            List<RegistryObject<Item>> bottledButterflies = itemRegistry.getBottledButterflies();
            List<RegistryObject<Item>> bottledCaterpillars = itemRegistry.getBottledCaterpillars();
            List<RegistryObject<Item>> butterflyEggs = itemRegistry.getButterflyEggs();
            List<RegistryObject<Item>> butterflyScrolls = itemRegistry.getButterflyScrolls();
            List<RegistryObject<Item>> caterpillars = itemRegistry.getCaterpillars();

            for (ButterflyData butterfly : butterflies) {
                if (butterfly.type() != ButterflyData.ButterflyType.SPECIAL) {
                    int i = butterfly.butterflyIndex();
                    switch (butterfly.rarity()) {
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
    private void onWandererTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades();

        Collection<ButterflyData> butterflies = ButterflyData.getButterflyDataCollection();

        rareTrades.add(new SellingItemTrade(itemRegistry.getZhuangziBook().get(), 35, 1, 30));

        List<RegistryObject<Item>> bottledButterflies = itemRegistry.getBottledButterflies();

        for (ButterflyData butterfly : butterflies) {
            if (butterfly.type() != ButterflyData.ButterflyType.SPECIAL) {
                int i = butterfly.butterflyIndex();
                if (Objects.requireNonNull(butterfly.rarity()) == ButterflyData.Rarity.RARE) {
                    rareTrades.add(new SellingItemTrade(bottledButterflies.get(i).get(), 32, 1, 30));
                }
            }
        }
    }
}
