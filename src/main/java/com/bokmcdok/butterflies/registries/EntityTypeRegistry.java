package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.ButterflyInfo;
import com.bokmcdok.butterflies.world.entity.animal.*;
import com.bokmcdok.butterflies.world.entity.decoration.ButterflyScroll;
import com.bokmcdok.butterflies.world.entity.monster.*;
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
    private TagRegistry tagRegistry;

    private List<RegistryObject<EntityType<? extends Butterfly>>> butterflies;
    private List<RegistryObject<EntityType<ButterflyEgg>>> butterflyEggs;
    private RegistryObject<EntityType<IronGolem>> butterflyGolem;
    private RegistryObject<EntityType<ButterflyScroll>> butterflyScroll; // TODO: Remove after migration, kept for backwards compatibility
    private List<RegistryObject<EntityType<ButterflyScroll>>> butterflyScrolls;
    private List<RegistryObject<EntityType<Caterpillar>>> caterpillars;
    private List<RegistryObject<EntityType<Chrysalis>>> chrysalises;
    private RegistryObject<EntityType<PeacemakerButterfly>> peacemakerButterfly;
    private RegistryObject<EntityType<PeacemakerEvoker>> peacemakerEvoker;
    private RegistryObject<EntityType<PeacemakerIllusioner>> peacemakerIllusioner;
    private RegistryObject<EntityType<PeacemakerPillager>> peacemakerPillager;
    private RegistryObject<EntityType<PeacemakerVillager>> peacemakerVillager;
    private RegistryObject<EntityType<PeacemakerVindicator>> peacemakerVindicator;
    private RegistryObject<EntityType<PeacemakerWitch>> peacemakerWitch;


    /**
     * Constructs the entity type registry and registers the deferred register with the given event bus.
     * @param modEventBus The mod event bus to register with.
     */
    public EntityTypeRegistry(IEventBus modEventBus) {
        this.deferredRegister = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ButterfliesMod.MOD_ID);
        this.deferredRegister.register(modEventBus);
    }

    /**
     * Initializes entity types based on the provided block registry.
     * @param blockRegistry The block registry instance.
     */
    public void initialise(BlockRegistry blockRegistry,
                           TagRegistry tagRegistry) {
        this.blockRegistry = blockRegistry;
        this.tagRegistry = tagRegistry;

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

        peacemakerButterfly = registerPeacemakerButterfly();
        peacemakerEvoker = registerPeacemakerEvoker();
        peacemakerIllusioner = registerPeacemakerIllusioner();
        peacemakerPillager = registerPeacemakerPillager();
        peacemakerVillager = registerPeacemakerVillager();
        peacemakerVindicator = registerPeacemakerVindicator();
        peacemakerWitch = registerPeacemakerWitch();
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

    public RegistryObject<EntityType<PeacemakerButterfly>> getPeacemakerButterfly() {
        return peacemakerButterfly;
    }

    public RegistryObject<EntityType<PeacemakerEvoker>> getPeacemakerEvoker() {
        return peacemakerEvoker;
    }

    public RegistryObject<EntityType<PeacemakerIllusioner>> getPeacemakerIllusioner() {
        return peacemakerIllusioner;
    }

    public RegistryObject<EntityType<PeacemakerPillager>> getPeacemakerPillager() {
        return peacemakerPillager;
    }

    public RegistryObject<EntityType<PeacemakerVillager>> getPeacemakerVillager() {
        return peacemakerVillager;
    }

    public RegistryObject<EntityType<PeacemakerVindicator>> getPeacemakerVindicator() {
        return peacemakerVindicator;
    }

    public RegistryObject<EntityType<PeacemakerWitch>> getPeacemakerWitch() {
        return peacemakerWitch;
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

    private PeacemakerButterfly createPeacemakerButterfly(
            EntityType<? extends PeacemakerButterfly> entityType,
            Level level) {
        return new PeacemakerButterfly(this.tagRegistry, entityType, level);
    }

    private PeacemakerEvoker createPeacemakerEvoker(
            EntityType<? extends PeacemakerEvoker> entityType,
            Level level) {
        return new PeacemakerEvoker(this.tagRegistry, entityType, level);
    }

    private PeacemakerIllusioner createPeacemakerIllusioner(
            EntityType<? extends PeacemakerIllusioner> entityType,
            Level level) {
        return new PeacemakerIllusioner(this.tagRegistry, entityType, level);
    }

    private PeacemakerPillager createPeacemakerPillager(
            EntityType<? extends PeacemakerPillager> entityType,
            Level level) {
        return new PeacemakerPillager(this.tagRegistry, entityType, level);
    }

    private PeacemakerVillager createPeacemakerVillager(
            EntityType<? extends PeacemakerVillager> entityType,
            Level level) {
        return new PeacemakerVillager(entityType, level);
    }

    private PeacemakerVindicator createPeacemakerVindicator(
            EntityType<? extends PeacemakerVindicator> entityType,
            Level level) {
        return new PeacemakerVindicator(this.tagRegistry, entityType, level);
    }

    private PeacemakerWitch createPeacemakerWitch(
            EntityType<? extends PeacemakerWitch> entityType,
            Level level) {
        return new PeacemakerWitch(entityType, level);
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

    private RegistryObject<EntityType<PeacemakerButterfly>> registerPeacemakerButterfly() {
        String registryId = "peacemaker_butterfly";
        return deferredRegister.register(registryId,
                () -> EntityType.Builder.of(this::createPeacemakerButterfly, MobCategory.MONSTER)
                        .sized(0.3f, 0.4f)
                        .build(registryId));
    }

    private RegistryObject<EntityType<PeacemakerEvoker>> registerPeacemakerEvoker() {
        String registryId = "peacemaker_evoker";
        return deferredRegister.register(registryId,
                () -> EntityType.Builder.of(this::createPeacemakerEvoker, MobCategory.MONSTER)
                        .sized(0.6f, 1.95f)
                        .clientTrackingRange(8)
                        .build(registryId));
    }

    private RegistryObject<EntityType<PeacemakerIllusioner>> registerPeacemakerIllusioner() {
        String registryId = "peacemaker_illusioner";
        return deferredRegister.register(registryId,
                () -> EntityType.Builder.of(this::createPeacemakerIllusioner, MobCategory.MONSTER)
                        .sized(0.6f, 1.95f)
                        .clientTrackingRange(8)
                        .build(registryId));
    }

    private RegistryObject<EntityType<PeacemakerPillager>> registerPeacemakerPillager() {
        String registryId = "peacemaker_pillager";
        return deferredRegister.register(registryId,
                () -> EntityType.Builder.of(this::createPeacemakerPillager, MobCategory.MONSTER)
                        .sized(0.3f, 0.4f)
                        .build(registryId));
    }

    private RegistryObject<EntityType<PeacemakerVillager>> registerPeacemakerVillager() {
        String registryId = "peacemaker_villager";
        return deferredRegister.register(registryId,
                () -> EntityType.Builder.of(this::createPeacemakerVillager, MobCategory.MONSTER)
                        .sized(0.6f, 1.95f)
                        .clientTrackingRange(10)
                        .build(registryId));
    }

    private RegistryObject<EntityType<PeacemakerVindicator>> registerPeacemakerVindicator() {
        String registryId = "peacemaker_vindicator";
        return deferredRegister.register(registryId,
                () -> EntityType.Builder.of(this::createPeacemakerVindicator, MobCategory.MONSTER)
                        .sized(0.6f, 1.95f)
                        .clientTrackingRange(10)
                        .build(registryId));
    }

    private RegistryObject<EntityType<PeacemakerWitch>> registerPeacemakerWitch() {
        String registryId = "peacemaker_witch";
        return deferredRegister.register(registryId,
                () -> EntityType.Builder.of(this::createPeacemakerWitch, MobCategory.MONSTER)
                        .sized(0.6F, 1.95F)
                        .clientTrackingRange(8)
                        .build(registryId));
    }
}
