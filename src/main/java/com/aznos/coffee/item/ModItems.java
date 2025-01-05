package com.aznos.coffee.item;

import com.aznos.coffee.Coffee;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item COFFEE_CUP = registerItem(
            "coffee_cup",
            new Item(new Item.Settings())
    );

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Coffee.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Coffee.LOGGER.info("Registering mod items for " + Coffee.MOD_ID);
    }
}
