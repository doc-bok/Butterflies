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

    // Registry Items
    private List<RegistryObject<Item>> bottledButterflies;
    private List<RegistryObject<Item>> bottledCaterpillars;
    private RegistryObject<Item> burntButterflyNet;
    private RegistryObject<Item> butterflyBannerPattern;
    private RegistryObject<Item> butterflyBook;
    private List<RegistryObject<Item>> butterflyEggs;
    private RegistryObject<Item> butterflyFeeder;
    private RegistryObject<Item> butterflyMicroscope;
    private List<RegistryObject<Item>> butterflyNets;
    private RegistryObject<Item> butterflyPotterySherd;
    private List<RegistryObject<Item>> butterflyScrolls;
    private RegistryObject<Item> butterflyGolemSpawnEgg;
    private List<RegistryObject<Item>> butterflySpawnEggs;
    private List<RegistryObject<Item>> caterpillars;
    private List<RegistryObject<Item>> caterpillarSpawnEggs;
    private RegistryObject<Item> emptyButterflyNet;
    private RegistryObject<Item> infestedApple;
    private RegistryObject<Item> silk;
    private RegistryObject<Item> zhuangziBook;

    // Origami
    private RegistryObject<Item> butterflyOrigamiBlack;
    private RegistryObject<Item> butterflyOrigamiBlue;
    private RegistryObject<Item> butterflyOrigamiBrown;
    private RegistryObject<Item> butterflyOrigamiCyan;
    private RegistryObject<Item> butterflyOrigamiGray;
    private RegistryObject<Item> butterflyOrigamiGreen;
    private RegistryObject<Item> butterflyOrigamiLightBlue;
    private RegistryObject<Item> butterflyOrigamiLightGray;
    private RegistryObject<Item> butterflyOrigamiLime;
    private RegistryObject<Item> butterflyOrigamiMagenta;
    private RegistryObject<Item> butterflyOrigamiOrange;
    private RegistryObject<Item> butterflyOrigamiPink;
    private RegistryObject<Item> butterflyOrigamiPurple;
    private RegistryObject<Item> butterflyOrigamiRed;
    private RegistryObject<Item> butterflyOrigamiWhite;
    private RegistryObject<Item> butterflyOrigamiYellow;

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public ItemRegistry(IEventBus modEventBus) {
        this.deferredRegister = DeferredRegister.create(ForgeRegistries.ITEMS, ButterfliesMod.MOD_ID);
        this.deferredRegister.register(modEventBus);
    }

    /**
     * Register the items.
     * @param blockRegistry The block registry.
     * @param entityTypeRegistry The entity type registry.
     */
    public void initialise(BannerPatternRegistry bannerPatternRegistry,
                           BlockRegistry blockRegistry,
                           EntityTypeRegistry entityTypeRegistry) {

        this.blockRegistry = blockRegistry;
        this.entityTypeRegistry = entityTypeRegistry;

        this.bottledButterflies = new ArrayList<>() {
            {
                for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
                    add(registerBottledButterfly(i));
                }
            }
        };

        this.bottledCaterpillars = new ArrayList<>() {
            {
                for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
                    add(registerBottledCaterpillar(i));
                }
            }
        };

        this.burntButterflyNet = deferredRegister.register("butterfly_net_burnt", () -> new Item(new Item.Properties()));

        this.butterflyBannerPattern = deferredRegister.register("banner_pattern_butterfly", () -> new BannerPatternItem(
                bannerPatternRegistry.getButterflyBannerPatternTagKey(),
                (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON)));

        this.butterflyBook = deferredRegister.register(ButterflyBookItem.NAME, ButterflyBookItem::new);

        this.butterflyEggs = new ArrayList<>() {
            {
                for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
                    add(registerButterflyEgg(i));
                }
            }
        };

        this.butterflyFeeder = deferredRegister.register("butterfly_feeder",
                        () -> new BlockItem(blockRegistry.getButterflyFeeder().get(), new Item.Properties()));

        this.butterflyMicroscope = deferredRegister.register("butterfly_microscope",
                () -> new BlockItem(blockRegistry.getButterflyMicroscope().get(), new Item.Properties()));

        this.butterflyNets = new ArrayList<>() {
            {
                for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
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

        this.butterflyPotterySherd = deferredRegister.register("butterfly_pottery_sherd",
                () -> new Item(new Item.Properties()));

        this.butterflyScrolls = new ArrayList<>() {
            {
                for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
                    add(registerButterflyScroll(i));
                }
            }
        };

        this.butterflyGolemSpawnEgg = deferredRegister.register("butterfly_golem",
                () -> new ForgeSpawnEggItem(entityTypeRegistry.getButterflyGolem(),
                        0x888800, 0x333333, new Item.Properties()));

        this.butterflySpawnEggs = new ArrayList<>() {
            {
                for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
                    add(registerButterflySpawnEgg(i));
                }
            }
        };

        this.caterpillars = new ArrayList<>() {
            {
                for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
                    add(registerCaterpillar(i));
                }
            }
        };

        this.caterpillarSpawnEggs = new ArrayList<>() {
            {
                for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
                    add(registerCaterpillarSpawnEgg(i));
                }
            }
        };

        this.emptyButterflyNet = deferredRegister.register(ButterflyNetItem.EMPTY_NAME, () -> new ButterflyNetItem(this, -1));
        this.infestedApple = deferredRegister.register("infested_apple", () -> new Item(new Item.Properties()));
        this.silk = deferredRegister.register("silk", () -> new Item(new Item.Properties()));
        this.zhuangziBook = deferredRegister.register(ButterflyZhuangziItem.NAME, ButterflyZhuangziItem::new);
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
     * Get a butterfly origami.
     * @return The registry object.
     */
    public RegistryObject<Item> getButterflyOrigamiBlack() {
        return butterflyOrigamiBlack;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public RegistryObject<Item> getButterflyOrigamiBlue() {
        return butterflyOrigamiBlue;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public RegistryObject<Item> getButterflyOrigamiBrown() {
        return butterflyOrigamiBrown;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public RegistryObject<Item> getButterflyOrigamiCyan() {
        return butterflyOrigamiCyan;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public RegistryObject<Item> getButterflyOrigamiGray() {
        return butterflyOrigamiGray;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public RegistryObject<Item> getButterflyOrigamiGreen() {
        return butterflyOrigamiGreen;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public RegistryObject<Item> getButterflyOrigamiLightBlue() {
        return butterflyOrigamiLightBlue;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public RegistryObject<Item> getButterflyOrigamiLightGray() {
        return butterflyOrigamiLightGray;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public RegistryObject<Item> getButterflyOrigamiLime() {
        return butterflyOrigamiLime;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public RegistryObject<Item> getButterflyOrigamiMagenta() {
        return butterflyOrigamiMagenta;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public RegistryObject<Item> getButterflyOrigamiOrange() {
        return butterflyOrigamiOrange;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public RegistryObject<Item> getButterflyOrigamiPink() {
        return butterflyOrigamiPink;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public RegistryObject<Item> getButterflyOrigamiPurple() {
        return butterflyOrigamiPurple;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public RegistryObject<Item> getButterflyOrigamiRed() {
        return butterflyOrigamiRed;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public RegistryObject<Item> getButterflyOrigamiWhite() {
        return butterflyOrigamiWhite;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public RegistryObject<Item> getButterflyOrigamiYellow() {
        return butterflyOrigamiYellow;
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
     * Register a butterfly net.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private RegistryObject<Item> registerButterflyNet(int butterflyIndex) {
        return deferredRegister.register(ButterflyNetItem.getRegistryId(butterflyIndex),
                () -> new ButterflyNetItem(this, butterflyIndex));
    }

    /**
     * Register a bottled butterfly.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private RegistryObject<Item> registerBottledButterfly(int butterflyIndex) {
        return deferredRegister.register(BottledButterflyItem.getRegistryId(butterflyIndex),
                () -> new BottledButterflyItem(blockRegistry.getBottledButterflyBlocks().get(butterflyIndex), butterflyIndex));
    }

    /**
     * Register a bottled caterpillar.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private RegistryObject<Item> registerBottledCaterpillar(int butterflyIndex) {
        return deferredRegister.register(BottledCaterpillarItem.getRegistryId(butterflyIndex),
                () -> new BottledCaterpillarItem(blockRegistry.getBottledCaterpillarBlocks().get(butterflyIndex), butterflyIndex));
    }

    /**
     * Register a butterfly egg.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private RegistryObject<Item> registerButterflyEgg(int butterflyIndex) {
        return deferredRegister.register(ButterflyEggItem.getRegistryId(butterflyIndex),
                () -> new ButterflyEggItem(butterflyIndex, new Item.Properties()));
    }

    /**
     * Registers an origami block item.
     * @param id The ID of the item to register.
     * @return A new registry object.
     */
    private RegistryObject<Item> registerButterflyOrigami(String id, RegistryObject<Block> block) {
        return deferredRegister.register(id, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    /**
     * Register a butterfly scroll.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private RegistryObject<Item> registerButterflyScroll(int butterflyIndex) {
        return deferredRegister.register(ButterflyScrollItem.getRegistryId(butterflyIndex),
                () -> new ButterflyScrollItem(entityTypeRegistry, butterflyIndex));
    }

    /**
     * Register a butterfly spawn egg.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private RegistryObject<Item> registerButterflySpawnEgg(int butterflyIndex) {
        return deferredRegister.register(Butterfly.getRegistryId(butterflyIndex),
                () -> new ForgeSpawnEggItem(entityTypeRegistry.getButterflies().get(butterflyIndex),
                        0x880000, 0x0088ff, new Item.Properties()));
    }

    /**
     * Register a caterpillar.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private RegistryObject<Item> registerCaterpillar(int butterflyIndex) {
        return deferredRegister.register(CaterpillarItem.getRegistryId(butterflyIndex),
                () -> new CaterpillarItem(Caterpillar.getRegistryId(butterflyIndex)));
    }

    /**
     * Register a caterpillar spawn egg.
     * @param butterflyIndex The index of the butterfly.
     * @return A new registry object.
     */
    private RegistryObject<Item> registerCaterpillarSpawnEgg(int butterflyIndex) {
        return deferredRegister.register(Caterpillar.getRegistryId(butterflyIndex),
                () -> new ForgeSpawnEggItem(entityTypeRegistry.getCaterpillars().get(butterflyIndex),
                        0x0088ff, 0x880000, new Item.Properties()));
    }
}
