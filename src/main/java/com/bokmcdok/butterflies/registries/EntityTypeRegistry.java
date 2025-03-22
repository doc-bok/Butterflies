package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflySpeciesList;
import com.bokmcdok.butterflies.world.entity.animal.*;
import com.bokmcdok.butterflies.world.entity.decoration.ButterflyScroll;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;


/**
 * This class registers all the entities we use with Forge's Entity Type Registry
 */
public class EntityTypeRegistry {

    // An instance of a deferred registry we use to register our entity types.
    private final DeferredRegister<EntityType<?>> deferredRegister;

    // The block registry.
    private BlockRegistry blockRegistry;

    // The Butterfly Scroll entity.
    private DeferredHolder<EntityType<?>, EntityType<ButterflyScroll>> butterflyScroll;

    // The butterfly and moth entities.
    private List<DeferredHolder<EntityType<?>, EntityType<? extends Butterfly>>> butterflies;

    // The caterpillar and larva entities.
    private List<DeferredHolder<EntityType<?>, EntityType<Caterpillar>>> caterpillars;

    // The chrysalis and cocoon entities.
    private List<DeferredHolder<EntityType<?>, EntityType<Chrysalis>>> chrysalises;

    // The egg entities (not spawn eggs!).
    private List<DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>>> butterflyEggs;

    // The butterfly golem
    private DeferredHolder<EntityType<?>, EntityType<IronGolem>> butterflyGolem;

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public EntityTypeRegistry(IEventBus modEventBus) {
        this.deferredRegister = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, ButterfliesMod.MOD_ID);
        this.deferredRegister.register(modEventBus);
    }

    /**
     * Register the entity types.
     * @param blockRegistry The block registry.
     */
    public void initialise(BlockRegistry blockRegistry) {

        this.blockRegistry = blockRegistry;

        // Create the resource key for the butterfly scroll.
        ResourceKey<EntityType<?>> butterflyScrollKey = ResourceKey.create(
                Registries.ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(
                        ButterfliesMod.MOD_ID,
                        ButterflyScroll.NAME));

        this.butterflyScroll =
                this.deferredRegister.register(ButterflyScroll.NAME, () -> EntityType.Builder.of(ButterflyScroll::create, MobCategory.MISC)
                        .sized(1.0f, 1.0f)
                        .build(butterflyScrollKey));

        this.butterflies = new ArrayList<>() {
            {
                for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                    add(registerButterfly(i));
                }
            }
        };

        this.caterpillars = new ArrayList<>() {
            {
                for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                    add(registerCaterpillar(i));
                }
            }
        };

        this.chrysalises = new ArrayList<>() {
            {
                for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                    add(registerChrysalis(i));
                }
            }
        };

        this.butterflyEggs = new ArrayList<>() {
            {
                for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                    add(registerButterflyEgg(i));
                }
            }
        };

        this.butterflyGolem = registerButterflyGolem();
    }

    /**
     * Accessor for the butterflies.
     * @return The list of registry objects.
     */
    public List<DeferredHolder<EntityType<?>, EntityType<? extends Butterfly>>> getButterflies() {
        return butterflies;
    }

    /**
     * Accessor for the caterpillars.
     * @return The list of registry objects.
     */
    public List<DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>>> getButterflyEggs() {
        return butterflyEggs;
    }

    /**
     * Accessor for the butterfly scroll.
     * @return The registry object.
     */
    public DeferredHolder<EntityType<?>, EntityType<ButterflyScroll>> getButterflyScroll() {
        return butterflyScroll;
    }

    /**
     * Accessor for the caterpillars.
     * @return The list of registry objects.
     */
    public List<DeferredHolder<EntityType<?>, EntityType<Caterpillar>>> getCaterpillars() {
        return caterpillars;
    }

    /**
     * Accessor for the caterpillars.
     * @return The list of registry objects.
     */
    public List<DeferredHolder<EntityType<?>, EntityType<Chrysalis>>> getChrysalises() {
        return chrysalises;
    }

    /**
     * Accessor for the butterfly golem.
     * @return The registry entry.
     */
    public DeferredHolder<EntityType<?>, EntityType<IronGolem>> getButterflyGolem() {
        return butterflyGolem;
    }

    /**
     * Helper method to create a butterfly entity.
     * @param entityType The entity's type.
     * @param level The current level.
     * @return A new butterfly.
     */
    private Butterfly createButterfly(EntityType<? extends Butterfly> entityType,
                                      Level level) {
        return new Butterfly(blockRegistry, entityType, level);
    }

    /**
     * Helper method to create an ice butterfly entity.
     * @param entityType The entity's type.
     * @param level The current level.
     * @return A new butterfly.
     */
    private Butterfly createIceButterfly(EntityType<? extends Butterfly> entityType,
                                         Level level) {
        return new IceButterfly(blockRegistry, entityType, level);
    }

    /**
     * Helper method to create a lava moth entity.
     * @param entityType The entity's type.
     * @param level The current level.
     * @return A new butterfly.
     */
    private Butterfly createLavaMoth(EntityType<? extends Butterfly> entityType,
                                         Level level) {
        return new LavaMoth(blockRegistry, entityType, level);
    }

    /**
     * Register the butterflies.
     * @param butterflyIndex The index of the butterfly to register.
     * @return The new registry object.
     */

    private DeferredHolder<EntityType<?>, EntityType<? extends Butterfly>> registerButterfly(int butterflyIndex) {

        String registryId = Butterfly.getRegistryId(butterflyIndex);

        float width = 0.3f;
        float height = 0.2f;

        // Create the resource key.
        ResourceKey<EntityType<?>> key = ResourceKey.create(
                Registries.ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(
                        ButterfliesMod.MOD_ID,
                        Butterfly.getRegistryId(butterflyIndex)));

        // Ice Butterfly
        if (registryId.equals("ice")) {
            return this.deferredRegister.register(registryId,
                    () -> EntityType.Builder.of(this::createIceButterfly, MobCategory.CREATURE)
                            .sized(width, height)
                            .clientTrackingRange(10)
                            .build(key));
        }

        // Lava Moth
        if (registryId.equals("lava")) {
            return this.deferredRegister.register(registryId,
                    () -> EntityType.Builder.of(this::createLavaMoth, MobCategory.CREATURE)
                            .sized(width, height)
                            .clientTrackingRange(10)
                            .build(key));
        }

        return this.deferredRegister.register(registryId,
                () -> EntityType.Builder.of(this::createButterfly, MobCategory.CREATURE)
                        .sized(width, height)
                        .clientTrackingRange(10)
                        .build(key));
    }

    /**
     * Register the caterpillars.
     * @param butterflyIndex The index of the caterpillar to register.
     * @return The new registry object.
     */
    private DeferredHolder<EntityType<?>, EntityType<Caterpillar>> registerCaterpillar(int butterflyIndex) {
        float sized = 0.1f;

        // Create the resource key.
        ResourceKey<EntityType<?>> key = ResourceKey.create(
                Registries.ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(
                        ButterfliesMod.MOD_ID,
                        Caterpillar.getRegistryId(butterflyIndex)));

        return this.deferredRegister.register(Caterpillar.getRegistryId(butterflyIndex),
                () -> EntityType.Builder.of(Caterpillar::new, MobCategory.CREATURE)
                .sized(sized, sized)
                .build(key));
    }

    /**
     * Register the chrysalises.
     * @param butterflyIndex The index of the chrysalis to register.
     * @return The new registry object.
     */
    private DeferredHolder<EntityType<?>, EntityType<Chrysalis>> registerChrysalis(int butterflyIndex) {
        float sized = 0.1f;

        // Create the resource key.
        ResourceKey<EntityType<?>> key = ResourceKey.create(
                Registries.ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(
                        ButterfliesMod.MOD_ID,
                        Chrysalis.getRegistryId(butterflyIndex)));

        return this.deferredRegister.register(Chrysalis.getRegistryId(butterflyIndex),
                () -> EntityType.Builder.of(Chrysalis::new, MobCategory.CREATURE)
                        .sized(sized, sized)
                .build(key));
    }

    /**
     * Register the butterfly eggs.
     * @param butterflyIndex The index of the butterfly egg to register.
     * @return The new registry object.
     */
    private DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>> registerButterflyEgg(int butterflyIndex) {
        float sized = 0.1f;

        // Create the resource key.
        ResourceKey<EntityType<?>> key = ResourceKey.create(
                Registries.ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(
                        ButterfliesMod.MOD_ID,
                        ButterflyEgg.getRegistryId(butterflyIndex)));

        return this.deferredRegister.register(ButterflyEgg.getRegistryId(butterflyIndex),
                () -> EntityType.Builder.of(ButterflyEgg::new, MobCategory.CREATURE)
                        .sized(sized, sized)
                        .build(key));
    }

    /**
     * Register a butterfly golem.
     * @return The new registry object.
     */
    private DeferredHolder<EntityType<?>, EntityType<IronGolem>> registerButterflyGolem() {

        // Create the resource key.
        ResourceKey<EntityType<?>> key = ResourceKey.create(
                Registries.ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(
                        ButterfliesMod.MOD_ID,
                        "butterfly_golem"));

        return this.deferredRegister.register("butterfly_golem",
                () -> EntityType.Builder.of(IronGolem::new, MobCategory.MISC)
                        .sized(1.4F, 2.7F)
                        .clientTrackingRange(10)
                        .build(key));
    }
}
