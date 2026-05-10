package com.bokmcdok.butterflies.world.entity.ai;

import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.registries.TagRegistry;
import com.bokmcdok.butterflies.world.entity.monster.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

/**
 * Helper class for Peacemaker entity goals.
 */
public class PeacemakerGoalRegistrar {

    // A reference to the Tag Registry.
    private final TagRegistry tagRegistry;

    /**
     * Construction
     * @param tagRegistry The tag registry to set.
     */
    public PeacemakerGoalRegistrar(@NotNull TagRegistry tagRegistry) {
        this.tagRegistry = tagRegistry;
    }

    /**
     * Override the target goals to ignore peacemaker mobs
     */
    public void registerGoals(PathfinderMob entity) {

        //  Tempt goals
        entity.goalSelector.addGoal(1,
                new TemptGoal(
                        entity,
                        1.25D,
                        Ingredient.of(ItemRegistry.PEACEMAKER_HONEY_BOTTLE.get()),
                        false));

        GoalSelector targetSelector = entity.targetSelector;
        targetSelector.removeAllGoals((x) -> true);
        targetSelector.addGoal(1, (new HurtByTargetGoal(entity, Raider.class))
                .setAlertOthers()
                .setAlertOthers(PeacemakerButterfly.class)
                .setAlertOthers(PeacemakerEvoker.class)
                .setAlertOthers(PeacemakerIllusioner.class)
                .setAlertOthers(PeacemakerPillager.class)
                .setAlertOthers(PeacemakerVindicator.class)
                .setAlertOthers(PeacemakerWitch.class));
        targetSelector.addGoal(2, (new NearestAttackableTargetGoal<>(entity, Player.class, true))
                .setUnseenMemoryTicks(300));
        targetSelector.addGoal(3, (new NearestAttackableTargetGoal<>(entity, AbstractVillager.class, false,
                this::isNotPeacemaker)).setUnseenMemoryTicks(300));
        targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(entity, IronGolem.class, false));
    }

    /**
     * Checks whether the entity is a Peacemaker Butterfly.
     * @param entity The entity to check.
     * @return True if the entity is a Peacemaker Butterfly.
     */
    public boolean isNotPeacemaker(LivingEntity entity) {
        return !entity.getType().is(this.tagRegistry.getPeacemakerEntities());
    }
}
