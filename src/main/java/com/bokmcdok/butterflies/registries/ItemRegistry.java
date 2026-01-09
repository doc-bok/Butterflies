package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyInfo;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import com.bokmcdok.butterflies.world.entity.animal.Caterpillar;
import com.bokmcdok.butterflies.world.item.BottledButterflyItem;
import com.bokmcdok.butterflies.world.item.BottledCaterpillarItem;
import com.bokmcdok.butterflies.world.item.ButterflyBookItem;
import com.bokmcdok.butterflies.world.item.ButterflyEggItem;
import com.bokmcdok.butterflies.world.item.ButterflyNetItem;
import com.bokmcdok.butterflies.world.item.ButterflyScrollItem;
import com.bokmcdok.butterflies.world.item.ButterflyZhuangziItem;
import com.bokmcdok.butterflies.world.item.CaterpillarItem;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class registers items with Forge's Item Registry
 */
public class ItemRegistry {

    // An instance of a deferred registry we use to register items.
    private final DeferredRegister<Item> deferredRegister;

    // Other registry references
    private BlockRegistry blockRegistry;
    private EntityTypeRegistry entityTypeRegistry;

    // Nets
    private RegistryObject<Item> emptyButterflyNet;
    private List<RegistryObject<Item>> butterflyNets;
    private RegistryObject<Item> burntButterflyNet;

    // Eggs
    private List<RegistryObject<Item>> butterflyEggs;

    // Caterpillars
    private List<RegistryObject<Item>> caterpillars;

    // Bottles
    private List<RegistryObject<Item>> bottledButterflies;
    private List<RegistryObject<Item>> bottledCaterpillars;

    // Scrolls
    private List<RegistryObject<Item>> butterflyScrolls;

    // Books
    private RegistryObject<Item> butterflyBook;
    private RegistryObject<Item> zhuangziBook;

    // Blocks
    private RegistryObject<Item> butterflyFeeder;
    private RegistryObject<Item> butterflyMicroscope;

    // Infested Apple
    private RegistryObject<Item> infestedApple;

    // Silk
    private RegistryObject<Item> silk;

    // Origami
    private List<RegistryObject<Item>> butterflyOrigami;

    // Sherd
    private RegistryObject<Item> butterflyPotterySherd;

    // Banner Pattern
    private RegistryObject<Item> butterflyBannerPattern;

    // Spawn Eggs
    private List<RegistryObject<Item>> eggSpawnEggs;
    private List<RegistryObject<Item>> chrysalisSpawnEggs;
    private List<RegistryObject<Item>> caterpillarSpawnEggs;
    private List<RegistryObject<Item>> butterflySpawnEggs;
    private RegistryObject<Item> butterflyGolemSpawnEgg;
    private RegistryObject<Item> peacemakerButterflySpawnEgg;
    private RegistryObject<Item> peacemakerEvokerSpawnEgg;
    private RegistryObject<Item> peacemakerIllusionerSpawnEgg;
    private RegistryObject<Item> peacemakerPillagerSpawnEgg;
    private RegistryObject<Item> peacemakerVillagerSpawnEgg;
    private RegistryObject<Item> peacemakerVindicatorSpawnEgg;
    private RegistryObject<Item> peacemakerWanderingTraderSpawnEgg;
    private RegistryObject<Item> peacemakerWitchSpawnEgg;


    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public ItemRegistry(@NotNull IEventBus modEventBus) {
        this.deferredRegister = DeferredRegister.create(ForgeRegistries.ITEMS, ButterfliesMod.MOD_ID);
        this.deferredRegister.register(modEventBus);
    }

    /**
     * Register the items. Must be called after construction and after block
     * registry initialisation.
     * @param blockRegistry The block registry.
     * @param entityTypeRegistry The entity type registry.
     */
    public void initialise(@NotNull BannerPatternRegistry bannerPatternRegistry,
                           @NotNull BlockRegistry blockRegistry,
                           @NotNull EntityTypeRegistry entityTypeRegistry,
                           @NotNull TagRegistry tagRegistry) {

        this.blockRegistry = Objects.requireNonNull(blockRegistry, "blockRegistry cannot be null");
        this.entityTypeRegistry = Objects.requireNonNull(entityTypeRegistry, "entityTypeRegistry cannot be null");

        // Nets
        this.emptyButterflyNet = deferredRegister.register(ButterflyNetItem.EMPTY_NAME, () -> new ButterflyNetItem(this, -1));
        this.butterflyNets = new ArrayList<>();
        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            this.butterflyNets.add(registerButterflyNet(i));
        }

        this.burntButterflyNet = deferredRegister.register("butterfly_net_burnt", () -> new Item(new Item.Properties()));

        // Eggs
        this.butterflyEggs = new ArrayList<>();
        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            this.butterflyEggs.add(registerButterflyEgg(i));
        }

        // Caterpillars
        this.caterpillars = new ArrayList<>();
        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            this.caterpillars.add(registerCaterpillar(i));
        }

        // Bottles
        this.bottledButterflies = new ArrayList<>();
        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            this.bottledButterflies.add(registerBottledButterfly(i));
        }

        this.bottledCaterpillars = new ArrayList<>();
        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            this.bottledCaterpillars.add(registerBottledCaterpillar(i));
        }

        // Scrolls
        this.butterflyScrolls = new ArrayList<>();
        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            this.butterflyScrolls.add(registerButterflyScroll(i));
        }

        // Books
        this.butterflyBook = deferredRegister.register(ButterflyBookItem.NAME, ButterflyBookItem::new);
        this.zhuangziBook = deferredRegister.register(ButterflyZhuangziItem.NAME, ButterflyZhuangziItem::new);

        // Blocks
        this.butterflyFeeder = deferredRegister.register("butterfly_feeder",
                () -> new BlockItem(blockRegistry.getButterflyFeeder().get(), new Item.Properties()));

        this.butterflyMicroscope = deferredRegister.register("butterfly_microscope",
                () -> new BlockItem(blockRegistry.getButterflyMicroscope().get(), new Item.Properties()));

        // Infested Apple
        this.infestedApple = deferredRegister.register("infested_apple", () -> new Item(new Item.Properties()));

        // Silk
        this.silk = deferredRegister.register("silk", () -> new Item(new Item.Properties()));

        // Origami
        this.butterflyOrigami = new ArrayList<>();
        for (RegistryObject<Block> block : blockRegistry.getButterflyOrigami()) {
            butterflyOrigami.add(deferredRegister.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties())));
        }

        // Sherd
        this.butterflyPotterySherd = deferredRegister.register("butterfly_pottery_sherd",
                () -> new Item(new Item.Properties()));

        // Banner Pattern
        this.butterflyBannerPattern = deferredRegister.register("banner_pattern_butterfly", () -> new BannerPatternItem(
                tagRegistry.getButterflyBannerPattern(),
                (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON)));

        // Spawn Eggs
        this.eggSpawnEggs = new ArrayList<>();
        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            this.eggSpawnEggs.add(registerButterflyEggSpawnEgg(i));
        }
        this.chrysalisSpawnEggs = new ArrayList<>();
        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            this.chrysalisSpawnEggs.add(registerChrysalisSpawnEgg(i));
        }

        this.caterpillarSpawnEggs = new ArrayList<>();
        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            this.caterpillarSpawnEggs.add(registerCaterpillarSpawnEgg(i));
        }

        this.butterflySpawnEggs = new ArrayList<>();
        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            this.butterflySpawnEggs.add(registerButterflySpawnEgg(i));
        }

        this.butterflyGolemSpawnEgg = deferredRegister.register("spawn_egg_golem_butterfly",
                () -> new ForgeSpawnEggItem(entityTypeRegistry.getButterflyGolem(),
                        0xffffff, 0xffffff, new Item.Properties()));

        this.peacemakerButterflySpawnEgg = deferredRegister.register("spawn_egg_peacemaker_butterfly",
                () -> new ForgeSpawnEggItem(entityTypeRegistry.getPeacemakerButterfly(),
                        0xffffff, 0xffffff, new Item.Properties()));

        this.peacemakerEvokerSpawnEgg = deferredRegister.register("spawn_egg_peacemaker_evoker",
                () -> new ForgeSpawnEggItem(entityTypeRegistry.getPeacemakerEvoker(),
                        0xffffff, 0xffffff, new Item.Properties()));

        this.peacemakerIllusionerSpawnEgg = deferredRegister.register("spawn_egg_peacemaker_illusioner",
                () -> new ForgeSpawnEggItem(entityTypeRegistry.getPeacemakerIllusioner(),
                        0xffffff, 0xffffff, new Item.Properties()));

        this.peacemakerPillagerSpawnEgg = deferredRegister.register("spawn_egg_peacemaker_pillager",
                () -> new ForgeSpawnEggItem(entityTypeRegistry.getPeacemakerPillager(),
                        0xffffff, 0xffffff, new Item.Properties()));

        this.peacemakerVillagerSpawnEgg = deferredRegister.register("spawn_egg_peacemaker_villager",
                () -> new ForgeSpawnEggItem(entityTypeRegistry.getPeacemakerVillager(),
                        0xffffff, 0xffffff, new Item.Properties()));

        this.peacemakerVindicatorSpawnEgg = deferredRegister.register("spawn_egg_peacemaker_vindicator",
                () -> new ForgeSpawnEggItem(entityTypeRegistry.getPeacemakerVindicator(),
                        0xffffff, 0xffffff, new Item.Properties()));

        this.peacemakerWanderingTraderSpawnEgg = deferredRegister.register("spawn_egg_peacemaker_wandering_trader",
                () -> new ForgeSpawnEggItem(entityTypeRegistry.getPeacemakerWanderingTrader(),
                        0xffffff, 0xffffff, new Item.Properties()));

        this.peacemakerWitchSpawnEgg = deferredRegister.register("spawn_egg_peacemaker_witch",
                () -> new ForgeSpawnEggItem(entityTypeRegistry.getPeacemakerWitch(),
                        0xffffff, 0xffffff, new Item.Properties()));
    }

    /**
     * Helper method to get the correct butterfly net item.
     * @param butterflyIndex The butterfly index.
     * @return The registry entry for the related item.
     */
    public RegistryObject<Item> getButterflyNetFromIndex(int butterflyIndex) {
        if (butterflyIndex < 0) {
            return emptyButterflyNet;
        } else if (Objects.equals(ButterflyInfo.SPECIES[butterflyIndex], "lava")) {
            return burntButterflyNet;
        } else {
            return butterflyNets.get(butterflyIndex);
        }
    }

    // Accessor Methods

    public List<RegistryObject<Item>> getBottledButterflies() {
        return bottledButterflies;
    }

    public List<RegistryObject<Item>> getBottledCaterpillars() {
        return bottledCaterpillars;
    }

    public RegistryObject<Item> getButterflyBannerPattern() {
        return butterflyBannerPattern;
    }

    public RegistryObject<Item> getButterflyBook() {
        return butterflyBook;
    }

    public RegistryObject<Item> getButterflyMicroscope() {
        return butterflyMicroscope;
    }

    public RegistryObject<Item> getBurntButterflyNet() {
        return burntButterflyNet;
    }

    public List<RegistryObject<Item>> getButterflyEggs() {
        return butterflyEggs;
    }

    public RegistryObject<Item> getButterflyFeeder() {
        return butterflyFeeder;
    }

    public List<RegistryObject<Item>> getButterflyNets() {
        return butterflyNets;
    }

    public List<RegistryObject<Item>> getButterflyOrigami() {
        return butterflyOrigami;
    }

    public RegistryObject<Item> getButterflyPotterySherd() {
        return butterflyPotterySherd;
    }

    public List<RegistryObject<Item>> getButterflyScrolls() {
        return butterflyScrolls;
    }

    public List<RegistryObject<Item>> getButterflySpawnEggs() {
        return butterflySpawnEggs;
    }

    public RegistryObject<Item> getButterflyGolemSpawnEgg() {
        return butterflyGolemSpawnEgg;
    }

    public List<RegistryObject<Item>> getCaterpillars() {
        return caterpillars;
    }

    public List<RegistryObject<Item>> getEggSpawnEggs() {
        return eggSpawnEggs;
    }

    public List<RegistryObject<Item>> getChrysalisSpawnEggs() {
        return chrysalisSpawnEggs;
    }

    public List<RegistryObject<Item>> getCaterpillarSpawnEggs() {
        return caterpillarSpawnEggs;
    }

    public RegistryObject<Item> getEmptyButterflyNet() {
        return emptyButterflyNet;
    }

    public RegistryObject<Item> getInfestedApple() {
        return infestedApple;
    }

    public RegistryObject<Item> getPeacemakerButterflySpawnEgg() {
        return peacemakerButterflySpawnEgg;
    }

    public RegistryObject<Item> getPeacemakerEvokerSpawnEgg() {
        return peacemakerEvokerSpawnEgg;
    }

    public RegistryObject<Item> getPeacemakerIllusionerSpawnEgg() {
        return peacemakerIllusionerSpawnEgg;
    }

    public RegistryObject<Item> getPeacemakerPillagerSpawnEgg() {
        return peacemakerPillagerSpawnEgg;
    }

    public RegistryObject<Item> getPeacemakerVillagerSpawnEgg() {
        return peacemakerVillagerSpawnEgg;
    }

    public RegistryObject<Item> getPeacemakerVindicatorSpawnEgg() {
        return peacemakerVindicatorSpawnEgg;
    }

    public RegistryObject<Item> getPeacemakerWanderingTraderSpawnEgg() {
        return peacemakerWanderingTraderSpawnEgg;
    }

    public RegistryObject<Item> getPeacemakerWitchSpawnEgg() {
        return peacemakerWitchSpawnEgg;
    }

    public RegistryObject<Item> getSilk() {
        return this.silk;
    }

    public RegistryObject<Item> getZhuangziBook() {
        return zhuangziBook;
    }

    // Register Methods

    private RegistryObject<Item> registerButterflyNet(int butterflyIndex) {
        return deferredRegister.register(ButterflyNetItem.getRegistryId(butterflyIndex),
                () -> new ButterflyNetItem(this, butterflyIndex));
    }

    private RegistryObject<Item> registerBottledButterfly(int butterflyIndex) {
        return deferredRegister.register(BottledButterflyItem.getRegistryId(butterflyIndex),
                () -> new BottledButterflyItem(blockRegistry.getBottledButterflyBlocks().get(butterflyIndex), butterflyIndex));
    }

    private RegistryObject<Item> registerBottledCaterpillar(int butterflyIndex) {
        return deferredRegister.register(BottledCaterpillarItem.getRegistryId(butterflyIndex),
                () -> new BottledCaterpillarItem(blockRegistry.getBottledCaterpillarBlocks().get(butterflyIndex), butterflyIndex));
    }

    private RegistryObject<Item> registerButterflyEgg(int butterflyIndex) {
        return deferredRegister.register(ButterflyEggItem.getRegistryId(butterflyIndex),
                () -> new ButterflyEggItem(butterflyIndex, new Item.Properties()));
    }

    private RegistryObject<Item> registerButterflyScroll(int butterflyIndex) {
        return deferredRegister.register(ButterflyScrollItem.getRegistryId(butterflyIndex),
                () -> new ButterflyScrollItem(entityTypeRegistry, butterflyIndex));
    }

    private RegistryObject<Item> registerCaterpillar(int butterflyIndex) {
        return deferredRegister.register(CaterpillarItem.getRegistryId(butterflyIndex),
                () -> new CaterpillarItem(Caterpillar.getRegistryId(butterflyIndex)));
    }

    private RegistryObject<Item> registerButterflyEggSpawnEgg(int butterflyIndex) {
        return deferredRegister.register("spawn_egg_egg_" + Butterfly.getRegistryId(butterflyIndex),
                () -> new ForgeSpawnEggItem(entityTypeRegistry.getButterflyEggs().get(butterflyIndex),
                        0xffffff, 0xffffff, new Item.Properties()));
    }

    private RegistryObject<Item> registerCaterpillarSpawnEgg(int butterflyIndex) {
        return deferredRegister.register("spawn_egg_" + CaterpillarItem.getRegistryId(butterflyIndex),
                () -> new ForgeSpawnEggItem(entityTypeRegistry.getCaterpillars().get(butterflyIndex),
                        0xffffff, 0xffffff, new Item.Properties()));
    }

    private RegistryObject<Item> registerChrysalisSpawnEgg(int butterflyIndex) {
        return deferredRegister.register("spawn_egg_chrysalis_" + Butterfly.getRegistryId(butterflyIndex),
                () -> new ForgeSpawnEggItem(entityTypeRegistry.getChrysalises().get(butterflyIndex),
                        0xffffff, 0xffffff, new Item.Properties()));
    }

    private RegistryObject<Item> registerButterflySpawnEgg(int butterflyIndex) {
        return deferredRegister.register("spawn_egg_butterfly_" + Butterfly.getRegistryId(butterflyIndex),
                () -> new ForgeSpawnEggItem(entityTypeRegistry.getButterflies().get(butterflyIndex),
                        0xffffff, 0xffffff, new Item.Properties()));
    }
}
