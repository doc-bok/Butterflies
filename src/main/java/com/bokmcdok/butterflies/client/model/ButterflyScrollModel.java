package com.bokmcdok.butterflies.client.model;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ButterflyScrollModel extends Model {

    /**
     * The root of the model.
     */
    private final ModelPart root;

    /**
     * The layer location to register with Forge.
     */
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(ButterfliesMod.MODID, "butterfly_scroll"), "main");


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
        super(RenderType::entityCutoutNoCull);
        this.root = root.getChild("main");
    }

    /**
     * Render the model to the specified buffer.
     * @param poseStack The current translation stack.
     * @param vertexConsumer The vertices to render.
     * @param packedLight The current light.
     * @param packedOverlay The overlay.
     * @param red Red tint.
     * @param green Green tint.
     * @param blue Blue tint.
     * @param alpha Alpha tint.
     */
    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack,
                               @NotNull VertexConsumer vertexConsumer,
                               int packedLight,
                               int packedOverlay,
                               float red,
                               float green,
                               float blue,
                               float alpha) {
        this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
