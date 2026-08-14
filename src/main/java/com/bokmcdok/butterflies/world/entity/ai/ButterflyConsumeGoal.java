package com.bokmcdok.butterflies.world.entity.ai;

import com.bokmcdok.butterflies.butterfly_data.ButterflyData;
import com.bokmcdok.butterflies.butterfly_data.ButterflyRegistry;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

/**
 * Goal that enables butterflies to eat crops.
 */
public class ButterflyConsumeGoal extends MoveToBlockGoal {

    private static final int POST_EAT_LINGER_TICKS = 10;

    // The butterfly using this goal.
    private final Butterfly butterfly;

    // The flower this butterfly prefers.
    private Block foodSource = null;

    // Has consumption been attempted yet?
    private boolean hasTried;
    private int postEatTicks;

    /**
     * Construction
     * @param mob                 The instance of the butterfly.
     * @param speedModifier       The speed modifier applied when this goal is in progress.
     * @param searchRange         The range to search for blocks.
     * @param verticalSearchRange The vertical range to search for blocks.
     */
    public ButterflyConsumeGoal(Butterfly mob,
                                double speedModifier,
                                int searchRange,
                                int verticalSearchRange) {
        super(mob, speedModifier, searchRange, verticalSearchRange);
        butterfly = mob;

        ButterflyData data = ButterflyRegistry.getEntry(butterfly.getButterflyIndex());
        if (data != null) {
            Optional<Holder.Reference<Block>> block = BuiltInRegistries.BLOCK.get(data.foodBlock());
            block.ifPresent(blockReference -> foodSource = blockReference.value());
        }
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
        if (!butterfly.getIsActive()) {
            return false;
        }

        if (hasTried) {
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
        return foodSource != null
                && butterfly.getIsActive()
                && super.canUse();
    }

    /**
     * Start using the goal - ensure the butterfly is not landed.
     */
    @Override
    public void start() {
        hasTried = false;
        postEatTicks = 0;
        super.start();
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

        Vec3 delta = butterfly.getDeltaMovement();
        butterfly.setDeltaMovement(0.0, delta.y, 0.0);

        if (!hasTried) {
            Level level = mob.level();
            BlockState state = level.getBlockState(blockPos);

            if (tryEatTarget(level, state)) {
                hasTried = true;
                postEatTicks = POST_EAT_LINGER_TICKS;
            }
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
        return "Eat Crop / Food Source = [" + Objects.toString(foodSource.toString(), "<none>") +
                "] / Target = [" + getMoveToTarget() +
                "] / Reached Target = [" + isReachedTarget() +
                "] / Has Eaten = [" + hasTried +
                "]";
    }

    /**
     * Tells the base goal which blocks are valid targets.
     * @param levelReader Gives access to the level.
     * @param blockPos    The block position to check.
     * @return TRUE if the block is a valid target.
     */
    @Override
    protected boolean isValidTarget(@NotNull LevelReader levelReader,
                                    @NotNull BlockPos blockPos) {
        if (foodSource == null) {
            return false;
        }

        if (!levelReader.getBlockState(blockPos.above()).isAir()) {
            return false;
        }

        BlockState blockState = levelReader.getBlockState(blockPos);
        return blockState.is(foodSource);
    }

    /**
     * Attempt to eat the target. If it has any kind of age property, its age
     * will be reduced by 1.
     * @param level    The current level.
     * @param state    The Block State of the target Block.
     */
    private boolean tryEatTarget(Level level,
                                 BlockState state) {
        IntegerProperty ageProperty = getEdibleAgeProperty(state);
        if (ageProperty == null) {
            return false;
        }

        int age = state.getValue(ageProperty);
        if (age <= 0) {
            return false;
        }

        BlockState newState = state.setValue(ageProperty, age - 1);
        level.setBlockAndUpdate(blockPos, newState);
        return true;
    }

    /**
     * Gets the age property for a block if it exists.
     * @param state The current block state.
     * @return The Age Property if it exists, otherwise null.
     */
    @Nullable
    private IntegerProperty getEdibleAgeProperty(BlockState state) {
        for (Property<?> property : state.getProperties()) {
            if (property instanceof IntegerProperty intProp && "age".equals(property.getName())) {
                return intProp;
            }
        }

        return null;
    }
}
