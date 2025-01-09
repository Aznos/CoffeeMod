package com.aznos.coffee.item;

import com.aznos.coffee.Coffee;
import com.aznos.coffee.block.ModBlocks;
import com.aznos.coffee.item.custom.RawCoffeeBeanItem;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.AliasedBlockItem;
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
            new Item(new Item.Settings()
                    .food(COFFEE_CUP_COMPONENT)
                    .maxCount(1)
            )
    );

    public static final Item COFFEE_CHERRY_SEEDS = registerItem(
            "coffee_cherry_seeds",
            new AliasedBlockItem(
                    ModBlocks.COFFEE_CHERRY_CROP,
                    new Item.Settings()
            )
    );

    public static final Item COFFEE_CHERRY = registerItem(
            "coffee_cherry",
            new Item(new Item.Settings()
                    .food(new FoodComponent.Builder()
                            .saturationModifier(0.3F)
                            .nutrition(1)
                            .snack()
                            .build()
                    )
            )
    );

    public static final Item RAW_COFFEE_BEAN = registerItem(
            "raw_coffee_bean",
            new RawCoffeeBeanItem(new Item.Settings())
    );

    public static final Item DEHYDRATED_COFFEE_BEAN = registerItem(
            "dehydrated_coffee_bean",
            new Item(new Item.Settings())
    );

    public static final Item ROASTED_COFFEE_BEAN = registerItem(
            "roasted_coffee_bean",
            new Item(new Item.Settings())
    );

    public static final Item MORTAR_AND_PESTLE = registerItem(
            "mortar_and_pestle",
            new Item(new Item.Settings()
                    .maxCount(1)
                    .maxDamage(64)
            )
    );

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Coffee.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Coffee.LOGGER.info("Registering mod items for " + Coffee.MOD_ID);
    }
}
