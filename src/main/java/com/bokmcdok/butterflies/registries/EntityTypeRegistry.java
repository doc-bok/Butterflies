package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.client.model.ButterflyEggModel;
import com.bokmcdok.butterflies.client.model.ButterflyModel;
import com.bokmcdok.butterflies.client.model.ButterflyScrollModel;
import com.bokmcdok.butterflies.client.model.CaterpillarModel;
import com.bokmcdok.butterflies.client.model.ChrysalisModel;
import com.bokmcdok.butterflies.client.renderer.entity.*;
import com.bokmcdok.butterflies.world.ButterflySpeciesList;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import com.bokmcdok.butterflies.world.entity.animal.IceButterfly;
import com.bokmcdok.butterflies.world.entity.animal.ButterflyEgg;
import com.bokmcdok.butterflies.world.entity.animal.Caterpillar;
import com.bokmcdok.butterflies.world.entity.animal.Chrysalis;
import com.bokmcdok.butterflies.world.entity.animal.DirectionalCreature;
import com.bokmcdok.butterflies.world.entity.decoration.ButterflyScroll;
import net.minecraft.resources.ResourceLocation;
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

import java.util.ArrayList;
import java.util.List;


/**
 * This class registers all the entities we use with Forge's Entity Type Registry
 */
@Mod.EventBusSubscriber(modid = ButterfliesMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityTypeRegistry {

    /**
     * An instance of a deferred registry we use to register our entity types.
     */
    public static final DeferredRegister<EntityType<?>> INSTANCE =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ButterfliesMod.MODID);

    /**
     * The Butterfly Scroll entity.
     */
    public static final RegistryObject<EntityType<ButterflyScroll>> BUTTERFLY_SCROLL =
            INSTANCE.register(ButterflyScroll.NAME, () -> EntityType.Builder.of(ButterflyScroll::create, MobCategory.MISC)
                    .sized(1.0f, 1.0f)
                    .build(ButterflyScroll.NAME));

    /**
     * Register the butterflies.
     */
    private static RegistryObject<EntityType<? extends Butterfly>> registerButterfly(int butterflyIndex) {

        // Ice Butterfly
        if (butterflyIndex == 17) {
            return INSTANCE.register(Butterfly.getRegistryId(butterflyIndex),
                    () -> EntityType.Builder.of(IceButterfly::new, MobCategory.CREATURE)
                            .sized(0.4f, 0.3f)
                            .build(Butterfly.getRegistryId(butterflyIndex)));
        }

        return INSTANCE.register(Butterfly.getRegistryId(butterflyIndex),
                () -> EntityType.Builder.of(Butterfly::new, MobCategory.CREATURE)
                .sized(0.4f, 0.3f)
                .build(Butterfly.getRegistryId(butterflyIndex)));
    }

    public static final List<RegistryObject<EntityType<? extends Butterfly>>> BUTTERFLY_ENTITIES = new ArrayList<>() {
        {
            for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                add(registerButterfly(i));
            }
        }
    };

    // Register the caterpillars.
    private static RegistryObject<EntityType<Caterpillar>> registerCaterpillar(int butterflyIndex) {
        return INSTANCE.register(Caterpillar.getRegistryId(butterflyIndex),
                () -> EntityType.Builder.of(Caterpillar::new, MobCategory.CREATURE)
                .sized(0.1f, 0.1f)
                .build(Caterpillar.getRegistryId(butterflyIndex)));
    }

    public static final List<RegistryObject<EntityType<Caterpillar>>> CATERPILLAR_ENTITIES = new ArrayList<>() {
        {
            for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                add(registerCaterpillar(i));
            }
        }
    };

    // Register the chrysalises.
    private static RegistryObject<EntityType<Chrysalis>> registerChrysalis(int butterflyIndex) {
        return INSTANCE.register(Chrysalis.getRegistryId(butterflyIndex),
                () -> EntityType.Builder.of(Chrysalis::new, MobCategory.CREATURE)
                .sized(0.1f, 0.1f)
                .build(Chrysalis.getRegistryId(butterflyIndex)));
    }

    public static final List<RegistryObject<EntityType<Chrysalis>>> CHRYSALIS_ENTITIES = new ArrayList<>() {
        {
            for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                add(registerChrysalis(i));
            }
        }
    };

    // Register the butterfly eggs.
    private static RegistryObject<EntityType<ButterflyEgg>> registerButterflyEgg(int butterflyIndex) {
        return INSTANCE.register(ButterflyEgg.getRegistryId(butterflyIndex),
                () -> EntityType.Builder.of(ButterflyEgg::new, MobCategory.CREATURE)
                .sized(0.1f, 0.1f)
                .build(ButterflyEgg.getRegistryId(butterflyIndex)));
    }

    public static final List<RegistryObject<EntityType<ButterflyEgg>>> BUTTERFLY_EGG_ENTITIES = new ArrayList<>() {
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

        for (RegistryObject<EntityType<? extends Butterfly>> i : BUTTERFLY_ENTITIES) {
            if (i.getId().compareTo(new ResourceLocation(ButterfliesMod.MODID, "ice")) == 0) {
                event.registerEntityRenderer(i.get(), IceButterflyRenderer::new);
            } else {
                event.registerEntityRenderer(i.get(), ButterflyRenderer::new);
            }
        }

        for (RegistryObject<EntityType<Caterpillar>> i : CATERPILLAR_ENTITIES) {
            event.registerEntityRenderer(i.get(), CaterpillarRenderer::new);
        }

        for (RegistryObject<EntityType<Chrysalis>> i : CHRYSALIS_ENTITIES) {
            event.registerEntityRenderer(i.get(), ChrysalisRenderer::new);
        }

        for (RegistryObject<EntityType<ButterflyEgg>> i : BUTTERFLY_EGG_ENTITIES) {
            event.registerEntityRenderer(i.get(), ButterflyEggRenderer::new);
        }
    }

    /**
     * Register the attributes for living entities
     */
    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        for (RegistryObject<EntityType<? extends Butterfly>> i : BUTTERFLY_ENTITIES) {
            event.put(i.get(), Butterfly.createAttributes().build());
        }

        for (RegistryObject<EntityType<Caterpillar>> i : CATERPILLAR_ENTITIES) {
            event.put(i.get(), Caterpillar.createAttributes().build());
        }

        for (RegistryObject<EntityType<Chrysalis>> i : CHRYSALIS_ENTITIES) {
            event.put(i.get(), Chrysalis.createAttributes().build());
        }

        for (RegistryObject<EntityType<ButterflyEgg>> i : BUTTERFLY_EGG_ENTITIES) {
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

        for (RegistryObject<EntityType<? extends Butterfly>> i : BUTTERFLY_ENTITIES) {
            event.register(i.get(),
                    SpawnPlacements.Type.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING,
                    Butterfly::checkButterflySpawnRules,
                    SpawnPlacementRegisterEvent.Operation.AND);
        }

        for (RegistryObject<EntityType<Caterpillar>> i : CATERPILLAR_ENTITIES) {
            event.register(i.get(),
                    SpawnPlacements.Type.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING,
                    DirectionalCreature::checkDirectionalSpawnRules,
                    SpawnPlacementRegisterEvent.Operation.AND);
        }

        for (RegistryObject<EntityType<Chrysalis>> i : CHRYSALIS_ENTITIES) {
            event.register(i.get(),
                    SpawnPlacements.Type.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING,
                    DirectionalCreature::checkDirectionalSpawnRules,
                    SpawnPlacementRegisterEvent.Operation.AND);
        }

        for (RegistryObject<EntityType<ButterflyEgg>> i : BUTTERFLY_EGG_ENTITIES) {
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
