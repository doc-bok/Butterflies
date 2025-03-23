package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflySpeciesList;
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

    // Registry Items
    private List<DeferredHolder<Item, Item>> bottledButterflies;
    private List<DeferredHolder<Item, Item>> bottledCaterpillars;
    private DeferredHolder<Item, Item> burntButterflyNet;
    private DeferredHolder<Item, Item> butterflyBannerPattern;
    private DeferredHolder<Item, Item> butterflyBook;
    private List<DeferredHolder<Item, Item>> butterflyEggs;
    private DeferredHolder<Item, Item> butterflyFeeder;
    private DeferredHolder<Item, Item> butterflyMicroscope;
    private List<DeferredHolder<Item, Item>> butterflyNets;
    private DeferredHolder<Item, Item> butterflyPotterySherd;
    private List<DeferredHolder<Item, Item>> butterflyScrolls;
    private DeferredHolder<Item, Item> butterflyGolemSpawnEgg;
    private List<DeferredHolder<Item, Item>> butterflySpawnEggs;
    private List<DeferredHolder<Item, Item>> caterpillars;
    private List<DeferredHolder<Item, Item>> caterpillarSpawnEggs;
    private DeferredHolder<Item, Item> emptyButterflyNet;
    private DeferredHolder<Item, Item> infestedApple;
    private DeferredHolder<Item, Item> silk;
    private DeferredHolder<Item, Item> zhuangziBook;

    // Origami
    private DeferredHolder<Item, Item> butterflyOrigamiBlack;
    private DeferredHolder<Item, Item> butterflyOrigamiBlue;
    private DeferredHolder<Item, Item> butterflyOrigamiBrown;
    private DeferredHolder<Item, Item> butterflyOrigamiCyan;
    private DeferredHolder<Item, Item> butterflyOrigamiGray;
    private DeferredHolder<Item, Item> butterflyOrigamiGreen;
    private DeferredHolder<Item, Item> butterflyOrigamiLightBlue;
    private DeferredHolder<Item, Item> butterflyOrigamiLightGray;
    private DeferredHolder<Item, Item> butterflyOrigamiLime;
    private DeferredHolder<Item, Item> butterflyOrigamiMagenta;
    private DeferredHolder<Item, Item> butterflyOrigamiOrange;
    private DeferredHolder<Item, Item> butterflyOrigamiPink;
    private DeferredHolder<Item, Item> butterflyOrigamiPurple;
    private DeferredHolder<Item, Item> butterflyOrigamiRed;
    private DeferredHolder<Item, Item> butterflyOrigamiWhite;
    private DeferredHolder<Item, Item> butterflyOrigamiYellow;

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public ItemRegistry(IEventBus modEventBus) {
        this.deferredRegister = DeferredRegister.create(BuiltInRegistries.ITEM, ButterfliesMod.MOD_ID);
        this.deferredRegister.register(modEventBus);
    }

    /**
     * Register the items.
     * @param blockRegistry The block registry.
     * @param entityTypeRegistry The entity type registry.
     */
    public void initialise(BannerPatternRegistry bannerPatternRegistry,
                           BlockRegistry blockRegistry,
                           DataComponentRegistry dataComponentRegistry,
                           EntityTypeRegistry entityTypeRegistry) {

        this.blockRegistry = blockRegistry;
        this.dataComponentRegistry = dataComponentRegistry;
        this.entityTypeRegistry = entityTypeRegistry;

        this.bottledButterflies = new ArrayList<>() {
            {
                for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                    add(registerBottledButterfly(i));
                }
            }
        };

        this.bottledCaterpillars = new ArrayList<>() {
            {
                for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                    add(registerBottledCaterpillar(i));
                }
            }
        };

        this.burntButterflyNet = registerItem("butterfly_net_burnt");
        this.butterflyBannerPattern = registerBannerPattern(bannerPatternRegistry);
        this.butterflyBook = registerButterflyBook();

        this.butterflyEggs = new ArrayList<>() {
            {
                for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                    add(registerButterflyEgg(i));
                }
            }
        };

        this.butterflyFeeder = registerButterflyFeeder();
        this.butterflyMicroscope = registerButterflyMicroscope();

        this.butterflyNets = new ArrayList<>() {
            {
                for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                    add(registerButterflyNet(i));
                }
            }
        };

        this.butterflyOrigamiBlack = registerButterflyOrigami("butterfly_origami_black", blockRegistry.getButterflyOrigamiBlack());
        this.butterflyOrigamiBlue = registerButterflyOrigami("butterfly_origami_blue", blockRegistry.getButterflyOrigamiBlue());
        this.butterflyOrigamiBrown = registerButterflyOrigami("butterfly_origami_brown", blockRegistry.getButterflyOrigamiBrown());
        this.butterflyOrigamiCyan = registerButterflyOrigami("butterfly_origami_cyan", blockRegistry.getButterflyOrigamiCyan());
        this.butterflyOrigamiGray = registerButterflyOrigami("butterfly_origami_gray", blockRegistry.getButterflyOrigamiGray());
        this.butterflyOrigamiGreen = registerButterflyOrigami("butterfly_origami_green", blockRegistry.getButterflyOrigamiGreen());
        this.butterflyOrigamiLightBlue = registerButterflyOrigami("butterfly_origami_light_blue", blockRegistry.getButterflyOrigamiLightBlue());
        this.butterflyOrigamiLightGray = registerButterflyOrigami("butterfly_origami_light_gray", blockRegistry.getButterflyOrigamiLightGray());
        this.butterflyOrigamiLime = registerButterflyOrigami("butterfly_origami_lime", blockRegistry.getButterflyOrigamiLime());
        this.butterflyOrigamiMagenta = registerButterflyOrigami("butterfly_origami_magenta", blockRegistry.getButterflyOrigamiMagenta());
        this.butterflyOrigamiOrange = registerButterflyOrigami("butterfly_origami_orange", blockRegistry.getButterflyOrigamiOrange());
        this.butterflyOrigamiPink = registerButterflyOrigami("butterfly_origami_pink", blockRegistry.getButterflyOrigamiPink());
        this.butterflyOrigamiPurple = registerButterflyOrigami("butterfly_origami_purple", blockRegistry.getButterflyOrigamiPurple());
        this.butterflyOrigamiRed = registerButterflyOrigami("butterfly_origami_red", blockRegistry.getButterflyOrigamiRed());
        this.butterflyOrigamiWhite = registerButterflyOrigami("butterfly_origami_white", blockRegistry.getButterflyOrigamiWhite());
        this.butterflyOrigamiYellow = registerButterflyOrigami("butterfly_origami_yellow", blockRegistry.getButterflyOrigamiYellow());

        this.butterflyPotterySherd = registerItem("butterfly_pottery_sherd");

        this.butterflyScrolls = new ArrayList<>() {
            {
                for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                    add(registerButterflyScroll(i));
                }
            }
        };

        this.butterflyGolemSpawnEgg = registerSpawnEgg("butterfly_golem", entityTypeRegistry.getButterflyGolem());

        this.butterflySpawnEggs = new ArrayList<>() {
            {
                for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                    add(registerButterflySpawnEgg(i));
                }
            }
        };

        this.caterpillars = new ArrayList<>() {
            {
                for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                    add(registerCaterpillar(i));
                }
            }
        };

        this.caterpillarSpawnEggs = new ArrayList<>() {
            {
                for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                    add(registerCaterpillarSpawnEgg(i));
                }
            }
        };

        this.emptyButterflyNet = registerButterflyNet(-1, ButterflyNetItem.EMPTY_NAME);
        this.infestedApple = registerItem("infested_apple");
        this.silk = registerItem("silk");
        this.zhuangziBook = registerZhuangZiBook();
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
        } else if (Objects.equals(ButterflySpeciesList.SPECIES[butterflyIndex], "lava")) {
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
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Item, Item> getButterflyOrigamiBlack() {
        return butterflyOrigamiBlack;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Item, Item> getButterflyOrigamiBlue() {
        return butterflyOrigamiBlue;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Item, Item> getButterflyOrigamiBrown() {
        return butterflyOrigamiBrown;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Item, Item> getButterflyOrigamiCyan() {
        return butterflyOrigamiCyan;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Item, Item> getButterflyOrigamiGray() {
        return butterflyOrigamiGray;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Item, Item> getButterflyOrigamiGreen() {
        return butterflyOrigamiGreen;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Item, Item> getButterflyOrigamiLightBlue() {
        return butterflyOrigamiLightBlue;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Item, Item> getButterflyOrigamiLightGray() {
        return butterflyOrigamiLightGray;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Item, Item> getButterflyOrigamiLime() {
        return butterflyOrigamiLime;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Item, Item> getButterflyOrigamiMagenta() {
        return butterflyOrigamiMagenta;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Item, Item> getButterflyOrigamiOrange() {
        return butterflyOrigamiOrange;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Item, Item> getButterflyOrigamiPink() {
        return butterflyOrigamiPink;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Item, Item> getButterflyOrigamiPurple() {
        return butterflyOrigamiPurple;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Item, Item> getButterflyOrigamiRed() {
        return butterflyOrigamiRed;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Item, Item> getButterflyOrigamiWhite() {
        return butterflyOrigamiWhite;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Item, Item> getButterflyOrigamiYellow() {
        return butterflyOrigamiYellow;
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

    private ResourceKey<Item> createResourceKey(String registryId) {
        ResourceLocation location = ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, registryId);
        return ResourceKey.create(Registries.ITEM, location);
    }

    /**
     * Register a banner pattern.
     *
     * @return A new registry object.
     */
    private DeferredHolder<Item, Item> registerBannerPattern(BannerPatternRegistry bannerPatternRegistry) {

        final String registryId = "banner_pattern_butterfly";
        ResourceKey<Item> key = createResourceKey(registryId);

        return deferredRegister.register(registryId, () -> new BannerPatternItem(
                bannerPatternRegistry.getButterflyBannerPatternTagKey(),
                new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).setId(key)));
    }

    /**
     * Register the butterfly book.
     * @return A new registry object.
     */
    private DeferredHolder<Item, Item> registerButterflyBook() {
        ResourceKey<Item> key = createResourceKey(ButterflyBookItem.NAME);
        return deferredRegister.register(ButterflyBookItem.NAME,
                () -> new ButterflyBookItem(
                        new Item.Properties().stacksTo(1).setId(key),
                        dataComponentRegistry));
    }

    /**
     * Register the butterfly feeder.
     * @return A new registry object.
     */
    private DeferredHolder<Item, Item> registerButterflyFeeder() {

        final String registryId = "butterfly_feeder";
        ResourceKey<Item> key = createResourceKey(registryId);

        return deferredRegister.register(registryId, () -> new BlockItem(
                blockRegistry.getButterflyFeeder().get(),
                new Item.Properties().setId(key)));

    }

    /**
     * Register the butterfly microscope.
     * @return A new registry object.
     */
    private DeferredHolder<Item, Item> registerButterflyMicroscope() {

        final String registryId = "butterfly_microscope";
        ResourceKey<Item> key = createResourceKey(registryId);

        return deferredRegister.register(registryId, () -> new BlockItem(
                blockRegistry.getButterflyMicroscope().get(),
                new Item.Properties().setId(key)));

    }

    /**
     * Register a butterfly net.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private DeferredHolder<Item, Item> registerButterflyNet(int butterflyIndex) {
        return registerButterflyNet(butterflyIndex, ButterflyNetItem.getRegistryId(butterflyIndex));
    }

    /**
     * Register a butterfly net.
     * @param butterflyIndex The index of the butterfly.
     * @param registryId The Registry ID of the item.
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

    /**
     * Register a bottled butterfly.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
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

    /**
     * Register a bottled caterpillar.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private DeferredHolder<Item, Item> registerBottledCaterpillar(int butterflyIndex) {

        String registryId = BottledCaterpillarItem.getRegistryId(butterflyIndex);
        ResourceKey<Item> key = createResourceKey(registryId);

        return deferredRegister.register(registryId,
                () -> new BottledCaterpillarItem(
                        new Item.Properties().stacksTo(1).setId(key),
                        blockRegistry.getBottledCaterpillarBlocks().get(butterflyIndex),
                        butterflyIndex));
    }

    /**
     * Register a butterfly egg.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
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

    /**
     * Register a butterfly scroll.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private DeferredHolder<Item, Item> registerButterflyScroll(int butterflyIndex) {

        String registryId = ButterflyScrollItem.getRegistryId(butterflyIndex);
        ResourceKey<Item> key = createResourceKey(registryId);

        return deferredRegister.register(registryId,
                () -> new ButterflyScrollItem(
                        new Item.Properties().setId(key),
                        dataComponentRegistry,
                        entityTypeRegistry,
                        this,
                        butterflyIndex));
    }

    /**
     * Register a butterfly spawn egg.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private DeferredHolder<Item, Item> registerButterflySpawnEgg(int butterflyIndex) {

        String registryId = Butterfly.getRegistryId(butterflyIndex);
        return registerSpawnEgg(registryId, entityTypeRegistry.getButterflies().get(butterflyIndex));
    }

    /**
     * Register a caterpillar.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private DeferredHolder<Item, Item> registerCaterpillar(int butterflyIndex) {

        String registryId = CaterpillarItem.getRegistryId(butterflyIndex);
        ResourceKey<Item> key = createResourceKey(registryId);

        return deferredRegister.register(registryId,
                () -> new CaterpillarItem(new Item.Properties().setId(key), Caterpillar.getRegistryId(butterflyIndex)));
    }

    /**
     * Register a caterpillar spawn egg.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private DeferredHolder<Item, Item> registerCaterpillarSpawnEgg(int butterflyIndex) {

        String registryId = Caterpillar.getRegistryId(butterflyIndex);
        return registerSpawnEgg(registryId, entityTypeRegistry.getCaterpillars().get(butterflyIndex));
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
}
