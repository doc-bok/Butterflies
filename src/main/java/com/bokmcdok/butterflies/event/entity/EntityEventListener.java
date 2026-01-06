package com.bokmcdok.butterflies.event.entity;

import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.entity.animal.*;
import com.bokmcdok.butterflies.world.entity.monster.*;
import com.bokmcdok.butterflies.world.entity.npc.PeacemakerVillager;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

/**
 * Holds event listeners for entities.
 */
public class EntityEventListener {

    // The entity type registry.
    private final EntityTypeRegistry entityTypeRegistry;

    /**
     * Construction
     * @param forgeEventBus The event bus to register with.
     */
    public EntityEventListener(IEventBus forgeEventBus,
                               IEventBus modEventBus,
                               EntityTypeRegistry entityTypeRegistry) {
        forgeEventBus.register(this);
        forgeEventBus.addListener(this::onEntityJoinLevel);

        modEventBus.register(this);
        modEventBus.addListener(this::onEntityAttributeCreation);
        modEventBus.addListener(this::onSpawnPlacementRegister);

        this.entityTypeRegistry = entityTypeRegistry;
    }

    /**
     * Used to stop entities from attacking inedible butterflies.
     * @param entity The entity the cat wants to target.
     * @return TRUE if the entity is any butterfly except for a Forester.
     */
    private static boolean isButterflyEdible(LivingEntity entity) {
        if (entity instanceof Butterfly butterfly) {
            return !butterfly.getData().hasTrait(ButterflyData.Trait.INEDIBLE);
        }

        return false;
    }

    /**
     * Used to stop cats from attacking forester butterflies.
     * @param entity The entity the cat wants to target.
     * @return TRUE if the entity is any butterfly except for a Forester.
     */
    private static boolean isButterflyAttackableByCat(LivingEntity entity) {
        if (entity instanceof Butterfly butterfly) {
            return !butterfly.getData().hasTrait(ButterflyData.Trait.CATFRIEND) &&
                    isButterflyEdible(entity);
        }

        return false;
    }

    /**
     * Register the attributes for living entities
     */
    private void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        for (RegistryObject<EntityType<? extends Butterfly>> i : entityTypeRegistry.getButterflies()) {
            event.put(i.get(), Butterfly.createAttributes().build());
        }

        for (RegistryObject<EntityType<Caterpillar>> i : entityTypeRegistry.getCaterpillars()) {
            event.put(i.get(), Caterpillar.createAttributes().build());
        }

        for (RegistryObject<EntityType<Chrysalis>> i : entityTypeRegistry.getChrysalises()) {
            event.put(i.get(), Chrysalis.createAttributes().build());
        }

        for (RegistryObject<EntityType<ButterflyEgg>> i : entityTypeRegistry.getButterflyEggs()) {
            event.put(i.get(), ButterflyEgg.createAttributes().build());
        }

        event.put(entityTypeRegistry.getButterflyGolem().get(), IronGolem.createAttributes().build());
        event.put(entityTypeRegistry.getPeacemakerButterfly().get(), PeacemakerButterfly.createAttributes().build());
        event.put(entityTypeRegistry.getPeacemakerEvoker().get(), PeacemakerEvoker.createAttributes().build());
        event.put(entityTypeRegistry.getPeacemakerIllusioner().get(), PeacemakerIllusioner.createAttributes().build());
        event.put(entityTypeRegistry.getPeacemakerPillager().get(), PeacemakerPillager.createAttributes().build());
        event.put(entityTypeRegistry.getPeacemakerVillager().get(), PeacemakerVillager.createAttributes().build());
        event.put(entityTypeRegistry.getPeacemakerVindicator().get(), PeacemakerVindicator.createAttributes().build());
        event.put(entityTypeRegistry.getPeacemakerWitch().get(), PeacemakerWitch.createAttributes().build());
    }

    /**
     * On joining the world modify entities' goals so butterflies have predators.
     * @param event Information for the event.
     */
    private void onEntityJoinLevel(EntityJoinLevelEvent event) {

        //  Cat
        if (event.getEntity() instanceof Cat cat) {
            cat.targetSelector.addGoal(1, new NonTameRandomTargetGoal<>(
                    cat, Butterfly.class, false, EntityEventListener::isButterflyAttackableByCat));
        }

        //  Foxes
        if (event.getEntity() instanceof Fox fox) {
            fox.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(
                    fox, Butterfly.class, false, EntityEventListener::isButterflyEdible));
        }

        //  Ocelots and Parrots
        if (event.getEntity() instanceof Ocelot ||
                event.getEntity() instanceof Parrot) {

            Mob mob = (Mob) event.getEntity();
            mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(
                    mob, Butterfly.class, false, EntityEventListener::isButterflyEdible));
        }

        //  Spiders, Cave Spiders, Witches, and Zombies of all kinds
        if (event.getEntity() instanceof Spider ||
                event.getEntity() instanceof Witch ||
                event.getEntity() instanceof Zombie) {

            Mob mob = (Mob) event.getEntity();
            mob.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(
                    mob, Butterfly.class, false, EntityEventListener::isButterflyEdible));
        }

        //  Wolf
        if (event.getEntity() instanceof Wolf wolf) {
            wolf.targetSelector.addGoal(5, new NonTameRandomTargetGoal<>(
                    wolf, Butterfly.class, false, EntityEventListener::isButterflyEdible));
        }
    }

    /**
     * Register entity spawn placements here
     * @param event The event information
     */
    private void onSpawnPlacementRegister(SpawnPlacementRegisterEvent event) {
        for (RegistryObject<EntityType<? extends Butterfly>> i : entityTypeRegistry.getButterflies()) {
            event.register(i.get(),
                    SpawnPlacements.Type.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING,
                    Butterfly::checkButterflySpawnRules,
                    SpawnPlacementRegisterEvent.Operation.AND);
        }

        for (RegistryObject<EntityType<Caterpillar>> i : entityTypeRegistry.getCaterpillars()) {
            event.register(i.get(),
                    SpawnPlacements.Type.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING,
                    DirectionalCreature::checkDirectionalSpawnRules,
                    SpawnPlacementRegisterEvent.Operation.AND);
        }

        for (RegistryObject<EntityType<Chrysalis>> i : entityTypeRegistry.getChrysalises()) {
            event.register(i.get(),
                    SpawnPlacements.Type.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING,
                    DirectionalCreature::checkDirectionalSpawnRules,
                    SpawnPlacementRegisterEvent.Operation.AND);
        }

        for (RegistryObject<EntityType<ButterflyEgg>> i : entityTypeRegistry.getButterflyEggs()) {
            event.register(i.get(),
                    SpawnPlacements.Type.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING,
                    DirectionalCreature::checkDirectionalSpawnRules,
                    SpawnPlacementRegisterEvent.Operation.AND);
        }

        event.register(entityTypeRegistry.getButterflyGolem().get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(entityTypeRegistry.getPeacemakerButterfly().get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                Monster::checkMonsterSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(entityTypeRegistry.getPeacemakerEvoker().get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(entityTypeRegistry.getPeacemakerIllusioner().get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(entityTypeRegistry.getPeacemakerPillager().get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(entityTypeRegistry.getPeacemakerVillager().get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(entityTypeRegistry.getPeacemakerVindicator().get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(entityTypeRegistry.getPeacemakerWitch().get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);
    }
}