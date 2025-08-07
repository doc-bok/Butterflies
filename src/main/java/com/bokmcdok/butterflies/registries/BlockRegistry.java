package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyData;
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
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Registers the blocks used by the mod.
 */
public class BlockRegistry {

    private static final String BUTTERFLY_MICROSCOPE_ID = "butterfly_microscope";

    // A list of Butterfly Origami IDs used by the registry.
    private static final String[] ORIGAMI_IDS = {
            "butterfly_origami_black",
            "butterfly_origami_blue",
            "butterfly_origami_brown",
            "butterfly_origami_cyan",
            "butterfly_origami_gray",
            "butterfly_origami_green",
            "butterfly_origami_light_blue",
            "butterfly_origami_light_gray",
            "butterfly_origami_lime",
            "butterfly_origami_magenta",
            "butterfly_origami_orange",
            "butterfly_origami_pink",
            "butterfly_origami_purple",
            "butterfly_origami_red",
            "butterfly_origami_white",
            "butterfly_origami_yellow"
    };

    // The base properties for bottled butterflies.
    private static final BlockBehaviour.Properties BOTTLED_BUTTERFLY_PROPERTIES =
            BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)
                    .isRedstoneConductor(BlockRegistry::never)
                    .isSuffocating(BlockRegistry::never)
                    .isValidSpawn(BlockRegistry::never)
                    .isViewBlocking(BlockRegistry::never)
                    .noOcclusion()
                    .sound(SoundType.GLASS)
                    .strength(0.3F);

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
    private List<DeferredHolder<Block, Block>> butterflyOrigami;

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

    public BlockRegistry(@NotNull IEventBus modEventBus) {
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
    public void initialise(@NotNull BlockEntityTypeRegistry blockEntityTypeRegistry,
                           @NotNull DataComponentRegistry dataComponentRegistry,
                           @NotNull ItemRegistry itemRegistry,
                           @NotNull MenuTypeRegistry menuTypeRegistry) {

        Objects.requireNonNull(blockEntityTypeRegistry);
        Objects.requireNonNull(dataComponentRegistry);
        Objects.requireNonNull(itemRegistry);
        Objects.requireNonNull(menuTypeRegistry);

        this.bottledButterflyBlocks = new ArrayList<>();
        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            DeferredHolder<Block, Block> newBlock = registerBottledButterfly(i);
            this.bottledButterflyBlocks.add(newBlock);
        }

        this.bottledCaterpillarBlocks = new ArrayList<>();
        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            this.bottledCaterpillarBlocks.add(registerBottledCaterpillar(i));
        }
        
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

        this.butterflyOrigami = new ArrayList<>();
        for(String id : ORIGAMI_IDS) {
            butterflyOrigami.add(registerButterflyOrigami(id));
        }
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

    public List<DeferredHolder<Block, Block>> getButterflyOrigami() {
        return butterflyOrigami;
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
        ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, registryId);
        ResourceKey<Block> resourceKey = ResourceKey.create(Registries.BLOCK, resourceLocation);
        BlockBehaviour.Properties properties = BOTTLED_BUTTERFLY_PROPERTIES.setId(resourceKey);

        // Light Butterflies glow when they are in a bottle.
        if (Arrays.asList(ButterflyInfo.TRAITS[butterflyIndex]).contains(ButterflyData.Trait.GLOW)) {
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
