package com.bokmcdok.butterflies.client.model;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ButterflyScrollModel extends Model {

    /**
     * The layer location to register with Forge.
     */
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "butterfly_scroll"), "main");


    /**
     * Defines a simple model for the Butterfly Scroll.
     * @return A new layer definition.
     */
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();


        partDefinition.addOrReplaceChild("main",
                CubeListBuilder.create()
                        .texOffs(26, 8)
                        .addBox(-128.0F, -165.0F, 0.0F, 128.0F, 165.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(8.0F, 24.0F, -8.0F));

        return LayerDefinition.create(meshDefinition, 256, 256);
    }

    /**
     * Construction.
     * @param root The root of the model.
     */
    public ButterflyScrollModel(ModelPart root) {
        super(root, RenderType::entityCutoutNoCull);
    }
}
