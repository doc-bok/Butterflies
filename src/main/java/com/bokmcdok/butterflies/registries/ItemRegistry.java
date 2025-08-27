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
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
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
                           @NotNull EntityTypeRegistry entityTypeRegistry) {

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
                () -> new ForgeSpawnEggItem(entityTypeRegistry.getButterflyGolem(),
                        0xffffff, 0xffffff, new Item.Properties()));
    }

    /**
     * Accessor for bottled butterflies.
     * @return The registry objects.
     */
    public List<RegistryObject<Item>> getBottledButterflies() {
        return bottledButterflies;
    }

    /**
     * Accessor for bottled caterpillars.
     * @return The registry objects.
     */
    public List<RegistryObject<Item>> getBottledCaterpillars() {
        return bottledCaterpillars;
    }

    /**
     * Accessor for butterfly banner pattern.
     * @return The registry object.
     */
    public RegistryObject<Item> getButterflyBannerPattern() {
        return butterflyBannerPattern;
    }

    /**
     * Accessor for butterfly book.
     * @return The registry object.
     */
    public RegistryObject<Item> getButterflyBook() {
        return butterflyBook;
    }

    /**
     * Accessor for butterfly microscope.
     * @return The registry object.
     */
    public RegistryObject<Item> getButterflyMicroscope() {
        return butterflyMicroscope;
    }

    /**
     * Accessor for burnt butterfly net.
     * @return The registry object.
     */
    public RegistryObject<Item> getBurntButterflyNet() {
        return burntButterflyNet;
    }

    /**
     * Accessor for butterfly eggs.
     * @return The registry objects.
     */
    public List<RegistryObject<Item>> getButterflyEggs() {
        return butterflyEggs;
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

    /**
     * Accessor for the butterfly feeder.
     * @return The registry object.
     */
    public RegistryObject<Item> getButterflyFeeder() {
        return butterflyFeeder;
    }

    /**
     * Accessor for butterfly nets.
     * @return The registry objects.
     */
    public List<RegistryObject<Item>> getButterflyNets() {
        return butterflyNets;
    }

    /**
     * Returns a list of all butterfly origami.
     * @return The list of origami.
     */
    public List<RegistryObject<Item>> getButterflyOrigami() {
        return butterflyOrigami;
    }

    /**
     * Accessor for butterfly pottery sherd.
     * @return The butterfly pottery sherd.
     */
    public RegistryObject<Item> getButterflyPotterySherd() {
        return butterflyPotterySherd;
    }

    /**
     * Accessor for butterfly scrolls.
     * @return The registry objects.
     */
    public List<RegistryObject<Item>> getButterflyScrolls() {
        return butterflyScrolls;
    }

    /**
     * Accessor for butterfly spawn eggs.
     * @return The registry objects.
     */
    public List<RegistryObject<Item>> getButterflySpawnEggs() {
        return butterflySpawnEggs;
    }

    /**
     * Accessor for butterfly golem spawn eggs.
     * @return The registry object.
     */
    public RegistryObject<Item> getButterflyGolemSpawnEgg() {
        return butterflyGolemSpawnEgg;
    }

    /**
     * Accessor for caterpillars.
     * @return The registry objects.
     */
    public List<RegistryObject<Item>> getCaterpillars() {
        return caterpillars;
    }

    /**
     * Accessor for egg spawn eggs.
     * @return The registry objects.
     */
    public List<RegistryObject<Item>> getEggSpawnEggs() {
        return eggSpawnEggs;
    }

    /**
     * Accessor for egg spawn eggs.
     * @return The registry objects.
     */
    public List<RegistryObject<Item>> getChrysalisSpawnEggs() {
        return chrysalisSpawnEggs;
    }

    /**
     * Accessor for caterpillar spawn eggs.
     * @return The registry objects.
     */
    public List<RegistryObject<Item>> getCaterpillarSpawnEggs() {
        return caterpillarSpawnEggs;
    }

    /**
     * Accessor for empty butterfly net.
     * @return The registry object.
     */
    public RegistryObject<Item> getEmptyButterflyNet() {
        return emptyButterflyNet;
    }

    /**
     * Accessor for infested apple.
     * @return The registry object.
     */
    public RegistryObject<Item> getInfestedApple() {
        return infestedApple;
    }

    /**
     * Accessor for silk.
     * @return The registry object.
     */
    public RegistryObject<Item> getSilk() {
        return this.silk;
    }

    /**
     * Accessor for secret book.
     * @return The registry object.
     */
    public RegistryObject<Item> getZhuangziBook() {
        return zhuangziBook;
    }

    /**
     * Register a butterfly net for the given butterfly index.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private RegistryObject<Item> registerButterflyNet(int butterflyIndex) {
        return deferredRegister.register(ButterflyNetItem.getRegistryId(butterflyIndex),
                () -> new ButterflyNetItem(this, butterflyIndex));
    }

    /**
     * Register a bottled butterfly for the given butterfly index.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private RegistryObject<Item> registerBottledButterfly(int butterflyIndex) {
        return deferredRegister.register(BottledButterflyItem.getRegistryId(butterflyIndex),
                () -> new BottledButterflyItem(blockRegistry.getBottledButterflyBlocks().get(butterflyIndex), butterflyIndex));
    }

    /**
     * Register a bottled caterpillar for the given butterfly index.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private RegistryObject<Item> registerBottledCaterpillar(int butterflyIndex) {
        return deferredRegister.register(BottledCaterpillarItem.getRegistryId(butterflyIndex),
                () -> new BottledCaterpillarItem(blockRegistry.getBottledCaterpillarBlocks().get(butterflyIndex), butterflyIndex));
    }

    /**
     * Register a butterfly egg for the given butterfly index.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private RegistryObject<Item> registerButterflyEgg(int butterflyIndex) {
        return deferredRegister.register(ButterflyEggItem.getRegistryId(butterflyIndex),
                () -> new ButterflyEggItem(butterflyIndex, new Item.Properties()));
    }

    /**
     * Register a butterfly scroll for the given butterfly index.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private RegistryObject<Item> registerButterflyScroll(int butterflyIndex) {
        return deferredRegister.register(ButterflyScrollItem.getRegistryId(butterflyIndex),
                () -> new ButterflyScrollItem(entityTypeRegistry, butterflyIndex));
    }

    /**
     * Register a caterpillar for the given butterfly index.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private RegistryObject<Item> registerCaterpillar(int butterflyIndex) {
        return deferredRegister.register(CaterpillarItem.getRegistryId(butterflyIndex),
                () -> new CaterpillarItem(Caterpillar.getRegistryId(butterflyIndex)));
    }

    /**
     * Register an egg spawn egg for the given butterfly index.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private RegistryObject<Item> registerButterflyEggSpawnEgg(int butterflyIndex) {
        return deferredRegister.register("spawn_egg_egg_" + Butterfly.getRegistryId(butterflyIndex),
                () -> new ForgeSpawnEggItem(entityTypeRegistry.getButterflyEggs().get(butterflyIndex),
                        0xffffff, 0xffffff, new Item.Properties()));
    }

    /**
     * Register a caterpillar spawn egg for the given butterfly index.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private RegistryObject<Item> registerCaterpillarSpawnEgg(int butterflyIndex) {
        return deferredRegister.register("spawn_egg_" + CaterpillarItem.getRegistryId(butterflyIndex),
                () -> new ForgeSpawnEggItem(entityTypeRegistry.getCaterpillars().get(butterflyIndex),
                        0xffffff, 0xffffff, new Item.Properties()));
    }

    /**
     * Register a chrysalis spawn egg for the given butterfly index.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private RegistryObject<Item> registerChrysalisSpawnEgg(int butterflyIndex) {
        return deferredRegister.register("spawn_egg_chrysalis_" + Butterfly.getRegistryId(butterflyIndex),
                () -> new ForgeSpawnEggItem(entityTypeRegistry.getChrysalises().get(butterflyIndex),
                        0xffffff, 0xffffff, new Item.Properties()));
    }

    /**
     * Register a butterfly spawn egg for the given butterfly index.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private RegistryObject<Item> registerButterflySpawnEgg(int butterflyIndex) {
        return deferredRegister.register("spawn_egg_butterfly_" + Butterfly.getRegistryId(butterflyIndex),
                () -> new ForgeSpawnEggItem(entityTypeRegistry.getButterflies().get(butterflyIndex),
                        0xffffff, 0xffffff, new Item.Properties()));
    }
}
