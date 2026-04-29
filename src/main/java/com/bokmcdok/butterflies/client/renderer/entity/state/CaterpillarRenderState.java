package com.bokmcdok.butterflies.client.renderer.entity.state;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CaterpillarRenderState extends DirectionalRenderState {
    public float renderScale;
    public String debugInfo;
    public ResourceLocation texture;
}
