package com.aznos.coffee.datagen;

import com.aznos.coffee.block.ModBlocks;
import com.aznos.coffee.block.custom.CoffeeCherryCropBlock;
import com.aznos.coffee.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerCrop(ModBlocks.COFFEE_CHERRY_CROP, CoffeeCherryCropBlock.AGE, 0, 1, 2, 3, 4, 5);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.COFFEE_CUP, Models.GENERATED);
        itemModelGenerator.register(ModItems.COFFEE_CHERRY, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_COFFEE_BEAN, Models.GENERATED);
        itemModelGenerator.register(ModItems.DEHYDRATED_COFFEE_BEAN, Models.GENERATED);
    }
}
