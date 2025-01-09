package com.aznos.coffee.block.entity.renderer;

import com.aznos.coffee.block.entity.custom.BrewerBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

public class BrewerBlockEntityRenderer implements BlockEntityRenderer<BrewerBlockEntity> {
    public BrewerBlockEntityRenderer(BlockEntityRendererFactory.Context context) {

    }

    @Override
    public void render(BrewerBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

    }
}
