package com.bokmcdok.butterflies.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChrysalisRenderState extends LivingEntityRenderState {
    public float renderScale;
    public Direction surfaceDirection;
    public ResourceLocation texture;
}
