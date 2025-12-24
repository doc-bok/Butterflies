package com.bokmcdok.butterflies.world.entity.ai;

import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * Handles butterflies flying around mud puddles to gain nutrients.
 */
public class ButterflyMudPuddlingGoal extends MoveToBlockGoal {

    //  The butterfly using this goal.
    private final Butterfly butterfly;

    public ButterflyMudPuddlingGoal(Butterfly mob,
                                    double speedModifier,
                                    int searchRange,
                                    int verticalSearchRange) {
        super(mob, speedModifier, searchRange, verticalSearchRange);
        this.butterfly = mob;
    }

    /**
     * Set the accepted distance for mud puddling to provide benefits.
     * @return The distance at which mud puddling starts reducing age.
     */
    @Override
    public double acceptedDistance() {
        return 2.0;
    }

    /**
     * Stop using if time of day changes to an inactive time.
     * @return Whether the goal can continue being active.
     */
    @Override
    public boolean canContinueToUse() {
        return this.butterfly.getIsActive() && super.canContinueToUse();
    }

    /**
     * Butterflies can only mud puddle when active.
     * @return TRUE if the butterfly can mud puddle right now.
     */
    @Override
    public boolean canUse() {
        return this.butterfly.getIsActive() && super.canUse();
    }

    /**
     * Make the butterfly recalculate its path more often.
     * @return TRUE every half second.
     */
    @Override
    public boolean shouldRecalculatePath() {
        return this.tryTicks % 10 == 0;
    }

    /**
     * Update the butterfly's movement, reducing their age if they are close to
     * the mud puddle.
     */
    @Override
    public void tick() {
        ++this.tryTicks;

        if (this.isReachedTarget()) {

            // Don't allow butterflies to have an age outside its lifespan
            ButterflyData data = this.butterfly.getData();
            if (data.getOverallLifeSpan() != ButterflyData.Lifespan.IMMORTAL) {
                int newAge = Math.max(this.butterfly.getData().butterflyLifespan(), this.butterfly.getAge() - 2);
                this.butterfly.setAge(newAge);
            }

            // Even if the butterfly is immortal we should still have the
            // effect.
            this.butterfly.setMudPuddling();
        }

        if (this.shouldRecalculatePath()) {
            this.moveMobToBlock();
        }
    }

    /**
     * Used for debug information.
     * @return The name of the goal.
     */
    @NotNull
    @Override
    public String toString() {
        return "Mud Puddle / Target = [" + this.getMoveToTarget() +
                "] / Position = [" + this.butterfly.blockPosition() +
                "] / Age = [" + this.butterfly.getAge() +
                "]";
    }

    /**
     * Tells the goal if the butterfly is close to the mud puddle.
     * @return TRUE if the butterfly is close enough.
     */
    @Override
    protected boolean isReachedTarget() {
        return this.blockPos.closerToCenterThan(this.butterfly.position(), this.acceptedDistance());
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

        var state = levelReader.getBlockState(blockPos);
        if (!state.is(Blocks.WATER)) {
            return false;
        }

        return levelReader.getBlockState(blockPos.north()).is(Blocks.CLAY)
                || levelReader.getBlockState(blockPos.east()).is(Blocks.CLAY)
                || levelReader.getBlockState(blockPos.south()).is(Blocks.CLAY)
                || levelReader.getBlockState(blockPos.west()).is(Blocks.CLAY);
    }

    /**
     * Override so the butterfly moves to the specified target block and
     * "orbits" around it, not just the block above it.
     */
    @Override
    protected void moveMobToBlock() {
        Random random = this.mob.getRandom();
        this.mob.getNavigation().moveTo(
                (double)this.blockPos.getX() + (3.0 * random.nextDouble()) - 1.5,
                (double)this.blockPos.getY() + random.nextDouble() + 1.0,
                (double)this.blockPos.getZ() + (3.0 * random.nextDouble()) - 1.5,
                this.speedModifier);
    }
}
