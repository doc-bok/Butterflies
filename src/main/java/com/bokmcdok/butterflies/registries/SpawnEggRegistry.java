package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyInfo;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import com.bokmcdok.butterflies.world.item.CaterpillarItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpawnEggRegistry {

    // An instance of a deferred registry we use to register items.
    public static final DeferredRegister<Item> SPAWN_EGGS;

    // Spawn Eggs
    public static final List<RegistryObject<Item>> EGG_SPAWN_EGGS;
    public static final List<RegistryObject<Item>> CHRYSALIS_SPAWN_EGGS;
    public static final List<RegistryObject<Item>> CATERPILLAR_SPAWN_EGGS;
    public static final List<RegistryObject<Item>> BUTTERFLY_SPAWN_EGGS;
    public static final RegistryObject<Item> BUTTERFLY_GOLEM_SPAWN_EGG;
    public static final RegistryObject<Item> PEACEMAKER_BUTTERFLY_SPAWN_EGG;
    public static final RegistryObject<Item> PEACEMAKER_EVOKER_SPAWN_EGG;
    public static final RegistryObject<Item> PEACEMAKER_ILLUSIONER_SPAWN_EGG;
    public static final RegistryObject<Item> PEACEMAKER_PILLAGER_SPAWN_EGG;
    public static final RegistryObject<Item> PEACEMAKER_VILLAGER_SPAWN_EGG;
    public static final RegistryObject<Item> PEACEMAKER_VINDICATOR_SPAWN_EGG;
    public static final RegistryObject<Item> PEACEMAKER_WANDERING_TRADER_SPAWN_EGG;
    public static final RegistryObject<Item> PEACEMAKER_WITCH_SPAWN_EGG;

    static {
        SPAWN_EGGS = DeferredRegister.create(ForgeRegistries.ITEMS, ButterfliesMod.MOD_ID);

        List<RegistryObject<Item>> eggSpawnEggs = new ArrayList<>();
        List<RegistryObject<Item>> chrysalisSpawnEggs = new ArrayList<>();
        List<RegistryObject<Item>> caterpillarSpawnEggs = new ArrayList<>();
        List<RegistryObject<Item>> butterflySpawnEggs = new ArrayList<>();
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
                () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_GOLEM,
                        0xffffff, 0xffffff, new Item.Properties()));

        PEACEMAKER_BUTTERFLY_SPAWN_EGG = SPAWN_EGGS.register("spawn_egg_peacemaker_butterfly",
                () -> new ForgeSpawnEggItem(PeacemakerEntityTypeRegistry.PEACEMAKER_BUTTERFLY,
                        0xffffff, 0xffffff, new Item.Properties()));

        PEACEMAKER_EVOKER_SPAWN_EGG = SPAWN_EGGS.register("spawn_egg_peacemaker_evoker",
                () -> new ForgeSpawnEggItem(PeacemakerEntityTypeRegistry.PEACEMAKER_EVOKER,
                        0xffffff, 0xffffff, new Item.Properties()));

        PEACEMAKER_ILLUSIONER_SPAWN_EGG = SPAWN_EGGS.register("spawn_egg_peacemaker_illusioner",
                () -> new ForgeSpawnEggItem(PeacemakerEntityTypeRegistry.PEACEMAKER_ILLUSIONER,
                        0xffffff, 0xffffff, new Item.Properties()));

        PEACEMAKER_PILLAGER_SPAWN_EGG = SPAWN_EGGS.register("spawn_egg_peacemaker_pillager",
                () -> new ForgeSpawnEggItem(PeacemakerEntityTypeRegistry.PEACEMAKER_PILLAGER,
                        0xffffff, 0xffffff, new Item.Properties()));

        PEACEMAKER_VILLAGER_SPAWN_EGG = SPAWN_EGGS.register("spawn_egg_peacemaker_villager",
                () -> new ForgeSpawnEggItem(PeacemakerEntityTypeRegistry.PEACEMAKER_VILLAGER,
                        0xffffff, 0xffffff, new Item.Properties()));

        PEACEMAKER_VINDICATOR_SPAWN_EGG = SPAWN_EGGS.register("spawn_egg_peacemaker_vindicator",
                () -> new ForgeSpawnEggItem(PeacemakerEntityTypeRegistry.PEACEMAKER_VINDICATOR,
                        0xffffff, 0xffffff, new Item.Properties()));

        PEACEMAKER_WANDERING_TRADER_SPAWN_EGG = SPAWN_EGGS.register("spawn_egg_peacemaker_wandering_trader",
                () -> new ForgeSpawnEggItem(PeacemakerEntityTypeRegistry.PEACEMAKER_WANDERING_TRADER,
                        0xffffff, 0xffffff, new Item.Properties()));

        PEACEMAKER_WITCH_SPAWN_EGG = SPAWN_EGGS.register("spawn_egg_peacemaker_witch",
                () -> new ForgeSpawnEggItem(PeacemakerEntityTypeRegistry.PEACEMAKER_WITCH,
                        0xffffff, 0xffffff, new Item.Properties()));
    }

    private static RegistryObject<Item> registerButterflyEggSpawnEgg(int butterflyIndex) {
        return SPAWN_EGGS.register("spawn_egg_egg_" + Butterfly.getRegistryId(butterflyIndex),
                () -> new ForgeSpawnEggItem(ButterflyEntityTypeRegistry.BUTTERFLY_EGGS.get(butterflyIndex),
                        0xffffff, 0xffffff, new Item.Properties()));
    }

    private static RegistryObject<Item> registerCaterpillarSpawnEgg(int butterflyIndex) {
        return SPAWN_EGGS.register("spawn_egg_" + CaterpillarItem.getRegistryId(butterflyIndex),
                () -> new ForgeSpawnEggItem(ButterflyEntityTypeRegistry.CATERPILLARS.get(butterflyIndex),
                        0xffffff, 0xffffff, new Item.Properties()));
    }

    private static RegistryObject<Item> registerChrysalisSpawnEgg(int butterflyIndex) {
        return SPAWN_EGGS.register("spawn_egg_chrysalis_" + Butterfly.getRegistryId(butterflyIndex),
                () -> new ForgeSpawnEggItem(ButterflyEntityTypeRegistry.CHRYSALISES.get(butterflyIndex),
                        0xffffff, 0xffffff, new Item.Properties()));
    }

    private static RegistryObject<Item> registerButterflySpawnEgg(int butterflyIndex) {
        return SPAWN_EGGS.register("spawn_egg_butterfly_" + Butterfly.getRegistryId(butterflyIndex),
                () -> new ForgeSpawnEggItem(ButterflyEntityTypeRegistry.BUTTERFLIES.get(butterflyIndex),
                        0xffffff, 0xffffff, new Item.Properties()));
    }

    /**
     * Prevent construction.
     */
    private SpawnEggRegistry() {}
}
