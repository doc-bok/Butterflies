package com.bokmcdok.butterflies.client.model;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.entity.animal.PeacemakerCow;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * The model and animations for a Peacemaker Butterfly.
 */
@OnlyIn(Dist.CLIENT)
public class PeacemakerCowModel extends HierarchicalModel<PeacemakerCow> {
    
    // Names for the various model parts.
    private static final String BODY = "body";
    private static final String HEAD = "head";
    private static final String TAIL = "tail";
    private static final String LEGS = "legs";

    public static final ModelLayerLocation LAYER_LOCATION = new
            ModelLayerLocation(new ResourceLocation(ButterfliesMod.MOD_ID, "peacemaker_cow"), "main");

    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart tail;
    private final ModelPart legs;

    /**
     * Creates the 3D model.
     * @return A layer definition defining the Peacemaker Butterfly's model.
     */
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild(BODY, CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-20.0F, -34.0F, -24.0F, 40.0F, 34.0F, 48.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 18.0F, 0.0F));

        body.addOrReplaceChild(HEAD, CubeListBuilder.create()
                        .texOffs(0, 82).addBox(-13.0F, -27.0F, 24.0F, 26.0F, 28.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        body.addOrReplaceChild(TAIL, CubeListBuilder.create()
                        .texOffs(88, 82).addBox(-9.0F, -20.0F, -49.0F, 18.0F, 20.0F, 25.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        body.addOrReplaceChild(LEGS, CubeListBuilder.create().texOffs(0, 128).addBox(-21.0F, 0.0F, 14.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 128).addBox(18.0F, 0.0F, 14.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 128).addBox(-21.0F, 0.0F, -2.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 128).addBox(18.0F, 0.0F, -2.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 128).addBox(-21.0F, 0.0F, -18.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 128).addBox(18.0F, 0.0F, -18.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    /**
     * Construction
     * @param root The root of the model.
     */
    public PeacemakerCowModel(ModelPart root) {
        this.body = root.getChild(BODY);
        this.head = this.body.getChild(HEAD);
        this.tail = this.body.getChild(TAIL);
        this.legs = this.body.getChild(LEGS);
    }

    /**
     * Get the root of the hierarchical model.
     * @return The root node.
     */
    @Override
    public @NotNull ModelPart root() {
        return this.body;
    }

    /**
     * Animate the model.
     * @param entity The entity.
     * @param limbSwing Unused
     * @param limbSwingAmount Unused
     * @param ageInTicks The current age of the entity in ticks.
     * @param netHeadYaw The current yaw of the head.
     * @param headPitch The current pitch of the head.
     */
    @Override
    public void setupAnim(@NotNull PeacemakerCow entity,
                          float limbSwing,
                          float limbSwingAmount,
                          float ageInTicks,
                          float netHeadYaw,
                          float headPitch) {

        //  Head
        this.head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        this.head.xRot = headPitch * Mth.DEG_TO_RAD;
    }
}
