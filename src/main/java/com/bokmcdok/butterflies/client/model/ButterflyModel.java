package com.bokmcdok.butterflies.client.model;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * A model of a butterfly.
 */
@OnlyIn(Dist.CLIENT)
public class ButterflyModel  extends HierarchicalModel<Butterfly> {

    //  Holds the layers for the butterfly.
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(ButterfliesMod.MODID, "butterfly"), "main");

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

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, 23.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        body.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 20).addBox(-5.0F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 1.0F, 1.0F, 0.0F, 3.1416F, 0.0F));


        body.addOrReplaceChild("left_wing", CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-8.0F, 1.0F, 2.0F, 17.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        body.addOrReplaceChild("right_wing", CubeListBuilder.create()
                        .texOffs(0, 10).addBox(-8.0F, 1.0F, -10.0F, 17.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition antennae = body.addOrReplaceChild("left_antenna", CubeListBuilder.create()
                .texOffs(0, 2).addBox(5.0F, -2.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        antennae.addOrReplaceChild("right_antenna", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-1.0F, 0.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(6.0F, 0.0F, 2.0F, 3.1416F, 0.0F, 0.0F));

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

        //  When landed butterflies hold their wings together.
        if (entity.getIsLanded()) {
            this.body.yRot = 0.7853982F;
            if (entity.getIsMoth()) {
                this.right_wing.xRot = 0;
            } else {
                this.right_wing.xRot = -Mth.PI * 0.5F;
            }

        } else {
            this.body.yRot = 0.7853982F + Mth.cos(ageInTicks * 0.1F) * 0.15F;
            this.right_wing.xRot = Mth.sin(ageInTicks * 1.3F) * Mth.PI * 0.25F;
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