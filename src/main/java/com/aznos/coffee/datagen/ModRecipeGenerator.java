package com.aznos.coffee.datagen;

import com.aznos.coffee.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

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
    }
}
