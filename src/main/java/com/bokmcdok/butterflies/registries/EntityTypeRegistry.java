package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.model.ButterflyModel;
import com.bokmcdok.butterflies.renderer.entity.ButterflyRenderer;
import com.bokmcdok.butterflies.world.entity.ambient.Butterfly;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


/**
 * This class registers all the entities we use with Forge's Entity Type Registry
 */
@Mod.EventBusSubscriber(modid = ButterfliesMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityTypeRegistry {

    // An instance of a deferred registry we use to register our entity types.
    public static final DeferredRegister<EntityType<?>> INSTANCE =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ButterfliesMod.MODID);

    // Register the butterflies.
    public static final RegistryObject<EntityType<Butterfly>> BUTTERFLY_MORPHO =
            INSTANCE.register(Butterfly.MORPHO_NAME, () -> EntityType.Builder.of(Butterfly::createMorphoButterfly, MobCategory.AMBIENT)
                    .sized(0.3f, 0.4f)
                    .build(Butterfly.MORPHO_NAME));

    public static final RegistryObject<EntityType<Butterfly>> BUTTERFLY_FORESTER =
            INSTANCE.register(Butterfly.FORESTER_NAME, () -> EntityType.Builder.of(Butterfly::createForesterButterfly, MobCategory.AMBIENT)
                    .sized(0.3f, 0.4f)
                    .build(Butterfly.FORESTER_NAME));

    public static final RegistryObject<EntityType<Butterfly>> BUTTERFLY_COMMON =
            INSTANCE.register(Butterfly.COMMON_NAME, () -> EntityType.Builder.of(Butterfly::createCommonButterfly, MobCategory.AMBIENT)
                    .sized(0.3f, 0.4f)
                    .build(Butterfly.COMMON_NAME));

    public static final RegistryObject<EntityType<Butterfly>> BUTTERFLY_EMPEROR =
            INSTANCE.register(Butterfly.EMPEROR_NAME, () -> EntityType.Builder.of(Butterfly::createEmperorButterfly, MobCategory.AMBIENT)
                    .sized(0.3f, 0.4f)
                    .build(Butterfly.EMPEROR_NAME));

    public static final RegistryObject<EntityType<Butterfly>> BUTTERFLY_HAIRSTREAK =
            INSTANCE.register(Butterfly.HAIRSTREAK_NAME, () -> EntityType.Builder.of(Butterfly::createHairstreakButterfly, MobCategory.AMBIENT)
                    .sized(0.3f, 0.4f)
                    .build(Butterfly.HAIRSTREAK_NAME));

    public static final RegistryObject<EntityType<Butterfly>> BUTTERFLY_RAINBOW =
            INSTANCE.register(Butterfly.RAINBOW_NAME, () -> EntityType.Builder.of(Butterfly::createRainbowButterfly, MobCategory.AMBIENT)
                    .sized(0.3f, 0.4f)
                    .build(Butterfly.RAINBOW_NAME));

    public static final RegistryObject<EntityType<Butterfly>> BUTTERFLY_HEATH =
            INSTANCE.register(Butterfly.HEATH_NAME, () -> EntityType.Builder.of(Butterfly::createHeathButterfly, MobCategory.AMBIENT)
                    .sized(0.3f, 0.4f)
                    .build(Butterfly.HEATH_NAME));

    public static final RegistryObject<EntityType<Butterfly>> BUTTERFLY_GLASSWING =
            INSTANCE.register(Butterfly.GLASSWING_NAME, () -> EntityType.Builder.of(Butterfly::createGlasswingButterfly, MobCategory.AMBIENT)
                    .sized(0.3f, 0.4f)
                    .build(Butterfly.GLASSWING_NAME));

    public static final RegistryObject<EntityType<Butterfly>> BUTTERFLY_CHALKHILL =
            INSTANCE.register(Butterfly.CHALKHILL_NAME, () -> EntityType.Builder.of(Butterfly::createChalkhillButterfly, MobCategory.AMBIENT)
                    .sized(0.3f, 0.4f)
                    .build(Butterfly.CHALKHILL_NAME));

    public static final RegistryObject<EntityType<Butterfly>> BUTTERFLY_SWALLOWTAIL =
            INSTANCE.register(Butterfly.SWALLOWTAIL_NAME, () -> EntityType.Builder.of(Butterfly::createSwallowtailButterfly, MobCategory.AMBIENT)
                    .sized(0.3f, 0.4f)
                    .build(Butterfly.SWALLOWTAIL_NAME));

    public static final RegistryObject<EntityType<Butterfly>> BUTTERFLY_MONARCH =
            INSTANCE.register(Butterfly.MONARCH_NAME, () -> EntityType.Builder.of(Butterfly::createMonarchButterfly, MobCategory.AMBIENT)
                    .sized(0.3f, 0.4f)
                    .build(Butterfly.MONARCH_NAME));

    public static final RegistryObject<EntityType<Butterfly>> BUTTERFLY_CABBAGE =
            INSTANCE.register(Butterfly.CABBAGE_NAME, () -> EntityType.Builder.of(Butterfly::createCabbageButterfly, MobCategory.AMBIENT)
                    .sized(0.3f, 0.4f)
                    .build(Butterfly.CABBAGE_NAME));

    /**
     * Register the renderers for our entities
     * @param event The event information
     */
    @SubscribeEvent
    public static void registerEntityRenders(final EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(BUTTERFLY_MORPHO.get(), ButterflyRenderer::new);
        event.registerEntityRenderer(BUTTERFLY_COMMON.get(), ButterflyRenderer::new);
        event.registerEntityRenderer(BUTTERFLY_FORESTER.get(), ButterflyRenderer::new);
        event.registerEntityRenderer(BUTTERFLY_EMPEROR.get(), ButterflyRenderer::new);
        event.registerEntityRenderer(BUTTERFLY_HAIRSTREAK.get(), ButterflyRenderer::new);
        event.registerEntityRenderer(BUTTERFLY_RAINBOW.get(), ButterflyRenderer::new);
        event.registerEntityRenderer(BUTTERFLY_HEATH.get(), ButterflyRenderer::new);
        event.registerEntityRenderer(BUTTERFLY_GLASSWING.get(), ButterflyRenderer::new);
        event.registerEntityRenderer(BUTTERFLY_CHALKHILL.get(), ButterflyRenderer::new);
        event.registerEntityRenderer(BUTTERFLY_SWALLOWTAIL.get(), ButterflyRenderer::new);
        event.registerEntityRenderer(BUTTERFLY_MONARCH.get(), ButterflyRenderer::new);
        event.registerEntityRenderer(BUTTERFLY_CABBAGE.get(), ButterflyRenderer::new);
    }

    /**
     * Register the attributes for living entities
     */
    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(BUTTERFLY_MORPHO.get(), Butterfly.createAttributes().build());
        event.put(BUTTERFLY_COMMON.get(), Butterfly.createAttributes().build());
        event.put(BUTTERFLY_FORESTER.get(), Butterfly.createAttributes().build());
        event.put(BUTTERFLY_EMPEROR.get(), Butterfly.createAttributes().build());
        event.put(BUTTERFLY_HAIRSTREAK.get(), Butterfly.createAttributes().build());
        event.put(BUTTERFLY_RAINBOW.get(), Butterfly.createAttributes().build());
        event.put(BUTTERFLY_HEATH.get(), Butterfly.createAttributes().build());
        event.put(BUTTERFLY_GLASSWING.get(), Butterfly.createAttributes().build());
        event.put(BUTTERFLY_CHALKHILL.get(), Butterfly.createAttributes().build());
        event.put(BUTTERFLY_SWALLOWTAIL.get(), Butterfly.createAttributes().build());
        event.put(BUTTERFLY_MONARCH.get(), Butterfly.createAttributes().build());
        event.put(BUTTERFLY_CABBAGE.get(), Butterfly.createAttributes().build());
    }

    /**
     * Register entity spawn placements here
     * @param event The event information
     */
    @SubscribeEvent
    public static void registerEntitySpawnPlacement(SpawnPlacementRegisterEvent event) {
        event.register(BUTTERFLY_MORPHO.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                Butterfly::checkButterflySpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_COMMON.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                Butterfly::checkButterflySpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_FORESTER.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                Butterfly::checkButterflySpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_EMPEROR.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                Butterfly::checkButterflySpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_HAIRSTREAK.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                Butterfly::checkButterflySpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_RAINBOW.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                Butterfly::checkButterflySpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_HEATH.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                Butterfly::checkButterflySpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_GLASSWING.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                Butterfly::checkButterflySpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_CHALKHILL.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                Butterfly::checkButterflySpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_SWALLOWTAIL.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                Butterfly::checkButterflySpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_MONARCH.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                Butterfly::checkButterflySpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_CABBAGE.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                Butterfly::checkButterflySpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);
    }

    /**
     * Registers models to be used for rendering
     * @param event The event information
     */
    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ButterflyModel.LAYER_LOCATION, ButterflyModel::createBodyLayer);
    }
}
