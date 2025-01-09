package com.aznos.coffee.block;

import com.aznos.coffee.Coffee;
import com.aznos.coffee.block.custom.BrewerBlock;
import com.aznos.coffee.block.custom.CoffeeCherryCropBlock;
import com.aznos.coffee.block.custom.DryingRackBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block COFFEE_CHERRY_CROP = registerBlockWithoutBlockItem(
            "coffee_cherry_crop",
            new CoffeeCherryCropBlock(
                    AbstractBlock.Settings.copy(Blocks.WHEAT)
            )
    );

    public static final Block DRYING_RACK = registerBlock(
            "drying_rack",
            new DryingRackBlock(
                    AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)
                            .nonOpaque()
            )
    );

    public static final Block BREWER = registerBlock(
            "brewer",
            new BrewerBlock(AbstractBlock.Settings.create()
                    .nonOpaque()
                    .strength(2.0f)
            )
    );

    private static Block registerBlockWithoutBlockItem(String name, Block block) {
        return Registry.register(
                Registries.BLOCK,
                Identifier.of(Coffee.MOD_ID, name),
                block
        );
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(
                Registries.BLOCK,
                Identifier.of(Coffee.MOD_ID, name),
                block
        );
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(
                Registries.ITEM,
                Identifier.of(Coffee.MOD_ID, name),
                new BlockItem(block, new Item.Settings())
        );
    }

    public static void registerModBlocks() {
        Coffee.LOGGER.info("Registering mod blocks for " + Coffee.MOD_ID);
    }
}
