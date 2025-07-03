package com.bokmcdok.butterflies.client.model;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.client.renderer.entity.state.ButterflyRenderState;
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
 * A model of a butterfly.
 */
@OnlyIn(Dist.CLIENT)
public class ButterflyModel  extends EntityModel<ButterflyRenderState> {

    // Names for the various model parts.
    private static final String ANTENNAE = "antennae";
    private static final String BODY = "body";
    private static final String LEFT_WING = "left_wing";
    private static final String RIGHT_WING = "right_wing";

    //  Holds the layers for the butterfly.
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "butterfly"), "main");

    //  The body of the butterfly.
    private final ModelPart body;

    //  The left wing.
    private final ModelPart left_wing;

    //  The right wing.
    private final ModelPart right_wing;

    /**
     * Construct a butterfly model.
     * @param root The root part to attach the model to.
     */
    public ButterflyModel(ModelPart root) {
        super(root);

        this.body = root.getChild(BODY);
        this.left_wing = this.body.getChild(LEFT_WING);
        this.right_wing = this.body.getChild(RIGHT_WING);
    }

    /**
     * Create the 3D model.
     * @return The complete 3D model for the butterfly.
     */
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild(BODY, CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        body.addOrReplaceChild(BODY, CubeListBuilder.create()
                .texOffs(0, 20).addBox(-5.0F, -2.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        body.addOrReplaceChild(LEFT_WING, CubeListBuilder.create()
                .texOffs(0, 0).addBox(-9.0F, -1.0F, 1.0F, 17.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -1.0F, 0.0F));

        body.addOrReplaceChild(RIGHT_WING, CubeListBuilder.create()
                .texOffs(0, 10).addBox(-9.0F, -1.0F, -11.0F, 17.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -1.0F, 0.0F));

        body.addOrReplaceChild(ANTENNAE, CubeListBuilder.create()
                .texOffs(0, 0).addBox(-8.0F, -4.0F, -1.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-8.0F, -4.0F, 1.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    /**
     * Create a flying animation
     * @param renderState The current rendering state.
     */
    @Override
    public void setupAnim(@NotNull ButterflyRenderState renderState) {

        // Adjust these to modify wing animations
        final float WING_ARC = 0.25F;
        final float WING_SPEED = 1.3F;

        // Adjust these to modify body animations.
        final float BODY_ANGLE = 0.7853982F;
        final float BODY_ARC = 0.15f;
        final float BODY_SPEED = 0.1f;

        //  When landed butterflies hold their wings together.
        if (renderState.isLanded) {
            this.body.yRot = BODY_ANGLE;
            if (renderState.isMoth) {
                this.right_wing.xRot = 0.15F;

            // Butterflies raise their wings up.
            } else {
                this.right_wing.xRot = -1.495796F; // (0.15 - Mth.PI) * 0.5
            }
        } else {
            this.body.yRot = BODY_ANGLE + Mth.cos(renderState.ageInTicks * BODY_SPEED) * BODY_ARC;
            this.right_wing.xRot = Mth.sin(renderState.ageInTicks * WING_SPEED) * Mth.PI * WING_ARC;
        }

        this.left_wing.xRot = -right_wing.xRot;
    }
}