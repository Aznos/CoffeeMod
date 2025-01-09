package com.aznos.coffee.client;

import com.aznos.coffee.block.ModBlocks;
import com.aznos.coffee.block.entity.ModBlockEntities;
import com.aznos.coffee.block.entity.renderer.BrewerBlockEntityRenderer;
import com.aznos.coffee.block.entity.renderer.DryingRackBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class CoffeeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.COFFEE_CHERRY_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DRYING_RACK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BREWER, RenderLayer.getCutout());

        BlockEntityRendererFactories.register(
                ModBlockEntities.DRYING_RACK_BE,
                DryingRackBlockEntityRenderer::new
        );

        BlockEntityRendererFactories.register(
                ModBlockEntities.BREWER_BE,
                BrewerBlockEntityRenderer::new
        );
    }
}
