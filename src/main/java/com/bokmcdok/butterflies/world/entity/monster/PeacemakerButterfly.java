package com.bokmcdok.butterflies.world.entity.monster;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.registries.TagRegistry;
import com.bokmcdok.butterflies.world.entity.ai.PeacemakerGoals;
import com.bokmcdok.butterflies.world.entity.ai.navigation.ButterflyFlyingPathNavigation;
import com.bokmcdok.butterflies.world.entity.npc.PeacemakerWanderingTrader;
import com.bokmcdok.butterflies.world.entity.npc.PeacemakerVillager;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class PeacemakerButterfly extends Monster {

    // Constants for Peacemaker Butterfly attributes.
    private static final double PEACEMAKER_BUTTERFLY_ATTACK_DAMAGE = 3.0d;
    private static final double PEACEMAKER_BUTTERFLY_HEALTH = 6.0d;
    private static final double PEACEMAKER_BUTTERFLY_SPEED = 0.9d;

    // The goals for shared code.
    private PeacemakerGoals peacemakerGoals;

    /**
     * Convert a raider to one with a butterfly host
     * @param level   The current level
     * @param raider The raider to convert
     */
    public static void possess(ServerLevelAccessor level,
                               Raider raider) {

        // Don't spawn in PEACEFUL difficulty, and reduce chances of spawn in
        // NORMAL difficulty.
        Difficulty difficulty = level.getDifficulty();
        if (difficulty == Difficulty.NORMAL || difficulty == Difficulty.HARD) {
            if (difficulty != Difficulty.HARD && raider.getRandom().nextBoolean()) {
                return;
            }

            if (raider instanceof Evoker) {
                possess(level, raider, "peacemaker_evoker");
            } else if (raider instanceof Illusioner) {
                possess(level, raider, "peacemaker_illusioner");
            } else if (raider instanceof Pillager) {
                possess(level, raider, "peacemaker_pillager");
            } else if (raider instanceof Vindicator) {
                possess(level, raider, "peacemaker_vindicator");
            } else if (raider instanceof Witch) {
                possess(level, raider, "peacemaker_witch");
            }
        }
    }

    /**
     * Set the eye height of the Peacemaker Butterfly. Ensures the bounding box
     * is correct.
     * @return The height of the entity's eyes.
     */
    @Override
    public double getEyeY() {
        return this.position().y + 0.4f;
    }

    /**
     * Convert a Villager to one with a Butterfly host
     * @param level The current level
     * @param villager The villager to convert
     */
    @SuppressWarnings({"unchecked", "UnstableApiUsage"})
    public static void possess(ServerLevelAccessor level,
                               Villager villager) {


        if (villager.level().isClientSide()) {
            return;
        }
        Difficulty difficulty = level.getDifficulty();
        if (difficulty == Difficulty.NORMAL || difficulty == Difficulty.HARD) {
            if (difficulty != Difficulty.HARD && villager.getRandom().nextBoolean()) {
                return;
            }

            ResourceLocation location = ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "peacemaker_villager");
            EntityType<PeacemakerVillager> entityType = (EntityType<PeacemakerVillager>) BuiltInRegistries.ENTITY_TYPE.get(location);
            if (EventHooks.canLivingConvert(villager, entityType, (x) -> {
            })) {
                PeacemakerVillager peacemakerVillager = villager.convertTo(entityType, false);
                if (peacemakerVillager != null) {
                    peacemakerVillager.setVillagerData(villager.getVillagerData());
                    peacemakerVillager.setGossips(villager.getGossips().store(NbtOps.INSTANCE));
                    peacemakerVillager.setOffers(villager.getOffers());
                    peacemakerVillager.setVillagerXp(villager.getVillagerXp());
                    peacemakerVillager.finalizeSpawn(level,
                            level.getCurrentDifficultyAt(peacemakerVillager.blockPosition()),
                            MobSpawnType.CONVERSION,
                            null);

                    peacemakerVillager.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));

                    EventHooks.onLivingConvert(villager, peacemakerVillager);

                    if (!peacemakerVillager.isSilent()) {
                        level.levelEvent(null, 1026, peacemakerVillager.blockPosition(), 0);
                    }
                }
            }
        }
    }

    /**
     * Convert a Wandering Trader to one with a Butterfly host
     * @param level The current level
     * @param wanderingTrader The wanderingTrader to convert
     */
    @SuppressWarnings({"unchecked", "UnstableApiUsage"})
    public static void possess(ServerLevelAccessor level,
                               WanderingTrader wanderingTrader) {


        if (wanderingTrader.level().isClientSide()) {
            return;
        }
        Difficulty difficulty = level.getDifficulty();
        if (difficulty == Difficulty.NORMAL || difficulty == Difficulty.HARD) {
            if (difficulty != Difficulty.HARD && wanderingTrader.getRandom().nextBoolean()) {
                return;
            }

            ResourceLocation location = ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "peacemaker_wandering_trader");
            EntityType<PeacemakerWanderingTrader> entityType = (EntityType<PeacemakerWanderingTrader>)BuiltInRegistries.ENTITY_TYPE.get(location);
            if (entityType == null) {
                return;
            }

            if (EventHooks.canLivingConvert(wanderingTrader, entityType, (x) -> {
            })) {
                PeacemakerWanderingTrader peacemakerWanderingTrader = wanderingTrader.convertTo(entityType, false);
                if (peacemakerWanderingTrader != null) {

                    peacemakerWanderingTrader.finalizeSpawn(level,
                            level.getCurrentDifficultyAt(peacemakerWanderingTrader.blockPosition()),
                            MobSpawnType.CONVERSION,
                            null);

                    peacemakerWanderingTrader.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));

                    EventHooks.onLivingConvert(wanderingTrader, peacemakerWanderingTrader);

                    if (!peacemakerWanderingTrader.isSilent()) {
                        level.levelEvent(null, 1026, peacemakerWanderingTrader.blockPosition(), 0);
                    }
                }
            }
        }
    }

    /**
     * Generates attributes for the Peacemaker Butterfly.
     * @return A builder containing the mob's attributes.
     */
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.ATTACK_DAMAGE, PEACEMAKER_BUTTERFLY_ATTACK_DAMAGE)
                .add(Attributes.MAX_HEALTH, PEACEMAKER_BUTTERFLY_HEALTH)
                .add(Attributes.FLYING_SPEED, PEACEMAKER_BUTTERFLY_SPEED)
                .add(Attributes.MOVEMENT_SPEED, PEACEMAKER_BUTTERFLY_SPEED * 5d);
    }

    /**
     * Respawns a peacemaker butterfly after its host has died
     * @param entity The host entity
     */
    public static void spawn(LivingEntity entity) {
        final ResourceLocation PEACEMAKER_BUTTERFLY =
                ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "peacemaker_butterfly");

        if (!entity.level().isClientSide()) {
            EntityType<?> entityType =
                    BuiltInRegistries.ENTITY_TYPE.get(PEACEMAKER_BUTTERFLY);
            if (entityType != null) {

                Entity newEntity = entityType.create(entity.level());
                if (newEntity instanceof PeacemakerButterfly butterfly) {
                    butterfly.copyPosition(entity);
                    butterfly.finalizeSpawn((ServerLevel) entity.level(),
                            butterfly.level().getCurrentDifficultyAt(butterfly.getOnPos()),
                            MobSpawnType.CONVERSION,
                            null);
                    entity.level().addFreshEntity(butterfly);
                }
            }
        }
    }

    /**
     * Convert a raider to one with a butterfly host
     * @param level    The current level
     * @param raider  The raider to convert
     * @param entityId The ID of the entity
     * @param <T>      The entity class
     */
    @SuppressWarnings({"UnstableApiUsage", "deprecation", "OverrideOnly", "unchecked"})
    private static <T extends Mob> void possess(ServerLevelAccessor level,
                                                Raider raider,
                                                String entityId) {

        if (!raider.level().isClientSide()) {
            ResourceLocation location = ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, entityId);
            EntityType<T> entityType = (EntityType<T>)BuiltInRegistries.ENTITY_TYPE.get(location);
            if (entityType != null) {

                if (EventHooks.canLivingConvert(raider, entityType, (x) -> {
                })) {
                    T newMob = raider.convertTo(entityType, false);
                    if (newMob != null) {
                        newMob.finalizeSpawn(level,
                                level.getCurrentDifficultyAt(newMob.blockPosition()),
                                MobSpawnType.CONVERSION,
                                null);

                        EventHooks.onLivingConvert(raider, newMob);

                        if (!newMob.isSilent()) {
                            level.levelEvent(null, 1026, newMob.blockPosition(), 0);
                        }
                    }
                }
            }
        }
    }

    /**
     * Construction
     * @param entityType The type of this entity.
     * @param level The currently loaded level.
     */
    public PeacemakerButterfly(TagRegistry tagRegistry,
                               EntityType<? extends Monster> entityType,
                               Level level) {
        super(entityType, level);

        if (!this.level().isClientSide()) {
            this.peacemakerGoals.setTagRegistry(tagRegistry);
        }

        // Setup for a flying mob.
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.setNoGravity(true);
    }

    /**
     * Overrides how fall damage is applied to the entity. Butterflies ignore
     * all fall damage.
     * @param fallDistance The distance fallen.
     * @param blockModifier The damage modifier for the block landed on.
     * @param damageSource The source of the damage.
     * @return Always false, as no damage is applied.
     */
    @Override
    public boolean causeFallDamage(float fallDistance,
                                   float blockModifier,
                                   @NotNull DamageSource damageSource) {
        return false;
    }

    /**
     * Set persistence if we are spawning from a spawn egg.
     * @param levelAccessor Access to the level.
     * @param difficulty The local difficulty.
     * @param spawnType The type of spawn.
     * @param groupData The group data.
     * @return The updated group data.
     */
    @SuppressWarnings("deprecation")
    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor levelAccessor,
                                        @NotNull DifficultyInstance difficulty,
                                        @NotNull MobSpawnType spawnType,
                                        @Nullable SpawnGroupData groupData) {
        if (spawnType == MobSpawnType.SPAWN_EGG) {
            setPersistenceRequired();
        }

        return super.finalizeSpawn(levelAccessor, difficulty, spawnType, groupData);
    }

    /**
     * Convert villagers and pillagers to Peacemaker mobs
     * @param level The current level
     * @param victim The entity just "killed"
     */
    @Override
    public boolean killedEntity(@NotNull ServerLevel level,
                       @NotNull LivingEntity victim) {

        if (victim instanceof Raider raider) {
            possess(level, raider);
            this.remove(RemovalReason.DISCARDED);
        }

        if (victim instanceof Villager villager) {
            possess(level, villager);
            if (!this.isSilent()) {
                level.levelEvent(null, 1027, this.blockPosition(), 0);
            }

            this.remove(RemovalReason.DISCARDED);
        }

        if (victim instanceof WanderingTrader wanderingTrader) {
            possess(level, wanderingTrader);
            if (!this.isSilent()) {
                level.levelEvent(null, 1027, this.blockPosition(), 0);
            }

            this.remove(RemovalReason.DISCARDED);
        }

        return super.killedEntity(level, victim);
    }

    /**
     * Hacky fix to stop butterflies teleporting.
     * TODO: We need a better fix than this.
     * @param x The x-position.
     * @param y The y-position.
     * @param z The z-position.
     */
    @Override
    public void setPos(double x, double y, double z) {
        Vec3 delta = new Vec3(x, y, z).subtract(this.position());
        if (delta.lengthSqr() <= 5 || this.position().lengthSqr() == 0) {
            super.setPos(x, y, z);
        }
    }

    /**
     * Create a flying navigator for the Peacemaker Butterfly. Uses the
     * butterfly navigation class.
     * @param level The current level.
     * @return The flying navigation.
     */
    @Override
    @NotNull
    protected PathNavigation createNavigation(@NotNull Level level) {
        return new ButterflyFlyingPathNavigation(this, level);
    }

    /**
     * Register the goals for the Peacemaker Butterfly AI.
     */
    @Override
    protected void registerGoals() {

        peacemakerGoals = new PeacemakerGoals();

        //  Movement goals
        this.goalSelector.addGoal(0, new FloatGoal(this));
        //this.goalSelector.addGoal(6, new MoveThroughVillageGoal(this, 1.0D, false, 4, () -> false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomFlyingGoal(this, 1.0D));

        //  Look at goals
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        //  Attack goals
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));

        //  Tempt goals
        //this.goalSelector.addGoal(1, new TemptGoal(this, 1.25D, Ingredient.of(ItemList.PEACEMAKER_HONEY_BOTTLE.get()), false));

        //  Targets
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this))
                .setAlertOthers()
                .setAlertOthers(PeacemakerButterfly.class)
                .setAlertOthers(PeacemakerEvoker.class)
                .setAlertOthers(PeacemakerIllusioner.class)
                .setAlertOthers(PeacemakerPillager.class)
                .setAlertOthers(PeacemakerVindicator.class)
                .setAlertOthers(PeacemakerWitch.class));

        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Raider.class, false,
                peacemakerGoals::isNotPeacemaker));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false,
                peacemakerGoals::isNotPeacemaker));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    @Override
    public boolean hurt(DamageSource source,
                        float amount) {
        LogUtils.getLogger().info("Damaged for [{}] by [{}]", amount, source.toString());
        return super.hurt(source, amount);
    }
}
