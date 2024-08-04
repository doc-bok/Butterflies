package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;

public class GlowButterflyRenderer extends ButterflyRenderer {

    /**
     * Bakes a new model for the renderer
     * @param context The current rendering context
     */
    public GlowButterflyRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    /**
     * Make the ice butterfly glow.
     * @param butterfly The butterfly.
     * @param blockPos The position of the butterfly.
     * @return Always max light level.
     */
    @Override
    protected int getBlockLightLevel(@NotNull Butterfly butterfly,
                                     @NotNull BlockPos blockPos) {
        return 15;
    }
}
