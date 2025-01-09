package com.aznos.coffee.item;

import com.aznos.coffee.Coffee;
import com.aznos.coffee.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup COFFEE_GROUP = Registry.register(
            Registries.ITEM_GROUP,
            Identifier.of(Coffee.MOD_ID, "coffee"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.coffee"))
                    .icon(() -> new ItemStack(ModItems.COFFEE_CUP)).entries((displayContext, entries) -> {
                        entries.add(ModItems.COFFEE_CUP);
                        entries.add(ModItems.COFFEE_CHERRY_SEEDS);
                        entries.add(ModItems.COFFEE_CHERRY);
                        entries.add(ModItems.RAW_COFFEE_BEAN);
                        entries.add(ModItems.DEHYDRATED_COFFEE_BEAN);
                        entries.add(ModBlocks.DRYING_RACK);
                    }).build());

    public static void registerItemGroups() {
        Coffee.LOGGER.info("Registering item groups for " + Coffee.MOD_ID);
    }
}
