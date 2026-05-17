package com.bokmcdok.butterflies.world.entity;

import com.bokmcdok.butterflies.registries.ItemRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * Interface to indicate an entity is infected with a Peacemaker Butterfly.
 */
public interface PeacemakerEntity {

    /**
     * Override the target goals to ignore peacemaker mobs
     */
    default void registerPathfinderGoals(PathfinderMob entity) {

        //  Tempt goals
        entity.goalSelector.addGoal(1,
                new TemptGoal(
                        entity,
                        1.25D,
                        Ingredient.of(ItemRegistry.PEACEMAKER_HONEY_BOTTLE.get()),
                        false));

        GoalSelector targetSelector = entity.targetSelector;
        targetSelector.removeAllGoals((x) -> true);
        targetSelector.addGoal(1, (new HurtByTargetGoal(entity))
                .setAlertOthers(PeacemakerEntity.class));
        targetSelector.addGoal(2, (new NearestAttackableTargetGoal<>(entity, Player.class, true))
                .setUnseenMemoryTicks(300));
        targetSelector.addGoal(3, (new NearestAttackableTargetGoal<>(entity, AbstractVillager.class, false,
                PeacemakerEntity::isNotPeacemaker)).setUnseenMemoryTicks(300));
        targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(entity, IronGolem.class, false));
    }

    /**
     * Checks whether the entity is a Peacemaker Butterfly.
     * @param entity The entity to check.
     * @return True if the entity is a Peacemaker Butterfly.
     */
    static boolean isNotPeacemaker(LivingEntity entity) {
        return !(entity instanceof PeacemakerEntity);
    }
}
