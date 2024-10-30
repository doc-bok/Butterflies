package com.bokmcdok.butterflies.client.model;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.client.model.IronGolemModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * The model for a butterfly golem.
 */
@OnlyIn(Dist.CLIENT)
public class ButterflyGolemModel extends IronGolemModel<IronGolem> {

    // Holds the layers for the model.
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(ButterfliesMod.MOD_ID, "butterfly_golem"), "main");

    // The golem's wings.
    private final ModelPart leftWing;
    private final ModelPart rightWing;

    /**
     * Construction
     * @param modelPart The base part for the model.
     */
    public ButterflyGolemModel(ModelPart modelPart) {
        super(modelPart);
        this.leftWing = root().getChild("left_wing");
        this.rightWing = root().getChild("right_wing");
    }

    /**
     * Create the layer definition that includes the wings.
     * @return The new layer definition.
     */
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-4.0F, -12.0F, -5.5F, 8.0F, 10.0F, 8.0F)
                .texOffs(24, 0).addBox(-1.0F, -5.0F, -7.5F, 2.0F, 4.0F, 2.0F)
                .texOffs(0, 18).addBox(-7.0F, -15.0F, -1.0F, 14.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -7.0F, -2.0F));

        partDefinition.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 40).addBox(-9.0F, -2.0F, -6.0F, 18.0F, 12.0F, 11.0F)
                .texOffs(0, 70).addBox(-4.5F, 10.0F, -3.0F, 9.0F, 5.0F, 6.0F, new CubeDeformation(0.5F)),
                PartPose.offset(0.0F, -7.0F, 0.0F));

        partDefinition.addOrReplaceChild("right_arm", CubeListBuilder.create()
                .texOffs(60, 21).addBox(-13.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F),
                PartPose.offset(0.0F, -7.0F, 0.0F));

        partDefinition.addOrReplaceChild("left_arm", CubeListBuilder.create()
                .texOffs(60, 58).addBox(9.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F),
                PartPose.offset(0.0F, -7.0F, 0.0F));

        partDefinition.addOrReplaceChild("right_leg", CubeListBuilder.create()
                .texOffs(37, 0).addBox(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F),
                PartPose.offset(-4.0F, 11.0F, 0.0F));

        partDefinition.addOrReplaceChild("left_leg", CubeListBuilder.create()
                .texOffs(60, 0).mirror().addBox(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F),
                PartPose.offset(5.0F, 11.0F, 0.0F));

        partDefinition.addOrReplaceChild("left_wing", CubeListBuilder.create()
                .texOffs(20, 87).addBox(0.0F, -18.0F, 0.0F, 20.0F, 41.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -3.0F, 5.0F, 0.0F, -0.5236F, 0.0F));

        partDefinition.addOrReplaceChild("right_wing", CubeListBuilder.create()
                .texOffs(0, 87).addBox(-20.0F, -18.0F, 0.0F, 20.0F, 41.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -3.0F, 5.0F, 0.0F, 0.5236F, 0.0F));

        return LayerDefinition.create(meshDefinition, 128, 128);
    }

    /**
     * Animate the wings.
     * @param entity The butterfly entity
     * @param limbSwing Unused
     * @param limbSwingAmount Unused
     * @param ageInTicks The current age of the entity in ticks
     * @param netHeadYaw unused
     * @param headPitch unused
     */
    @Override
    public void setupAnim(@NotNull IronGolem entity,
                          float limbSwing,
                          float limbSwingAmount,
                          float ageInTicks,
                          float netHeadYaw,
                          float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        this.rightWing.yRot = 0.5F - 1.5F * Mth.triangleWave(limbSwing, 13.0F) * limbSwingAmount;
        this.leftWing.yRot = -0.5f + 1.5F * Mth.triangleWave(limbSwing, 13.0F) * limbSwingAmount;
    }
}
