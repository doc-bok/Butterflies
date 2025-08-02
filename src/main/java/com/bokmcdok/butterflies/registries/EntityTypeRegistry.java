package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.ButterflyInfo;
import com.bokmcdok.butterflies.world.entity.animal.*;
import com.bokmcdok.butterflies.world.entity.decoration.ButterflyScroll;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * This class registers all entity types used in the butterflies mod with Forge's Entity Type Registry.
 */
public class EntityTypeRegistry {

    /**
     * The mob category for registering butterflies.
     */
    public static final MobCategory BUTTERFLY_SPAWN_POOL = MobCategory.create(
            "BUTTERFLY_SPAWNS",
            "butterfly_spawns",
            30,
            true,
            true,
            128);

    private final DeferredRegister<EntityType<?>> deferredRegister;

    private BlockRegistry blockRegistry;

    private List<RegistryObject<EntityType<? extends Butterfly>>> butterflies;
    private List<RegistryObject<EntityType<ButterflyEgg>>> butterflyEggs;
    private RegistryObject<EntityType<IronGolem>> butterflyGolem;
    private RegistryObject<EntityType<ButterflyScroll>> butterflyScroll; // TODO: Remove after migration, kept for backwards compatibility
    private List<RegistryObject<EntityType<ButterflyScroll>>> butterflyScrolls;
    private List<RegistryObject<EntityType<Caterpillar>>> caterpillars;
    private List<RegistryObject<EntityType<Chrysalis>>> chrysalises;

    /**
     * Constructs the entity type registry and registers the deferred register with the given event bus.
     * @param modEventBus The mod event bus to register with.
     */
    public EntityTypeRegistry(IEventBus modEventBus) {
        this.deferredRegister = DeferredRegister.create(ForgeRegistries.ENTITIES, ButterfliesMod.MOD_ID);
        this.deferredRegister.register(modEventBus);
    }

    /**
     * Initializes entity types based on the provided block registry.
     * @param blockRegistry The block registry instance.
     */
    public void initialise(BlockRegistry blockRegistry) {
        this.blockRegistry = blockRegistry;

        final int speciesCount = ButterflyInfo.SPECIES.length;

        butterflies = new ArrayList<>(speciesCount);
        butterflyEggs = new ArrayList<>(speciesCount);
        butterflyScrolls = new ArrayList<>(speciesCount);
        caterpillars = new ArrayList<>(speciesCount);
        chrysalises = new ArrayList<>(speciesCount);

        for (int i = 0; i < speciesCount; i++) {
            butterflies.add(registerButterfly(i));
            butterflyEggs.add(registerButterflyEgg(i));
            butterflyScrolls.add(registerButterflyScroll(i));
            caterpillars.add(registerCaterpillar(i));
            chrysalises.add(registerChrysalis(i));
        }

        butterflyGolem = registerButterflyGolem();

        // Register the single butterfly scroll separately (backwards compatibility)
        butterflyScroll = deferredRegister.register(
                ButterflyScroll.NAME,
                () -> EntityType.Builder.of(ButterflyScroll::create, MobCategory.MISC)
                        .sized(1.0f, 1.0f)
                        .build(ButterflyScroll.NAME));
    }

    // Accessors
    public List<RegistryObject<EntityType<? extends Butterfly>>> getButterflies() {
        return butterflies;
    }

    public List<RegistryObject<EntityType<ButterflyEgg>>> getButterflyEggs() {
        return butterflyEggs;
    }

    public RegistryObject<EntityType<IronGolem>> getButterflyGolem() {
        return butterflyGolem;
    }

    public RegistryObject<EntityType<ButterflyScroll>> getButterflyScroll() {
        return butterflyScroll;
    }

    public List<RegistryObject<EntityType<ButterflyScroll>>> getButterflyScrolls() {
        return butterflyScrolls;
    }

    public List<RegistryObject<EntityType<Caterpillar>>> getCaterpillars() {
        return caterpillars;
    }

    public List<RegistryObject<EntityType<Chrysalis>>> getChrysalises() {
        return chrysalises;
    }

    // Entity factory methods

    private Butterfly createButterfly(EntityType<? extends Butterfly> entityType, Level level) {
        return new Butterfly(blockRegistry, entityType, level);
    }

    private Butterfly createIceButterfly(EntityType<? extends Butterfly> entityType, Level level) {
        return new ParticleButterfly(blockRegistry, entityType, level, ParticleTypes.ELECTRIC_SPARK);
    }

    private Butterfly createLavaMoth(EntityType<? extends Butterfly> entityType, Level level) {
        return new ParticleButterfly(blockRegistry, entityType, level, ParticleTypes.DRIPPING_DRIPSTONE_LAVA);
    }

    /**
     * Returns the appropriate entity factory based on butterfly traits.
     * @param butterflyIndex The index of the butterfly species.
     * @return The factory method for creating butterfly entities.
     */
    private EntityType.@NotNull EntityFactory<Butterfly> getEntityFactory(int butterflyIndex) {
        ButterflyData.Trait[] traits = ButterflyInfo.TRAITS[butterflyIndex];

        for (ButterflyData.Trait trait : traits) {
            if (trait == ButterflyData.Trait.ICY) {
                return this::createIceButterfly;
            }
            if (trait == ButterflyData.Trait.LAVA) {
                return this::createLavaMoth;
            }
        }
        return this::createButterfly;
    }

    // Registration methods

    private RegistryObject<EntityType<? extends Butterfly>> registerButterfly(int butterflyIndex) {
        String registryId = Butterfly.getRegistryId(butterflyIndex);
        EntityType.EntityFactory<Butterfly> entityFactory = getEntityFactory(butterflyIndex);

        return deferredRegister.register(registryId,
                () -> EntityType.Builder.of(entityFactory, BUTTERFLY_SPAWN_POOL)
                        .sized(0.3f, 0.2f)
                        .clientTrackingRange(10)
                        .build(registryId));
    }

    private RegistryObject<EntityType<ButterflyEgg>> registerButterflyEgg(int butterflyIndex) {
        String registryId = ButterflyEgg.getRegistryId(butterflyIndex);
        return deferredRegister.register(registryId,
                () -> EntityType.Builder.of(ButterflyEgg::new, BUTTERFLY_SPAWN_POOL)
                        .sized(0.1f, 0.1f)
                        .build(registryId));
    }

    private RegistryObject<EntityType<IronGolem>> registerButterflyGolem() {
        String registryId = "butterfly_golem";
        return deferredRegister.register(registryId,
                () -> EntityType.Builder.of(IronGolem::new, MobCategory.MISC)
                        .sized(1.4F, 2.7F)
                        .clientTrackingRange(10)
                        .build(registryId));
    }

    private RegistryObject<EntityType<ButterflyScroll>> registerButterflyScroll(int butterflyIndex) {
        String registryId = ButterflyScroll.getRegistryId(butterflyIndex);
        return deferredRegister.register(registryId,
                () -> EntityType.Builder.of(ButterflyScroll::create, MobCategory.MISC)
                        .sized(1.0f, 1.0f)
                        .build(registryId));
    }

    private RegistryObject<EntityType<Caterpillar>> registerCaterpillar(int butterflyIndex) {
        String registryId = Caterpillar.getRegistryId(butterflyIndex);
        return deferredRegister.register(registryId,
                () -> EntityType.Builder.of(Caterpillar::new, BUTTERFLY_SPAWN_POOL)
                        .sized(0.1f, 0.1f)
                        .build(registryId));
    }

    private RegistryObject<EntityType<Chrysalis>> registerChrysalis(int butterflyIndex) {
        String registryId = Chrysalis.getRegistryId(butterflyIndex);
        return deferredRegister.register(registryId,
                () -> EntityType.Builder.of(Chrysalis::new, BUTTERFLY_SPAWN_POOL)
                        .sized(0.1f, 0.1f)
                        .build(registryId));
    }
}
