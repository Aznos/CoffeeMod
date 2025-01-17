package com.aznos.coffee.item.custom;

import com.aznos.coffee.damage.ModDamageTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;

public class CoffeeCupItem extends Item {
    private static final int MAX_SUGARS = 4;
    private int sugars = 0;

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

        int duration = 20 * 120 + sugars * 30 * 20;
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, duration, 1, false, false, false));

        return super.finishUsing(stack, world, user);
    }

    @Override
    public void onCraft(ItemStack stack, World world) {
        if(!world.isClient && sugars < MAX_SUGARS) {
            sugars++;
        }
    }
}
