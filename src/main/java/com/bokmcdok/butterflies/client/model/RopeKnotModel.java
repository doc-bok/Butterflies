package com.bokmcdok.butterflies.client.model;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.entity.decoration.RopeKnotEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class RopeKnotModel<T extends RopeKnotEntity> extends AbstractRopeModel<T> {
    private static final String KNOT = "knot";

    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(ButterfliesMod.MOD_ID, "rope_knot"), KNOT);

    public RopeKnotModel(ModelPart modelPart) {
        super(modelPart);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild(KNOT, CubeListBuilder.create().texOffs(7, 14)
                .addBox(-3.0F, -8.0F, -3.0F, 6.0F, 8.0F, 6.0F), PartPose.ZERO);

        partdefinition.addOrReplaceChild(LOWER,
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(1.7F, 0.0F, -1.5F, 3.0F, 10.0F, 3.0F),
                PartPose.ZERO);

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(@NotNull RopeKnotEntity entity,
                          float limbSwing,
                          float limbSwingAmount,
                          float ageInTicks,
                          float netHeadYaw,
                          float headPitch) {

        switch (entity.getDirection()) {
            case NORTH:
                this.root().yRot = (float)Math.PI / 2.0f;
                break;

            case SOUTH:
                this.root().yRot = 3.0f * ((float)Math.PI / 2.0f);
                break;

            case EAST:
                this.root().yRot = (float)Math.PI;
                break;

            default:
                this.root().yRot = 0;
                break;
        }

        if (entity.getTransitioning()) {
            this.lower.zRot = entity.getTransitioning() ? -ROPE_CONNECT_ANGLE : 0;
        }
    }
}
