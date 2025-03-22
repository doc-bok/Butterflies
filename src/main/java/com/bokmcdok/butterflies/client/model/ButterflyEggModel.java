package com.bokmcdok.butterflies.client.model;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.client.renderer.entity.state.ButterflyEggRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Model for a butterfly egg entity.
 */
@OnlyIn(Dist.CLIENT)
public class ButterflyEggModel extends EntityModel<ButterflyEggRenderState> {

    // Holds the layers for the model.
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "butterfly_egg"), "main");

    /**
     * Constructor
     * @param root The root of the model.
     */
    public ButterflyEggModel(ModelPart root) {
        super(root);
    }

    /**
     * Create a simple model for the butterfly egg.
     * @return The new layer definition.
     */
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("main",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-8.0F, -16.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(0, -16).addBox(0.0F, -16.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)),
                        PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    /**
     * Animate the model (or rather, don't).
     * @param renderState The current render state.
     */
    @Override
    public void setupAnim(@NotNull ButterflyEggRenderState renderState) {
        // No-op
    }
}
