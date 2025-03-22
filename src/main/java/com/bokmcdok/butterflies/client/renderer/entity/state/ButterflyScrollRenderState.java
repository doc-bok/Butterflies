package com.bokmcdok.butterflies.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.core.Direction;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ButterflyScrollRenderState extends LivingEntityRenderState {
    public int butterflyIndex;
    public Direction direction;
}
