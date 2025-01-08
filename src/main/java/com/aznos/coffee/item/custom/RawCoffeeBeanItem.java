package com.aznos.coffee.item.custom;

import com.aznos.coffee.components.ModDataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class RawCoffeeBeanItem extends Item {
    private int dehydrationLevel = 0;

    public RawCoffeeBeanItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal("Place a raw coffee bean on a drying rack to dehydrate it.").formatted(Formatting.GRAY));

        Integer levelObj = stack.get(ModDataComponentTypes.DEHYDRATION_LEVEL);
        int level = (levelObj != null) ? levelObj : 0;

        if(level > 0) {
            tooltip.add(Text.literal("Dehydration: " + level + "%").formatted(Formatting.GRAY));
        }

        super.appendTooltip(stack, context, tooltip, type);
    }
}
