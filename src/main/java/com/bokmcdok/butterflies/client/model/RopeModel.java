package com.bokmcdok.butterflies.client.model;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.entity.decoration.AbstractRopeEntity;
import com.bokmcdok.butterflies.world.entity.decoration.RopeEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class RopeModel<T extends RopeEntity> extends AbstractRopeModel<T> {
    private static final String ROPE = "rope";
    private static final String UPPER = "upper";

    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(ButterfliesMod.MOD_ID, ROPE), ROPE);

    public RopeModel(ModelPart modelPart) {
        super(modelPart);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild(LOWER,
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 10.0F, 3.0F),
                PartPose.ZERO);

        partdefinition.addOrReplaceChild(UPPER,
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-1.5F, -10.0F, -1.5F, 3.0F, 10.0F, 3.0F),
                PartPose.ZERO);

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(@NotNull RopeEntity entity,
                          float limbSwing,
                          float limbSwingAmount,
                          float ageInTicks,
                          float netHeadYaw,
                          float headPitch) {

        if (entity.getTransitioning()) {
            switch (entity.getDirection()) {
                case NORTH:
                    this.lower.xRot = -ROPE_CONNECT_ANGLE;
                    this.lower.zRot = 0;
                    break;

                case SOUTH:
                    this.lower.xRot = ROPE_CONNECT_ANGLE;
                    this.lower.zRot = 0;
                    break;

                case EAST:
                    this.lower.xRot = 0;
                    this.lower.zRot = ROPE_CONNECT_ANGLE;
                    break;

                case WEST:
                    this.lower.xRot = 0;
                    this.lower.zRot = -ROPE_CONNECT_ANGLE;
                    break;

                default:
                    this.lower.xRot = 0;
                    this.lower.zRot = 0;
                    break;
            }
        } else {
            this.lower.xRot = 0;
            this.lower.zRot = 0;
        }
    }
}
