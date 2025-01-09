package com.aznos.coffee.damage;

import com.aznos.coffee.Coffee;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModDamageTypes {
    public static final RegistryKey<DamageType> CAFFEINE_DAMAGE = RegistryKey.of(
            RegistryKeys.DAMAGE_TYPE,
            Identifier.of(Coffee.MOD_ID, "caffeine_damage")
    );

    public static void registerDamageTypes() {
        Coffee.LOGGER.info("Registering damage types for " + Coffee.MOD_ID);
    }
}
