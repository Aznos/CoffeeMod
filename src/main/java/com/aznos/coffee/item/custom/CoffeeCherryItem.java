package com.aznos.coffee.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class CoffeeCherryItem extends Item {
    public CoffeeCherryItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal("Extract the coffee bean by placing the cherry in a crafting grid.").formatted(Formatting.GRAY));

        super.appendTooltip(stack, context, tooltip, type);
    }
}
