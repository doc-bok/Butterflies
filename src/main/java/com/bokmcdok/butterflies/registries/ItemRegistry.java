package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyInfo;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import com.bokmcdok.butterflies.world.entity.animal.Caterpillar;
import com.bokmcdok.butterflies.world.item.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
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
                           @NotNull EntityTypeRegistry entityTypeRegistry) {

        this.blockRegistry = Objects.requireNonNull(blockRegistry, "blockRegistry cannot be null");
        this.dataComponentRegistry = Objects.requireNonNull(dataComponentRegistry, "dataComponentRegistry cannot be null");
        this.entityTypeRegistry = Objects.requireNonNull(entityTypeRegistry, "entityTypeRegistry cannot be null");

        // Nets
        this.emptyButterflyNet = deferredRegister.register(
                ButterflyNetItem.EMPTY_NAME,
                () -> new ButterflyNetItem(this.dataComponentRegistry, this, -1));

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
        this.butterflyBook = deferredRegister.register(ButterflyBookItem.NAME, () -> new ButterflyBookItem(this.dataComponentRegistry));
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
        for (DeferredHolder<Block, Block> block : blockRegistry.getButterflyOrigami()) {
            butterflyOrigami.add(deferredRegister.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties())));
        }

        // Sherd
        this.butterflyPotterySherd = deferredRegister.register("butterfly_pottery_sherd",
                () -> new Item(new Item.Properties()));

        // Banner Pattern
        this.butterflyBannerPattern = deferredRegister.register("banner_pattern_butterfly", () -> new BannerPatternItem(
                bannerPatternRegistry.getButterflyBannerPatternTagKey(),
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
                () -> new DeferredSpawnEggItem(entityTypeRegistry.getButterflyGolem(),
                        0xffffff, 0xffffff, new Item.Properties()));
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

    /**
     * Accessor for the butterfly feeder.
     * @return The registry object.
     */
    public DeferredHolder<Item, Item> getButterflyFeeder() {
        return butterflyFeeder;
    }

    /**
     * Accessor for butterfly nets.
     * @return The registry objects.
     */
    public List<DeferredHolder<Item, Item>> getButterflyNets() {
        return butterflyNets;
    }

    /**
     * Returns a list of all butterfly origami.
     * @return The list of origami.
     */
    public List<DeferredHolder<Item, Item>> getButterflyOrigami() {
        return butterflyOrigami;
    }

    /**
     * Accessor for butterfly pottery sherd.
     * @return The butterfly pottery sherd.
     */
    public DeferredHolder<Item, Item> getButterflyPotterySherd() {
        return butterflyPotterySherd;
    }

    /**
     * Accessor for butterfly scrolls.
     * @return The registry objects.
     */
    public List<DeferredHolder<Item, Item>> getButterflyScrolls() {
        return butterflyScrolls;
    }

    /**
     * Accessor for butterfly spawn eggs.
     * @return The registry objects.
     */
    public List<DeferredHolder<Item, Item>> getButterflySpawnEggs() {
        return butterflySpawnEggs;
    }

    /**
     * Accessor for butterfly golem spawn eggs.
     * @return The registry object.
     */
    public DeferredHolder<Item, Item> getButterflyGolemSpawnEgg() {
        return butterflyGolemSpawnEgg;
    }

    /**
     * Accessor for caterpillars.
     * @return The registry objects.
     */
    public List<DeferredHolder<Item, Item>> getCaterpillars() {
        return caterpillars;
    }

    /**
     * Accessor for egg spawn eggs.
     * @return The registry objects.
     */
    public List<DeferredHolder<Item, Item>> getEggSpawnEggs() {
        return eggSpawnEggs;
    }

    /**
     * Accessor for egg spawn eggs.
     * @return The registry objects.
     */
    public List<DeferredHolder<Item, Item>> getChrysalisSpawnEggs() {
        return chrysalisSpawnEggs;
    }

    /**
     * Accessor for caterpillar spawn eggs.
     * @return The registry objects.
     */
    public List<DeferredHolder<Item, Item>> getCaterpillarSpawnEggs() {
        return caterpillarSpawnEggs;
    }

    /**
     * Accessor for empty butterfly net.
     * @return The registry object.
     */
    public DeferredHolder<Item, Item> getEmptyButterflyNet() {
        return emptyButterflyNet;
    }

    /**
     * Accessor for infested apple.
     * @return The registry object.
     */
    public DeferredHolder<Item, Item> getInfestedApple() {
        return infestedApple;
    }

    /**
     * Accessor for silk.
     * @return The registry object.
     */
    public DeferredHolder<Item, Item> getSilk() {
        return this.silk;
    }

    /**
     * Accessor for secret book.
     * @return The registry object.
     */
    public DeferredHolder<Item, Item> getZhuangziBook() {
        return zhuangziBook;
    }

    /**
     * Register a butterfly net for the given butterfly index.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private DeferredHolder<Item, Item> registerButterflyNet(int butterflyIndex) {
        return deferredRegister.register(ButterflyNetItem.getRegistryId(butterflyIndex),
                () -> new ButterflyNetItem(this.dataComponentRegistry, this, butterflyIndex));
    }

    /**
     * Register a bottled butterfly for the given butterfly index.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private DeferredHolder<Item, Item> registerBottledButterfly(int butterflyIndex) {
        return deferredRegister.register(BottledButterflyItem.getRegistryId(butterflyIndex),
                () -> new BottledButterflyItem(this.dataComponentRegistry, blockRegistry.getBottledButterflyBlocks().get(butterflyIndex), butterflyIndex));
    }

    /**
     * Register a bottled caterpillar for the given butterfly index.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private DeferredHolder<Item, Item> registerBottledCaterpillar(int butterflyIndex) {
        return deferredRegister.register(BottledCaterpillarItem.getRegistryId(butterflyIndex),
                () -> new BottledCaterpillarItem(blockRegistry.getBottledCaterpillarBlocks().get(butterflyIndex), butterflyIndex));
    }

    /**
     * Register a butterfly egg for the given butterfly index.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private DeferredHolder<Item, Item> registerButterflyEgg(int butterflyIndex) {
        return deferredRegister.register(ButterflyEggItem.getRegistryId(butterflyIndex),
                () -> new ButterflyEggItem(butterflyIndex, new Item.Properties()));
    }

    /**
     * Register a butterfly scroll for the given butterfly index.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private DeferredHolder<Item, Item> registerButterflyScroll(int butterflyIndex) {
        return deferredRegister.register(ButterflyScrollItem.getRegistryId(butterflyIndex),
                () -> new ButterflyScrollItem(this.dataComponentRegistry, entityTypeRegistry, butterflyIndex));
    }

    /**
     * Register a caterpillar for the given butterfly index.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private DeferredHolder<Item, Item> registerCaterpillar(int butterflyIndex) {
        return deferredRegister.register(CaterpillarItem.getRegistryId(butterflyIndex),
                () -> new CaterpillarItem(Caterpillar.getRegistryId(butterflyIndex)));
    }

    /**
     * Register an egg spawn egg for the given butterfly index.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private DeferredHolder<Item, Item> registerButterflyEggSpawnEgg(int butterflyIndex) {
        return deferredRegister.register("spawn_egg_egg_" + Butterfly.getRegistryId(butterflyIndex),
                () -> new DeferredSpawnEggItem(entityTypeRegistry.getButterflyEggs().get(butterflyIndex),
                        0xffffff, 0xffffff, new Item.Properties()));
    }

    /**
     * Register a caterpillar spawn egg for the given butterfly index.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private DeferredHolder<Item, Item> registerCaterpillarSpawnEgg(int butterflyIndex) {
        return deferredRegister.register("spawn_egg_" + CaterpillarItem.getRegistryId(butterflyIndex),
                () -> new DeferredSpawnEggItem(entityTypeRegistry.getCaterpillars().get(butterflyIndex),
                        0xffffff, 0xffffff, new Item.Properties()));
    }

    /**
     * Register a chrysalis spawn egg for the given butterfly index.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private DeferredHolder<Item, Item> registerChrysalisSpawnEgg(int butterflyIndex) {
        return deferredRegister.register("spawn_egg_chrysalis_" + Butterfly.getRegistryId(butterflyIndex),
                () -> new DeferredSpawnEggItem(entityTypeRegistry.getChrysalises().get(butterflyIndex),
                        0xffffff, 0xffffff, new Item.Properties()));
    }

    /**
     * Register a butterfly spawn egg for the given butterfly index.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private DeferredHolder<Item, Item> registerButterflySpawnEgg(int butterflyIndex) {
        return deferredRegister.register("spawn_egg_butterfly_" + Butterfly.getRegistryId(butterflyIndex),
                () -> new DeferredSpawnEggItem(entityTypeRegistry.getButterflies().get(butterflyIndex),
                        0xffffff, 0xffffff, new Item.Properties()));
    }
}
