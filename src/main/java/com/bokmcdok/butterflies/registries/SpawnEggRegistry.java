package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyInfo;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import com.bokmcdok.butterflies.world.item.CaterpillarItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpawnEggRegistry {

    // An instance of a deferred registry we use to register items.
    public static final DeferredRegister<Item> SPAWN_EGGS;

    // Spawn Eggs
    public static final List<DeferredHolder<Item, Item>> EGG_SPAWN_EGGS;
    public static final List<DeferredHolder<Item, Item>> CHRYSALIS_SPAWN_EGGS;
    public static final List<DeferredHolder<Item, Item>> CATERPILLAR_SPAWN_EGGS;
    public static final List<DeferredHolder<Item, Item>> BUTTERFLY_SPAWN_EGGS;
    public static final DeferredHolder<Item, Item> BUTTERFLY_GOLEM_SPAWN_EGG;
    public static final DeferredHolder<Item, Item> PEACEMAKER_BUTTERFLY_SPAWN_EGG;
    public static final DeferredHolder<Item, Item> PEACEMAKER_COW_SPAWN_EGG;
    public static final DeferredHolder<Item, Item> PEACEMAKER_EVOKER_SPAWN_EGG;
    public static final DeferredHolder<Item, Item> PEACEMAKER_ILLUSIONER_SPAWN_EGG;
    public static final DeferredHolder<Item, Item> PEACEMAKER_PILLAGER_SPAWN_EGG;
    public static final DeferredHolder<Item, Item> PEACEMAKER_VILLAGER_SPAWN_EGG;
    public static final DeferredHolder<Item, Item> PEACEMAKER_VINDICATOR_SPAWN_EGG;
    public static final DeferredHolder<Item, Item> PEACEMAKER_WANDERING_TRADER_SPAWN_EGG;
    public static final DeferredHolder<Item, Item> PEACEMAKER_WITCH_SPAWN_EGG;

    static {
        SPAWN_EGGS = DeferredRegister.create(BuiltInRegistries.ITEM, ButterfliesMod.MOD_ID);

        List<DeferredHolder<Item, Item>> eggSpawnEggs = new ArrayList<>();
        List<DeferredHolder<Item, Item>> chrysalisSpawnEggs = new ArrayList<>();
        List<DeferredHolder<Item, Item>> caterpillarSpawnEggs = new ArrayList<>();
        List<DeferredHolder<Item, Item>> butterflySpawnEggs = new ArrayList<>();
        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            eggSpawnEggs.add(registerButterflyEggSpawnEgg(i));
            chrysalisSpawnEggs.add(registerChrysalisSpawnEgg(i));
            caterpillarSpawnEggs.add(registerCaterpillarSpawnEgg(i));
            butterflySpawnEggs.add(registerButterflySpawnEgg(i));
        }

        EGG_SPAWN_EGGS = Collections.unmodifiableList(eggSpawnEggs);
        CHRYSALIS_SPAWN_EGGS = Collections.unmodifiableList(chrysalisSpawnEggs);
        CATERPILLAR_SPAWN_EGGS = Collections.unmodifiableList(caterpillarSpawnEggs);
        BUTTERFLY_SPAWN_EGGS = Collections.unmodifiableList(butterflySpawnEggs);

        BUTTERFLY_GOLEM_SPAWN_EGG = SPAWN_EGGS.register("spawn_egg_golem_butterfly",
                () -> new SpawnEggItem(EntityTypeRegistry.BUTTERFLY_GOLEM.get(),
                        new Item.Properties().setId(ItemRegistry.createResourceKey("spawn_egg_golem_butterfly"))));

        PEACEMAKER_BUTTERFLY_SPAWN_EGG = SPAWN_EGGS.register("spawn_egg_peacemaker_butterfly",
                () -> new SpawnEggItem(PeacemakerEntityTypeRegistry.PEACEMAKER_BUTTERFLY.get(),
                        new Item.Properties().setId(ItemRegistry.createResourceKey("spawn_egg_peacemaker_butterfly"))));

        PEACEMAKER_COW_SPAWN_EGG = SPAWN_EGGS.register("spawn_egg_peacemaker_cow",
                () -> new SpawnEggItem(PeacemakerEntityTypeRegistry.PEACEMAKER_COW.get(),
                        new Item.Properties().setId(ItemRegistry.createResourceKey("spawn_egg_peacemaker_cow"))));

        PEACEMAKER_EVOKER_SPAWN_EGG = SPAWN_EGGS.register("spawn_egg_peacemaker_evoker",
                () -> new SpawnEggItem(PeacemakerEntityTypeRegistry.PEACEMAKER_EVOKER.get(),
                        new Item.Properties().setId(ItemRegistry.createResourceKey("spawn_egg_peacemaker_evoker"))));

        PEACEMAKER_ILLUSIONER_SPAWN_EGG = SPAWN_EGGS.register("spawn_egg_peacemaker_illusioner",
                () -> new SpawnEggItem(PeacemakerEntityTypeRegistry.PEACEMAKER_ILLUSIONER.get(),
                        new Item.Properties().setId(ItemRegistry.createResourceKey("spawn_egg_peacemaker_illusioner"))));

        PEACEMAKER_PILLAGER_SPAWN_EGG = SPAWN_EGGS.register("spawn_egg_peacemaker_pillager",
                () -> new SpawnEggItem(PeacemakerEntityTypeRegistry.PEACEMAKER_PILLAGER.get(),
                        new Item.Properties().setId(ItemRegistry.createResourceKey("spawn_egg_peacemaker_pillager"))));

        PEACEMAKER_VILLAGER_SPAWN_EGG = SPAWN_EGGS.register("spawn_egg_peacemaker_villager",
                () -> new SpawnEggItem(PeacemakerEntityTypeRegistry.PEACEMAKER_VILLAGER.get(),
                        new Item.Properties().setId(ItemRegistry.createResourceKey("spawn_egg_peacemaker_villager"))));

        PEACEMAKER_VINDICATOR_SPAWN_EGG = SPAWN_EGGS.register("spawn_egg_peacemaker_vindicator",
                () -> new SpawnEggItem(PeacemakerEntityTypeRegistry.PEACEMAKER_VINDICATOR.get(),
                        new Item.Properties().setId(ItemRegistry.createResourceKey("spawn_egg_peacemaker_vindicator"))));

        PEACEMAKER_WANDERING_TRADER_SPAWN_EGG = SPAWN_EGGS.register("spawn_egg_peacemaker_wandering_trader",
                () -> new SpawnEggItem(PeacemakerEntityTypeRegistry.PEACEMAKER_WANDERING_TRADER.get(),
                        new Item.Properties().setId(ItemRegistry.createResourceKey("spawn_egg_peacemaker_wandering_trader"))));

        PEACEMAKER_WITCH_SPAWN_EGG = SPAWN_EGGS.register("spawn_egg_peacemaker_witch",
                () -> new SpawnEggItem(PeacemakerEntityTypeRegistry.PEACEMAKER_WITCH.get(),
                        new Item.Properties().setId(ItemRegistry.createResourceKey("spawn_egg_peacemaker_witch"))));
    }

    private static DeferredHolder<Item, Item> registerButterflyEggSpawnEgg(int butterflyIndex) {
        String registryId = "spawn_egg_egg_" + Butterfly.getRegistryId(butterflyIndex);
        return SPAWN_EGGS.register(registryId,
                () -> new SpawnEggItem(ButterflyEntityTypeRegistry.BUTTERFLY_EGGS.get(butterflyIndex).get(),
                        new Item.Properties().setId(ItemRegistry.createResourceKey(registryId))));
    }

    private static DeferredHolder<Item, Item> registerCaterpillarSpawnEgg(int butterflyIndex) {
        String registryId = "spawn_egg_" + CaterpillarItem.getRegistryId(butterflyIndex);
        return SPAWN_EGGS.register(registryId,
                () -> new SpawnEggItem(ButterflyEntityTypeRegistry.CATERPILLARS.get(butterflyIndex).get(),
                        new Item.Properties().setId(ItemRegistry.createResourceKey(registryId))));
    }

    private static DeferredHolder<Item, Item> registerChrysalisSpawnEgg(int butterflyIndex) {
        String registryId = "spawn_egg_chrysalis_" + Butterfly.getRegistryId(butterflyIndex);
        return SPAWN_EGGS.register(registryId,
                () -> new SpawnEggItem(ButterflyEntityTypeRegistry.CHRYSALISES.get(butterflyIndex).get(),
                        new Item.Properties().setId(ItemRegistry.createResourceKey(registryId))));
    }

    private static DeferredHolder<Item, Item> registerButterflySpawnEgg(int butterflyIndex) {
        String registryId = "spawn_egg_butterfly_" + Butterfly.getRegistryId(butterflyIndex);
        return SPAWN_EGGS.register(registryId,
                () -> new SpawnEggItem(ButterflyEntityTypeRegistry.BUTTERFLIES.get(butterflyIndex).get(),
                        new Item.Properties().setId(ItemRegistry.createResourceKey(registryId))));
    }

    /**
     * Prevent construction.
     */
    private SpawnEggRegistry() {}
}
