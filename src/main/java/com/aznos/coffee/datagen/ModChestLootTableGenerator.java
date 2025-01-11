package com.aznos.coffee.datagen;

import com.aznos.coffee.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ModChestLootTableGenerator extends SimpleFabricLootTableProvider {
    public ModChestLootTableGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup, LootContextTypes.CHEST);
    }

    @Override
    public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
        RegistryKey<LootTable> plainsHouseLootTableKey = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of("minecraft", "chests/village/village_plains_house"));

        // Create a new loot pool with your item entry
        LootPool.Builder poolBuilder = LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(ItemEntry.builder(ModItems.COFFEE_CHERRY_SEEDS)
                        .weight(9999)); // Adjust weight as needed

        // Add the new pool to the existing loot table
        lootTableBiConsumer.accept(plainsHouseLootTableKey, LootTable.builder().pool(poolBuilder));
    }
}
