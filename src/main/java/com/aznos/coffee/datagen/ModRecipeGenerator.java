package com.aznos.coffee.datagen;

import com.aznos.coffee.Coffee;
import com.aznos.coffee.block.ModBlocks;
import com.aznos.coffee.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public class ModRecipeGenerator extends FabricRecipeProvider {
    public ModRecipeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapelessRecipeJsonBuilder.create(
                RecipeCategory.FOOD,
                ModItems.RAW_COFFEE_BEAN
        ).input(ModItems.COFFEE_CHERRY).criterion(FabricRecipeProvider.hasItem(ModItems.COFFEE_CHERRY), FabricRecipeProvider.conditionsFromItem(ModItems.COFFEE_CHERRY)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DRYING_RACK)
                .pattern("   ")
                .pattern("WSW")
                .pattern("| |")
                .input('W', Blocks.OAK_PLANKS)
                .input('|', Items.STICK)
                .input('S', Items.STRING)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(Items.STRING), conditionsFromItem(Items.STRING))
                .criterion(hasItem(Blocks.OAK_PLANKS), conditionsFromItem(Blocks.OAK_PLANKS))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModItems.MORTAR_AND_PESTLE)
                .pattern("  S")
                .pattern("CSC")
                .pattern(" C ")
                .input('C', Blocks.CLAY)
                .input('S', Items.STICK)
                .criterion(hasItem(Blocks.CLAY), conditionsFromItem(Blocks.CLAY))
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(
                RecipeCategory.FOOD,
                ModItems.COFFEE_POWDER
        ).input(ModItems.ROASTED_COFFEE_BEAN).input(ModItems.MORTAR_AND_PESTLE).criterion(FabricRecipeProvider.hasItem(ModItems.ROASTED_COFFEE_BEAN), FabricRecipeProvider.conditionsFromItem(ModItems.ROASTED_COFFEE_BEAN)).criterion(FabricRecipeProvider.hasItem(ModItems.MORTAR_AND_PESTLE), FabricRecipeProvider.conditionsFromItem(ModItems.MORTAR_AND_PESTLE)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModBlocks.BREWER)
                .pattern("CCC")
                .pattern("C G")
                .pattern("CCC")
                .input('C', Blocks.GRAY_CONCRETE)
                .input('G', Blocks.GLASS_PANE)
                .criterion(hasItem(Blocks.GRAY_CONCRETE), conditionsFromItem(Blocks.GRAY_CONCRETE))
                .criterion(hasItem(Blocks.GLASS_PANE), conditionsFromItem(Blocks.GLASS_PANE))
                .offerTo(exporter);

        offerSmelting(exporter, Collections.singletonList(ModItems.DEHYDRATED_COFFEE_BEAN), RecipeCategory.FOOD, ModItems.ROASTED_COFFEE_BEAN, 0.4f, 60, Coffee.MOD_ID);
    }
}
