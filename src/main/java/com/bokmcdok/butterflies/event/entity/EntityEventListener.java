package com.bokmcdok.butterflies.event.entity;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

/**
 * Holds event listeners for entities.
 */
@Mod.EventBusSubscriber(modid = ButterfliesMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntityEventListener {

    /**
     * On joining the world modify entities' goals so butterflies have predators.
     * @param event Information for the event.
     */
    @SubscribeEvent
    public static void onJoinWorld(EntityJoinLevelEvent event) {

        //  Cat
        if (event.getEntity() instanceof Cat cat) {
            cat.targetSelector.addGoal(1, new NonTameRandomTargetGoal<>(
                    cat, Butterfly.class, false, EntityEventListener::isButterflyAttackableByCat));
        }

        //  Foxes
        if (event.getEntity() instanceof Fox fox) {
            fox.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(
                    fox, Butterfly.class, false));
        }

        //  Ocelots and Parrots
        if (event.getEntity() instanceof Ocelot ||
            event.getEntity() instanceof Parrot) {

            Mob mob = (Mob)event.getEntity();
            mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(
                    mob, Butterfly.class, false));
        }

        //  Spiders, Cave Spiders, Witches, and Zombies of all kinds
        if (event.getEntity() instanceof Spider ||
            event.getEntity() instanceof Witch ||
            event.getEntity() instanceof Zombie) {

            Mob mob = (Mob)event.getEntity();
            mob.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(
                    mob, Butterfly.class, false));
        }

        //  Wolf
        if (event.getEntity() instanceof Wolf wolf) {
            wolf.targetSelector.addGoal(5, new NonTameRandomTargetGoal<>(
                    wolf, Butterfly.class, false, null));
        }
    }

    /**
     * Used to stop cats from attacking forester butterflies.
     * @param entity The entity the cat wants to target.
     * @return TRUE if the entity is any butterfly except for a Forester.
     */
    private static boolean isButterflyAttackableByCat(LivingEntity entity) {
        if (entity instanceof Butterfly butterfly) {
            return !Objects.equals(butterfly.getData().entityId(), "forester");
        }

        return false;
    }
}
