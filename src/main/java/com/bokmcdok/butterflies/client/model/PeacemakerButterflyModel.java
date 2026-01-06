package com.bokmcdok.butterflies.client.model;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.entity.monster.PeacemakerButterfly;
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

import java.util.Objects;

/**
 * The model and animations for a Peacemaker Butterfly.
 */
@OnlyIn(Dist.CLIENT)
public class PeacemakerButterflyModel extends HierarchicalModel<PeacemakerButterfly> {

    // Names for the various model parts.
    private static final String ANTENNAE = "antennae";
    private static final String BODY = "body";
    private static final String LEFT_WING = "left_wing";
    private static final String RIGHT_WING = "right_wing";
    private static final String LEGS = "legs";
    private static final String HEAD = "head";

    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(ButterfliesMod.MOD_ID, "peacemaker_butterfly"), "main");

    //  The parts of the model
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart right_wing;
    private final ModelPart left_wing;

    /**
     * Creates the 3D model.
     * @return A layer definition defining the Peacemaker Butterfly's model.
     */
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild(BODY, CubeListBuilder.create()
                .texOffs(0, 24).addBox(-2.0F, -5.0F, -3.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offset(1.0F, 23.0F, 0.0F));

        body.addOrReplaceChild(LEFT_WING, CubeListBuilder.create()
                .texOffs(0, 17).addBox(0.0F, 0.0F, -6.0F, 10.0F, 0.0F, 17.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -5.0F, 0.0F));

        body.addOrReplaceChild(RIGHT_WING, CubeListBuilder.create()
                .texOffs(0, 0).addBox(-12.0F, 0.0F, -6.0F, 10.0F, 0.0F, 17.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild(HEAD, CubeListBuilder.create()
                .texOffs(0, 0).addBox(-1.0F, -0.5F, -4.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-1.0F, -4.5F, -3.0F));

        head.addOrReplaceChild(ANTENNAE, CubeListBuilder.create()
                .texOffs(6, 2).addBox(-2.0F, -7.0F, -9.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 2).addBox(0.0F, -7.0F, -9.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(1.0F, 4.5F, 2.0F));

        partdefinition.addOrReplaceChild(LEGS, CubeListBuilder.create()
                .texOffs(0, 4).addBox(1.0F, -4.0F, -2.0F, 0.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, -4.0F, -2.0F, 0.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    /**
     * Construct a Peacemaker Butterfly model.
     * @param root The root of the hierarchical model.
     */
    public PeacemakerButterflyModel(@NotNull ModelPart root) {
        this.root = root;

        ModelPart body = Objects.requireNonNull(root.getChild(BODY));
        this.head = Objects.requireNonNull(body.getChild(HEAD));
        this.right_wing = Objects.requireNonNull(body.getChild(RIGHT_WING));
        this.left_wing = Objects.requireNonNull(body.getChild(LEFT_WING));
    }

    /**
     * Get the root of the hierarchical model.
     * @return The root node.
     */
    @Override
    public @NotNull ModelPart root() {
        return this.root;
    }

    /**
     * Animate the Peacemaker Butterfly.
     * @param entity The entity.
     * @param limbSwing Unused
     * @param limbSwingAmount Unused
     * @param ageInTicks The current age of the entity in ticks.
     * @param netHeadYaw The current yaw of the head.
     * @param headPitch The current pitch of the head.
     */
    @Override
    public void setupAnim(@NotNull PeacemakerButterfly entity,
                          float limbSwing,
                          float limbSwingAmount,
                          float ageInTicks,
                          float netHeadYaw,
                          float headPitch) {

        final float WING_SPEED = 0.85F;
        final float WING_ARC = 0.25F;

        //  Head
        this.head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        this.head.xRot = headPitch * Mth.DEG_TO_RAD;

        //  Wings
        this.right_wing.zRot = Mth.sin(ageInTicks * WING_SPEED) * Mth.PI * WING_ARC;
        this.left_wing.zRot = -this.right_wing.zRot;

    }
}
