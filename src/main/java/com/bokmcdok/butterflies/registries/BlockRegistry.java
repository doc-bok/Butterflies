package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.block.BottledButterflyBlock;
import com.bokmcdok.butterflies.world.block.BottledCaterpillarBlock;
import com.bokmcdok.butterflies.world.block.ButterflyLeavesBlock;
import com.bokmcdok.butterflies.world.block.ButterflyMangroveLeavesBlock;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Registers the blocks used by the mod.
 */
@Mod.EventBusSubscriber(modid = ButterfliesMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockRegistry {

    // An instance of a deferred registry we use to register items.
    public static final DeferredRegister<Block> INSTANCE =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ButterfliesMod.MODID);

    // The bottled butterfly block when it is in the world.

    // Bottled Caterpillars
    public static final RegistryObject<Block> BOTTLED_BUTTERFLY_ADMIRAL =
            INSTANCE.register(BottledButterflyBlock.ADMIRAL_NAME, BottledButterflyBlock::new);
    public static final RegistryObject<Block> BOTTLED_BUTTERFLY_BUCKEYE =
            INSTANCE.register(BottledButterflyBlock.BUCKEYE_NAME, BottledButterflyBlock::new);
    public static final RegistryObject<Block> BOTTLED_BUTTERFLY_CABBAGE =
            INSTANCE.register(BottledButterflyBlock.CABBAGE_NAME, BottledButterflyBlock::new);
    public static final RegistryObject<Block> BOTTLED_BUTTERFLY_CHALKHILL =
            INSTANCE.register(BottledButterflyBlock.CHALKHILL_NAME, BottledButterflyBlock::new);
    public static final RegistryObject<Block> BOTTLED_BUTTERFLY_CLIPPER =
            INSTANCE.register(BottledButterflyBlock.CLIPPER_NAME, BottledButterflyBlock::new);
    public static final RegistryObject<Block> BOTTLED_BUTTERFLY_COMMON =
            INSTANCE.register(BottledButterflyBlock.COMMON_NAME, BottledButterflyBlock::new);
    public static final RegistryObject<Block> BOTTLED_BUTTERFLY_EMPEROR =
            INSTANCE.register(BottledButterflyBlock.EMPEROR_NAME, BottledButterflyBlock::new);
    public static final RegistryObject<Block> BOTTLED_BUTTERFLY_FORESTER =
            INSTANCE.register(BottledButterflyBlock.FORESTER_NAME, BottledButterflyBlock::new);
    public static final RegistryObject<Block> BOTTLED_BUTTERFLY_GLASSWING =
            INSTANCE.register(BottledButterflyBlock.GLASSWING_NAME, BottledButterflyBlock::new);
    public static final RegistryObject<Block> BOTTLED_BUTTERFLY_HAIRSTREAK =
            INSTANCE.register(BottledButterflyBlock.HAIRSTREAK_NAME, BottledButterflyBlock::new);
    public static final RegistryObject<Block> BOTTLED_BUTTERFLY_HEATH =
            INSTANCE.register(BottledButterflyBlock.HEATH_NAME, BottledButterflyBlock::new);
    public static final RegistryObject<Block> BOTTLED_BUTTERFLY_LONGWING =
            INSTANCE.register(BottledButterflyBlock.LONGWING_NAME, BottledButterflyBlock::new);
    public static final RegistryObject<Block> BOTTLED_BUTTERFLY_MONARCH =
            INSTANCE.register(BottledButterflyBlock.MONARCH_NAME, BottledButterflyBlock::new);
    public static final RegistryObject<Block> BOTTLED_BUTTERFLY_MORPHO =
            INSTANCE.register(BottledButterflyBlock.MORPHO_NAME, BottledButterflyBlock::new);
    public static final RegistryObject<Block> BOTTLED_BUTTERFLY_RAINBOW =
            INSTANCE.register(BottledButterflyBlock.RAINBOW_NAME, BottledButterflyBlock::new);
    public static final RegistryObject<Block> BOTTLED_BUTTERFLY_SWALLOWTAIL =
            INSTANCE.register(BottledButterflyBlock.SWALLOWTAIL_NAME, BottledButterflyBlock::new);
    
    //  TODO: This is here for backward compatibility. It should be removed in
    //         future versions.
    public static final RegistryObject<Block> BOTTLED_BUTTERFLY_BLOCK =
            INSTANCE.register(BottledButterflyBlock.NAME, BottledButterflyBlock::new);

    // Bottled Caterpillars
    public static final RegistryObject<Block> BOTTLED_CATERPILLAR_ADMIRAL =
            INSTANCE.register(BottledCaterpillarBlock.ADMIRAL_NAME, BlockRegistry::bottledCaterpillarBlock);
    public static final RegistryObject<Block> BOTTLED_CATERPILLAR_BUCKEYE =
            INSTANCE.register(BottledCaterpillarBlock.BUCKEYE_NAME, BlockRegistry::bottledCaterpillarBlock);
    public static final RegistryObject<Block> BOTTLED_CATERPILLAR_CABBAGE =
            INSTANCE.register(BottledCaterpillarBlock.CABBAGE_NAME, BlockRegistry::bottledCaterpillarBlock);
    public static final RegistryObject<Block> BOTTLED_CATERPILLAR_CHALKHILL =
            INSTANCE.register(BottledCaterpillarBlock.CHALKHILL_NAME, BlockRegistry::bottledCaterpillarBlock);
    public static final RegistryObject<Block> BOTTLED_CATERPILLAR_CLIPPER =
            INSTANCE.register(BottledCaterpillarBlock.CLIPPER_NAME, BlockRegistry::bottledCaterpillarBlock);
    public static final RegistryObject<Block> BOTTLED_CATERPILLAR_COMMON =
            INSTANCE.register(BottledCaterpillarBlock.COMMON_NAME, BlockRegistry::bottledCaterpillarBlock);
    public static final RegistryObject<Block> BOTTLED_CATERPILLAR_EMPEROR =
            INSTANCE.register(BottledCaterpillarBlock.EMPEROR_NAME, BlockRegistry::bottledCaterpillarBlock);
    public static final RegistryObject<Block> BOTTLED_CATERPILLAR_FORESTER =
            INSTANCE.register(BottledCaterpillarBlock.FORESTER_NAME, BlockRegistry::bottledCaterpillarBlock);
    public static final RegistryObject<Block> BOTTLED_CATERPILLAR_GLASSWING =
            INSTANCE.register(BottledCaterpillarBlock.GLASSWING_NAME, BlockRegistry::bottledCaterpillarBlock);
    public static final RegistryObject<Block> BOTTLED_CATERPILLAR_HAIRSTREAK =
            INSTANCE.register(BottledCaterpillarBlock.HAIRSTREAK_NAME, BlockRegistry::bottledCaterpillarBlock);
    public static final RegistryObject<Block> BOTTLED_CATERPILLAR_HEATH =
            INSTANCE.register(BottledCaterpillarBlock.HEATH_NAME, BlockRegistry::bottledCaterpillarBlock);
    public static final RegistryObject<Block> BOTTLED_CATERPILLAR_LONGWING =
            INSTANCE.register(BottledCaterpillarBlock.LONGWING_NAME, BlockRegistry::bottledCaterpillarBlock);
    public static final RegistryObject<Block> BOTTLED_CATERPILLAR_MONARCH =
            INSTANCE.register(BottledCaterpillarBlock.MONARCH_NAME, BlockRegistry::bottledCaterpillarBlock);
    public static final RegistryObject<Block> BOTTLED_CATERPILLAR_MORPHO =
            INSTANCE.register(BottledCaterpillarBlock.MORPHO_NAME, BlockRegistry::bottledCaterpillarBlock);
    public static final RegistryObject<Block> BOTTLED_CATERPILLAR_RAINBOW =
            INSTANCE.register(BottledCaterpillarBlock.RAINBOW_NAME, BlockRegistry::bottledCaterpillarBlock);
    public static final RegistryObject<Block> BOTTLED_CATERPILLAR_SWALLOWTAIL =
            INSTANCE.register(BottledCaterpillarBlock.SWALLOWTAIL_NAME, BlockRegistry::bottledCaterpillarBlock);

    // Represent leaves that have butterfly eggs in them.
    public static final RegistryObject<Block> BUTTERFLY_OAK_LEAVES =
            INSTANCE.register("butterfly_oak_leaves", () -> butterflyLeaves(SoundType.GRASS));

    public static final RegistryObject<Block> BUTTERFLY_SPRUCE_LEAVES =
            INSTANCE.register("butterfly_spruce_leaves", () -> butterflyLeaves(SoundType.GRASS));

    public static final RegistryObject<Block> BUTTERFLY_BIRCH_LEAVES =
            INSTANCE.register("butterfly_birch_leaves", () -> butterflyLeaves(SoundType.GRASS));

    public static final RegistryObject<Block> BUTTERFLY_JUNGLE_LEAVES =
            INSTANCE.register("butterfly_jungle_leaves", () -> butterflyLeaves(SoundType.GRASS));

    public static final RegistryObject<Block> BUTTERFLY_ACACIA_LEAVES =
            INSTANCE.register("butterfly_acacia_leaves", () -> butterflyLeaves(SoundType.GRASS));

    public static final RegistryObject<Block> BUTTERFLY_DARK_OAK_LEAVES =
            INSTANCE.register("butterfly_dark_oak_leaves", () -> butterflyLeaves(SoundType.GRASS));

    public static final RegistryObject<Block> BUTTERFLY_AZALEA_LEAVES =
            INSTANCE.register("butterfly_azalea_leaves", () -> butterflyLeaves(SoundType.AZALEA_LEAVES));

    public static final RegistryObject<Block> BUTTERFLY_FLOWERING_AZALEA_LEAVES =
            INSTANCE.register("butterfly_flowering_azalea_leaves", () -> butterflyLeaves(SoundType.AZALEA_LEAVES));

    public static final RegistryObject<Block> BUTTERFLY_MANGROVE_LEAVES =
            INSTANCE.register("butterfly_mangrove_leaves", () ->
                    new ButterflyMangroveLeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES)
                            .strength(0.2F)
                            .randomTicks()
                            .sound(SoundType.GRASS)
                            .noOcclusion().isValidSpawn(BlockRegistry::ocelotOrParrot)
                            .isSuffocating(BlockRegistry::never)
                            .isViewBlocking(BlockRegistry::never)));

    private static ButterflyLeavesBlock butterflyLeaves(SoundType p_152615_) {
        return new ButterflyLeavesBlock(BlockBehaviour.Properties
                .of(Material.LEAVES)
                .strength(0.2F)
                .randomTicks()
                .sound(p_152615_)
                .noOcclusion()
                .isValidSpawn(BlockRegistry::ocelotOrParrot)
                .isSuffocating(BlockRegistry::never)
                .isViewBlocking(BlockRegistry::never));
    }

    private static BottledCaterpillarBlock bottledCaterpillarBlock() {
        return new BottledCaterpillarBlock(BlockBehaviour.Properties.copy(Blocks.GLASS)
                .isRedstoneConductor(BlockRegistry::never)
                .isSuffocating(BlockRegistry::never)
                .isValidSpawn(BlockRegistry::never)
                .isViewBlocking(BlockRegistry::never)
                .noOcclusion()
                .sound(SoundType.GLASS)
                .strength(0.3F));
    }

    /**
     * Helper method for the "never" attribute.
     * @param blockState The current block state.
     * @param blockGetter Access to the block.
     * @param blockPos The block's position.
     * @param entityType The entity type trying to spawn.
     * @return Always FALSE.
     */
    public static boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, EntityType<?> entityType) {
        return false;
    }

    /**
     * Helper method for the "never" attribute.
     * @param blockState The current block state.
     * @param blockGetter Access to the block.
     * @param blockPos The block's position.
     * @return Always FALSE.
     */
    public static boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return false;
    }

    private static Boolean ocelotOrParrot(BlockState blockState,
                                          BlockGetter blockGetter,
                                          BlockPos position,
                                          EntityType<?> entityType) {
        return entityType == EntityType.OCELOT || entityType == EntityType.PARROT;
    }

    /**
     * Register colors for (e.g.) foliage-style blocks.
     * @param event The event fired to let us know we can register block colors.
     */
    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event){
        // Evergreen
        event.register((state, tint, position, color) -> FoliageColor.getEvergreenColor(),
                BUTTERFLY_SPRUCE_LEAVES.get());

        // Birch
        event.register((state, tint, position, color) -> FoliageColor.getBirchColor(),
                BUTTERFLY_BIRCH_LEAVES.get());

        // Default
        event.register((state, tint, position, color) ->
                        tint != null && position != null ? BiomeColors.getAverageFoliageColor(tint, position) :
                                                           FoliageColor.getDefaultColor(),
                BUTTERFLY_OAK_LEAVES.get(),
                BUTTERFLY_JUNGLE_LEAVES.get(),
                BUTTERFLY_ACACIA_LEAVES.get(),
                BUTTERFLY_DARK_OAK_LEAVES.get(),
                BUTTERFLY_MANGROVE_LEAVES.get());
    }
}
