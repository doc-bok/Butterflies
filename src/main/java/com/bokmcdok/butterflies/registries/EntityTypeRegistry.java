package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.ButterflyInfo;
import com.bokmcdok.butterflies.world.entity.ai.PeacemakerGoalRegistrar;
import com.bokmcdok.butterflies.world.entity.animal.*;
import com.bokmcdok.butterflies.world.entity.decoration.ButterflyScroll;
import com.bokmcdok.butterflies.world.entity.monster.*;
import com.bokmcdok.butterflies.world.entity.npc.PeacemakerVillager;
import com.bokmcdok.butterflies.world.entity.npc.PeacemakerWanderingTrader;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.level.Level;
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

    public static final DeferredRegister<EntityType<?>> REGISTER;

    private static final PeacemakerGoalRegistrar PEACEMAKER_GOAL_REGISTRAR;

    public static List<RegistryObject<EntityType<? extends Butterfly>>> BUTTERFLIES;
    public static List<RegistryObject<EntityType<ButterflyEgg>>> BUTTERFLY_EGGS;
    public static RegistryObject<EntityType<IronGolem>> BUTTERFLY_GOLEM;
    public static RegistryObject<EntityType<ButterflyScroll>> BUTTERFLY_SCROLL; // TODO: Remove after migration, kept for backwards compatibility
    public static List<RegistryObject<EntityType<ButterflyScroll>>> BUTTERFLY_SCROLLS;
    public static List<RegistryObject<EntityType<Caterpillar>>> CATERPILLARS;
    public static List<RegistryObject<EntityType<Chrysalis>>> CHRYSALISES;
    public static RegistryObject<EntityType<PeacemakerButterfly>> PEACEMAKER_BUTTERFLY;
    public static RegistryObject<EntityType<PeacemakerEvoker>> PEACEMAKER_EVOKER;
    public static RegistryObject<EntityType<PeacemakerIllusioner>> PEACEMAKER_ILLUSIONER;
    public static RegistryObject<EntityType<PeacemakerPillager>> PEACEMAKER_PILLAGER;
    public static RegistryObject<EntityType<PeacemakerVillager>> PEACEMAKER_VILLAGER;
    public static RegistryObject<EntityType<PeacemakerVindicator>> PEACEMAKER_VINDICATOR;
    public static RegistryObject<EntityType<PeacemakerWanderingTrader>> PEACEMAKER_WANDERING_TRADER;
    public static RegistryObject<EntityType<PeacemakerWitch>> PEACEMAKER_WITCH;

    static {
        REGISTER = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ButterfliesMod.MOD_ID);final int speciesCount = ButterflyInfo.SPECIES.length;

        BUTTERFLIES = new ArrayList<>(speciesCount);
        BUTTERFLY_EGGS = new ArrayList<>(speciesCount);
        BUTTERFLY_SCROLLS = new ArrayList<>(speciesCount);
        CATERPILLARS = new ArrayList<>(speciesCount);
        CHRYSALISES = new ArrayList<>(speciesCount);

        for (int i = 0; i < speciesCount; i++) {
            BUTTERFLIES.add(registerButterfly(i));
            BUTTERFLY_EGGS.add(registerButterflyEgg(i));
            BUTTERFLY_SCROLLS.add(registerButterflyScroll(i));
            CATERPILLARS.add(registerCaterpillar(i));
            CHRYSALISES.add(registerChrysalis(i));
        }

        BUTTERFLY_GOLEM = registerButterflyGolem();

        // Register the single butterfly scroll separately (backwards compatibility)
        BUTTERFLY_SCROLL = REGISTER.register(
                ButterflyScroll.NAME,
                () -> EntityType.Builder.of(ButterflyScroll::create, MobCategory.MISC)
                        .sized(1.0f, 1.0f)
                        .build(ButterflyScroll.NAME));

        PEACEMAKER_GOAL_REGISTRAR = new PeacemakerGoalRegistrar(ButterfliesMod.TAG_REGISTRY);
        PEACEMAKER_BUTTERFLY = registerPeacemakerButterfly();
        PEACEMAKER_EVOKER = registerPeacemakerEvoker();
        PEACEMAKER_ILLUSIONER = registerPeacemakerIllusioner();
        PEACEMAKER_PILLAGER = registerPeacemakerPillager();
        PEACEMAKER_VILLAGER = registerPeacemakerVillager();
        PEACEMAKER_VINDICATOR = registerPeacemakerVindicator();
        PEACEMAKER_WANDERING_TRADER = registerPeacemakerWanderingTrader();
        PEACEMAKER_WITCH = registerPeacemakerWitch();
    }

    // Entity factory methods
    private static Butterfly createButterfly(EntityType<? extends Butterfly> entityType, Level level) {
        return new Butterfly(entityType, level);
    }

    private static Butterfly createIceButterfly(EntityType<? extends Butterfly> entityType, Level level) {
        return new ParticleButterfly(entityType, level, ParticleTypes.ELECTRIC_SPARK);
    }

    private static Butterfly createLavaMoth(EntityType<? extends Butterfly> entityType, Level level) {
        return new ParticleButterfly(entityType, level, ParticleTypes.DRIPPING_DRIPSTONE_LAVA);
    }

    private static PeacemakerButterfly createPeacemakerButterfly(
            EntityType<? extends PeacemakerButterfly> entityType,
            Level level) {
        return new PeacemakerButterfly(PEACEMAKER_GOAL_REGISTRAR, entityType, level);
    }

    private static PeacemakerEvoker createPeacemakerEvoker(
            EntityType<? extends PeacemakerEvoker> entityType,
            Level level) {
        return new PeacemakerEvoker(PEACEMAKER_GOAL_REGISTRAR, entityType, level);
    }

    private static PeacemakerIllusioner createPeacemakerIllusioner(
            EntityType<? extends PeacemakerIllusioner> entityType,
            Level level) {
        return new PeacemakerIllusioner(PEACEMAKER_GOAL_REGISTRAR, entityType, level);
    }

    private static PeacemakerPillager createPeacemakerPillager(
            EntityType<? extends PeacemakerPillager> entityType,
            Level level) {
        return new PeacemakerPillager(PEACEMAKER_GOAL_REGISTRAR, entityType, level);
    }

    private static PeacemakerVillager createPeacemakerVillager(
            EntityType<? extends PeacemakerVillager> entityType,
            Level level) {
        return new PeacemakerVillager(entityType, level);
    }

    private static PeacemakerVindicator createPeacemakerVindicator(
            EntityType<? extends PeacemakerVindicator> entityType,
            Level level) {
        return new PeacemakerVindicator(PEACEMAKER_GOAL_REGISTRAR, entityType, level);
    }

    private static PeacemakerWanderingTrader createPeacemakerWanderingTrader(
            EntityType<? extends PeacemakerWanderingTrader> entityType,
            Level level) {
        return new PeacemakerWanderingTrader(entityType, level);
    }

    private static PeacemakerWitch createPeacemakerWitch(
            EntityType<? extends PeacemakerWitch> entityType,
            Level level) {
        return new PeacemakerWitch(entityType, level);
    }

    /**
     * Returns the appropriate entity factory based on butterfly traits.
     * @param butterflyIndex The index of the butterfly species.
     * @return The factory method for creating butterfly entities.
     */
    private static EntityType.@NotNull EntityFactory<Butterfly> getEntityFactory(int butterflyIndex) {
        ButterflyData.Trait[] traits = ButterflyInfo.TRAITS[butterflyIndex];

        for (ButterflyData.Trait trait : traits) {
            if (trait == ButterflyData.Trait.ICY) {
                return EntityTypeRegistry::createIceButterfly;
            }
            if (trait == ButterflyData.Trait.LAVA) {
                return EntityTypeRegistry::createLavaMoth;
            }
        }
        return EntityTypeRegistry::createButterfly;
    }

    // Registration methods
    private static RegistryObject<EntityType<? extends Butterfly>> registerButterfly(int butterflyIndex) {
        String registryId = Butterfly.getRegistryId(butterflyIndex);
        EntityType.EntityFactory<Butterfly> entityFactory = getEntityFactory(butterflyIndex);

        return REGISTER.register(registryId,
                () -> EntityType.Builder.of(entityFactory, BUTTERFLY_SPAWN_POOL)
                        .sized(0.3f, 0.2f)
                        .clientTrackingRange(10)
                        .build(registryId));
    }

    private static RegistryObject<EntityType<ButterflyEgg>> registerButterflyEgg(int butterflyIndex) {
        String registryId = ButterflyEgg.getRegistryId(butterflyIndex);
        return REGISTER.register(registryId,
                () -> EntityType.Builder.of(ButterflyEgg::new, BUTTERFLY_SPAWN_POOL)
                        .sized(0.1f, 0.1f)
                        .build(registryId));
    }

    private static RegistryObject<EntityType<IronGolem>> registerButterflyGolem() {
        String registryId = "butterfly_golem";
        return REGISTER.register(registryId,
                () -> EntityType.Builder.of(IronGolem::new, MobCategory.MISC)
                        .sized(1.4F, 2.7F)
                        .clientTrackingRange(10)
                        .build(registryId));
    }

    private static RegistryObject<EntityType<ButterflyScroll>> registerButterflyScroll(int butterflyIndex) {
        String registryId = ButterflyScroll.getRegistryId(butterflyIndex);
        return REGISTER.register(registryId,
                () -> EntityType.Builder.of(ButterflyScroll::create, MobCategory.MISC)
                        .sized(1.0f, 1.0f)
                        .build(registryId));
    }

    private static RegistryObject<EntityType<Caterpillar>> registerCaterpillar(int butterflyIndex) {
        String registryId = Caterpillar.getRegistryId(butterflyIndex);
        return REGISTER.register(registryId,
                () -> EntityType.Builder.of(Caterpillar::new, BUTTERFLY_SPAWN_POOL)
                        .sized(0.1f, 0.1f)
                        .build(registryId));
    }

    private static RegistryObject<EntityType<Chrysalis>> registerChrysalis(int butterflyIndex) {
        String registryId = Chrysalis.getRegistryId(butterflyIndex);
        return REGISTER.register(registryId,
                () -> EntityType.Builder.of(Chrysalis::new, BUTTERFLY_SPAWN_POOL)
                        .sized(0.1f, 0.1f)
                        .build(registryId));
    }

    private static RegistryObject<EntityType<PeacemakerButterfly>> registerPeacemakerButterfly() {
        String registryId = "peacemaker_butterfly";
        return REGISTER.register(registryId,
                () -> EntityType.Builder.of(EntityTypeRegistry::createPeacemakerButterfly, MobCategory.MONSTER)
                        .sized(1.0f, 0.4f)
                        .clientTrackingRange(8)
                        .build(registryId));
    }

    private static RegistryObject<EntityType<PeacemakerEvoker>> registerPeacemakerEvoker() {
        String registryId = "peacemaker_evoker";
        return REGISTER.register(registryId,
                () -> EntityType.Builder.of(EntityTypeRegistry::createPeacemakerEvoker, MobCategory.MONSTER)
                        .sized(0.6f, 1.95f)
                        .clientTrackingRange(8)
                        .build(registryId));
    }

    private static RegistryObject<EntityType<PeacemakerIllusioner>> registerPeacemakerIllusioner() {
        String registryId = "peacemaker_illusioner";
        return REGISTER.register(registryId,
                () -> EntityType.Builder.of(EntityTypeRegistry::createPeacemakerIllusioner, MobCategory.MONSTER)
                        .sized(0.6f, 1.95f)
                        .clientTrackingRange(8)
                        .build(registryId));
    }

    private static RegistryObject<EntityType<PeacemakerPillager>> registerPeacemakerPillager() {
        String registryId = "peacemaker_pillager";
        return REGISTER.register(registryId,
                () -> EntityType.Builder.of(EntityTypeRegistry::createPeacemakerPillager, MobCategory.MONSTER)
                        .sized(0.6f, 1.95f)
                        .build(registryId));
    }

    private static RegistryObject<EntityType<PeacemakerVillager>> registerPeacemakerVillager() {
        String registryId = "peacemaker_villager";
        return REGISTER.register(registryId,
                () -> EntityType.Builder.of(EntityTypeRegistry::createPeacemakerVillager, MobCategory.MISC)
                        .sized(0.6f, 1.95f)
                        .clientTrackingRange(10)
                        .build(registryId));
    }

    private static RegistryObject<EntityType<PeacemakerVindicator>> registerPeacemakerVindicator() {
        String registryId = "peacemaker_vindicator";
        return REGISTER.register(registryId,
                () -> EntityType.Builder.of(EntityTypeRegistry::createPeacemakerVindicator, MobCategory.MONSTER)
                        .sized(0.6f, 1.95f)
                        .clientTrackingRange(10)
                        .build(registryId));
    }

    private static RegistryObject<EntityType<PeacemakerWanderingTrader>> registerPeacemakerWanderingTrader() {
        String registryId = "peacemaker_wandering_trader";
        return REGISTER.register(registryId,
                () -> EntityType.Builder.of(EntityTypeRegistry::createPeacemakerWanderingTrader, MobCategory.MISC)
                        .sized(0.6f, 1.95f)
                        .clientTrackingRange(10)
                        .build(registryId));
    }

    private static RegistryObject<EntityType<PeacemakerWitch>> registerPeacemakerWitch() {
        String registryId = "peacemaker_witch";
        return REGISTER.register(registryId,
                () -> EntityType.Builder.of(EntityTypeRegistry::createPeacemakerWitch, MobCategory.MONSTER)
                        .sized(0.6F, 1.95F)
                        .clientTrackingRange(8)
                        .build(registryId));
    }

    // Prevent construction.
    private EntityTypeRegistry() {}
}
