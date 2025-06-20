package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyInfo;
import com.bokmcdok.butterflies.world.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

/**
 * Registers the blocks used by the mod.
 */
public class BlockRegistry {

    private static final String BUTTERFLY_MICROSCOPE_ID = "butterfly_microscope";

    // An instance of a deferred registry we use to register items.
    private final DeferredRegister<Block> deferredRegister;

    // Bottled creatures.
    private List<DeferredHolder<Block, Block>> bottledButterflyBlocks;
    private List<DeferredHolder<Block, Block>> bottledCaterpillarBlocks;

    // Butterfly Feeder
    private DeferredHolder<Block, Block> butterflyFeeder;

    // Butterfly Microscope
    private DeferredHolder<Block, Block> butterflyMicroscope;

    // Flower Buds
    private DeferredHolder<Block, Block> alliumBud;
    private DeferredHolder<Block, Block> azureBluetBud;
    private DeferredHolder<Block, Block> blueOrchidBud;
    private DeferredHolder<Block, Block> cornflowerBud;
    private DeferredHolder<Block, Block> dandelionBud;
    private DeferredHolder<Block, Block> lilyOfTheValleyBud;
    private DeferredHolder<Block, Block> orangeTulipBud;
    private DeferredHolder<Block, Block> oxeyeDaisyBud;
    private DeferredHolder<Block, Block> pinkTulipBud;
    private DeferredHolder<Block, Block> poppyBud;
    private DeferredHolder<Block, Block> redTulipBud;
    private DeferredHolder<Block, Block> whiteTulipBud;
    private DeferredHolder<Block, Block> witherRoseBud;

    // Origami
    private DeferredHolder<Block, Block> butterflyOrigamiBlack;
    private DeferredHolder<Block, Block> butterflyOrigamiBlue;
    private DeferredHolder<Block, Block> butterflyOrigamiBrown;
    private DeferredHolder<Block, Block> butterflyOrigamiCyan;
    private DeferredHolder<Block, Block> butterflyOrigamiGray;
    private DeferredHolder<Block, Block> butterflyOrigamiGreen;
    private DeferredHolder<Block, Block> butterflyOrigamiLightBlue;
    private DeferredHolder<Block, Block> butterflyOrigamiLightGray;
    private DeferredHolder<Block, Block> butterflyOrigamiLime;
    private DeferredHolder<Block, Block> butterflyOrigamiMagenta;
    private DeferredHolder<Block, Block> butterflyOrigamiOrange;
    private DeferredHolder<Block, Block> butterflyOrigamiPink;
    private DeferredHolder<Block, Block> butterflyOrigamiPurple;
    private DeferredHolder<Block, Block> butterflyOrigamiRed;
    private DeferredHolder<Block, Block> butterflyOrigamiWhite;
    private DeferredHolder<Block, Block> butterflyOrigamiYellow;

    /**
     * Helper method for the "never" attribute. Used in block properties during
     * block construction.
     * @param ignoredBlockState The current block state.
     * @param ignoredBlockGetter Access to the block.
     * @param ignoredBlockPos The block's position.
     * @param ignoredEntityType The entity type trying to spawn.
     * @return Always FALSE.
     */
    public static boolean never(BlockState ignoredBlockState,
                                BlockGetter ignoredBlockGetter,
                                BlockPos ignoredBlockPos,
                                EntityType<?> ignoredEntityType) {
        return false;
    }

    /**
     * Helper method for the "never" attribute. Used in block properties during
     * block construction.
     * @param ignoredBlockState The current block state.
     * @param ignoredBlockGetter Access to the block.
     * @param ignoredBlockPos The block's position.
     * @return Always FALSE.
     */
    public static boolean never(BlockState ignoredBlockState,
                                BlockGetter ignoredBlockGetter,
                                BlockPos ignoredBlockPos) {
        return false;
    }

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */

    public BlockRegistry(IEventBus modEventBus) {
        this.deferredRegister = DeferredRegister.create(BuiltInRegistries.BLOCK, ButterfliesMod.MOD_ID);
        this.deferredRegister.register(modEventBus);
    }

    /**
     * Register the blocks.
     * @param blockEntityTypeRegistry The block entity registry.
     * @param dataComponentRegistry The data component registry.
     * @param itemRegistry The item registry.
     * @param menuTypeRegistry The menu type registry.
     */
    public void initialise(BlockEntityTypeRegistry blockEntityTypeRegistry,
                           DataComponentRegistry dataComponentRegistry,
                           ItemRegistry itemRegistry,
                           MenuTypeRegistry menuTypeRegistry) {
        
        this.bottledButterflyBlocks = new ArrayList<>() {
            {
                for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
                    DeferredHolder<Block, Block> newBlock = registerBottledButterfly(i);
                    add(newBlock);
                }
            }
        };
        
        this.bottledCaterpillarBlocks = new ArrayList<>() {
            {
                for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
                    DeferredHolder<Block, Block> newBlock = registerBottledCaterpillar(i);
                    add(newBlock);
                }
            }
        };
        
        this.alliumBud = registerFlowerCropBlock("bud_allium", Blocks.ALLIUM);
        this.azureBluetBud = registerFlowerCropBlock("bud_azure_bluet", Blocks.AZURE_BLUET);
        this.blueOrchidBud = registerFlowerCropBlock("bud_blue_orchid", Blocks.BLUE_ORCHID);
        this.cornflowerBud = registerFlowerCropBlock("bud_cornflower", Blocks.CORNFLOWER);
        this.dandelionBud = registerFlowerCropBlock("bud_dandelion", Blocks.DANDELION);
        this.lilyOfTheValleyBud = registerFlowerCropBlock("bud_lily_of_the_valley", Blocks.LILY_OF_THE_VALLEY);
        this.orangeTulipBud = registerFlowerCropBlock("bud_orange_tulip", Blocks.ORANGE_TULIP);
        this.oxeyeDaisyBud = registerFlowerCropBlock("bud_oxeye_daisy", Blocks.OXEYE_DAISY);
        this.pinkTulipBud = registerFlowerCropBlock("bud_pink_tulip", Blocks.PINK_TULIP);
        this.poppyBud = registerFlowerCropBlock("bud_poppy", Blocks.POPPY);
        this.redTulipBud = registerFlowerCropBlock("bud_red_tulip", Blocks.RED_TULIP);
        this.whiteTulipBud = registerFlowerCropBlock("bud_white_tulip", Blocks.WHITE_TULIP);
        this.witherRoseBud = registerFlowerCropBlock("bud_wither_rose", Blocks.WITHER_ROSE);

        this.butterflyFeeder = registerButterflyFeeder(blockEntityTypeRegistry, menuTypeRegistry);

        this.butterflyMicroscope = registerButterflyMicroscope(dataComponentRegistry, itemRegistry, menuTypeRegistry);

        this.butterflyOrigamiBlack = registerButterflyOrigami("butterfly_origami_black");
        this.butterflyOrigamiBlue = registerButterflyOrigami("butterfly_origami_blue");
        this.butterflyOrigamiBrown = registerButterflyOrigami("butterfly_origami_brown");
        this.butterflyOrigamiCyan = registerButterflyOrigami("butterfly_origami_cyan");
        this.butterflyOrigamiGray = registerButterflyOrigami("butterfly_origami_gray");
        this.butterflyOrigamiGreen = registerButterflyOrigami("butterfly_origami_green");
        this.butterflyOrigamiLightBlue = registerButterflyOrigami("butterfly_origami_light_blue");
        this.butterflyOrigamiLightGray = registerButterflyOrigami("butterfly_origami_light_gray");
        this.butterflyOrigamiLime = registerButterflyOrigami("butterfly_origami_lime");
        this.butterflyOrigamiMagenta = registerButterflyOrigami("butterfly_origami_magenta");
        this.butterflyOrigamiOrange = registerButterflyOrigami("butterfly_origami_orange");
        this.butterflyOrigamiPink = registerButterflyOrigami("butterfly_origami_pink");
        this.butterflyOrigamiPurple = registerButterflyOrigami("butterfly_origami_purple");
        this.butterflyOrigamiRed = registerButterflyOrigami("butterfly_origami_red");
        this.butterflyOrigamiWhite = registerButterflyOrigami("butterfly_origami_white");
        this.butterflyOrigamiYellow = registerButterflyOrigami("butterfly_origami_yellow");
    }

    /**
     * Allium bud accessor.
     * @return The registry object.
     */
    public DeferredHolder<Block, Block> getAlliumBud() {
        return alliumBud;
    }

    /**
     * Azure bluet bud accessor.
     * @return The registry object.
     */
    public DeferredHolder<Block, Block> getAzureBluetBud() {
        return azureBluetBud;
    }

    /**
     * Blue orchid bud accessor.
     * @return The registry object.
     */
    public DeferredHolder<Block, Block> getBlueOrchidBud() {
        return blueOrchidBud;
    }

    /**
     * Get the bottled butterfly blocks.
     * @return The list of bottled butterfly blocks.
     */
    public List<DeferredHolder<Block, Block>> getBottledButterflyBlocks() {
        return this.bottledButterflyBlocks;
    }

    /**
     * Get the bottled caterpillar blocks.
     * @return The list of bottled caterpillar blocks.
     */
    public List<DeferredHolder<Block, Block>> getBottledCaterpillarBlocks() {
        return this.bottledCaterpillarBlocks;
    }

    /**
     * Get the butterfly feeder block.
     * @return The butterfly feeder block.
     */
    public DeferredHolder<Block, Block> getButterflyFeeder() {
        return butterflyFeeder;
    }

    /**
     * Get the butterfly microscope block.
     * @return The butterfly microscope block.
     */
    public DeferredHolder<Block, Block> getButterflyMicroscope() {
        return butterflyMicroscope;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Block, Block> getButterflyOrigamiBlack() {
        return butterflyOrigamiBlack;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Block, Block> getButterflyOrigamiBlue() {
        return butterflyOrigamiBlue;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Block, Block> getButterflyOrigamiBrown() {
        return butterflyOrigamiBrown;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Block, Block> getButterflyOrigamiCyan() {
        return butterflyOrigamiCyan;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Block, Block> getButterflyOrigamiGray() {
        return butterflyOrigamiGray;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Block, Block> getButterflyOrigamiGreen() {
        return butterflyOrigamiGreen;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Block, Block> getButterflyOrigamiLightBlue() {
        return butterflyOrigamiLightBlue;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Block, Block> getButterflyOrigamiLightGray() {
        return butterflyOrigamiLightGray;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Block, Block> getButterflyOrigamiLime() {
        return butterflyOrigamiLime;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Block, Block> getButterflyOrigamiMagenta() {
        return butterflyOrigamiMagenta;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Block, Block> getButterflyOrigamiOrange() {
        return butterflyOrigamiOrange;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Block, Block> getButterflyOrigamiPink() {
        return butterflyOrigamiPink;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Block, Block> getButterflyOrigamiPurple() {
        return butterflyOrigamiPurple;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Block, Block> getButterflyOrigamiRed() {
        return butterflyOrigamiRed;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Block, Block> getButterflyOrigamiWhite() {
        return butterflyOrigamiWhite;
    }

    /**
     * Get a butterfly origami.
     * @return The registry object.
     */
    public DeferredHolder<Block, Block> getButterflyOrigamiYellow() {
        return butterflyOrigamiYellow;
    }

    /**
     * Cornflower bud accessor.
     * @return The registry object.
     */
    public DeferredHolder<Block, Block> getCornflowerBud() {
        return cornflowerBud;
    }

    /**
     * Dandelion bud accessor.
     * @return The registry object.
     */
    public DeferredHolder<Block, Block> getDandelionBud() {
        return dandelionBud;
    }

    /**
     * Lily of the valley bud accessor.
     * @return The registry object.
     */
    public DeferredHolder<Block, Block> getLilyOfTheValleyBud() {
        return lilyOfTheValleyBud;
    }

    /**
     * Orange tulip bud accessor.
     * @return The registry object.
     */
    public DeferredHolder<Block, Block> getOrangeTulipBud() {
        return orangeTulipBud;
    }

    /**
     * Oxeye daisy bud accessor.
     * @return The registry object.
     */
    public DeferredHolder<Block, Block> getOxeyeDaisyBud() {
        return oxeyeDaisyBud;
    }

    /**
     * Pink tulip bud accessor.
     * @return The registry object.
     */
    public DeferredHolder<Block, Block> getPinkTulipBud() {
        return pinkTulipBud;
    }

    /**
     * Poppy bud accessor.
     * @return The registry object.
     */
    public DeferredHolder<Block, Block> getPoppyBud() {
        return poppyBud;
    }

    /**
     * Red tulip bud accessor.
     * @return The registry object.
     */
    public DeferredHolder<Block, Block> getRedTulipBud() {
        return redTulipBud;
    }

    /**
     * White tulip bud accessor.
     * @return The registry object.
     */
    public DeferredHolder<Block, Block> getWhiteTulipBud() {
        return whiteTulipBud;
    }

    /**
     * Wither rose bud accessor.
     * @return The registry object.
     */
    public DeferredHolder<Block, Block> getWitherRoseBud() {
        return witherRoseBud;
    }

    /**
     * Helper method to generate the Registry ID for bottled butterflies.
     * @param butterflyIndex The butterfly index of the species.
     * @return The registry ID.
     */
    private String getBottledButterflyRegistryId(int butterflyIndex) {
        return "bottled_butterfly_" + ButterflyInfo.SPECIES[butterflyIndex];
    }

    /**
     * Helper method to generate the Registry ID for bottled caterpillars.
     * @param butterflyIndex The butterfly index of the species.
     * @return The registry ID.
     */
    private String getBottledCaterpillarRegistryId(int butterflyIndex) {
        return "bottled_caterpillar_" + ButterflyInfo.SPECIES[butterflyIndex];
    }

    /**
     * Register a bottled butterfly.
     * @param butterflyIndex The butterfly index to register for.
     * @return The registry object.
     */
    private DeferredHolder<Block, Block> registerBottledButterfly(int butterflyIndex) {
        String registryId = getBottledButterflyRegistryId(butterflyIndex);
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)
                .isRedstoneConductor(BlockRegistry::never)
                .isSuffocating(BlockRegistry::never)
                .isValidSpawn(BlockRegistry::never)
                .isViewBlocking(BlockRegistry::never)
                .noOcclusion()
                .sound(SoundType.GLASS)
                .strength(0.3F)
                .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, registryId)));

        // Light Butterflies glow when they are in a bottle.
        if (registryId.equals("bottled_butterfly_light")) {
            properties.lightLevel((blockState) -> 15);
        }

        return deferredRegister.register(registryId, () -> new BottledButterflyBlock(properties));
    }

    /**
     * Register a bottled caterpillar.
     * @param butterflyIndex The butterfly index to register for.
     * @return The registry object.
     */
    private DeferredHolder<Block, Block> registerBottledCaterpillar(int butterflyIndex) {
        String registryId = getBottledCaterpillarRegistryId(butterflyIndex);
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)
                .isRedstoneConductor(BlockRegistry::never)
                .isSuffocating(BlockRegistry::never)
                .isValidSpawn(BlockRegistry::never)
                .isViewBlocking(BlockRegistry::never)
                .noOcclusion()
                .sound(SoundType.GLASS)
                .strength(0.3F)
                .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, registryId)));

        return deferredRegister.register(registryId, () -> new BottledCaterpillarBlock(properties));
    }

    /**
     * Register the butterfly feeder.
     * @param blockEntityTypeRegistry The block entity registry.
     * @param menuTypeRegistry The menu registry.
     * @return The registry object.
     */
    private DeferredHolder<Block, Block> registerButterflyFeeder(BlockEntityTypeRegistry blockEntityTypeRegistry,
                                                                 MenuTypeRegistry menuTypeRegistry) {
        BlockBehaviour.Properties properties = ButterflyFeederBlock.PROPERTIES;
        return deferredRegister.register(ButterflyFeederBlock.ID, () -> new ButterflyFeederBlock(blockEntityTypeRegistry, menuTypeRegistry, properties));
    }

    /**
     * Register the butterfly feeder.
     * @param dataComponentRegistry The data component registry.
     * @param itemRegistry The item registry.
     * @param menuTypeRegistry The menu registry.
     * @return The registry object.
     */
    private DeferredHolder<Block, Block> registerButterflyMicroscope(DataComponentRegistry dataComponentRegistry,
                                                                     ItemRegistry itemRegistry,
                                                                     MenuTypeRegistry menuTypeRegistry) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
                .mapColor(MapColor.STONE)
                .isRedstoneConductor(BlockRegistry::never)
                .isSuffocating(BlockRegistry::never)
                .isValidSpawn(BlockRegistry::never)
                .isViewBlocking(BlockRegistry::never)
                .noOcclusion()
                .sound(SoundType.STONE)
                .strength(1.0F)
                .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, BUTTERFLY_MICROSCOPE_ID)));

        return deferredRegister.register(BUTTERFLY_MICROSCOPE_ID, () -> new ButterflyMicroscopeBlock(dataComponentRegistry, itemRegistry, menuTypeRegistry, properties));
    }

    /**
     * Registers an origami block.
     * @param id The ID of the block to register.
     * @return A new registry object.
     */
    private DeferredHolder<Block, Block> registerButterflyOrigami(String id) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
                .noCollission()
                .strength(0.5F, 2.5F)
                .sound(SoundType.PINK_PETALS)
                .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, id)));

        return deferredRegister.register(id, () -> new ButterflyOrigamiBlock(properties));
    }

    /**
     * Register a flower crop.
     * @param block The base flower block.
     * @param registryId The value to use as a registry ID.
     * @return The registry object.
     */
    private DeferredHolder<Block, Block> registerFlowerCropBlock(String registryId, 
                                                                 Block block) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.ofFullCopy(block)
                .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, registryId)));

        return deferredRegister.register(registryId, () -> new FlowerCropBlock(block, properties));
    }
}
