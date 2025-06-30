package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.ButterflyInfo;
import com.bokmcdok.butterflies.world.entity.ButterflyMobCategory;
import com.bokmcdok.butterflies.world.entity.animal.*;
import com.bokmcdok.butterflies.world.entity.decoration.ButterflyScroll;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * This class registers all the entities we use with Forge's Entity Type Registry
 */
public class EntityTypeRegistry {

    // An instance of a deferred registry we use to register our entity types.
    private final DeferredRegister<EntityType<?>> deferredRegister;

    // The block registry.
    private BlockRegistry blockRegistry;

    // The butterfly and moth entities.
    private List<DeferredHolder<EntityType<?>, EntityType<? extends Mob>>> butterflies;

    // The egg entities (not spawn eggs!).
    private List<DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>>> butterflyEggs;

    // The butterfly golem
    private DeferredHolder<EntityType<?>, EntityType<? extends Mob>> butterflyGolem;

    // The Butterfly Scroll entity.
    // TODO: Kept for backwards compatibility. This can be removed eventually.
    private DeferredHolder<EntityType<?>, EntityType<ButterflyScroll>> butterflyScroll;

    // The Butterfly Scroll entities.
    private List<DeferredHolder<EntityType<?>, EntityType<ButterflyScroll>>> butterflyScrolls;

    // The caterpillar and larva entities.
    private List<DeferredHolder<EntityType<?>, EntityType<? extends Mob>>> caterpillars;

    // The chrysalis and cocoon entities.
    private List<DeferredHolder<EntityType<?>, EntityType<Chrysalis>>> chrysalises;

    /**
     * Construction
     *
     * @param modEventBus The event bus to register with.
     */
    public EntityTypeRegistry(IEventBus modEventBus) {
        this.deferredRegister = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, ButterfliesMod.MOD_ID);
        this.deferredRegister.register(modEventBus);
    }

    /**
     * Register the entity types.
     *
     * @param blockRegistry The block registry.
     */
    public void initialise(BlockRegistry blockRegistry) {

        this.blockRegistry = blockRegistry;

        this.butterflies = new ArrayList<>() {
            {
                for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
                    add(registerButterfly(i));
                }
            }
        };

        this.butterflyEggs = new ArrayList<>() {
            {
                for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
                    add(registerButterflyEgg(i));
                }
            }
        };

        this.butterflyGolem = registerButterflyGolem();

        // Create the resource key for the butterfly scroll.
        ResourceKey<EntityType<?>> butterflyScrollKey = ResourceKey.create(
                Registries.ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(
                        ButterfliesMod.MOD_ID,
                        ButterflyScroll.NAME));

        // Register the scroll
        this.butterflyScroll =
                this.deferredRegister.register(ButterflyScroll.NAME, () -> EntityType.Builder.of(ButterflyScroll::create, MobCategory.MISC)
                        .sized(1.0f, 1.0f)
                        .build(butterflyScrollKey));

        // Register the new scrolls
        this.butterflyScrolls = new ArrayList<>() {
            {
                for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
                    add(registerButterflyScroll(i));
                }
            }
        };

        this.caterpillars = new ArrayList<>() {
            {
                for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
                    add(registerCaterpillar(i));
                }
            }
        };

        this.chrysalises = new ArrayList<>() {
            {
                for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
                    add(registerChrysalis(i));
                }
            }
        };
    }

    /**
     * Accessor for the butterflies.
     *
     * @return The list of registry objects.
     */
    public List<DeferredHolder<EntityType<?>, EntityType<? extends Mob>>> getButterflies() {
        return butterflies;
    }

    /**
     * Accessor for the caterpillars.
     *
     * @return The list of registry objects.
     */
    public List<DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>>> getButterflyEggs() {
        return butterflyEggs;
    }

    /**
     * Accessor for the butterfly golem.
     * @return The registry entry.
     */
    public DeferredHolder<EntityType<?>, EntityType<? extends Mob>> getButterflyGolem() {
        return butterflyGolem;
    }

    /**
     * Accessor for the butterfly scroll.
     *
     * @return The registry object.
     */
    public DeferredHolder<EntityType<?>, EntityType<ButterflyScroll>> getButterflyScroll() {
        return butterflyScroll;
    }

    /**
     * Accessor for the butterfly Scrolls.
     *
     * @return The list of registry objects.
     */
    public List<DeferredHolder<EntityType<?>, EntityType<ButterflyScroll>>> getButterflyScrolls() {
        return butterflyScrolls;
    }

    /**
     * Accessor for the caterpillars.
     *
     * @return The list of registry objects.
     */
    public List<DeferredHolder<EntityType<?>, EntityType<? extends Mob>>> getCaterpillars() {
        return caterpillars;
    }

    /**
     * Accessor for the caterpillars.
     *
     * @return The list of registry objects.
     */
    public List<DeferredHolder<EntityType<?>, EntityType<Chrysalis>>> getChrysalises() {
        return chrysalises;
    }

    /**
     * Helper method to create a butterfly entity.
     *
     * @param entityType The entity's type.
     * @param level      The current level.
     * @return A new butterfly.
     */
    private Butterfly createButterfly(EntityType<? extends Butterfly> entityType,
                                      Level level) {
        return new Butterfly(blockRegistry, entityType, level);
    }

    /**
     * Helper method to create an ice butterfly entity.
     *
     * @param entityType The entity's type.
     * @param level      The current level.
     * @return A new butterfly.
     */
    private Butterfly createIceButterfly(EntityType<? extends Butterfly> entityType,
                                         Level level) {
        return new ParticleButterfly(blockRegistry, entityType, level, ParticleTypes.ELECTRIC_SPARK);
    }

    /**
     * Helper method to create a lava moth entity.
     *
     * @param entityType The entity's type.
     * @param level      The current level.
     * @return A new butterfly.
     */
    private Butterfly createLavaMoth(EntityType<? extends Butterfly> entityType,
                                     Level level) {
        return new ParticleButterfly(blockRegistry, entityType, level, ParticleTypes.DRIPPING_DRIPSTONE_LAVA);
    }

    /**
     * Helper method that creates resource keys.
     * @param registryId The Registry ID to create the key from.
     * @return A new Resource Key.
     */
    private ResourceKey<EntityType<?>> createResourceKey(String registryId) {
        return ResourceKey.create(
                Registries.ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(
                        ButterfliesMod.MOD_ID,
                        registryId));
    }

    /**
     * Get the entity factory to use based on butterfly traits.
     * @param butterflyIndex The index of the butterfly.
     * @return A new entity factory.
     */
    private EntityType.@NotNull EntityFactory<Butterfly> getEntityFactory(int butterflyIndex) {
        EntityType.EntityFactory<Butterfly> entityFactory = this::createButterfly;

        // Ice Butterfly
        if (Arrays.asList(ButterflyInfo.TRAITS[butterflyIndex]).contains(ButterflyData.Trait.ICY)) {
            entityFactory = this::createIceButterfly;
        }

        // Lava Moth
        if (Arrays.asList(ButterflyInfo.TRAITS[butterflyIndex]).contains(ButterflyData.Trait.LAVA)) {
            entityFactory = this::createLavaMoth;
        }

        return entityFactory;
    }

    /**
     * Register the butterflies.
     *
     * @param butterflyIndex The index of the butterfly to register.
     * @return The new registry object.
     */

    private DeferredHolder<EntityType<?>, EntityType<? extends Mob>> registerButterfly(int butterflyIndex) {

        String registryId = Butterfly.getRegistryId(butterflyIndex);
        ResourceKey<EntityType<?>> resourceKey = createResourceKey(registryId);
        EntityType.EntityFactory<Butterfly> entityFactory = getEntityFactory(butterflyIndex);

        // Register the butterfly
        return this.deferredRegister.register(
                registryId,
                () -> EntityType.Builder.of(entityFactory, ButterflyMobCategory.BUTTERFLY)
                        .sized(0.3f, 0.2f)
                        .clientTrackingRange(10)
                        .build(resourceKey));
    }

    /**
     * Register the butterfly eggs.
     *
     * @param butterflyIndex The index of the butterfly egg to register.
     * @return The new registry object.
     */
    private DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>> registerButterflyEgg(int butterflyIndex) {

        String registryId = ButterflyEgg.getRegistryId(butterflyIndex);
        ResourceKey<EntityType<?>> resourceKey = createResourceKey(registryId);

        return this.deferredRegister.register(
                registryId,
                () -> EntityType.Builder.of(ButterflyEgg::new, ButterflyMobCategory.BUTTERFLY)
                        .sized(0.1f, 0.1f)
                        .build(resourceKey));
    }

    /**
     * Register a butterfly golem.
     *
     * @return The new registry object.
     */
    private DeferredHolder<EntityType<?>, EntityType<? extends Mob>> registerButterflyGolem() {

        String registryId = "butterfly_golem";
        ResourceKey<EntityType<?>> resourceKey = createResourceKey(registryId);

        return this.deferredRegister.register(
                registryId,
                () -> EntityType.Builder.of(IronGolem::new, MobCategory.MISC)
                        .sized(1.4F, 2.7F)
                        .clientTrackingRange(10)
                        .build(resourceKey));
    }

    /**
     * Register the butterflies.
     *
     * @param butterflyIndex The index of the butterfly to register.
     * @return The new registry object.
     */
    private DeferredHolder<EntityType<?>, EntityType<ButterflyScroll>> registerButterflyScroll(int butterflyIndex) {

        String registryId = ButterflyScroll.getRegistryId(butterflyIndex);
        ResourceKey<EntityType<?>> resourceKey = createResourceKey(registryId);

        return this.deferredRegister.register(
                registryId,
                () -> EntityType.Builder.of(ButterflyScroll::create, MobCategory.MISC)
                        .sized(1.0f, 1.0f)
                        .build(resourceKey));

    }

    /**
     * Register the caterpillars.
     * @param butterflyIndex The index of the caterpillar to register.
     * @return The new registry object.
     */
    private DeferredHolder<EntityType<?>, EntityType<? extends Mob>> registerCaterpillar(int butterflyIndex) {

        String registryId = Caterpillar.getRegistryId(butterflyIndex);
        ResourceKey<EntityType<?>> resourceKey = createResourceKey(registryId);

        return this.deferredRegister.register(
                registryId,
                () -> EntityType.Builder.of(Caterpillar::new, ButterflyMobCategory.BUTTERFLY)
                        .sized(0.1f, 0.1f)
                        .build(resourceKey));
    }

    /**
     * Register the chrysalises.
     * @param butterflyIndex The index of the chrysalis to register.
     * @return The new registry object.
     */
    private DeferredHolder<EntityType<?>, EntityType<Chrysalis>> registerChrysalis(int butterflyIndex) {

        String registryId = Chrysalis.getRegistryId(butterflyIndex);
        ResourceKey<EntityType<?>> resourceKey = createResourceKey(registryId);

        return this.deferredRegister.register(
                registryId,
                () -> EntityType.Builder.of(Chrysalis::new, ButterflyMobCategory.BUTTERFLY)
                        .sized(0.1f, 0.1f)
                        .build(resourceKey));
    }
}
