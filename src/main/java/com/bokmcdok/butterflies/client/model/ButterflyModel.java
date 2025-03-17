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
 * A model of a butterfly.
 */
@OnlyIn(Dist.CLIENT)
public class ButterflyModel  extends HierarchicalModel<Butterfly> {

    //  Holds the layers for the butterfly.
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "butterfly"), "main");

    //  The root of the model.
    private final ModelPart root;

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
        this.root = root;
        this.body = root.getChild("body");
        this.left_wing = this.body.getChild("left_wing");
        this.right_wing = this.body.getChild("right_wing");
    }

    /**
     * Create the 3D model.
     * @return The complete 3D model for the butterfly.
     */
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        body.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 20).addBox(-5.0F, -2.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        body.addOrReplaceChild("left_wing", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-9.0F, -1.0F, 1.0F, 17.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -1.0F, 0.0F));

        body.addOrReplaceChild("right_wing", CubeListBuilder.create()
                .texOffs(0, 10).addBox(-9.0F, -1.0F, -11.0F, 17.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -1.0F, 0.0F));

        body.addOrReplaceChild("antannae", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-8.0F, -4.0F, -1.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-8.0F, -4.0F, 1.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
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

        // The angle that the body rests at.
        final float BODY_REST_ANGLE = 0.7853982f;

        // The speed at which the body "hovers".
        final float BODY_MOVE_SPEED = 0.1f;

        // The arc through which the body moves.
        final float BODY_MOVE_ARC = 0.15f;

        // The arc through which the wings travel.
        final float WING_ARC = 0.25f;

        // The speed at which wings flap.
        final float WING_SPEED = 1.3f;

        //  When landed wings don't flap.
        if (entity.getIsLanded()) {
            this.body.yRot = BODY_REST_ANGLE;

            // Moths hold their wings flat.
            if (entity.getIsMoth()) {
                this.right_wing.xRot = 0.15F;

            // Butterflies raise their wings up.
            } else {
                this.right_wing.xRot = (0.15F - Mth.PI) * 0.5F;
            }
        } else {
            this.body.yRot = BODY_REST_ANGLE + Mth.cos(ageInTicks * BODY_MOVE_SPEED) * BODY_MOVE_ARC;
            this.right_wing.xRot = Mth.sin(ageInTicks * WING_SPEED) * Mth.PI * WING_ARC;
        }

        this.left_wing.xRot = -right_wing.xRot;
    }

    /**
     * Get the root of the model.
     * @return The root ModelPart
     */
    @Override
    public @NotNull ModelPart root() {
        return this.root;
    }
}