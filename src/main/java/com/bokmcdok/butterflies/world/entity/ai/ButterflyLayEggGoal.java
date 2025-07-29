package com.bokmcdok.butterflies.world.entity.ai;

import com.bokmcdok.butterflies.config.ButterfliesConfig;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import com.bokmcdok.butterflies.world.entity.animal.ButterflyEgg;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Goal that allows butterflies to lay eggs on leaves.
 */
public class ButterflyLayEggGoal extends ButterflyLandOnBlockGoal {

    /**
     * Construction
     * @param mob The instance of the butterfly.
     * @param speedModifier The speed modifier applied when this goal is in progress.
     * @param searchRange The range to search for blocks.
     * @param verticalSearchRange The vertical range to search for blocks.
     */
    public ButterflyLayEggGoal(Butterfly mob,
                                    double speedModifier,
                                    int searchRange,
                                    int verticalSearchRange) {
        super(mob, speedModifier, searchRange, verticalSearchRange);
    }

    /**
     * Stop using if time of day changes to inactive.
     * @return Whether the goal can continue being active.
     */
    @Override
    public boolean canContinueToUse() {
        return this.butterfly.getIsActive() && super.canContinueToUse();
    }

    /**
     * We can only use this goal if the butterfly has an egg to lay.
     * @return TRUE if we can use this goal.
     */
    @Override
    public boolean canUse() {
        return this.butterfly.getIsActive() &&
                butterfly.getIsFertile() &&
                super.canUse();
    }

    /**
     * Update the butterfly after it has landed.
     */
    @Override
    public void tick() {
        super.tick();

        if (this.butterfly.getIsLanded()) {

            this.tryTicks -= 11;
            
            if (this.butterfly.getIsFertile()) {

                // Don't lay an egg if there are too many butterflies in the area already.
                List<Butterfly> numButterflies = this.butterfly.level.getNearbyEntities(
                        Butterfly.class,
                        TargetingConditions.forNonCombat(),
                        butterfly,
                        this.butterfly.getBoundingBox().inflate(32.0D));

                int maxDensity = ButterfliesConfig.maxDensity.get();
                if (maxDensity == 0 || numButterflies.size() <= maxDensity) {
                    Direction direction = this.butterfly.getLandedDirection().getOpposite();
                    if (this.butterfly.getLevel().getBlockState(this.blockPos.relative(direction)).isAir()) {

                        // Always use the base butterfly type for eggs.
                        ButterflyData data = ButterflyData.getEntry(this.butterfly.getData().getBaseButterflyIndex());
                        if (data != null) {
                            ResourceLocation eggEntity = data.getButterflyEggEntity();
                            ButterflyEgg.spawn((ServerLevel) this.butterfly.getLevel(), eggEntity, this.blockPos, direction);
                            this.butterfly.setIsFertile(false);
                            this.butterfly.useEgg();
                        }
                    }
                }
            }
        }
    }

    /**
     * Used for debug information.
     * @return The name of the goal.
     */
    @NotNull
    @Override
    public String toString() {
        return "Lay Egg / Target = [" + this.getMoveToTarget() +
                "] / Position = [" + butterfly.blockPosition() +
                "] / Reached = [" + this.isReachedTarget() +
                "] / Landed = [" + butterfly.getIsLanded() +
                "] / Direction = [" + butterfly.getLandedDirection() +
                "] / Fertile = [" + this.butterfly.getIsFertile() +
                "] / Num Eggs = [" + this.butterfly.getNumEggs() +
                "]";
    }
}
