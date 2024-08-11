package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.client.model.ButterflyEggModel;
import com.bokmcdok.butterflies.client.model.ButterflyModel;
import com.bokmcdok.butterflies.client.model.ButterflyScrollModel;
import com.bokmcdok.butterflies.client.model.CaterpillarModel;
import com.bokmcdok.butterflies.client.model.ChrysalisModel;
import com.bokmcdok.butterflies.client.renderer.entity.*;
import com.bokmcdok.butterflies.world.ButterflySpeciesList;
import com.bokmcdok.butterflies.world.entity.animal.*;
import com.bokmcdok.butterflies.world.entity.decoration.ButterflyScroll;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.SpawnPlacementRegisterEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;


/**
 * This class registers all the entities we use with Forge's Entity Type Registry
 */
@Mod.EventBusSubscriber(modid = ButterfliesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityTypeRegistry {

    /**
     * An instance of a deferred registry we use to register our entity types.
     */
    public static final DeferredRegister<EntityType<?>> INSTANCE =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, ButterfliesMod.MOD_ID);

    /**
     * The Butterfly Scroll entity.
     */
    public static final DeferredHolder<EntityType<?>, EntityType<ButterflyScroll>> BUTTERFLY_SCROLL =
            INSTANCE.register(ButterflyScroll.NAME, () -> EntityType.Builder.of(ButterflyScroll::create, MobCategory.MISC)
                    .sized(1.0f, 1.0f)
                    .build(ButterflyScroll.NAME));

    /**
     * Register the butterflies.
     */
    private static DeferredHolder<EntityType<?>, EntityType<? extends Butterfly>> registerButterfly(int butterflyIndex) {

        String registryId = Butterfly.getRegistryId(butterflyIndex);

        // Ice Butterfly
        if (registryId.equals("ice")) {
            return INSTANCE.register(registryId,
                    () -> EntityType.Builder.of(IceButterfly::new, MobCategory.CREATURE)
                            .sized(0.3f, 0.2f)
                            .build(Butterfly.getRegistryId(butterflyIndex)));
        }

        // Lava Moth
        if (registryId.equals("lava")) {
            return INSTANCE.register(registryId,
                    () -> EntityType.Builder.of(LavaMoth::new, MobCategory.CREATURE)
                            .sized(0.3f, 0.2f)
                            .build(Butterfly.getRegistryId(butterflyIndex)));
        }

        return INSTANCE.register(registryId,
                () -> EntityType.Builder.of(Butterfly::new, MobCategory.CREATURE)
                .sized(0.3f, 0.2f)
                .build(Butterfly.getRegistryId(butterflyIndex)));
    }

    public static final List<DeferredHolder<EntityType<?>, EntityType<? extends Butterfly>>> BUTTERFLY_ENTITIES = new ArrayList<>() {
        {
            for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                add(registerButterfly(i));
            }
        }
    };

    // Register the caterpillars.
    private static DeferredHolder<EntityType<?>, EntityType<Caterpillar>> registerCaterpillar(int butterflyIndex) {
        return INSTANCE.register(Caterpillar.getRegistryId(butterflyIndex),
                () -> EntityType.Builder.of(Caterpillar::new, MobCategory.CREATURE)
                .sized(0.1f, 0.1f)
                .build(Caterpillar.getRegistryId(butterflyIndex)));
    }

    public static final List<DeferredHolder<EntityType<?>, EntityType<Caterpillar>>> CATERPILLAR_ENTITIES = new ArrayList<>() {
        {
            for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                add(registerCaterpillar(i));
            }
        }
    };

    // Register the chrysalises.
    private static DeferredHolder<EntityType<?>, EntityType<Chrysalis>> registerChrysalis(int butterflyIndex) {
        return INSTANCE.register(Chrysalis.getRegistryId(butterflyIndex),
                () -> EntityType.Builder.of(Chrysalis::new, MobCategory.CREATURE)
                .sized(0.1f, 0.1f)
                .build(Chrysalis.getRegistryId(butterflyIndex)));
    }

    public static final List<DeferredHolder<EntityType<?>, EntityType<Chrysalis>>> CHRYSALIS_ENTITIES = new ArrayList<>() {
        {
            for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                add(registerChrysalis(i));
            }
        }
    };

    // Register the butterfly eggs.
    private static DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>> registerButterflyEgg(int butterflyIndex) {
        return INSTANCE.register(ButterflyEgg.getRegistryId(butterflyIndex),
                () -> EntityType.Builder.of(ButterflyEgg::new, MobCategory.CREATURE)
                .sized(0.1f, 0.1f)
                .build(ButterflyEgg.getRegistryId(butterflyIndex)));
    }

    public static final List<DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>>> BUTTERFLY_EGG_ENTITIES = new ArrayList<>() {
        {
            for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                add(registerButterflyEgg(i));
            }
        }
    };

    /**
     * Register the renderers for our entities
     * @param event The event information
     */
    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void registerEntityRenders(final EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(BUTTERFLY_SCROLL.get(), ButterflyScrollRenderer::new);

        for (DeferredHolder<EntityType<?>, EntityType<? extends Butterfly>> i : BUTTERFLY_ENTITIES) {
            if (i.getId().compareTo(new ResourceLocation(ButterfliesMod.MOD_ID, "ice")) == 0 ||
                i.getId().compareTo(new ResourceLocation(ButterfliesMod.MOD_ID, "lava")) == 0) {
                event.registerEntityRenderer(i.get(), GlowButterflyRenderer::new);
            } else {
                event.registerEntityRenderer(i.get(), ButterflyRenderer::new);
            }
        }

        for (DeferredHolder<EntityType<?>, EntityType<Caterpillar>> i : CATERPILLAR_ENTITIES) {
            event.registerEntityRenderer(i.get(), CaterpillarRenderer::new);
        }

        for (DeferredHolder<EntityType<?>, EntityType<Chrysalis>> i : CHRYSALIS_ENTITIES) {
            event.registerEntityRenderer(i.get(), ChrysalisRenderer::new);
        }

        for (DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>> i : BUTTERFLY_EGG_ENTITIES) {
            event.registerEntityRenderer(i.get(), ButterflyEggRenderer::new);
        }
    }

    /**
     * Register the attributes for living entities
     */
    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        for (DeferredHolder<EntityType<?>, EntityType<? extends Butterfly>> i : BUTTERFLY_ENTITIES) {
            event.put(i.get(), Butterfly.createAttributes().build());
        }

        for (DeferredHolder<EntityType<?>, EntityType<Caterpillar>> i : CATERPILLAR_ENTITIES) {
            event.put(i.get(), Caterpillar.createAttributes().build());
        }

        for (DeferredHolder<EntityType<?>, EntityType<Chrysalis>> i : CHRYSALIS_ENTITIES) {
            event.put(i.get(), Chrysalis.createAttributes().build());
        }

        for (DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>> i : BUTTERFLY_EGG_ENTITIES) {
            event.put(i.get(), ButterflyEgg.createAttributes().build());
        }
    }

    /**
     * Register entity spawn placements here
     * @param event The event information
     */
    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void registerEntitySpawnPlacement(SpawnPlacementRegisterEvent event) {

        for (DeferredHolder<EntityType<?>, EntityType<? extends Butterfly>> i : BUTTERFLY_ENTITIES) {
            event.register(i.get(),
                    SpawnPlacements.Type.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING,
                    Butterfly::checkButterflySpawnRules,
                    SpawnPlacementRegisterEvent.Operation.AND);
        }

        for (DeferredHolder<EntityType<?>, EntityType<Caterpillar>> i : CATERPILLAR_ENTITIES) {
            event.register(i.get(),
                    SpawnPlacements.Type.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING,
                    DirectionalCreature::checkDirectionalSpawnRules,
                    SpawnPlacementRegisterEvent.Operation.AND);
        }

        for (DeferredHolder<EntityType<?>, EntityType<Chrysalis>> i : CHRYSALIS_ENTITIES) {
            event.register(i.get(),
                    SpawnPlacements.Type.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING,
                    DirectionalCreature::checkDirectionalSpawnRules,
                    SpawnPlacementRegisterEvent.Operation.AND);
        }

        for (DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>> i : BUTTERFLY_EGG_ENTITIES) {
            event.register(i.get(),
                    SpawnPlacements.Type.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING,
                    DirectionalCreature::checkDirectionalSpawnRules,
                    SpawnPlacementRegisterEvent.Operation.AND);
        }
    }

    /**
     * Registers models to be used for rendering
     * @param event The event information
     */
    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ButterflyModel.LAYER_LOCATION, ButterflyModel::createBodyLayer);
        event.registerLayerDefinition(CaterpillarModel.LAYER_LOCATION, CaterpillarModel::createBodyLayer);
        event.registerLayerDefinition(ChrysalisModel.LAYER_LOCATION, ChrysalisModel::createBodyLayer);
        event.registerLayerDefinition(ButterflyEggModel.LAYER_LOCATION, ButterflyEggModel::createBodyLayer);
        event.registerLayerDefinition(ButterflyScrollModel.LAYER_LOCATION, ButterflyScrollModel::createBodyLayer);
    }
}
