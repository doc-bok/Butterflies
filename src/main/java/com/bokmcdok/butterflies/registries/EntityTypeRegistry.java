package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflySpeciesList;
import com.bokmcdok.butterflies.world.entity.animal.*;
import com.bokmcdok.butterflies.world.entity.decoration.ButterflyScroll;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;


/**
 * This class registers all the entities we use with Forge's Entity Type Registry
 */
public class EntityTypeRegistry {

    // An instance of a deferred registry we use to register our entity types.
    private final DeferredRegister<EntityType<?>> deferredRegister;

    // The Butterfly Scroll entity.
    private final RegistryObject<EntityType<ButterflyScroll>> butterflyScroll;

    // The butterfly and moth entities.
    private final List<RegistryObject<EntityType<? extends Butterfly>>> butterflies;

    // The caterpillar and larva entities.
    private final List<RegistryObject<EntityType<Caterpillar>>> caterpillars;

    // The chrysalis and cocoon entities.
    private final List<RegistryObject<EntityType<Chrysalis>>> chrysalises;

    // The egg entities (not spawn eggs!).
    private final List<RegistryObject<EntityType<ButterflyEgg>>> butterflyEggs;

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public EntityTypeRegistry(IEventBus modEventBus) {
        this.deferredRegister = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ButterfliesMod.MOD_ID);
        this.deferredRegister.register(modEventBus);
        
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
     * Register the butterflies.
     * @param butterflyIndex The index of the butterfly to register.
     * @return The new registry object.
     */
    private RegistryObject<EntityType<? extends Butterfly>> registerButterfly(int butterflyIndex) {

        String registryId = Butterfly.getRegistryId(butterflyIndex);

        // Ice Butterfly
        if (registryId.equals("ice")) {
            return this.deferredRegister.register(registryId,
                    () -> EntityType.Builder.of(IceButterfly::new, MobCategory.CREATURE)
                            .sized(0.3f, 0.2f)
                            .build(Butterfly.getRegistryId(butterflyIndex)));
        }

        // Lava Moth
        if (registryId.equals("lava")) {
            return this.deferredRegister.register(registryId,
                    () -> EntityType.Builder.of(LavaMoth::new, MobCategory.CREATURE)
                            .sized(0.3f, 0.2f)
                            .build(Butterfly.getRegistryId(butterflyIndex)));
        }

        return this.deferredRegister.register(registryId,
                () -> EntityType.Builder.of(Butterfly::new, MobCategory.CREATURE)
                .sized(0.3f, 0.2f)
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
}
