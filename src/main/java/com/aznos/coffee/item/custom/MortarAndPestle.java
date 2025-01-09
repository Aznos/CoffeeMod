package com.aznos.coffee.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MortarAndPestle extends Item {
    public MortarAndPestle(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasRecipeRemainder() {
        return true;
    }

    @Override
    public ItemStack getRecipeRemainder(ItemStack stack) {
        ItemStack newStack = stack.copy();
        newStack.setDamage(newStack.getDamage() + 1);

        if(newStack.getDamage() >= newStack.getMaxDamage()) {
            return ItemStack.EMPTY;
        }

        return newStack;
    }
}
