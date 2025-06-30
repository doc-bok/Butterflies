package com.bokmcdok.butterflies.client.renderer.entity.state;

import it.unimi.dsi.fastutil.objects.ReferenceSortedSet;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ButterflyScrollRenderState extends LivingEntityRenderState {
    public int butterflyIndex;
    public Direction direction;
    public ResourceLocation scrollTexture;
}
