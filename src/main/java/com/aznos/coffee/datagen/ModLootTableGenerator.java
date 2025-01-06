package com.aznos.coffee.datagen;

import com.aznos.coffee.block.ModBlocks;
import com.aznos.coffee.block.custom.CoffeeCherryCropBlock;
import com.aznos.coffee.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModLootTableGenerator extends FabricBlockLootTableProvider {
    public ModLootTableGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        BlockStatePropertyLootCondition.Builder builder = BlockStatePropertyLootCondition.builder(ModBlocks.COFFEE_CHERRY_CROP)
                .properties(StatePredicate.Builder.create().exactMatch(CoffeeCherryCropBlock.AGE, 5));

        addDrop(ModBlocks.COFFEE_CHERRY_CROP, cropDrops(ModBlocks.COFFEE_CHERRY_CROP, ModItems.COFFEE_CUP, ModItems.COFFEE_CHERRY_SEEDS, builder));
    }
}
