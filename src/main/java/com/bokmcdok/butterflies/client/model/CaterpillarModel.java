package com.bokmcdok.butterflies.client.model;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * A model of a caterpillar.
 */
@OnlyIn(Dist.CLIENT)
public class CaterpillarModel extends EntityModel<LivingEntityRenderState> {

    // Holds the layers for the model.
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "caterpillar"), "main");

    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart thorax;

    /**
     * Construction
     * @param root The root of the model.
     */
    public CaterpillarModel(ModelPart root) {
        super(root);

        // The root of the model.
        this.body = root.getChild("body");
        this.head = this.body.getChild("head");
        this.thorax = this.body.getChild("thorax");
    }

    /**
     * Creates a model of a caterpillar.
     * @return The layer definition of the model.
     */
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 9).addBox(-3.0F, -5.0F, -6.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(20, 9).addBox(-5.0F, -7.0F, -2.0F, 10.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        body.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(18, 23).addBox(-2.0F, -3.0F, -8.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(20, 16).addBox(-5.0F, -7.0F, -6.0F, 10.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        body.addOrReplaceChild("thorax", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-3.0F, -4.0F, -2.0F, 6.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 18).addBox(-5.0F, -7.0F, 3.0F, 10.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(22, 0).addBox(-2.0F, -3.0F, 3.0F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    /**
     * Animate the model.
     * @param renderState The current render state.
     */
    @Override
    public void setupAnim(@NotNull LivingEntityRenderState renderState) {
        float sin = (float)Math.sin(renderState.ageInTicks * 0.2f);

        float yModifier = 0.5f * (sin + 1.f);

        this.head.y = yModifier;
        this.body.y = 24.0f - yModifier;
        this.thorax.y = yModifier;

        this.thorax.z = -1.0f - sin;
    }
}
