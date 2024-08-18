package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflySpeciesList;
import com.bokmcdok.butterflies.world.block.BottledButterflyBlock;
import com.bokmcdok.butterflies.world.block.BottledCaterpillarBlock;
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

    private final List<RegistryObject<Block>> bottledButterflyBlocks;
    private final List<RegistryObject<Block>> bottledCaterpillarBlocks;

    // Flower Buds
    private final RegistryObject<Block> alliumBud;
    private final RegistryObject<Block> azureBluetBud;
    private final RegistryObject<Block> blueOrchidBud;
    private final RegistryObject<Block> cornflowerBud;
    private final RegistryObject<Block> dandelionBud;
    private final RegistryObject<Block> lilyOfTheValleyBud;
    private final RegistryObject<Block> orangeTulipBud;
    private final RegistryObject<Block> oxeyeDaisyBud;
    private final RegistryObject<Block> pinkTulipBud;
    private final RegistryObject<Block> poppyBud;
    private final RegistryObject<Block> redTulipBud;
    private final RegistryObject<Block> whiteTulipBud;
    private final RegistryObject<Block> witherRoseBud;

    /**
     * Helper method for the "never" attribute.
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
     * Helper method for the "never" attribute.
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
        
        this.bottledButterflyBlocks = new ArrayList<>() {
            {
                for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                    RegistryObject<Block> newBlock =
                            deferredRegister.register(BottledButterflyBlock.getRegistryId(i), BottledButterflyBlock::new);
                    add(newBlock);
                }
            }
        };
        
        this.bottledCaterpillarBlocks = new ArrayList<>() {
            {
                for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                    RegistryObject<Block> newBlock =
                            deferredRegister.register(BottledCaterpillarBlock.getRegistryId(i), BottledButterflyBlock::new);
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
     * Gets the flower bud for the specified flower block.
     * @param flowerBlock The flower we are trying to seed.
     * @return The bud block for the specified flower, if any.
     */
    public Block getFlowerBud(Block flowerBlock) {
        if (flowerBlock == Blocks.ALLIUM) {
            return this.alliumBud.get();
        }

        if (flowerBlock == Blocks.AZURE_BLUET) {
            return this.azureBluetBud.get();
        }

        if (flowerBlock == Blocks.BLUE_ORCHID) {
            return this.blueOrchidBud.get();
        }

        if (flowerBlock == Blocks.CORNFLOWER) {
            return this.cornflowerBud.get();
        }

        if (flowerBlock == Blocks.DANDELION) {
            return this.dandelionBud.get();
        }

        if (flowerBlock == Blocks.LILY_OF_THE_VALLEY) {
            return this.lilyOfTheValleyBud.get();
        }

        if (flowerBlock == Blocks.ORANGE_TULIP) {
            return this.orangeTulipBud.get();
        }

        if (flowerBlock == Blocks.OXEYE_DAISY) {
            return this.oxeyeDaisyBud.get();
        }

        if (flowerBlock == Blocks.PINK_TULIP) {
            return this.pinkTulipBud.get();
        }

        if (flowerBlock == Blocks.POPPY) {
            return this.poppyBud.get();
        }

        if (flowerBlock == Blocks.RED_TULIP) {
            return this.redTulipBud.get();
        }

        if (flowerBlock == Blocks.TORCHFLOWER) {
            return Blocks.TORCHFLOWER_CROP;
        }

        if (flowerBlock == Blocks.WHITE_TULIP) {
            return this.whiteTulipBud.get();
        }

        if (flowerBlock == Blocks.WITHER_ROSE) {
            return this.witherRoseBud.get();
        }

        if (flowerBlock == Blocks.SWEET_BERRY_BUSH) {
            return Blocks.SWEET_BERRY_BUSH;
        }

        return null;
    }
}
