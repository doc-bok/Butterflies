package com.bokmcdok.butterflies.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ButterflyEggRenderState extends LivingEntityRenderState {
    public Direction surfaceDirection;
    public float renderScale;
    public ResourceLocation texture;
}
