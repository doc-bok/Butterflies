package com.bokmcdok.butterflies.client.model;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * The model and animations for a Peacemaker Butterfly.
 */
@OnlyIn(Dist.CLIENT)
public class PeacemakerCowModel extends EntityModel<LivingEntityRenderState> {
    
    // Names for the various model parts.
    private static final String BODY = "body";
    private static final String HEAD = "head";
    private static final String TAIL = "tail";
    private static final String LEGS = "legs";
    private static final String FRONT_LEFT = "front_left";
    private static final String FRONT_RIGHT = "front_right";
    private static final String MIDDLE_LEFT = "middle_left";
    private static final String MIDDLE_RIGHT = "middle_right";
    private static final String BACK_LEFT = "back_left";
    private static final String BACK_RIGHT = "back_right";

    public static final ModelLayerLocation LAYER_LOCATION = new
            ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "peacemaker_cow"), "main");

    private final ModelPart head;
    private final ModelPart tail;
    private final ModelPart frontLeftLeg;
    private final ModelPart frontRightLeg;
    private final ModelPart middleLeftLeg;
    private final ModelPart middleRightLeg;
    private final ModelPart backLeftLeg;
    private final ModelPart backRightLeg;

    /**
     * Creates the 3D model.
     * @return A layer definition defining the Peacemaker Butterfly's model.
     */
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild(BODY, CubeListBuilder.create().texOffs(0, 0)
                .addBox(-20.0F, -34.0F, -24.0F, 40.0F, 34.0F, 48.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.0F, 0.0F));

        body.addOrReplaceChild(HEAD, CubeListBuilder.create().texOffs(0, 82)
                .addBox(-13.0F, -14.0F, 0.0F, 26.0F, 28.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -13.0F, 24.0F));

        body.addOrReplaceChild(TAIL, CubeListBuilder.create().texOffs(88, 82)
                .addBox(-9.0F, -10.0F, -25.0F, 18.0F, 20.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.0F, -24.0F));

        PartDefinition legs = body.addOrReplaceChild(LEGS   , CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        legs.addOrReplaceChild(FRONT_LEFT, CubeListBuilder.create().texOffs(0, 128)
                .addBox(-2.0F, 0.0F, -2.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-19.0F, 0.0F, 16.0F));

        legs.addOrReplaceChild(FRONT_RIGHT, CubeListBuilder.create().texOffs(0, 128)
                .addBox(-1.0F, 0.0F, -2.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(19.0F, 0.0F, 16.0F));

        legs.addOrReplaceChild(MIDDLE_LEFT, CubeListBuilder.create().texOffs(0, 128)
                .addBox(-2.0F, 0.0F, -2.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-19.0F, 0.0F, 0.0F));

        legs.addOrReplaceChild(MIDDLE_RIGHT, CubeListBuilder.create().texOffs(0, 128)
                .addBox(-1.0F, 0.0F, -2.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(19.0F, 0.0F, 0.0F));

        legs.addOrReplaceChild(BACK_LEFT, CubeListBuilder.create().texOffs(0, 128)
                .addBox(-2.0F, -1.0F, -2.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-19.0F, 0.0F, -16.0F));

        legs.addOrReplaceChild(BACK_RIGHT, CubeListBuilder.create().texOffs(0, 128)
                .addBox(-1.0F, 0.0F, -2.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(19.0F, 0.0F, -16.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    /**
     * Construction
     * @param root The root of the model.
     */
    public PeacemakerCowModel(@NotNull ModelPart root) {
        super(root);

        ModelPart body = root.getChild(BODY);
        head = body.getChild(HEAD);
        tail = body.getChild(TAIL);
        ModelPart legs = body.getChild(LEGS);
        frontLeftLeg = legs.getChild(FRONT_LEFT);
        frontRightLeg = legs.getChild(FRONT_RIGHT);
        middleLeftLeg = legs.getChild(MIDDLE_LEFT);
        middleRightLeg = legs.getChild(MIDDLE_RIGHT);
        backLeftLeg = legs.getChild(BACK_LEFT);
        backRightLeg = legs.getChild(BACK_RIGHT);
    }

    /**
     * Animate the model.
     * @param renderState The current rendering state.
     */
    @Override
    public void setupAnim(@NotNull LivingEntityRenderState renderState) {
        // Precalculations
        float sineBasedRotation = (Mth.sin(renderState.ageInTicks * 0.1f) * 0.1f);
        float cosineBasedRotation = (Mth.cos(renderState.ageInTicks * 0.1f) * 0.1f);
        float swingModifier = 1.5F * Mth.triangleWave(renderState.walkAnimationPos, 13.0F) * renderState.walkAnimationSpeed;

        // Head
        head.xRot = sineBasedRotation + (renderState.xRot * Mth.DEG_TO_RAD);
        head.yRot = cosineBasedRotation + (renderState.yRot * Mth.DEG_TO_RAD);

        // Legs
        frontLeftLeg.zRot = swingModifier - sineBasedRotation;
        frontRightLeg.zRot = sineBasedRotation - swingModifier;
        middleLeftLeg.zRot = swingModifier - cosineBasedRotation;
        middleRightLeg.zRot = sineBasedRotation - swingModifier;
        backLeftLeg.zRot = swingModifier - cosineBasedRotation;
        backRightLeg.zRot = cosineBasedRotation - swingModifier;

        // Tail
        tail.yRot = cosineBasedRotation - swingModifier;
        tail.zRot = swingModifier - sineBasedRotation;
    }
}
