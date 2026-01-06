package com.bokmcdok.butterflies.world.entity.npc;

import com.bokmcdok.butterflies.world.entity.monster.PeacemakerButterfly;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.VillagerGoalPackages;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class PeacemakerVillager extends Villager {

    // Constants for Peacemaker Illusioner attributes.
    private static final double PEACEMAKER_VILLAGER_FOLLOW_RANGE = 48.0d;
    private static final double PEACEMAKER_VILLAGER_SPEED = 0.7d;

    /**
     * Butterflies make their hosts faster, stronger, and tougher
     * @return Attributes for the entity
     */
    @NotNull
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, PEACEMAKER_VILLAGER_SPEED)
                .add(Attributes.FOLLOW_RANGE, PEACEMAKER_VILLAGER_FOLLOW_RANGE);
    }

    /**
     * Create a peacemaker vindicator.
     * @param type The entity type
     * @param level The current level
     */
    public PeacemakerVillager(EntityType<? extends PeacemakerVillager> type,
                              Level level) {
        super(type, level);
    }

    /**
     * Spawn a peacemaker butterfly after death
     * @param damageSource The source of the damage that killed the villager
     */
    @Override
    public void die(@NotNull DamageSource damageSource) {
        super.die(damageSource);
        PeacemakerButterfly.spawn(this);
    }

    /**
     * Refresh the villager's brain. Overridden so we can use our custom
     * register brain goals method.
     * @param level The current level.
     */
    @Override
    public void refreshBrain(@NotNull ServerLevel level) {
        Brain<Villager> brain = this.getBrain();
        brain.stopAll(level, this);
        this.brain = brain.copyWithoutBehaviors();
        this.registerBrainGoals(this.getBrain());
    }

    /**
     * Make a brain for the villager. Overridden so we can use our custom
     * register brain goals method.
     * @param saveData The save data to load from.
     * @return A new brain.
     */
    @Override
    protected @NotNull Brain<?> makeBrain(@NotNull Dynamic<?> saveData) {
        Brain<Villager> brain = this.brainProvider().makeBrain(saveData);
        this.registerBrainGoals(brain);
        return brain;
    }

    /**
     * Register a brain's goals. Overrides sleeping behaviour in normal
     * villagers.
     * @param brain The brain to create a new one from.
     */
    private void registerBrainGoals(Brain<Villager> brain) {
        VillagerProfession villagerprofession = this.getVillagerData().getProfession();
        if (this.isBaby()) {
            brain.setSchedule(Schedule.VILLAGER_BABY);
            brain.addActivity(Activity.PLAY, VillagerGoalPackages.getPlayPackage(0.5F));
        } else {
            brain.setSchedule(Schedule.VILLAGER_DEFAULT);
            brain.addActivityWithConditions(Activity.WORK,
                    VillagerGoalPackages.getWorkPackage(villagerprofession, 0.5F),
                    ImmutableSet.of(Pair.of(MemoryModuleType.JOB_SITE, MemoryStatus.VALUE_PRESENT)));
        }

        brain.addActivity(Activity.CORE, VillagerGoalPackages.getCorePackage(villagerprofession, 0.5F));
        brain.addActivityWithConditions(Activity.MEET,
                VillagerGoalPackages.getMeetPackage(villagerprofession, 0.5F),
                ImmutableSet.of(Pair.of(MemoryModuleType.MEETING_POINT, MemoryStatus.VALUE_PRESENT)));

        // Override rest so that Villagers infected with Peacemaker Butterflies
        // hide instead of sleeping in a bed.
        brain.addActivity(Activity.REST, VillagerGoalPackages.getHidePackage(villagerprofession, 0.5F));

        brain.addActivity(Activity.IDLE, VillagerGoalPackages.getIdlePackage(villagerprofession, 0.5F));
        brain.addActivity(Activity.PANIC, VillagerGoalPackages.getPanicPackage(villagerprofession, 0.5F));
        brain.addActivity(Activity.PRE_RAID, VillagerGoalPackages.getPreRaidPackage(villagerprofession, 0.5F));
        brain.addActivity(Activity.RAID, VillagerGoalPackages.getRaidPackage(villagerprofession, 0.5F));
        brain.addActivity(Activity.HIDE, VillagerGoalPackages.getHidePackage(villagerprofession, 0.5F));
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.setActiveActivityIfPossible(Activity.IDLE);
        brain.updateActivityFromSchedule(this.level().getDayTime(), this.level().getGameTime());
    }
}
