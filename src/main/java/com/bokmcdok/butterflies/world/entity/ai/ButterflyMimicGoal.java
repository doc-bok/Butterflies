package com.bokmcdok.butterflies.world.entity.ai;

import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

/**
 * Goal used by the common mimic butterfly.
 */
public class ButterflyMimicGoal extends AvoidEntityGoal<LivingEntity> {

    //  The butterfly mob.
    private final Butterfly butterflyMob;

    /**
     * Construction
     * @param mob The butterfly mob.x
     * @param avoidClass The class to avoid.
     * @param range The range to start avoiding the class.
     * @param walkSpeedModifier Modifier for the walk speed.
     * @param sprintSpeedModifier Modifier for the sprint speed.
     * @param predicateOnAvoidEntity The predicate to run on the avoid entity.
     */
    public ButterflyMimicGoal(Butterfly mob,
                              Class<LivingEntity> avoidClass,
                              float range,
                              double walkSpeedModifier,
                              double sprintSpeedModifier,
                              Predicate<LivingEntity> predicateOnAvoidEntity) {
        super(mob, avoidClass, range, walkSpeedModifier, sprintSpeedModifier, predicateOnAvoidEntity);

        this.butterflyMob = mob;

    }

    /**
     * When the goal starts, set a random texture on the butterfly.
     */
    @Override
    public void start() {
        super.start();

        // Set butterfly texture index to random number.
        int i = butterflyMob.getRandom().nextInt(ButterflyData.getNumButterflySpecies());
        butterflyMob.setMimicTextureIndex(i);
    }

    /**
     * When the goal ends, revert back to the default texture.
     */
    @Override
    public void stop() {
        super.stop();

        // Set butterfly texture index to -1.
        butterflyMob.setMimicTextureIndex(-1);
    }

    /**
     * Used for debug information.
     * @return The name of the goal.
     */
    @NotNull
    @Override
    public String toString() {
        return "Mimic / Texture = [" + this.butterflyMob.getTexture().toString() +
                "]";
    }
}
