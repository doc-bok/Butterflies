package com.bokmcdok.butterflies.client.model;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.entity.animal.Chrysalis;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Model for a chrysalis entity.
 */
@OnlyIn(Dist.CLIENT)
public class ChrysalisModel extends EntityModel<Chrysalis> {

    // Holds the layers for the model.
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "chrysalis"), "main");

    //  The core of the model
    private final ModelPart main;

    /**
     * Constructor
     * @param root The root of the model.
     */
    public ChrysalisModel(ModelPart root) {
        main = root.getChild("main");
    }

    /**
     * Create a simple model for the chrysalis.
     * @return The new layer definition.
     */
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("main",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.0F, -16.0F, 0.0F, 8.0F, 16.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(0, -8).addBox(0.0F, -16.0F, -4.0F, 0.0F, 16.0F, 8.0F, new CubeDeformation(0.0F)),
                        PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    /**
     * Animate the model (or rather, don't)
     * @param entity The entity.
     * @param limbSwing The limb swing.
     * @param limbSwingAmount The limb swing amount.
     * @param ageInTicks Age of the entity in ticks.
     * @param netHeadYaw The head's yaw.
     * @param headPitch The head's pitch.
     */
    @Override
    public void setupAnim(@NotNull Chrysalis entity,
                          float limbSwing,
                          float limbSwingAmount,
                          float ageInTicks,
                          float netHeadYaw,
                          float headPitch) {
        // No-op
    }

    /**
     * Render the model.
     * @param poseStack The pose stack.
     * @param vertexConsumer The vertex consumer.
     * @param packedLight The packed light.
     * @param packedOverlay The packed overlay.
     * @param color The packed color.
     */
    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack,
                               @NotNull VertexConsumer vertexConsumer,
                               int packedLight,
                               int packedOverlay,
                               int color) {
        this.main.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}
