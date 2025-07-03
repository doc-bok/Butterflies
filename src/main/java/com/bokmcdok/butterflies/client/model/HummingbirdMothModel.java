package com.bokmcdok.butterflies.client.model;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * The model for the hummingbird moth.
 */
@OnlyIn(Dist.CLIENT)
public class HummingbirdMothModel extends HierarchicalModel<Butterfly> {

    // Names for the various model parts.
    private static final String ABDOMEN = "abdomen";
    private static final String ANTENNAE = "antennae";
    private static final String HEAD = "head";
    private static final String LEFT_WING = "left_wing";
    private static final String PROBOSCIS = "proboscis";
    private static final String RIGHT_WING = "right_wing";
    private static final String TAIL = "tail";
    private static final String THORAX = "thorax";
    private static final String WINGS = "wings";

    //  Holds the layers for the butterfly.
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "hummingbird_moth"), "main");

    // The main body and also the root of the model.
    private final ModelPart thorax;

    // The left wing.
    private final ModelPart left_wing;

    // The right wing.
    private final ModelPart right_wing;

    /**
     * Construct a butterfly model.
     * @param root The root part to attach the model to.
     */
    public HummingbirdMothModel(ModelPart root) {
        this.thorax = root.getChild(THORAX);
        // The wings.
        ModelPart wings = this.thorax.getChild(WINGS);
        this.left_wing = wings.getChild(LEFT_WING);
        this.right_wing = wings.getChild(RIGHT_WING);
    }

    /**
     * Create the 3D model.
     * @return The complete 3D model for the butterfly.
     */
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        // The upper part of the body.
        PartDefinition thorax = partdefinition.addOrReplaceChild(THORAX, CubeListBuilder.create().texOffs(0, 0)
                .addBox(-2.0F, -2.0F, -2.0F, 4.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 13.0F, 0.0F));

        // The lower part of the body.
        PartDefinition abdomen = thorax.addOrReplaceChild(ABDOMEN, CubeListBuilder.create().texOffs(14, 0)
                .addBox(-2.0F, 0.0F, 0.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, -1.0F));

        // The tail.
        abdomen.addOrReplaceChild(TAIL, CubeListBuilder.create().texOffs(14, 6)
                .addBox(-3.0F, 0.0F, 3.0F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, -1.0F));

        // The head.
        PartDefinition head = thorax.addOrReplaceChild(HEAD, CubeListBuilder.create().texOffs(10, 9)
                .addBox(-2.0F, -3.0F, -2.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

        // The antennae
        head.addOrReplaceChild(ANTENNAE, CubeListBuilder.create().texOffs(0, 15)
                .addBox(-3.0F, -7.0F, -2.0F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        // The proboscis that feeds on flowers.
        head.addOrReplaceChild(PROBOSCIS, CubeListBuilder.create().texOffs(0, -2)
                .addBox(0.0F, -2.0F, -12.0F, 0.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        // The wings.
        PartDefinition wings = thorax.addOrReplaceChild(WINGS, CubeListBuilder.create(), PartPose.offset(0.0F, 13.0F, 0.0F));
        wings.addOrReplaceChild(LEFT_WING, CubeListBuilder.create().texOffs(0, 4)
                .addBox(0.0F, -3.0F, 0.0F, 0.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -13.0F, 1.0F));
        wings.addOrReplaceChild(RIGHT_WING, CubeListBuilder.create().texOffs(0, 4)
                .addBox(0.0F, -3.0F, 0.0F, 0.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -13.0F, 1.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    /**
     * Create a flying animation
     * @param entity The butterfly entity
     * @param limbSwing Unused
     * @param limbSwingAmount Unused
     * @param ageInTicks The current age of the entity in ticks
     * @param netHeadYaw unused
     * @param headPitch unused
     */
    @Override
    public void setupAnim(@NotNull Butterfly entity,
                          float limbSwing,
                          float limbSwingAmount,
                          float ageInTicks,
                          float netHeadYaw,
                          float headPitch) {

        // Adjust these to modify wing animations
        final float WING_ARC = 0.2f;
        final float WING_SPEED = 13.0f;

        // Adjust these to modify body animations.
        final float BODY_ANGLE = 0.2853982f;
        final float BODY_ARC = 0.15f;
        final float BODY_SPEED = 0.1f;

        if (entity.getIsLanded()) {
            this.thorax.xRot = BODY_ANGLE + 0.8f;
            this.right_wing.yRot = 0.15f;
        } else {
            this.thorax.xRot = BODY_ANGLE + Mth.cos(ageInTicks * BODY_SPEED) * BODY_ARC;
            this.right_wing.yRot = Mth.sin(ageInTicks * WING_SPEED) * Mth.PI * WING_ARC;
        }

        this.left_wing.yRot = -right_wing.yRot;
    }

    /**
     * Get the root of the model.
     * @return The root ModelPart
     */
    @Override
    public @NotNull ModelPart root() {
        return this.thorax;
    }
}
