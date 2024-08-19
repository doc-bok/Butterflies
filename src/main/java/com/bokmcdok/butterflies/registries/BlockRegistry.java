package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflySpeciesList;
import com.bokmcdok.butterflies.world.block.BottledButterflyBlock;
import com.bokmcdok.butterflies.world.block.BottledCaterpillarBlock;
import com.bokmcdok.butterflies.world.block.ButterflyFeederBlock;
import com.bokmcdok.butterflies.world.block.FlowerCropBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Registers the blocks used by the mod.
 */
public class BlockRegistry {

    // An instance of a deferred registry we use to register items.
    private final DeferredRegister<Block> deferredRegister;

    // Bottled creatures.
    private List<RegistryObject<Block>> bottledButterflyBlocks;
    private List<RegistryObject<Block>> bottledCaterpillarBlocks;

    // Butterfly Feeder
    private RegistryObject<Block> butterflyFeeder;

    // Flower Buds
    private RegistryObject<Block> alliumBud;
    private RegistryObject<Block> azureBluetBud;
    private RegistryObject<Block> blueOrchidBud;
    private RegistryObject<Block> cornflowerBud;
    private RegistryObject<Block> dandelionBud;
    private RegistryObject<Block> lilyOfTheValleyBud;
    private RegistryObject<Block> orangeTulipBud;
    private RegistryObject<Block> oxeyeDaisyBud;
    private RegistryObject<Block> pinkTulipBud;
    private RegistryObject<Block> poppyBud;
    private RegistryObject<Block> redTulipBud;
    private RegistryObject<Block> whiteTulipBud;
    private RegistryObject<Block> witherRoseBud;

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
        this.deferredRegister = DeferredRegister.create(ForgeRegistries.BLOCKS, ButterfliesMod.MOD_ID);
        this.deferredRegister.register(modEventBus);
    }

    /**
     * Register the blocks.
     * @param blockEntityTypeRegistry The block entity registry.
     * @param menuTypeRegistry The menu type registry.
     */
    public void initialise(BlockEntityTypeRegistry blockEntityTypeRegistry,
                           MenuTypeRegistry menuTypeRegistry) {
        
        this.bottledButterflyBlocks = new ArrayList<>() {
            {
                for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                    RegistryObject<Block> newBlock =
                            deferredRegister.register(getBottledButterflyRegistryId(i), BottledButterflyBlock::new);
                    add(newBlock);
                }
            }
        };
        
        this.bottledCaterpillarBlocks = new ArrayList<>() {
            {
                for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                    RegistryObject<Block> newBlock =
                            deferredRegister.register(getBottledCaterpillarRegistryId(i), BottledCaterpillarBlock::new);
                    add(newBlock);
                }
            }
        };
        
        this.alliumBud = deferredRegister.register(
                "bud_allium", () -> new FlowerCropBlock(Blocks.ALLIUM)
        );

        this.azureBluetBud = deferredRegister.register(
                "bud_azure_bluet", () -> new FlowerCropBlock(Blocks.AZURE_BLUET)
        );

        this.blueOrchidBud = deferredRegister.register(
                "bud_blue_orchid", () -> new FlowerCropBlock(Blocks.BLUE_ORCHID)
        );

        this.cornflowerBud = deferredRegister.register(
                "bud_cornflower", () -> new FlowerCropBlock(Blocks.CORNFLOWER)
        );

        this.dandelionBud = deferredRegister.register(
                "bud_dandelion", () -> new FlowerCropBlock(Blocks.DANDELION)
        );

        this.lilyOfTheValleyBud = deferredRegister.register(
                "bud_lily_of_the_valley", () -> new FlowerCropBlock(Blocks.LILY_OF_THE_VALLEY)
        );

        this.orangeTulipBud = deferredRegister.register(
                "bud_orange_tulip", () -> new FlowerCropBlock(Blocks.ORANGE_TULIP)
        );

        this.oxeyeDaisyBud = deferredRegister.register(
                "bud_oxeye_daisy", () -> new FlowerCropBlock(Blocks.OXEYE_DAISY)
        );

        this.pinkTulipBud = deferredRegister.register(
                "bud_pink_tulip", () -> new FlowerCropBlock(Blocks.PINK_TULIP)
        );

        this.poppyBud = deferredRegister.register(
                "bud_poppy", () -> new FlowerCropBlock(Blocks.POPPY)
        );

        this.redTulipBud = deferredRegister.register(
                "bud_red_tulip", () -> new FlowerCropBlock(Blocks.RED_TULIP)
        );

        this.whiteTulipBud = deferredRegister.register(
                "bud_white_tulip", () -> new FlowerCropBlock(Blocks.WHITE_TULIP)
        );

        this.witherRoseBud = deferredRegister.register(
                "bud_wither_rose", () -> new FlowerCropBlock(Blocks.WITHER_ROSE)
        );

        this.butterflyFeeder = deferredRegister.register( "butterfly_feeder",
                () -> new ButterflyFeederBlock(blockEntityTypeRegistry, menuTypeRegistry));
    }

    /**
     * Allium bud accessor.
     * @return The registry object.
     */
    public RegistryObject<Block> getAlliumBud() {
        return alliumBud;
    }

    /**
     * Azure bluet bud accessor.
     * @return The registry object.
     */
    public RegistryObject<Block> getAzureBluetBud() {
        return azureBluetBud;
    }

    /**
     * Blue orchid bud accessor.
     * @return The registry object.
     */
    public RegistryObject<Block> getBlueOrchidBud() {
        return blueOrchidBud;
    }

    /**
     * Get the bottled butterfly blocks.
     * @return The list of bottled butterfly blocks.
     */
    public List<RegistryObject<Block>> getBottledButterflyBlocks() {
        return this.bottledButterflyBlocks;
    }

    /**
     * Get the bottled caterpillar blocks.
     * @return The list of bottled caterpillar blocks.
     */
    public List<RegistryObject<Block>> getBottledCaterpillarBlocks() {
        return this.bottledCaterpillarBlocks;
    }

    /**
     * Get the butterfly feeder block.
     * @return The butterfly feeder block.
     */
    public RegistryObject<Block> getButterflyFeeder() {
        return butterflyFeeder;
    }

    /**
     * Cornflower bud accessor.
     * @return The registry object.
     */
    public RegistryObject<Block> getCornflowerBud() {
        return cornflowerBud;
    }

    /**
     * Dandelion bud accessor.
     * @return The registry object.
     */
    public RegistryObject<Block> getDandelionBud() {
        return dandelionBud;
    }

    /**
     * Lily of the valley bud accessor.
     * @return The registry object.
     */
    public RegistryObject<Block> getLilyOfTheValleyBud() {
        return lilyOfTheValleyBud;
    }

    /**
     * Orange tulip bud accessor.
     * @return The registry object.
     */
    public RegistryObject<Block> getOrangeTulipBud() {
        return orangeTulipBud;
    }

    /**
     * Oxeye daisy bud accessor.
     * @return The registry object.
     */
    public RegistryObject<Block> getOxeyeDaisyBud() {
        return oxeyeDaisyBud;
    }

    /**
     * Pink tulip bud accessor.
     * @return The registry object.
     */
    public RegistryObject<Block> getPinkTulipBud() {
        return pinkTulipBud;
    }

    /**
     * Poppy bud accessor.
     * @return The registry object.
     */
    public RegistryObject<Block> getPoppyBud() {
        return poppyBud;
    }

    /**
     * Red tulip bud accessor.
     * @return The registry object.
     */
    public RegistryObject<Block> getRedTulipBud() {
        return redTulipBud;
    }

    /**
     * White tulip bud accessor.
     * @return The registry object.
     */
    public RegistryObject<Block> getWhiteTulipBud() {
        return whiteTulipBud;
    }

    /**
     * Wither rose bud accessor.
     * @return The registry object.
     */
    public RegistryObject<Block> getWitherRoseBud() {
        return witherRoseBud;
    }

    /**
     * Helper method to generate the Registry ID for bottled butterflies.
     * @param butterflyIndex The butterfly index of the species.
     * @return The registry ID.
     */
    private String getBottledButterflyRegistryId(int butterflyIndex) {
        return "bottled_butterfly_" + ButterflySpeciesList.SPECIES[butterflyIndex];
    }

    /**
     * Helper method to generate the Registry ID for bottled caterpillars.
     * @param butterflyIndex The butterfly index of the species.
     * @return The registry ID.
     */
    private String getBottledCaterpillarRegistryId(int butterflyIndex) {
        return "bottled_caterpillar_" + ButterflySpeciesList.SPECIES[butterflyIndex];
    }
}
