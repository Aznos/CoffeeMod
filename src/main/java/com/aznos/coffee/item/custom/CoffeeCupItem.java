package com.aznos.coffee.item.custom;

import com.aznos.coffee.damage.ModDamageTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;

public class CoffeeCupItem extends Item {
    public CoffeeCupItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if(!world.isClient && user != null) {
            DamageSource caffeineDamage = new DamageSource(
                    world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(ModDamageTypes.CAFFEINE_DAMAGE)
            );

            user.damage(caffeineDamage, 2.0F);
        }

        return super.finishUsing(stack, world, user);
    }
}
