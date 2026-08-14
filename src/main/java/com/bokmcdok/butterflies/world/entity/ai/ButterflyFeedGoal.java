package com.bokmcdok.butterflies.world.entity.ai;

import com.bokmcdok.butterflies.butterfly_data.ButterflyData;
import com.bokmcdok.butterflies.butterfly_data.ButterflyRegistry;
import com.bokmcdok.butterflies.world.block.entity.ButterflyFeederEntity;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Goal that enables butterflies to feed from Butterfly Feeders.
 */
public class ButterflyFeedGoal extends MoveToBlockGoal {

    private static final int POST_EAT_LINGER_TICKS = 10;

    // The butterfly using this goal.
    private final Butterfly butterfly;

    // The flower this butterfly prefers.
    private Holder.Reference<Item> foodSourceItem;

    // Has pollination been attempted yet?
    private boolean hasFedAtTarget;
    private int postEatTicks;

    /**
     * Construction
     * @param mob The instance of the butterfly.
     * @param speedModifier The speed modifier applied when this goal is in progress.
     * @param searchRange The range to search for blocks.
     * @param verticalSearchRange The vertical range to search for blocks.
     */
    public ButterflyFeedGoal(Butterfly mob,
                             double speedModifier,
                             int searchRange,
                             int verticalSearchRange) {
        super(mob, speedModifier, searchRange, verticalSearchRange);
        butterfly = mob;
        foodSourceItem = null;

        ButterflyData data = ButterflyRegistry.getEntry(butterfly.getButterflyIndex());
        if (data == null) {
            return;
        }

        Optional<Holder.Reference<Item>> itemReference = BuiltInRegistries.ITEM.get(data.foodItem());
        itemReference.ifPresent(reference -> foodSourceItem = reference);
    }

    /**
     * Increase the accepted distance.
     * @return A distance of 2 blocks.
     */
    @Override
    public double acceptedDistance() {
        return 2.0;
    }

    /**
     * Stop using if time of day changes to inactive.
     * @return Whether the goal can continue being active.
     */
    @Override
    public boolean canContinueToUse() {
        if (!canFeedNow()) {
            return false;
        }

        if (hasFedAtTarget) {
            return postEatTicks > 0;
        }

        return super.canContinueToUse();
    }

    /**
     * Butterflies can only pollinate when active.
     * @return TRUE if the butterfly can pollinate right now.
     */
    @Override
    public boolean canUse() {
        return canFeedNow() && super.canUse();
    }

    /**
     * Start using the goal - ensure the butterfly is not landed.
     */
    @Override
    public void start() {
        hasFedAtTarget = false;
        postEatTicks = 0;
        super.start();
    }

    /**
     * Ensure state is reset when we stop.
     */
    @Override
    public void stop() {
        hasFedAtTarget = false;
        postEatTicks = 0;
        super.stop();
    }

    /**
     * Update the butterfly after it has landed.
     */
    @Override
    public void tick() {
        super.tick();

        if (!isReachedTarget()) {
            return;
        }

        // Don't stay in the landed state for too long.
        Vec3 deltaMovement = butterfly.getDeltaMovement();
        butterfly.setDeltaMovement(0.0, deltaMovement.y, 0.0);

        if (!hasFedAtTarget) {
            hasFedAtTarget = true;
            postEatTicks = POST_EAT_LINGER_TICKS;

            tryEatFromFeeder();
        } else if (postEatTicks > 0) {
            --postEatTicks;
        }
    }

    /**
     * Used for debug information.
     * @return The name of the goal.
     */
    @NotNull
    @Override
    public String toString() {
        return "Feed / Target = [" + getMoveToTarget() +
                "] / Reached Target = [" + isReachedTarget() +
                "] / Has Tried = [" + hasFedAtTarget +
                "] / Num Eggs = [" + butterfly.getNumEggs() +
                "]";
    }

    /**
     * Tells the base goal which blocks are valid targets.
     * @param levelReader Gives access to the level.
     * @param blockPos The block position to check.
     * @return TRUE if the block is a valid target.
     */
    @Override
    protected boolean isValidTarget(@NotNull LevelReader levelReader,
                                    @NotNull BlockPos blockPos) {
        if (foodSourceItem == null) {
            return false;
        }

        if (!levelReader.getBlockState(blockPos.above()).isAir()) {
            return false;
        }

        if (levelReader.getBlockEntity(blockPos) instanceof ButterflyFeederEntity feeder) {
            return feeder.getItem(0).is(foodSourceItem);
        }

        return false;
    }

    /**
     * Check if the butterfly can feed right now.
     * @return True if the butterfly can feed.
     */
    private boolean canFeedNow() {
        if (foodSourceItem == null) {
            return false;
        }

        if (!butterfly.getIsActive()) {
            return false;
        }

        return butterfly.getNumEggs() == 0;
    }

    /**
     * Attempt to eat from a butterfly feeder.
     */
    private void tryEatFromFeeder() {
        if (!canFeedNow()) {
            return;
        }

        if (foodSourceItem == null) {
            return;
        }

        Level level = butterfly.level();
        if (!(level.getBlockEntity(blockPos) instanceof ButterflyFeederEntity feeder)) {
            return;
        }

        if (!feeder.getItem(0).is(foodSourceItem)) {
            return;
        }

        butterfly.setNumEggs(1);
        feeder.removeItem(0, 1);
    }
}
