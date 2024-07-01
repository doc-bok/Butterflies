package com.bokmcdok.butterflies.world.entity.ai;

import com.bokmcdok.butterflies.config.ButterfliesConfig;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import com.bokmcdok.butterflies.world.entity.animal.ButterflyEgg;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Goal that allows butterflies to lay eggs on leaves.
 */
public class ButterflyLayEggGoal extends MoveToBlockGoal {

    //  The butterfly using this goal.
    private final Butterfly butterfly;

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
        this.butterfly = mob;
    }

    /**
     * We can only use this goal if the butterfly has an egg to lay.
     * @return TRUE if we can use this goal.
     */
    @Override
    public boolean canUse() {
        return butterfly.getIsFertile() && super.canUse();
    }

    /**
     * Start using the goal - ensure the butterfly is not landed.
     */
    @Override
    public void start() {
        this.butterfly.setLanded(false);
        super.start();
    }

    /**
     * Update the butterfly after it has landed.
     */
    @Override
    public void tick() {
        super.tick();

        if (this.isReachedTarget()) {
            // Don't stay in the landed state for too long.
            this.tryTicks -= 11;

            Vec3 deltaMovement = this.butterfly.getDeltaMovement();
            this.butterfly.setLanded(true);
            this.butterfly.setDeltaMovement(0.0, deltaMovement.y, 0.0);

            if (this.butterfly.getIsFertile()) {

                // Don't lay an egg if there are too many butterflies in the area already.
                List<Butterfly> numButterflies = this.butterfly.level.getNearbyEntities(
                        Butterfly.class,
                        TargetingConditions.forNonCombat(),
                        butterfly,
                        this.butterfly.getBoundingBox().inflate(32.0D));

                int maxDensity = ButterfliesConfig.maxDensity.get();
                if (maxDensity == 0 || numButterflies.size() <= maxDensity) {

                    // Attempt to lay an egg.
                    Direction direction = switch (this.butterfly.getRandom().nextInt(6)) {
                        default -> Direction.UP;
                        case 1 -> Direction.DOWN;
                        case 2 -> Direction.NORTH;
                        case 3 -> Direction.EAST;
                        case 4 -> Direction.SOUTH;
                        case 5 -> Direction.WEST;
                    };

                    if (this.butterfly.level.getBlockState(this.blockPos.relative(direction)).isAir()) {
                        ResourceLocation eggEntity = ButterflyData.indexToButterflyEggEntity(this.butterfly.getButterflyIndex());
                        ButterflyEgg.spawn((ServerLevel) this.butterfly.level, eggEntity, this.blockPos, direction);
                        this.butterfly.setIsFertile(false);
                        this.butterfly.useEgg();
                    }
                }
            }
        } else {
            //  Give up on pathfinding quicker.
            this.tryTicks += 11;
        }
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

        if (!levelReader.isEmptyBlock(blockPos.above())) {
            return false;
        }

        if (levelReader.getBlockState(blockPos).is(BlockTags.LEAVES)) {
            return blockPos.getY() < this.butterfly.getBlockY();
        }

        return false;
    }
}
