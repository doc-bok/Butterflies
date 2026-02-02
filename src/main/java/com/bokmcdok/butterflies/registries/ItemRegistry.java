package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyInfo;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import com.bokmcdok.butterflies.world.entity.animal.Caterpillar;
import com.bokmcdok.butterflies.world.item.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
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
    private DataComponentRegistry dataComponentRegistry;
    private EntityTypeRegistry entityTypeRegistry;
    private TagRegistry tagRegistry;

    // Nets
    private DeferredHolder<Item, Item> emptyButterflyNet;
    private List<DeferredHolder<Item, Item>> butterflyNets;
    private DeferredHolder<Item, Item> burntButterflyNet;

    // Eggs
    private List<DeferredHolder<Item, Item>> butterflyEggs;

    // Caterpillars
    private List<DeferredHolder<Item, Item>> caterpillars;

    // Bottles
    private List<DeferredHolder<Item, Item>> bottledButterflies;
    private List<DeferredHolder<Item, Item>> bottledCaterpillars;

    // Scrolls
    private List<DeferredHolder<Item, Item>> butterflyScrolls;

    // Books
    private DeferredHolder<Item, Item> butterflyBook;
    private DeferredHolder<Item, Item> zhuangziBook;

    // Blocks
    private DeferredHolder<Item, Item> butterflyFeeder;
    private DeferredHolder<Item, Item> butterflyMicroscope;

    // Infested Apple
    private DeferredHolder<Item, Item> infestedApple;

    // Silk
    private DeferredHolder<Item, Item> silk;

    // Origami
    private List<DeferredHolder<Item, Item>> butterflyOrigami;

    // Sherd
    private DeferredHolder<Item, Item> butterflyPotterySherd;

    // Banner Pattern
    private DeferredHolder<Item, Item> butterflyBannerPattern;

    // Spawn Eggs
    private List<DeferredHolder<Item, Item>> eggSpawnEggs;
    private List<DeferredHolder<Item, Item>> chrysalisSpawnEggs;
    private List<DeferredHolder<Item, Item>> caterpillarSpawnEggs;
    private List<DeferredHolder<Item, Item>> butterflySpawnEggs;
    private DeferredHolder<Item, Item> butterflyGolemSpawnEgg;
    private DeferredHolder<Item, Item> peacemakerButterflySpawnEgg;
    private DeferredHolder<Item, Item> peacemakerEvokerSpawnEgg;
    private DeferredHolder<Item, Item> peacemakerIllusionerSpawnEgg;
    private DeferredHolder<Item, Item> peacemakerPillagerSpawnEgg;
    private DeferredHolder<Item, Item> peacemakerVillagerSpawnEgg;
    private DeferredHolder<Item, Item> peacemakerVindicatorSpawnEgg;
    private DeferredHolder<Item, Item> peacemakerWanderingTraderSpawnEgg;
    private DeferredHolder<Item, Item> peacemakerWitchSpawnEgg;

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public ItemRegistry(@NotNull IEventBus modEventBus) {
        this.deferredRegister = DeferredRegister.create(BuiltInRegistries.ITEM, ButterfliesMod.MOD_ID);
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
                           @NotNull DataComponentRegistry dataComponentRegistry,
                           @NotNull EntityTypeRegistry entityTypeRegistry,
                           @NotNull TagRegistry tagRegistry) {

        this.blockRegistry = Objects.requireNonNull(blockRegistry, "blockRegistry cannot be null");
        this.dataComponentRegistry = Objects.requireNonNull(dataComponentRegistry, "dataComponentRegistry cannot be null");
        this.entityTypeRegistry = Objects.requireNonNull(entityTypeRegistry, "entityTypeRegistry cannot be null");
        this.tagRegistry = Objects.requireNonNull(tagRegistry, "tagRegistry cannot be null");

        // Nets
        this.emptyButterflyNet = registerButterflyNet(-1, ButterflyNetItem.EMPTY_NAME);

        this.butterflyNets = new ArrayList<>();
        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            this.butterflyNets.add(registerButterflyNet(i));
        }

        this.burntButterflyNet = registerItem("butterfly_net_burnt");

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
        this.butterflyBook = registerButterflyBook();
        this.zhuangziBook = registerZhuangZiBook();


        // Blocks
        this.butterflyFeeder = registerButterflyFeeder();
        this.butterflyMicroscope = registerButterflyMicroscope();

        // Infested Apple
        this.infestedApple = registerItem("infested_apple");

        // Silk
        this.silk = registerItem("silk");

        // Origami
        this.butterflyOrigami = new ArrayList<>();
        for (DeferredHolder<Block, Block> block : blockRegistry.getButterflyOrigami()) {
            butterflyOrigami.add(registerButterflyOrigami(block.getId().getPath(), block));
        }

        // Sherd
        this.butterflyPotterySherd = registerItem("butterfly_pottery_sherd");

        // Banner Pattern
        this.butterflyBannerPattern = registerBannerPattern(bannerPatternRegistry);

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

        this.butterflyGolemSpawnEgg = registerSpawnEgg("spawn_egg_golem_butterfly", entityTypeRegistry.getButterflyGolem());

        this.peacemakerButterflySpawnEgg = registerSpawnEgg("spawn_egg_peacemaker_butterfly", entityTypeRegistry.getPeacemakerButterfly());
        this.peacemakerEvokerSpawnEgg = registerSpawnEgg("spawn_egg_peacemaker_evoker", entityTypeRegistry.getPeacemakerEvoker());
        this.peacemakerIllusionerSpawnEgg = registerSpawnEgg("spawn_egg_peacemaker_illusioner", entityTypeRegistry.getPeacemakerIllusioner());
        this.peacemakerPillagerSpawnEgg = registerSpawnEgg("spawn_egg_peacemaker_pillager", entityTypeRegistry.getPeacemakerPillager());
        this.peacemakerVillagerSpawnEgg = registerSpawnEgg("spawn_egg_peacemaker_villager", entityTypeRegistry.getPeacemakerVillager());
        this.peacemakerVindicatorSpawnEgg = registerSpawnEgg("spawn_egg_peacemaker_vindicator", entityTypeRegistry.getPeacemakerVindicator());
        this.peacemakerWanderingTraderSpawnEgg = registerSpawnEgg("spawn_egg_peacemaker_wandering_trader", entityTypeRegistry.getPeacemakerWanderingTrader());
        this.peacemakerWitchSpawnEgg = registerSpawnEgg("spawn_egg_peacemaker_witch", entityTypeRegistry.getPeacemakerWitch());
    }
    
    /**
     * Accessor for bottled butterflies.
     * @return The registry objects.
     */
    public List<DeferredHolder<Item, Item>> getBottledButterflies() {
        return bottledButterflies;
    }

    /**
     * Accessor for bottled caterpillars.
     * @return The registry objects.
     */
    public List<DeferredHolder<Item, Item>> getBottledCaterpillars() {
        return bottledCaterpillars;
    }

    /**
     * Accessor for butterfly banner pattern.
     * @return The registry object.
     */
    public DeferredHolder<Item, Item> getButterflyBannerPattern() {
        return butterflyBannerPattern;
    }

    /**
     * Accessor for butterfly book.
     * @return The registry object.
     */
    public DeferredHolder<Item, Item> getButterflyBook() {
        return butterflyBook;
    }

    /**
     * Accessor for butterfly microscope.
     * @return The registry object.
     */
    public DeferredHolder<Item, Item> getButterflyMicroscope() {
        return butterflyMicroscope;
    }

    /**
     * Accessor for burnt butterfly net.
     * @return The registry object.
     */
    public DeferredHolder<Item, Item> getBurntButterflyNet() {
        return burntButterflyNet;
    }

    /**
     * Accessor for butterfly eggs.
     * @return The registry objects.
     */
    public List<DeferredHolder<Item, Item>> getButterflyEggs() {
        return butterflyEggs;
    }

    /**
     * Helper method to get the correct butterfly net item.
     * @param butterflyIndex The butterfly index.
     * @return The registry entry for the related item.
     */
    public DeferredHolder<Item, Item> getButterflyNetFromIndex(int butterflyIndex) {
        if (butterflyIndex < 0) {
            return emptyButterflyNet;
        } else if (Objects.equals(ButterflyInfo.SPECIES[butterflyIndex], "lava")) {
            return burntButterflyNet;
        } else {
            return butterflyNets.get(butterflyIndex);
        }
    }

    // Accessor Methods
    public DeferredHolder<Item, Item> getButterflyFeeder() {
        return butterflyFeeder;
    }
    
    public List<DeferredHolder<Item, Item>> getButterflyNets() {
        return butterflyNets;
    }

    public List<DeferredHolder<Item, Item>> getButterflyOrigami() {
        return butterflyOrigami;
    }

    public DeferredHolder<Item, Item> getButterflyPotterySherd() {
        return butterflyPotterySherd;
    }

    public List<DeferredHolder<Item, Item>> getButterflyScrolls() {
        return butterflyScrolls;
    }

    public List<DeferredHolder<Item, Item>> getButterflySpawnEggs() {
        return butterflySpawnEggs;
    }

    public DeferredHolder<Item, Item> getButterflyGolemSpawnEgg() {
        return butterflyGolemSpawnEgg;
    }

    public List<DeferredHolder<Item, Item>> getCaterpillars() {
        return caterpillars;
    }

    public List<DeferredHolder<Item, Item>> getEggSpawnEggs() {
        return eggSpawnEggs;
    }

    public List<DeferredHolder<Item, Item>> getChrysalisSpawnEggs() {
        return chrysalisSpawnEggs;
    }

    public List<DeferredHolder<Item, Item>> getCaterpillarSpawnEggs() {
        return caterpillarSpawnEggs;
    }

    public DeferredHolder<Item, Item> getEmptyButterflyNet() {
        return emptyButterflyNet;
    }

    public DeferredHolder<Item, Item> getInfestedApple() {
        return infestedApple;
    }

    public DeferredHolder<Item, Item> getPeacemakerButterflySpawnEgg() {
        return peacemakerButterflySpawnEgg;
    }

    public DeferredHolder<Item, Item> getPeacemakerEvokerSpawnEgg() {
        return peacemakerEvokerSpawnEgg;
    }

    public DeferredHolder<Item, Item> getPeacemakerIllusionerSpawnEgg() {
        return peacemakerIllusionerSpawnEgg;
    }

    public DeferredHolder<Item, Item> getPeacemakerPillagerSpawnEgg() {
        return peacemakerPillagerSpawnEgg;
    }

    public DeferredHolder<Item, Item> getPeacemakerVillagerSpawnEgg() {
        return peacemakerVillagerSpawnEgg;
    }

    public DeferredHolder<Item, Item> getPeacemakerVindicatorSpawnEgg() {
        return peacemakerVindicatorSpawnEgg;
    }

    public DeferredHolder<Item, Item> getPeacemakerWanderingTraderSpawnEgg() {
        return peacemakerWanderingTraderSpawnEgg;
    }

    public DeferredHolder<Item, Item> getPeacemakerWitchSpawnEgg() {
        return peacemakerWitchSpawnEgg;
    }

    public DeferredHolder<Item, Item> getSilk() {
        return this.silk;
    }

    public DeferredHolder<Item, Item> getZhuangziBook() {
        return zhuangziBook;
    }

    private ResourceKey<Item> createResourceKey(String registryId) {
        ResourceLocation location = ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, registryId);
        return ResourceKey.create(Registries.ITEM, location);
    }

    // Register Methods

    private DeferredHolder<Item, Item> registerBannerPattern(BannerPatternRegistry bannerPatternRegistry) {

        final String registryId = "banner_pattern_butterfly";
        ResourceKey<Item> key = createResourceKey(registryId);

        return deferredRegister.register(registryId, () -> new BannerPatternItem(
                tagRegistry.getButterflyBannerPattern(),
                new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).setId(key)));
    }

    private DeferredHolder<Item, Item> registerButterflyBook() {
        ResourceKey<Item> key = createResourceKey(ButterflyBookItem.NAME);
        return deferredRegister.register(ButterflyBookItem.NAME,
                () -> new ButterflyBookItem(
                        new Item.Properties().stacksTo(1).setId(key),
                        dataComponentRegistry));
    }

    private DeferredHolder<Item, Item> registerButterflyFeeder() {

        final String registryId = "butterfly_feeder";
        ResourceKey<Item> key = createResourceKey(registryId);

        return deferredRegister.register(registryId, () -> new BlockItem(
                blockRegistry.getButterflyFeeder().get(),
                new Item.Properties().setId(key)));

    }

    private DeferredHolder<Item, Item> registerButterflyMicroscope() {

        final String registryId = "butterfly_microscope";
        ResourceKey<Item> key = createResourceKey(registryId);

        return deferredRegister.register(registryId, () -> new BlockItem(
                blockRegistry.getButterflyMicroscope().get(),
                new Item.Properties().setId(key)));

    }

    private DeferredHolder<Item, Item> registerButterflyNet(int butterflyIndex) {
        return registerButterflyNet(butterflyIndex, ButterflyNetItem.getRegistryId(butterflyIndex));
    }

    /**
     * Register a butterfly net for the given butterfly index.
     * @param butterflyIndex The index of the butterfly.
     * @param registryId The registry ID of the item.
     * @return A new registry object.
     */
    private DeferredHolder<Item, Item> registerButterflyNet(int butterflyIndex,
                                                            String registryId) {
        ResourceKey<Item> key = createResourceKey(registryId);
        return deferredRegister.register(registryId,
                () -> new ButterflyNetItem(
                        new Item.Properties().stacksTo(1).setId(key),
                        dataComponentRegistry,
                        this,
                        butterflyIndex));
    }

    private DeferredHolder<Item, Item> registerBottledButterfly(int butterflyIndex) {
        String registryId = BottledButterflyItem.getRegistryId(butterflyIndex);
        ResourceKey<Item> key = createResourceKey(registryId);

        return deferredRegister.register(registryId,
                () -> new BottledButterflyItem(
                        new Item.Properties().stacksTo(1).setId(key),
                        dataComponentRegistry,
                        blockRegistry.getBottledButterflyBlocks().get(butterflyIndex),
                        butterflyIndex));
    }

    private DeferredHolder<Item, Item> registerBottledCaterpillar(int butterflyIndex) {

        String registryId = BottledCaterpillarItem.getRegistryId(butterflyIndex);
        ResourceKey<Item> key = createResourceKey(registryId);

        return deferredRegister.register(registryId,
                () -> new BottledCaterpillarItem(
                        new Item.Properties().stacksTo(1).setId(key),
                        blockRegistry.getBottledCaterpillarBlocks().get(butterflyIndex),
                        butterflyIndex));
    }

    private DeferredHolder<Item, Item> registerButterflyEgg(int butterflyIndex) {

        String registryId = ButterflyEggItem.getRegistryId(butterflyIndex);
        ResourceKey<Item> key = createResourceKey(registryId);

        return deferredRegister.register(registryId,
                () -> new ButterflyEggItem(butterflyIndex, new Item.Properties().setId(key)));
    }

    /**
     * Registers an origami block item.
     * @param registryId The Registry ID of the item.
     * @param block The block this item is linked to.
     * @return A new registry object.
     */
    private DeferredHolder<Item, Item> registerButterflyOrigami(String registryId,
                                                                DeferredHolder<Block, Block> block) {
        ResourceKey<Item> key = createResourceKey(registryId);
        return deferredRegister.register(registryId,
                () -> new BlockItem(block.get(), new Item.Properties().setId(key)));
    }

    private DeferredHolder<Item, Item> registerButterflyScroll(int butterflyIndex) {
        String registryId = ButterflyScrollItem.getRegistryId(butterflyIndex);
        ResourceKey<Item> key = createResourceKey(registryId);

        return deferredRegister.register(registryId,
                () -> new ButterflyScrollItem(
                        new Item.Properties().setId(key),
                        dataComponentRegistry,
                        entityTypeRegistry,
                        butterflyIndex));
    }

    private DeferredHolder<Item, Item> registerCaterpillar(int butterflyIndex) {

        String registryId = CaterpillarItem.getRegistryId(butterflyIndex);
        ResourceKey<Item> key = createResourceKey(registryId);

        return deferredRegister.register(registryId,
                () -> new CaterpillarItem(new Item.Properties().setId(key), Caterpillar.getRegistryId(butterflyIndex)));
    }

    /**
     * Register a basic item.
     * @param registryId The Registry ID of the item.
     * @return A new registry object.
     */
    private DeferredHolder<Item, Item> registerItem(String registryId) {
        return registerItem(registryId, new Item.Properties());
    }

    /**
     * Register a basic item.
     * @param registryId The Registry ID of the item.
     * @param properties The base properties of the item.
     * @return A new registry object.
     */
    private DeferredHolder<Item, Item> registerItem(String registryId,
                                                    Item.Properties properties) {

        ResourceKey<Item> key = createResourceKey(registryId);
        return deferredRegister.register(registryId, () -> new Item(properties.setId(key)));
    }

    /**
     * Register a butterfly spawn egg.
     * @param registryId The Registry ID of the item.
     * @return A new registry object.
     */
    private DeferredHolder<Item, Item> registerSpawnEgg(String registryId,
                                                        DeferredHolder<EntityType<?>, EntityType<? extends Mob>> entityType) {

        ResourceKey<Item> key = createResourceKey(registryId);
        return deferredRegister.register(registryId,
                () -> new SpawnEggItem(entityType.get(), new Item.Properties().setId(key)));
    }

    /**
     * Register the secret book.
     * @return A new registry object.
     */
    private DeferredHolder<Item, Item> registerZhuangZiBook() {
        String registryId = ButterflyZhuangZiItem.NAME;
        ResourceKey<Item> key = createResourceKey(registryId);
        return deferredRegister.register(registryId,
                () -> new ButterflyZhuangZiItem(new Item.Properties().stacksTo(1).setId(key)));
    }

    private DeferredHolder<Item, Item> registerButterflyEggSpawnEgg(int butterflyIndex) {
        String registryId = "spawn_egg_egg_" + Butterfly.getRegistryId(butterflyIndex);
        return registerSpawnEgg(registryId, entityTypeRegistry.getButterflyEggs().get(butterflyIndex));
    }

    private DeferredHolder<Item, Item> registerCaterpillarSpawnEgg(int butterflyIndex) {
        String registryId = "spawn_egg_caterpillar_" + Butterfly.getRegistryId(butterflyIndex);
        return registerSpawnEgg(registryId, entityTypeRegistry.getCaterpillars().get(butterflyIndex));
    }

    private DeferredHolder<Item, Item> registerChrysalisSpawnEgg(int butterflyIndex) {
        String registryId = "spawn_egg_chrysalis_" + Butterfly.getRegistryId(butterflyIndex);
        return registerSpawnEgg(registryId, entityTypeRegistry.getChrysalises().get(butterflyIndex));
    }

    private DeferredHolder<Item, Item> registerButterflySpawnEgg(int butterflyIndex) {
        String registryId = "spawn_egg_butterfly_" + Butterfly.getRegistryId(butterflyIndex);
        return registerSpawnEgg(registryId, entityTypeRegistry.getButterflies().get(butterflyIndex));
    }
}
