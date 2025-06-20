package com.bokmcdok.butterflies.event.entity;

import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.entity.animal.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
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
    }

    /**
     * On joining the world modify entities' goals so butterflies have predators.
     * @param event Information for the event.
     */
    private void onEntityJoinLevel(EntityJoinWorldEvent event) {

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
}