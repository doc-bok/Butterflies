package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
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
public class EntityTypeRegistry {

    // An instance of a deferred registry we use to register our entity types.
    private final DeferredRegister<EntityType<?>> deferredRegister;

    // The block registry.
    private BlockRegistry blockRegistry;

    // The Butterfly Scroll entity.
    private RegistryObject<EntityType<ButterflyScroll>> butterflyScroll;

    // The butterfly and moth entities.
    private List<RegistryObject<EntityType<? extends Butterfly>>> butterflies;

    // The caterpillar and larva entities.
    private List<RegistryObject<EntityType<Caterpillar>>> caterpillars;

    // The chrysalis and cocoon entities.
    private List<RegistryObject<EntityType<Chrysalis>>> chrysalises;

    // The egg entities (not spawn eggs!).
    private List<RegistryObject<EntityType<ButterflyEgg>>> butterflyEggs;

    // The butterfly golem
    private RegistryObject<EntityType<IronGolem>> butterflyGolem;

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public EntityTypeRegistry(IEventBus modEventBus) {
        this.deferredRegister = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ButterfliesMod.MOD_ID);
        this.deferredRegister.register(modEventBus);
    }

    /**
     * Register the entity types.
     * @param blockRegistry The block registry.
     */
    public void initialise(BlockRegistry blockRegistry) {

        this.blockRegistry = blockRegistry;
        
        this.butterflyScroll =
                this.deferredRegister.register(ButterflyScroll.NAME, () -> EntityType.Builder.of(ButterflyScroll::create, MobCategory.MISC)
                        .sized(1.0f, 1.0f)
                        .build(ButterflyScroll.NAME));

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
    public List<RegistryObject<EntityType<? extends Butterfly>>> getButterflies() {
        return butterflies;
    }

    /**
     * Accessor for the caterpillars.
     * @return The list of registry objects.
     */
    public List<RegistryObject<EntityType<ButterflyEgg>>> getButterflyEggs() {
        return butterflyEggs;
    }

    /**
     * Accessor for the butterfly scroll.
     * @return The registry object.
     */
    public RegistryObject<EntityType<ButterflyScroll>> getButterflyScroll() {
        return butterflyScroll;
    }

    /**
     * Accessor for the caterpillars.
     * @return The list of registry objects.
     */
    public List<RegistryObject<EntityType<Caterpillar>>> getCaterpillars() {
        return caterpillars;
    }

    /**
     * Accessor for the caterpillars.
     * @return The list of registry objects.
     */
    public List<RegistryObject<EntityType<Chrysalis>>> getChrysalises() {
        return chrysalises;
    }

    /**
     * Accessor for the butterfly golem.
     * @return The registry entry.
     */
    public RegistryObject<EntityType<IronGolem>> getButterflyGolem() {
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

    private RegistryObject<EntityType<? extends Butterfly>> registerButterfly(int butterflyIndex) {

        String registryId = Butterfly.getRegistryId(butterflyIndex);

        // Ice Butterfly
        if (registryId.equals("ice")) {
            return this.deferredRegister.register(registryId,
                    () -> EntityType.Builder.of(this::createIceButterfly, MobCategory.CREATURE)
                            .sized(0.3f, 0.2f)
                            .clientTrackingRange(10)
                            .build(Butterfly.getRegistryId(butterflyIndex)));
        }

        // Lava Moth
        if (registryId.equals("lava")) {
            return this.deferredRegister.register(registryId,
                    () -> EntityType.Builder.of(this::createLavaMoth, MobCategory.CREATURE)
                            .sized(0.3f, 0.2f)
                            .clientTrackingRange(10)
                            .build(Butterfly.getRegistryId(butterflyIndex)));
        }

        return this.deferredRegister.register(registryId,
                () -> EntityType.Builder.of(this::createButterfly, MobCategory.CREATURE)
                        .sized(0.3f, 0.2f)
                        .clientTrackingRange(10)
                        .build(Butterfly.getRegistryId(butterflyIndex)));
    }

    /**
     * Register the caterpillars.
     * @param butterflyIndex The index of the caterpillar to register.
     * @return The new registry object.
     */
    private RegistryObject<EntityType<Caterpillar>> registerCaterpillar(int butterflyIndex) {
        return this.deferredRegister.register(Caterpillar.getRegistryId(butterflyIndex),
                () -> EntityType.Builder.of(Caterpillar::new, MobCategory.CREATURE)
                .sized(0.1f, 0.1f)
                .build(Caterpillar.getRegistryId(butterflyIndex)));
    }

    /**
     * Register the chrysalises.
     * @param butterflyIndex The index of the chrysalis to register.
     * @return The new registry object.
     */
    private RegistryObject<EntityType<Chrysalis>> registerChrysalis(int butterflyIndex) {
        return this.deferredRegister.register(Chrysalis.getRegistryId(butterflyIndex),
                () -> EntityType.Builder.of(Chrysalis::new, MobCategory.CREATURE)
                .sized(0.1f, 0.1f)
                .build(Chrysalis.getRegistryId(butterflyIndex)));
    }

    /**
     * Register the butterfly eggs.
     * @param butterflyIndex The index of the butterfly egg to register.
     * @return The new registry object.
     */
    private RegistryObject<EntityType<ButterflyEgg>> registerButterflyEgg(int butterflyIndex) {
        return this.deferredRegister.register(ButterflyEgg.getRegistryId(butterflyIndex),
                () -> EntityType.Builder.of(ButterflyEgg::new, MobCategory.CREATURE)
                        .sized(0.1f, 0.1f)
                        .build(ButterflyEgg.getRegistryId(butterflyIndex)));
    }

    /**
     * Register a butterfly golem.
     * @return The new registry object.
     */
    private RegistryObject<EntityType<IronGolem>> registerButterflyGolem() {
        return this.deferredRegister.register("butterfly_golem",
                () -> EntityType.Builder.of(IronGolem::new, MobCategory.MISC)
                        .sized(1.4F, 2.7F)
                        .clientTrackingRange(10)
                        .build("butterfly_golem"));
    }
}
