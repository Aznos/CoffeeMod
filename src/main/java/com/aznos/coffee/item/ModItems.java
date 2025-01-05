package com.aznos.coffee.item;

import com.aznos.coffee.Coffee;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final FoodComponent COFFEE_CUP_COMPONENT = new FoodComponent.Builder()
            .alwaysEdible()
            .snack()
            .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 20 * 300, 1, false, false, false), 1.0F)
            .build();

    public static final Item COFFEE_CUP = registerItem(
            "coffee_cup",
            new Item(new Item.Settings().food(COFFEE_CUP_COMPONENT))
    );

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Coffee.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Coffee.LOGGER.info("Registering mod items for " + Coffee.MOD_ID);
    }
}
