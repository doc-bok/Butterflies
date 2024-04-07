package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.client.model.ButterflyEggModel;
import com.bokmcdok.butterflies.client.model.ButterflyModel;
import com.bokmcdok.butterflies.client.model.ButterflyScrollModel;
import com.bokmcdok.butterflies.client.model.CaterpillarModel;
import com.bokmcdok.butterflies.client.model.ChrysalisModel;
import com.bokmcdok.butterflies.client.renderer.entity.ButterflyEggRenderer;
import com.bokmcdok.butterflies.client.renderer.entity.ButterflyScrollRenderer;
import com.bokmcdok.butterflies.client.renderer.entity.ButterflyRenderer;
import com.bokmcdok.butterflies.client.renderer.entity.CaterpillarRenderer;
import com.bokmcdok.butterflies.client.renderer.entity.ChrysalisRenderer;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import com.bokmcdok.butterflies.world.entity.animal.ButterflyEgg;
import com.bokmcdok.butterflies.world.entity.animal.Caterpillar;
import com.bokmcdok.butterflies.world.entity.animal.Chrysalis;
import com.bokmcdok.butterflies.world.entity.animal.DirectionalCreature;
import com.bokmcdok.butterflies.world.entity.decoration.ButterflyScroll;
import net.minecraft.core.registries.BuiltInRegistries;
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
     * The Butterfly Scroll enitity.
     */
    public static final DeferredHolder<EntityType<?>, EntityType<ButterflyScroll>> BUTTERFLY_SCROLL =
            INSTANCE.register(ButterflyScroll.NAME, () -> EntityType.Builder.of(ButterflyScroll::create, MobCategory.MISC)
                    .sized(1.0f, 1.0f)
                    .build(ButterflyScroll.NAME));

    /**
     * Register the butterflies.
     */
    public static final DeferredHolder<EntityType<?>, EntityType<Butterfly>> BUTTERFLY_MORPHO =
            INSTANCE.register(Butterfly.MORPHO_NAME, () -> EntityType.Builder.of(Butterfly::createMorphoButterfly, MobCategory.CREATURE)
                    .sized(0.3f, 0.4f)
                    .build(Butterfly.MORPHO_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Butterfly>> BUTTERFLY_FORESTER =
            INSTANCE.register(Butterfly.FORESTER_NAME, () -> EntityType.Builder.of(Butterfly::createForesterButterfly, MobCategory.CREATURE)
                    .sized(0.3f, 0.4f)
                    .build(Butterfly.FORESTER_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Butterfly>> BUTTERFLY_COMMON =
            INSTANCE.register(Butterfly.COMMON_NAME, () -> EntityType.Builder.of(Butterfly::createCommonButterfly, MobCategory.CREATURE)
                    .sized(0.3f, 0.4f)
                    .build(Butterfly.COMMON_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Butterfly>> BUTTERFLY_EMPEROR =
            INSTANCE.register(Butterfly.EMPEROR_NAME, () -> EntityType.Builder.of(Butterfly::createEmperorButterfly, MobCategory.CREATURE)
                    .sized(0.3f, 0.4f)
                    .build(Butterfly.EMPEROR_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Butterfly>> BUTTERFLY_HAIRSTREAK =
            INSTANCE.register(Butterfly.HAIRSTREAK_NAME, () -> EntityType.Builder.of(Butterfly::createHairstreakButterfly, MobCategory.CREATURE)
                    .sized(0.3f, 0.4f)
                    .build(Butterfly.HAIRSTREAK_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Butterfly>> BUTTERFLY_RAINBOW =
            INSTANCE.register(Butterfly.RAINBOW_NAME, () -> EntityType.Builder.of(Butterfly::createRainbowButterfly, MobCategory.CREATURE)
                    .sized(0.3f, 0.4f)
                    .build(Butterfly.RAINBOW_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Butterfly>> BUTTERFLY_HEATH =
            INSTANCE.register(Butterfly.HEATH_NAME, () -> EntityType.Builder.of(Butterfly::createHeathButterfly, MobCategory.CREATURE)
                    .sized(0.3f, 0.4f)
                    .build(Butterfly.HEATH_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Butterfly>> BUTTERFLY_GLASSWING =
            INSTANCE.register(Butterfly.GLASSWING_NAME, () -> EntityType.Builder.of(Butterfly::createGlasswingButterfly, MobCategory.CREATURE)
                    .sized(0.3f, 0.4f)
                    .build(Butterfly.GLASSWING_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Butterfly>> BUTTERFLY_CHALKHILL =
            INSTANCE.register(Butterfly.CHALKHILL_NAME, () -> EntityType.Builder.of(Butterfly::createChalkhillButterfly, MobCategory.CREATURE)
                    .sized(0.3f, 0.4f)
                    .build(Butterfly.CHALKHILL_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Butterfly>> BUTTERFLY_SWALLOWTAIL =
            INSTANCE.register(Butterfly.SWALLOWTAIL_NAME, () -> EntityType.Builder.of(Butterfly::createSwallowtailButterfly, MobCategory.CREATURE)
                    .sized(0.3f, 0.4f)
                    .build(Butterfly.SWALLOWTAIL_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Butterfly>> BUTTERFLY_MONARCH =
            INSTANCE.register(Butterfly.MONARCH_NAME, () -> EntityType.Builder.of(Butterfly::createMonarchButterfly, MobCategory.CREATURE)
                    .sized(0.3f, 0.4f)
                    .build(Butterfly.MONARCH_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Butterfly>> BUTTERFLY_CABBAGE =
            INSTANCE.register(Butterfly.CABBAGE_NAME, () -> EntityType.Builder.of(Butterfly::createCabbageButterfly, MobCategory.CREATURE)
                    .sized(0.3f, 0.4f)
                    .build(Butterfly.CABBAGE_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Butterfly>> BUTTERFLY_ADMIRAL =
            INSTANCE.register(Butterfly.ADMIRAL_NAME, () -> EntityType.Builder.of(Butterfly::createAdmiralButterfly, MobCategory.CREATURE)
                    .sized(0.3f, 0.4f)
                    .build(Butterfly.ADMIRAL_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Butterfly>> BUTTERFLY_LONGWING =
            INSTANCE.register(Butterfly.LONGWING_NAME, () -> EntityType.Builder.of(Butterfly::createLongwingButterfly, MobCategory.CREATURE)
                    .sized(0.3f, 0.4f)
                    .build(Butterfly.LONGWING_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Butterfly>> BUTTERFLY_CLIPPER =
            INSTANCE.register(Butterfly.CLIPPER_NAME, () -> EntityType.Builder.of(Butterfly::createClipperButterfly, MobCategory.CREATURE)
                    .sized(0.3f, 0.4f)
                    .build(Butterfly.CLIPPER_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Butterfly>> BUTTERFLY_BUCKEYE =
            INSTANCE.register(Butterfly.BUCKEYE_NAME, () -> EntityType.Builder.of(Butterfly::createBuckeyeButterfly, MobCategory.CREATURE)
                    .sized(0.3f, 0.4f)
                    .build(Butterfly.BUCKEYE_NAME));

    // Register the caterpillars.
    public static final DeferredHolder<EntityType<?>, EntityType<Caterpillar>> CATERPILLAR_MORPHO =
            INSTANCE.register(Caterpillar.MORPHO_NAME, () -> EntityType.Builder.of(Caterpillar::createMorphoCaterpillar, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(Caterpillar.MORPHO_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Caterpillar>> CATERPILLAR_FORESTER =
            INSTANCE.register(Caterpillar.FORESTER_NAME, () -> EntityType.Builder.of(Caterpillar::createForesterCaterpillar, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(Caterpillar.FORESTER_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Caterpillar>> CATERPILLAR_COMMON =
            INSTANCE.register(Caterpillar.COMMON_NAME, () -> EntityType.Builder.of(Caterpillar::createCommonCaterpillar, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(Caterpillar.COMMON_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Caterpillar>> CATERPILLAR_EMPEROR =
            INSTANCE.register(Caterpillar.EMPEROR_NAME, () -> EntityType.Builder.of(Caterpillar::createEmperorCaterpillar, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(Caterpillar.EMPEROR_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Caterpillar>> CATERPILLAR_HAIRSTREAK =
            INSTANCE.register(Caterpillar.HAIRSTREAK_NAME, () -> EntityType.Builder.of(Caterpillar::createHairstreakCaterpillar, MobCategory.AMBIENT)
                    .sized(0.1f, 0.1f)
                    .build(Caterpillar.HAIRSTREAK_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Caterpillar>> CATERPILLAR_RAINBOW =
            INSTANCE.register(Caterpillar.RAINBOW_NAME, () -> EntityType.Builder.of(Caterpillar::createRainbowCaterpillar, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(Caterpillar.RAINBOW_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Caterpillar>> CATERPILLAR_HEATH =
            INSTANCE.register(Caterpillar.HEATH_NAME, () -> EntityType.Builder.of(Caterpillar::createHeathCaterpillar, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(Caterpillar.HEATH_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Caterpillar>> CATERPILLAR_GLASSWING =
            INSTANCE.register(Caterpillar.GLASSWING_NAME, () -> EntityType.Builder.of(Caterpillar::createGlasswingCaterpillar, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(Caterpillar.GLASSWING_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Caterpillar>> CATERPILLAR_CHALKHILL =
            INSTANCE.register(Caterpillar.CHALKHILL_NAME, () -> EntityType.Builder.of(Caterpillar::createChalkhillCaterpillar, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(Caterpillar.CHALKHILL_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Caterpillar>> CATERPILLAR_SWALLOWTAIL =
            INSTANCE.register(Caterpillar.SWALLOWTAIL_NAME, () -> EntityType.Builder.of(Caterpillar::createSwallowtailCaterpillar, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(Caterpillar.SWALLOWTAIL_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Caterpillar>> CATERPILLAR_MONARCH =
            INSTANCE.register(Caterpillar.MONARCH_NAME, () -> EntityType.Builder.of(Caterpillar::createMonarchCaterpillar, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(Caterpillar.MONARCH_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Caterpillar>> CATERPILLAR_CABBAGE =
            INSTANCE.register(Caterpillar.CABBAGE_NAME, () -> EntityType.Builder.of(Caterpillar::createCabbageCaterpillar, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(Caterpillar.CABBAGE_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Caterpillar>> CATERPILLAR_ADMIRAL =
            INSTANCE.register(Caterpillar.ADMIRAL_NAME, () -> EntityType.Builder.of(Caterpillar::createAdmiralCaterpillar, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(Caterpillar.ADMIRAL_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Caterpillar>> CATERPILLAR_LONGWING =
            INSTANCE.register(Caterpillar.LONGWING_NAME, () -> EntityType.Builder.of(Caterpillar::createLongwingCaterpillar, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(Caterpillar.LONGWING_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Caterpillar>> CATERPILLAR_CLIPPER =
            INSTANCE.register(Caterpillar.CLIPPER_NAME, () -> EntityType.Builder.of(Caterpillar::createClipperCaterpillar, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(Caterpillar.CLIPPER_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Caterpillar>> CATERPILLAR_BUCKEYE =
            INSTANCE.register(Caterpillar.BUCKEYE_NAME, () -> EntityType.Builder.of(Caterpillar::createBuckeyeCaterpillar, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(Caterpillar.BUCKEYE_NAME));

    // Register the chrysalises.
    public static final DeferredHolder<EntityType<?>, EntityType<Chrysalis>> CHRYSALIS_MORPHO =
            INSTANCE.register(Chrysalis.MORPHO_NAME, () -> EntityType.Builder.of(Chrysalis::createMorpho, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(Chrysalis.MORPHO_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Chrysalis>> CHRYSALIS_FORESTER =
            INSTANCE.register(Chrysalis.FORESTER_NAME, () -> EntityType.Builder.of(Chrysalis::createForester, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(Chrysalis.FORESTER_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Chrysalis>> CHRYSALIS_COMMON =
            INSTANCE.register(Chrysalis.COMMON_NAME, () -> EntityType.Builder.of(Chrysalis::createCommon, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(Chrysalis.COMMON_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Chrysalis>> CHRYSALIS_EMPEROR =
            INSTANCE.register(Chrysalis.EMPEROR_NAME, () -> EntityType.Builder.of(Chrysalis::createEmperor, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(Chrysalis.EMPEROR_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Chrysalis>> CHRYSALIS_HAIRSTREAK =
            INSTANCE.register(Chrysalis.HAIRSTREAK_NAME, () -> EntityType.Builder.of(Chrysalis::createHairstreak, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(Chrysalis.HAIRSTREAK_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Chrysalis>> CHRYSALIS_RAINBOW =
            INSTANCE.register(Chrysalis.RAINBOW_NAME, () -> EntityType.Builder.of(Chrysalis::createRainbow, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(Chrysalis.RAINBOW_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Chrysalis>> CHRYSALIS_HEATH =
            INSTANCE.register(Chrysalis.HEATH_NAME, () -> EntityType.Builder.of(Chrysalis::createHeath, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(Chrysalis.HEATH_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Chrysalis>> CHRYSALIS_GLASSWING =
            INSTANCE.register(Chrysalis.GLASSWING_NAME, () -> EntityType.Builder.of(Chrysalis::createGlasswing, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(Chrysalis.GLASSWING_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Chrysalis>> CHRYSALIS_CHALKHILL =
            INSTANCE.register(Chrysalis.CHALKHILL_NAME, () -> EntityType.Builder.of(Chrysalis::createChalkhill, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(Chrysalis.CHALKHILL_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Chrysalis>> CHRYSALIS_SWALLOWTAIL =
            INSTANCE.register(Chrysalis.SWALLOWTAIL_NAME, () -> EntityType.Builder.of(Chrysalis::createSwallowtail, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(Chrysalis.SWALLOWTAIL_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Chrysalis>> CHRYSALIS_MONARCH =
            INSTANCE.register(Chrysalis.MONARCH_NAME, () -> EntityType.Builder.of(Chrysalis::createMonarch, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(Chrysalis.MONARCH_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Chrysalis>> CHRYSALIS_CABBAGE =
            INSTANCE.register(Chrysalis.CABBAGE_NAME, () -> EntityType.Builder.of(Chrysalis::createCabbage, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(Chrysalis.CABBAGE_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Chrysalis>> CHRYSALIS_ADMIRAL =
            INSTANCE.register(Chrysalis.ADMIRAL_NAME, () -> EntityType.Builder.of(Chrysalis::createAdmiral, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(Chrysalis.ADMIRAL_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Chrysalis>> CHRYSALIS_LONGWING =
            INSTANCE.register(Chrysalis.LONGWING_NAME, () -> EntityType.Builder.of(Chrysalis::createLongwing, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(Chrysalis.LONGWING_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Chrysalis>> CHRYSALIS_CLIPPER =
            INSTANCE.register(Chrysalis.CLIPPER_NAME, () -> EntityType.Builder.of(Chrysalis::createClipper, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(Chrysalis.CLIPPER_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<Chrysalis>> CHRYSALIS_BUCKEYE =
            INSTANCE.register(Chrysalis.BUCKEYE_NAME, () -> EntityType.Builder.of(Chrysalis::createBuckeye, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(Chrysalis.BUCKEYE_NAME));

    // Register the butterfly eggs.
    public static final DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>> BUTTERFLY_EGG_MORPHO =
            INSTANCE.register(ButterflyEgg.MORPHO_NAME, () -> EntityType.Builder.of(ButterflyEgg::createMorpho, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(ButterflyEgg.MORPHO_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>> BUTTERFLY_EGG_FORESTER =
            INSTANCE.register(ButterflyEgg.FORESTER_NAME, () -> EntityType.Builder.of(ButterflyEgg::createForester, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(ButterflyEgg.FORESTER_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>> BUTTERFLY_EGG_COMMON =
            INSTANCE.register(ButterflyEgg.COMMON_NAME, () -> EntityType.Builder.of(ButterflyEgg::createCommon, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(ButterflyEgg.COMMON_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>> BUTTERFLY_EGG_EMPEROR =
            INSTANCE.register(ButterflyEgg.EMPEROR_NAME, () -> EntityType.Builder.of(ButterflyEgg::createEmperor, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(ButterflyEgg.EMPEROR_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>> BUTTERFLY_EGG_HAIRSTREAK =
            INSTANCE.register(ButterflyEgg.HAIRSTREAK_NAME, () -> EntityType.Builder.of(ButterflyEgg::createHairstreak, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(ButterflyEgg.HAIRSTREAK_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>> BUTTERFLY_EGG_RAINBOW =
            INSTANCE.register(ButterflyEgg.RAINBOW_NAME, () -> EntityType.Builder.of(ButterflyEgg::createRainbow, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(ButterflyEgg.RAINBOW_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>> BUTTERFLY_EGG_HEATH =
            INSTANCE.register(ButterflyEgg.HEATH_NAME, () -> EntityType.Builder.of(ButterflyEgg::createHeath, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(ButterflyEgg.HEATH_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>> BUTTERFLY_EGG_GLASSWING =
            INSTANCE.register(ButterflyEgg.GLASSWING_NAME, () -> EntityType.Builder.of(ButterflyEgg::createGlasswing, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(ButterflyEgg.GLASSWING_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>> BUTTERFLY_EGG_CHALKHILL =
            INSTANCE.register(ButterflyEgg.CHALKHILL_NAME, () -> EntityType.Builder.of(ButterflyEgg::createChalkhill, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(ButterflyEgg.CHALKHILL_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>> BUTTERFLY_EGG_SWALLOWTAIL =
            INSTANCE.register(ButterflyEgg.SWALLOWTAIL_NAME, () -> EntityType.Builder.of(ButterflyEgg::createSwallowtail, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(ButterflyEgg.SWALLOWTAIL_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>> BUTTERFLY_EGG_MONARCH =
            INSTANCE.register(ButterflyEgg.MONARCH_NAME, () -> EntityType.Builder.of(ButterflyEgg::createMonarch, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(ButterflyEgg.MONARCH_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>> BUTTERFLY_EGG_CABBAGE =
            INSTANCE.register(ButterflyEgg.CABBAGE_NAME, () -> EntityType.Builder.of(ButterflyEgg::createCabbage, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(ButterflyEgg.CABBAGE_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>> BUTTERFLY_EGG_ADMIRAL =
            INSTANCE.register(ButterflyEgg.ADMIRAL_NAME, () -> EntityType.Builder.of(ButterflyEgg::createAdmiral, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(ButterflyEgg.ADMIRAL_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>> BUTTERFLY_EGG_LONGWING =
            INSTANCE.register(ButterflyEgg.LONGWING_NAME, () -> EntityType.Builder.of(ButterflyEgg::createLongwing, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(ButterflyEgg.LONGWING_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>> BUTTERFLY_EGG_CLIPPER =
            INSTANCE.register(ButterflyEgg.CLIPPER_NAME, () -> EntityType.Builder.of(ButterflyEgg::createClipper, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(ButterflyEgg.CLIPPER_NAME));
    public static final DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>> BUTTERFLY_EGG_BUCKEYE =
            INSTANCE.register(ButterflyEgg.BUCKEYE_NAME, () -> EntityType.Builder.of(ButterflyEgg::createBuckeye, MobCategory.CREATURE)
                    .sized(0.1f, 0.1f)
                    .build(ButterflyEgg.BUCKEYE_NAME));

    /**
     * Register the renderers for our entities
     * @param event The event information
     */
    @SubscribeEvent
    public static void registerEntityRenders(final EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(BUTTERFLY_SCROLL.get(), ButterflyScrollRenderer::new);

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
        event.registerEntityRenderer(BUTTERFLY_ADMIRAL.get(), ButterflyRenderer::new);
        event.registerEntityRenderer(BUTTERFLY_LONGWING.get(), ButterflyRenderer::new);
        event.registerEntityRenderer(BUTTERFLY_CLIPPER.get(), ButterflyRenderer::new);
        event.registerEntityRenderer(BUTTERFLY_BUCKEYE.get(), ButterflyRenderer::new);

        event.registerEntityRenderer(CATERPILLAR_MORPHO.get(), CaterpillarRenderer::new);
        event.registerEntityRenderer(CATERPILLAR_COMMON.get(), CaterpillarRenderer::new);
        event.registerEntityRenderer(CATERPILLAR_FORESTER.get(), CaterpillarRenderer::new);
        event.registerEntityRenderer(CATERPILLAR_EMPEROR.get(), CaterpillarRenderer::new);
        event.registerEntityRenderer(CATERPILLAR_HAIRSTREAK.get(), CaterpillarRenderer::new);
        event.registerEntityRenderer(CATERPILLAR_RAINBOW.get(), CaterpillarRenderer::new);
        event.registerEntityRenderer(CATERPILLAR_HEATH.get(), CaterpillarRenderer::new);
        event.registerEntityRenderer(CATERPILLAR_GLASSWING.get(), CaterpillarRenderer::new);
        event.registerEntityRenderer(CATERPILLAR_CHALKHILL.get(), CaterpillarRenderer::new);
        event.registerEntityRenderer(CATERPILLAR_SWALLOWTAIL.get(), CaterpillarRenderer::new);
        event.registerEntityRenderer(CATERPILLAR_MONARCH.get(), CaterpillarRenderer::new);
        event.registerEntityRenderer(CATERPILLAR_CABBAGE.get(), CaterpillarRenderer::new);
        event.registerEntityRenderer(CATERPILLAR_ADMIRAL.get(), CaterpillarRenderer::new);
        event.registerEntityRenderer(CATERPILLAR_LONGWING.get(), CaterpillarRenderer::new);
        event.registerEntityRenderer(CATERPILLAR_CLIPPER.get(), CaterpillarRenderer::new);
        event.registerEntityRenderer(CATERPILLAR_BUCKEYE.get(), CaterpillarRenderer::new);

        event.registerEntityRenderer(CHRYSALIS_MORPHO.get(), ChrysalisRenderer::new);
        event.registerEntityRenderer(CHRYSALIS_COMMON.get(), ChrysalisRenderer::new);
        event.registerEntityRenderer(CHRYSALIS_FORESTER.get(), ChrysalisRenderer::new);
        event.registerEntityRenderer(CHRYSALIS_EMPEROR.get(), ChrysalisRenderer::new);
        event.registerEntityRenderer(CHRYSALIS_HAIRSTREAK.get(), ChrysalisRenderer::new);
        event.registerEntityRenderer(CHRYSALIS_RAINBOW.get(), ChrysalisRenderer::new);
        event.registerEntityRenderer(CHRYSALIS_HEATH.get(), ChrysalisRenderer::new);
        event.registerEntityRenderer(CHRYSALIS_GLASSWING.get(), ChrysalisRenderer::new);
        event.registerEntityRenderer(CHRYSALIS_CHALKHILL.get(), ChrysalisRenderer::new);
        event.registerEntityRenderer(CHRYSALIS_SWALLOWTAIL.get(), ChrysalisRenderer::new);
        event.registerEntityRenderer(CHRYSALIS_MONARCH.get(), ChrysalisRenderer::new);
        event.registerEntityRenderer(CHRYSALIS_CABBAGE.get(), ChrysalisRenderer::new);
        event.registerEntityRenderer(CHRYSALIS_ADMIRAL.get(), ChrysalisRenderer::new);
        event.registerEntityRenderer(CHRYSALIS_LONGWING.get(), ChrysalisRenderer::new);
        event.registerEntityRenderer(CHRYSALIS_CLIPPER.get(), ChrysalisRenderer::new);
        event.registerEntityRenderer(CHRYSALIS_BUCKEYE.get(), ChrysalisRenderer::new);

        event.registerEntityRenderer(BUTTERFLY_EGG_MORPHO.get(), ButterflyEggRenderer::new);
        event.registerEntityRenderer(BUTTERFLY_EGG_COMMON.get(), ButterflyEggRenderer::new);
        event.registerEntityRenderer(BUTTERFLY_EGG_FORESTER.get(), ButterflyEggRenderer::new);
        event.registerEntityRenderer(BUTTERFLY_EGG_EMPEROR.get(), ButterflyEggRenderer::new);
        event.registerEntityRenderer(BUTTERFLY_EGG_HAIRSTREAK.get(), ButterflyEggRenderer::new);
        event.registerEntityRenderer(BUTTERFLY_EGG_RAINBOW.get(), ButterflyEggRenderer::new);
        event.registerEntityRenderer(BUTTERFLY_EGG_HEATH.get(), ButterflyEggRenderer::new);
        event.registerEntityRenderer(BUTTERFLY_EGG_GLASSWING.get(), ButterflyEggRenderer::new);
        event.registerEntityRenderer(BUTTERFLY_EGG_CHALKHILL.get(), ButterflyEggRenderer::new);
        event.registerEntityRenderer(BUTTERFLY_EGG_SWALLOWTAIL.get(), ButterflyEggRenderer::new);
        event.registerEntityRenderer(BUTTERFLY_EGG_MONARCH.get(), ButterflyEggRenderer::new);
        event.registerEntityRenderer(BUTTERFLY_EGG_CABBAGE.get(), ButterflyEggRenderer::new);
        event.registerEntityRenderer(BUTTERFLY_EGG_ADMIRAL.get(), ButterflyEggRenderer::new);
        event.registerEntityRenderer(BUTTERFLY_EGG_LONGWING.get(), ButterflyEggRenderer::new);
        event.registerEntityRenderer(BUTTERFLY_EGG_CLIPPER.get(), ButterflyEggRenderer::new);
        event.registerEntityRenderer(BUTTERFLY_EGG_BUCKEYE.get(), ButterflyEggRenderer::new);
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
        event.put(BUTTERFLY_ADMIRAL.get(), Butterfly.createAttributes().build());
        event.put(BUTTERFLY_LONGWING.get(), Butterfly.createAttributes().build());
        event.put(BUTTERFLY_CLIPPER.get(), Butterfly.createAttributes().build());
        event.put(BUTTERFLY_BUCKEYE.get(), Butterfly.createAttributes().build());

        event.put(CATERPILLAR_MORPHO.get(), Caterpillar.createAttributes().build());
        event.put(CATERPILLAR_COMMON.get(), Caterpillar.createAttributes().build());
        event.put(CATERPILLAR_FORESTER.get(), Caterpillar.createAttributes().build());
        event.put(CATERPILLAR_EMPEROR.get(), Caterpillar.createAttributes().build());
        event.put(CATERPILLAR_HAIRSTREAK.get(), Caterpillar.createAttributes().build());
        event.put(CATERPILLAR_RAINBOW.get(), Caterpillar.createAttributes().build());
        event.put(CATERPILLAR_HEATH.get(), Caterpillar.createAttributes().build());
        event.put(CATERPILLAR_GLASSWING.get(), Caterpillar.createAttributes().build());
        event.put(CATERPILLAR_CHALKHILL.get(), Caterpillar.createAttributes().build());
        event.put(CATERPILLAR_SWALLOWTAIL.get(), Caterpillar.createAttributes().build());
        event.put(CATERPILLAR_MONARCH.get(), Caterpillar.createAttributes().build());
        event.put(CATERPILLAR_CABBAGE.get(), Caterpillar.createAttributes().build());
        event.put(CATERPILLAR_ADMIRAL.get(), Caterpillar.createAttributes().build());
        event.put(CATERPILLAR_LONGWING.get(), Caterpillar.createAttributes().build());
        event.put(CATERPILLAR_CLIPPER.get(), Caterpillar.createAttributes().build());
        event.put(CATERPILLAR_BUCKEYE.get(), Caterpillar.createAttributes().build());

        event.put(CHRYSALIS_MORPHO.get(), Chrysalis.createAttributes().build());
        event.put(CHRYSALIS_COMMON.get(), Chrysalis.createAttributes().build());
        event.put(CHRYSALIS_FORESTER.get(), Chrysalis.createAttributes().build());
        event.put(CHRYSALIS_EMPEROR.get(), Chrysalis.createAttributes().build());
        event.put(CHRYSALIS_HAIRSTREAK.get(), Chrysalis.createAttributes().build());
        event.put(CHRYSALIS_RAINBOW.get(), Chrysalis.createAttributes().build());
        event.put(CHRYSALIS_HEATH.get(), Chrysalis.createAttributes().build());
        event.put(CHRYSALIS_GLASSWING.get(), Chrysalis.createAttributes().build());
        event.put(CHRYSALIS_CHALKHILL.get(), Chrysalis.createAttributes().build());
        event.put(CHRYSALIS_SWALLOWTAIL.get(), Chrysalis.createAttributes().build());
        event.put(CHRYSALIS_MONARCH.get(), Chrysalis.createAttributes().build());
        event.put(CHRYSALIS_CABBAGE.get(), Chrysalis.createAttributes().build());
        event.put(CHRYSALIS_ADMIRAL.get(), Chrysalis.createAttributes().build());
        event.put(CHRYSALIS_LONGWING.get(), Chrysalis.createAttributes().build());
        event.put(CHRYSALIS_CLIPPER.get(), Chrysalis.createAttributes().build());
        event.put(CHRYSALIS_BUCKEYE.get(), Chrysalis.createAttributes().build());

        event.put(BUTTERFLY_EGG_MORPHO.get(), ButterflyEgg.createAttributes().build());
        event.put(BUTTERFLY_EGG_COMMON.get(), ButterflyEgg.createAttributes().build());
        event.put(BUTTERFLY_EGG_FORESTER.get(), ButterflyEgg.createAttributes().build());
        event.put(BUTTERFLY_EGG_EMPEROR.get(), ButterflyEgg.createAttributes().build());
        event.put(BUTTERFLY_EGG_HAIRSTREAK.get(), ButterflyEgg.createAttributes().build());
        event.put(BUTTERFLY_EGG_RAINBOW.get(), ButterflyEgg.createAttributes().build());
        event.put(BUTTERFLY_EGG_HEATH.get(), ButterflyEgg.createAttributes().build());
        event.put(BUTTERFLY_EGG_GLASSWING.get(), ButterflyEgg.createAttributes().build());
        event.put(BUTTERFLY_EGG_CHALKHILL.get(), ButterflyEgg.createAttributes().build());
        event.put(BUTTERFLY_EGG_SWALLOWTAIL.get(), ButterflyEgg.createAttributes().build());
        event.put(BUTTERFLY_EGG_MONARCH.get(), ButterflyEgg.createAttributes().build());
        event.put(BUTTERFLY_EGG_CABBAGE.get(), ButterflyEgg.createAttributes().build());
        event.put(BUTTERFLY_EGG_ADMIRAL.get(), ButterflyEgg.createAttributes().build());
        event.put(BUTTERFLY_EGG_LONGWING.get(), ButterflyEgg.createAttributes().build());
        event.put(BUTTERFLY_EGG_CLIPPER.get(), ButterflyEgg.createAttributes().build());
        event.put(BUTTERFLY_EGG_BUCKEYE.get(), ButterflyEgg.createAttributes().build());
    }

    /**
     * Register entity spawn placements here
     * @param event The event information
     */
    @SubscribeEvent
    public static void registerEntitySpawnPlacement(SpawnPlacementRegisterEvent event) {
        event.register(BUTTERFLY_MORPHO.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                Butterfly::checkButterflySpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_COMMON.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                Butterfly::checkButterflySpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_FORESTER.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                Butterfly::checkButterflySpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_EMPEROR.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                Butterfly::checkButterflySpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_HAIRSTREAK.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                Butterfly::checkButterflySpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_RAINBOW.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                Butterfly::checkButterflySpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_HEATH.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                Butterfly::checkButterflySpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_GLASSWING.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                Butterfly::checkButterflySpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_CHALKHILL.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                Butterfly::checkButterflySpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_SWALLOWTAIL.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                Butterfly::checkButterflySpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_MONARCH.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                Butterfly::checkButterflySpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_CABBAGE.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                Butterfly::checkButterflySpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_ADMIRAL.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                Butterfly::checkButterflySpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_LONGWING.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                Butterfly::checkButterflySpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_CLIPPER.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                Butterfly::checkButterflySpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_BUCKEYE.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                Butterfly::checkButterflySpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CATERPILLAR_MORPHO.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                 DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CATERPILLAR_COMMON.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                 DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CATERPILLAR_FORESTER.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                 DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CATERPILLAR_EMPEROR.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                 DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CATERPILLAR_HAIRSTREAK.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                 DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CATERPILLAR_RAINBOW.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                 DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CATERPILLAR_HEATH.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                 DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CATERPILLAR_GLASSWING.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                 DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CATERPILLAR_CHALKHILL.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                 DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CATERPILLAR_SWALLOWTAIL.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                 DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CATERPILLAR_MONARCH.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                 DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CATERPILLAR_CABBAGE.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                 DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CATERPILLAR_ADMIRAL.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                 DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CATERPILLAR_LONGWING.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                 DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CATERPILLAR_CLIPPER.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                 DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CATERPILLAR_BUCKEYE.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                 DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CHRYSALIS_MORPHO.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CHRYSALIS_COMMON.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CHRYSALIS_FORESTER.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CHRYSALIS_EMPEROR.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CHRYSALIS_HAIRSTREAK.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CHRYSALIS_RAINBOW.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CHRYSALIS_HEATH.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CHRYSALIS_GLASSWING.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CHRYSALIS_CHALKHILL.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CHRYSALIS_SWALLOWTAIL.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CHRYSALIS_MONARCH.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CHRYSALIS_CABBAGE.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CHRYSALIS_ADMIRAL.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CHRYSALIS_LONGWING.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CHRYSALIS_CLIPPER.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(CHRYSALIS_BUCKEYE.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_EGG_MORPHO.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_EGG_COMMON.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_EGG_FORESTER.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_EGG_EMPEROR.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_EGG_HAIRSTREAK.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_EGG_RAINBOW.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_EGG_HEATH.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_EGG_GLASSWING.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_EGG_CHALKHILL.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_EGG_SWALLOWTAIL.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_EGG_MONARCH.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_EGG_CABBAGE.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_EGG_ADMIRAL.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_EGG_LONGWING.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_EGG_CLIPPER.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(BUTTERFLY_EGG_BUCKEYE.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING,
                DirectionalCreature::checkDirectionalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);
    }

    /**
     * Registers models to be used for rendering
     * @param event The event information
     */
    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ButterflyModel.LAYER_LOCATION, ButterflyModel::createBodyLayer);
        event.registerLayerDefinition(CaterpillarModel.LAYER_LOCATION, CaterpillarModel::createBodyLayer);
        event.registerLayerDefinition(ChrysalisModel.LAYER_LOCATION, ChrysalisModel::createBodyLayer);
        event.registerLayerDefinition(ButterflyEggModel.LAYER_LOCATION, ButterflyEggModel::createBodyLayer);
        event.registerLayerDefinition(ButterflyScrollModel.LAYER_LOCATION, ButterflyScrollModel::createBodyLayer);
    }
}
