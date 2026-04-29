package com.bokmcdok.butterflies.world.entity.monster;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.config.ButterfliesConfig;
import com.bokmcdok.butterflies.world.entity.DebugInfoSupplier;
import com.bokmcdok.butterflies.world.entity.EntityBehaviours;
import com.bokmcdok.butterflies.world.entity.ai.PeacemakerGoalRegistrar;
import com.bokmcdok.butterflies.world.entity.ai.navigation.ButterflyFlyingPathNavigation;
import net.minecraft.core.BlockPos;
import com.bokmcdok.butterflies.world.entity.npc.PeacemakerWanderingTrader;
import com.bokmcdok.butterflies.world.entity.npc.PeacemakerVillager;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class PeacemakerButterfly
        extends Monster
        implements DebugInfoSupplier {

    // Data accessors
    protected static final EntityDataAccessor<String> DATA_DEBUG_INFO =
            SynchedEntityData.defineId(PeacemakerButterfly.class, EntityDataSerializers.STRING);
    protected static final EntityDataAccessor<Optional<UUID>> DATA_FRIEND_UUID;


    // Constants for Peacemaker Butterfly attributes.
    private static final double PEACEMAKER_BUTTERFLY_ATTACK_DAMAGE = 3.0d;
    private static final double PEACEMAKER_BUTTERFLY_HEALTH = 6.0d;
    private static final double PEACEMAKER_BUTTERFLY_SPEED = 0.9d;

    // The item registry
    private final ItemRegistry itemRegistry;

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
    @SuppressWarnings({"unchecked"})
    public static void possess(ServerLevelAccessor level,
                               Villager villager) {

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

                    finalizePossess(level, villager, peacemakerVillager);
                }
            }
        }
    }

    /**
     * Convert a Wandering Trader to one with a Butterfly host
     * @param level The current level
     * @param wanderingTrader The wanderingTrader to convert
     */
    @SuppressWarnings({"unchecked"})
    public static void possess(ServerLevelAccessor level,
                               WanderingTrader wanderingTrader) {

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
                    finalizePossess(level, wanderingTrader, peacemakerWanderingTrader);
                }
            }
        }
    }

    /**
     * Finalizes the possession for villager-based entities.
     * @param level The current level.
     * @param unpossessed The unpossessed version of the entity to be removed.
     * @param possessed The possessed version of the entity to replace it with.
     */
    public static void finalizePossess(ServerLevelAccessor level,
                                       AbstractVillager unpossessed,
                                       AbstractVillager possessed) {
        possessed.finalizeSpawn(level,
                level.getCurrentDifficultyAt(possessed.blockPosition()),
                MobSpawnType.CONVERSION,
                null);

        possessed.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));

        EventHooks.onLivingConvert(unpossessed, possessed);

        if (!possessed.isSilent()) {
            level.levelEvent(null, 1026, possessed.blockPosition(), 0);
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
        Level level = entity.level();
        if (!level.isClientSide()) {
            spawn((ServerLevel) level, entity.blockPosition());
        }
    }

    /**
     * Spawns a peacemaker butterfly at the specified position.
     * @param level The current level.
     * @param position The position to spawn the butterfly.
     */
    public static void spawn(ServerLevel level,
                             BlockPos position) {

        final ResourceLocation PEACEMAKER_BUTTERFLY =
                ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "peacemaker_butterfly");

        EntityType<?> entityType =
                BuiltInRegistries.ENTITY_TYPE.get(PEACEMAKER_BUTTERFLY);

        if (entityType != null) {
            Entity newEntity = entityType.create(level);

            if (newEntity instanceof PeacemakerButterfly butterfly) {
                butterfly.setPos(position.getCenter());

                butterfly.finalizeSpawn(level,
                        level.getCurrentDifficultyAt(butterfly.getOnPos()),
                        MobSpawnType.CONVERSION,
                        null);

                level.addFreshEntity(butterfly);
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
    @SuppressWarnings("unchecked")
    private static <T extends Mob> void possess(ServerLevelAccessor level,
                                                Raider raider,
                                                String entityId) {
        ResourceLocation location = ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, entityId);
        EntityType<T> entityType = (EntityType<T>) BuiltInRegistries.ENTITY_TYPE.get(location);
        if (entityType != null) {
            if (EventHooks.canLivingConvert(raider, entityType, (x) -> {
            })) {
                T newMob = raider.convertTo(entityType, false);
                EntityBehaviours.finalizeConvert(level, raider, newMob);
            }
        }
    }

    /**
     * Construction
     * @param entityType The type of this entity.
     * @param level The currently loaded level.
     */
    public PeacemakerButterfly(@NotNull ItemRegistry itemRegistry,
                               @NotNull PeacemakerGoalRegistrar peacemakerGoalRegistrar,
                               EntityType<? extends Monster> entityType,
                               Level level) {
        super(entityType, level);

        this.itemRegistry = itemRegistry;

        if (!level.isClientSide()) {
            this.registerGoalsPost(peacemakerGoalRegistrar);
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
     * Get debug info for the Peacemaker Butterfly.
     * @return Any info that needs rendering.
     */
    @Override
    public String getDebugInfo() {
        return entityData.get(DATA_DEBUG_INFO);
    }

    /**
     * Handles events sent from the server.
     * @param eventId The ID of the event.
     */
    @Override
    public void handleEntityEvent(byte eventId) {
        if (eventId == 7) {
            this.spawnFriendParticles(true);
        } else if (eventId == 6) {
            this.spawnFriendParticles(false);
        } else {
            super.handleEntityEvent(eventId);
        }
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
     * Handles a player attempting to feed a Peacemaker Butterfly.
     * @param player The player interacting with the entity.
     * @param hand The hand they are using.
     * @return The result of the interaction.
     */
    @NotNull
    @Override
    public InteractionResult mobInteract(@NotNull Player player,
                                         @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        Level level = level();
        if (level.isClientSide()) {
            boolean shouldConsume =
                    this.getFriendUUID() != player.getUUID() &&
                    itemStack.is(itemRegistry.getPeacemakerHoneyBottle().get());
            return shouldConsume ? InteractionResult.CONSUME : InteractionResult.PASS;
        }

        if (itemStack.is(itemRegistry.getPeacemakerHoneyBottle().get())) {
            if (!player.getAbilities().instabuild) {
                player.setItemInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
            }

            if (this.random.nextInt(3) == 0) {
                this.setFriendUUID(player.getUUID());
                this.navigation.stop();
                this.setTarget(null);
                level.broadcastEntityEvent(this, (byte) 7);
            } else {
                level.broadcastEntityEvent(this, (byte) 6);
            }

            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    /**
     * Peacemaker Butterflies are never on a climbable block.
     * @return Always false.
     */
    public boolean onClimbable() {
        return false;
    }

    /**
     * Use custom travel code for flying creatures.
     * @param velocity The current velocity.
     */
    @Override
    public void travel(@NotNull Vec3 velocity) {
        EntityBehaviours.travel(this, velocity, this.getBlockPosBelowThatAffectsMyMovement());
    }

    /**
     * Peacemaker Butterflies don't take fall damage.
     * @param fallDistance The distance the entity has fallen.
     * @param onGround Whether the entity is on the ground.
     * @param blockState The current block state.
     * @param blockPos The current position.
     */
    @Override
    protected void checkFallDamage(double fallDistance,
                                   boolean onGround,
                                   @NotNull BlockState blockState,
                                   @NotNull BlockPos blockPos) {
        // No-op
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
     * A custom step for the AI update loop.
     */
    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();

        //  Don't do this unless the debug information flag is set.
        if (ButterfliesConfig.Server.debugInformation.get()) {
            StringBuilder debugOutput = new StringBuilder();
            WrappedGoal[] runningGoals = goalSelector.getAvailableGoals().toArray(WrappedGoal[]::new);

            for (WrappedGoal goal : runningGoals) {
                debugOutput.append(goal.getGoal());
                debugOutput.append(" / ");
            }

            setDebugInfo(debugOutput.toString());
        }
    }

    /**
     * Override to define extra data to be synced between server and client.
     */
    @Override
    protected void defineSynchedData(@NotNull SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_DEBUG_INFO, "");
        builder.define(DATA_FRIEND_UUID, Optional.empty());
    }

    /**
     * Register the goals for the Peacemaker Butterfly AI.
     */
    protected void registerGoalsPost(PeacemakerGoalRegistrar peacemakerGoalRegistrar) {

        //  Movement goals
        this.goalSelector.addGoal(0, new FloatGoal(this));
        //this.goalSelector.addGoal(6, new MoveThroughVillageGoal(this, 1.0D, false, 4, () -> false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomFlyingGoal(this, 1.0D));

        //  Look at goals
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        //  Attack goals
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));

        //  Targets
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this))
                .setAlertOthers()
                .setAlertOthers(PeacemakerButterfly.class)
                .setAlertOthers(PeacemakerEvoker.class)
                .setAlertOthers(PeacemakerIllusioner.class)
                .setAlertOthers(PeacemakerPillager.class)
                .setAlertOthers(PeacemakerVindicator.class)
                .setAlertOthers(PeacemakerWitch.class));

        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true,
                (x) -> x.getUUID() != this.getFriendUUID()));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Raider.class, false,
                peacemakerGoalRegistrar::isNotPeacemaker));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false,
                peacemakerGoalRegistrar::isNotPeacemaker));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));

        //  Tempt goals
        this.goalSelector.addGoal(1,
                new TemptGoal(
                        this,
                        1.25D,
                        Ingredient.of(itemRegistry.getPeacemakerHoneyBottle().get()),
                        false));
    }

    /**
     * Get the current friend UUID (if any)
     * @return The UUID of the friend.
     */
    @Nullable
    public UUID getFriendUUID() {
        return this.entityData.get(DATA_FRIEND_UUID).orElse(null);
    }
    
    /**
     * Set the debug info so it can be synchronized with the client for display.
     * @param debugInfo The debug info to set.
     */
    private void setDebugInfo(String debugInfo) {
        entityData.set(DATA_DEBUG_INFO, debugInfo);
    }

    /**
     * Set the friend's UUID.
     * @param uuid The UUID of the friend.
     */
    private void setFriendUUID(@Nullable UUID uuid) {
        this.entityData.set(DATA_FRIEND_UUID, Optional.ofNullable(uuid));
    }

    /**
     * Spawns particles based on whether the player successfully befriends the
     * Peacemaker Butterfly.
     * @param success True if the befriending attempt succeeds.
     */
    private void spawnFriendParticles(boolean success) {
        ParticleOptions particleType = ParticleTypes.HEART;
        if (!success) {
            particleType = ParticleTypes.SMOKE;
        }

        for(int i = 0; i < 7; ++i) {
            double d0 = this.random.nextGaussian() * 0.02;
            double d1 = this.random.nextGaussian() * 0.02;
            double d2 = this.random.nextGaussian() * 0.02;
            level().addParticle(
                    particleType,
                    this.getRandomX(1.0F),
                    this.getRandomY() + (double)0.5F,
                    this.getRandomZ(1.0F),
                    d0,
                    d1,
                    d2);
        }

    }

    /*
      Static initialisation.
     */
    static {
        DATA_FRIEND_UUID = SynchedEntityData.defineId(PeacemakerButterfly.class, EntityDataSerializers.OPTIONAL_UUID);
    }
}
