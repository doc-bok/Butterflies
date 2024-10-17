package com.bokmcdok.butterflies.client.renderer.blockentity;

import com.bokmcdok.butterflies.world.block.entity.ButterflyFeederEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Renders the items stored in a butterfly feeder.
 */
public class ButterflyFeederEntityRenderer implements BlockEntityRenderer<ButterflyFeederEntity> {

    /**
     * Construction
     * @param ignoredContext This is ignored.
     */
    public ButterflyFeederEntityRenderer(BlockEntityRendererProvider.Context ignoredContext) {
    }

    /**
     * Render items based on what is in the feeder and how many items are in
     * the feeder.
     * @param butterflyFeederEntity The butterfly feeder entity.
     * @param v Unknown.
     * @param poseStack The matrix stack.
     * @param multiBufferSource The render buffer.
     * @param unknown1 Unknown.
     * @param unknown2 Unknown.
     */
    @Override
    public void render(@NotNull ButterflyFeederEntity butterflyFeederEntity,
                       float v,
                       @NotNull PoseStack poseStack,
                       @NotNull MultiBufferSource multiBufferSource,
                       int unknown1,
                       int unknown2) {

        if (!butterflyFeederEntity.isEmpty()) {
            ItemStack itemStack = butterflyFeederEntity.getItem(0);
            int count = itemStack.getCount();

            // We'll render one for every 8 items, so we don't slow things down
            // too much.
            for (int i = 0, j = 0; i < count; i += 8, j = (j + 1) % 4 ) {
                double xMod = i * 0.005;
                double yMod = i * 0.005;

                if (j >= 2) {
                    xMod *= -1;
                }

                if (j % 2 != 0) {
                    yMod *= -1;
                }

                // Use a "random" starting rotation based on the block
                // position.
                BlockPos blockPos = butterflyFeederEntity.getBlockPos();
                float yRot = (float)blockPos.getCenter().lengthSqr();
                yRot += i * 3.0f;

                renderItem(
                        butterflyFeederEntity,
                        poseStack,
                        multiBufferSource,
                        unknown1,
                        unknown2,
                        0.5 + xMod,
                        0.5 + yMod,
                        yRot);
            }
        }
    }

    /**
     * Render an individual item.
     * @param butterflyFeederEntity The butterfly feeder entity.
     * @param poseStack The matrix stack.
     * @param multiBufferSource The render buffer.
     * @param i Unknown.
     * @param i1 Unknown.
     * @param x The x-offset of the item.
     * @param z The z-offset of the item.
     * @param yRot The y-rotation of the item.
     */
    private void renderItem(@NotNull ButterflyFeederEntity butterflyFeederEntity,
                            @NotNull PoseStack poseStack,
                            @NotNull MultiBufferSource multiBufferSource,
                            int i,
                            int i1,
                            double x,
                            double z,
                            float yRot) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        poseStack.pushPose();
        poseStack.translate(x, 0.9, z);
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));

        itemRenderer.renderStatic(
                butterflyFeederEntity.getItem(0),
                ItemDisplayContext.GROUND,
                i,
                i1,
                poseStack,
                multiBufferSource,
                butterflyFeederEntity.getLevel(),
                0);

        poseStack.popPose();
    }
}
