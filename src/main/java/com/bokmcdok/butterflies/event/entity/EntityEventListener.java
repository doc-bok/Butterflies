package com.bokmcdok.butterflies.event.entity;

import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.entity.animal.*;
import com.bokmcdok.butterflies.world.entity.monster.*;
import com.bokmcdok.butterflies.world.entity.npc.PeacemakerVillager;
import com.bokmcdok.butterflies.world.entity.npc.PeacemakerWanderingTrader;
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

    /**
     * Construction
     * @param forgeEventBus The event bus to register with.
     */
    public EntityEventListener(IEventBus forgeEventBus,
                               IEventBus modEventBus) {
        forgeEventBus.register(this);
        forgeEventBus.addListener(this::onEntityJoinLevel);

        modEventBus.register(this);
        modEventBus.addListener(this::onEntityAttributeCreation);
        modEventBus.addListener(this::onSpawnPlacementRegister);
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
        for (RegistryObject<EntityType<? extends Butterfly>> i : EntityTypeRegistry.BUTTERFLIES) {
            event.put(i.get(), Butterfly.createAttributes().build());
        }

        for (RegistryObject<EntityType<Caterpillar>> i : EntityTypeRegistry.CATERPILLARS) {
            event.put(i.get(), Caterpillar.createAttributes().build());
        }

        for (RegistryObject<EntityType<Chrysalis>> i : EntityTypeRegistry.CHRYSALISES) {
            event.put(i.get(), Chrysalis.createAttributes().build());
        }

        for (RegistryObject<EntityType<ButterflyEgg>> i : EntityTypeRegistry.BUTTERFLY_EGGS) {
            event.put(i.get(), ButterflyEgg.createAttributes().build());
        }

        event.put(EntityTypeRegistry.BUTTERFLY_GOLEM.get(), IronGolem.createAttributes().build());
        event.put(EntityTypeRegistry.PEACEMAKER_BUTTERFLY.get(), PeacemakerButterfly.createAttributes().build());
        event.put(EntityTypeRegistry.PEACEMAKER_EVOKER.get(), PeacemakerEvoker.createAttributes().build());
        event.put(EntityTypeRegistry.PEACEMAKER_ILLUSIONER.get(), PeacemakerIllusioner.createAttributes().build());
        event.put(EntityTypeRegistry.PEACEMAKER_PILLAGER.get(), PeacemakerPillager.createAttributes().build());
        event.put(EntityTypeRegistry.PEACEMAKER_VILLAGER.get(), PeacemakerVillager.createAttributes().build());
        event.put(EntityTypeRegistry.PEACEMAKER_VINDICATOR.get(), PeacemakerVindicator.createAttributes().build());
        event.put(EntityTypeRegistry.PEACEMAKER_WANDERING_TRADER.get(), PeacemakerWanderingTrader.createAttributes().build());
        event.put(EntityTypeRegistry.PEACEMAKER_WITCH.get(), PeacemakerWitch.createAttributes().build());
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
        for (RegistryObject<EntityType<? extends Butterfly>> i : EntityTypeRegistry.BUTTERFLIES) {
            event.register(i.get(),
                    SpawnPlacements.Type.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING,
                    Butterfly::checkButterflySpawnRules,
                    SpawnPlacementRegisterEvent.Operation.AND);
        }

        for (RegistryObject<EntityType<Caterpillar>> i : EntityTypeRegistry.CATERPILLARS) {
            event.register(i.get(),
                    SpawnPlacements.Type.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING,
                    DirectionalCreature::checkDirectionalSpawnRules,
                    SpawnPlacementRegisterEvent.Operation.AND);
        }

        for (RegistryObject<EntityType<Chrysalis>> i : EntityTypeRegistry.CHRYSALISES) {
            event.register(i.get(),
                    SpawnPlacements.Type.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING,
                    DirectionalCreature::checkDirectionalSpawnRules,
                    SpawnPlacementRegisterEvent.Operation.AND);
        }

        for (RegistryObject<EntityType<ButterflyEgg>> i : EntityTypeRegistry.BUTTERFLY_EGGS) {
            event.register(i.get(),
                    SpawnPlacements.Type.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING,
                    DirectionalCreature::checkDirectionalSpawnRules,
                    SpawnPlacementRegisterEvent.Operation.AND);
        }

        event.register(EntityTypeRegistry.BUTTERFLY_GOLEM.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(EntityTypeRegistry.PEACEMAKER_BUTTERFLY.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                Monster::checkMonsterSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(EntityTypeRegistry.PEACEMAKER_EVOKER.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(EntityTypeRegistry.PEACEMAKER_ILLUSIONER.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(EntityTypeRegistry.PEACEMAKER_PILLAGER.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(EntityTypeRegistry.PEACEMAKER_VILLAGER.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(EntityTypeRegistry.PEACEMAKER_VINDICATOR.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(EntityTypeRegistry.PEACEMAKER_WANDERING_TRADER.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);

        event.register(EntityTypeRegistry.PEACEMAKER_WITCH.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);
    }
}