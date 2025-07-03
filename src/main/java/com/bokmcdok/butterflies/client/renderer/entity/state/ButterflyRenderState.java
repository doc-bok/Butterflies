package com.bokmcdok.butterflies.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ButterflyRenderState extends LivingEntityRenderState {
    public boolean isLanded;
    public boolean isMoth;
    public Direction landedDirection;
    public float renderScale;
    public String debugInfo;
    public ResourceLocation texture;
}
