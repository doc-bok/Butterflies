package com.bokmcdok.butterflies.world.entity.ai;

import com.bokmcdok.butterflies.registries.TagRegistry;
import com.bokmcdok.butterflies.world.entity.monster.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;

/**
 * Helper class for Peacemaker entity goals.
 */
public class PeacemakerGoals {

    // A reference to the Tag Registry.
    private TagRegistry tagRegistry;

    /**
     * Override the target goals to ignore peacemaker mobs
     */
    public void registerGoals(PathfinderMob entity) {
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
     * Set the tag registry for use with the target selector.
     * @param tagRegistry The tag registry to set.
     */
    public void setTagRegistry(TagRegistry tagRegistry) {
        this.tagRegistry = tagRegistry;
    }

    /**
     * Checks whether the entity is a Peacemaker Butterfly.
     * @param entity The entity to check.
     * @return True if the entity is a Peacemaker Butterfly.
     */
    public boolean isNotPeacemaker(LivingEntity entity,
                                   ServerLevel level) {
        if (this.tagRegistry != null) {
             return !entity.getType().is(this.tagRegistry.getPeacemakerEntities());
        }

        return false;
    }
}
