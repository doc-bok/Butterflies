package com.bokmcdok.butterflies.client.model;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class RopeKnotModel<T extends Entity> extends HierarchicalModel<T> {
    private static final String KNOT = "knot";

    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(ButterfliesMod.MOD_ID, "rope"), KNOT);

    private final ModelPart root;
    private final ModelPart knot;

    public RopeKnotModel(ModelPart modelPart) {
        this.root = modelPart;
        this.knot = modelPart.getChild(KNOT);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild(KNOT, CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -8.0F, -3.0F, 6.0F, 8.0F, 6.0F), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @NotNull
    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(@NotNull T entity,
                          float limbSwing,
                          float limbSwingAmount,
                          float ageInTicks,
                          float netHeadYaw,
                          float headPitch) {
        this.knot.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.knot.xRot = headPitch * ((float)Math.PI / 180F);
    }
}
