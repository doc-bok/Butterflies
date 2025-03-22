package com.bokmcdok.butterflies.client.model;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.client.renderer.entity.state.HummingbirdMothRenderState;
import net.minecraft.client.model.EntityModel;
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
public class HummingbirdMothModel extends EntityModel<HummingbirdMothRenderState> {

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
        super(root);

        this.thorax = root.getChild("thorax");

        // The wings.
        ModelPart wings = this.thorax.getChild("wings");
        this.left_wing = wings.getChild("left_wing");
        this.right_wing = wings.getChild("right_wing");
    }

    /**
     * Create the 3D model.
     * @return The complete 3D model for the butterfly.
     */
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        // The upper part of the body.
        PartDefinition thorax = partdefinition.addOrReplaceChild("thorax", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-2.0F, -2.0F, -2.0F, 4.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 13.0F, 0.0F));

        // The lower part of the body.
        PartDefinition abdomen = thorax.addOrReplaceChild("abdomen", CubeListBuilder.create().texOffs(14, 0)
                .addBox(-2.0F, 0.0F, 0.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, -1.0F));

        // The tail.
        abdomen.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(14, 6)
                .addBox(-3.0F, 0.0F, 3.0F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, -1.0F));

        // The head.
        PartDefinition head = thorax.addOrReplaceChild("head", CubeListBuilder.create().texOffs(10, 9)
                .addBox(-2.0F, -3.0F, -2.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

        // The proboscis that feeds on flowers.
        head.addOrReplaceChild("proboscis", CubeListBuilder.create().texOffs(0, -2)
                .addBox(0.0F, -2.0F, -12.0F, 0.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        // The wings.
        PartDefinition wings = thorax.addOrReplaceChild("wings", CubeListBuilder.create(), PartPose.offset(0.0F, 13.0F, 0.0F));
        wings.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 4)
                .addBox(0.0F, -3.0F, 0.0F, 0.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -13.0F, 1.0F));
        wings.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(0, 4)
                .addBox(0.0F, -3.0F, 0.0F, 0.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -13.0F, 1.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    /**
     * Create a flying animation.
     * @param renderState The current render state.
     */
    @Override
    public void setupAnim(@NotNull HummingbirdMothRenderState renderState) {

        // The angle that the body rests at.
        final float BODY_REST_ANGLE = 0.2853982f;

        // The speed at which the body "hovers".
        final float BODY_MOVE_SPEED = 0.1f;

        // The arc through which the body moves.
        final float BODY_MOVE_ARC = 0.15f;

        // The arc through which the wings travel.
        final float WING_ARC = 0.2f;

        // The speed at which wings flap.
        final float WING_SPEED = 13.0f;

        this.thorax.xRot = BODY_REST_ANGLE + Mth.cos(renderState.ageInTicks * BODY_MOVE_SPEED) * BODY_MOVE_ARC;
        this.right_wing.yRot = Mth.sin(renderState.ageInTicks * WING_SPEED) * Mth.PI * WING_ARC;
        this.left_wing.yRot = -right_wing.yRot;
    }
}
