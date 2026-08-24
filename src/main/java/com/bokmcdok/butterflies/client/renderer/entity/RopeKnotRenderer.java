package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.client.model.RopeKnotModel;
import com.bokmcdok.butterflies.world.entity.decoration.RopeKnotEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class RopeKnotRenderer extends EntityRenderer<RopeKnotEntity> {

    private static final ResourceLocation KNOT_LOCATION = new ResourceLocation("textures/entity/lead_knot.png");
    private final RopeKnotModel<RopeKnotEntity> model;

    public RopeKnotRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new RopeKnotModel<>(context.bakeLayer(ModelLayers.LEASH_KNOT));
    }

    @Override
    public void render(@NotNull RopeKnotEntity ropeKnotEntity,
                       float yaw,
                       float partialTicks,
                       PoseStack poseStack,
                       MultiBufferSource bufferSource,
                       int overlay) {
        poseStack.pushPose();
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        this.model.setupAnim(ropeKnotEntity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = bufferSource.getBuffer(this.model.renderType(KNOT_LOCATION));
        this.model.renderToBuffer(poseStack, vertexconsumer, overlay, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
        super.render(ropeKnotEntity, yaw, partialTicks, poseStack, bufferSource, overlay);
    }

    @NotNull
    @Override
    public ResourceLocation getTextureLocation(@NotNull RopeKnotEntity ropeKnotEntity) {
        return KNOT_LOCATION;
    }
}
