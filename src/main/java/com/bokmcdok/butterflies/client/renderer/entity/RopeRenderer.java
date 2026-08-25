package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.client.model.RopeModel;
import com.bokmcdok.butterflies.world.entity.decoration.RopeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class RopeRenderer extends EntityRenderer<RopeEntity> {

    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/entity/lead_knot.png");
    private final RopeModel<RopeEntity> model;

    public RopeRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new RopeModel<>(context.bakeLayer(RopeModel.LAYER_LOCATION));
    }

    @Override
    public void render(@NotNull RopeEntity ropeEntity,
                       float yaw,
                       float partialTicks,
                       PoseStack poseStack,
                       MultiBufferSource bufferSource,
                       int overlay) {
        poseStack.pushPose();
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        this.model.setupAnim(ropeEntity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = bufferSource.getBuffer(this.model.renderType(TEXTURE_LOCATION));
        this.model.renderToBuffer(poseStack, vertexconsumer, overlay, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
        super.render(ropeEntity, yaw, partialTicks, poseStack, bufferSource, overlay);
    }

    @NotNull
    @Override
    public ResourceLocation getTextureLocation(@NotNull RopeEntity ropeKnotEntity) {
        return TEXTURE_LOCATION;
    }
}
