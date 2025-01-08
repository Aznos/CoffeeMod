package com.aznos.coffee.components;

import com.aznos.coffee.Coffee;
import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {
    public static final ComponentType<Integer> DEHYDRATION_LEVEL = register(
            "dehyration_level",
            builder -> builder.codec(
                    Codec.intRange(0, 100)
            )
    );

    private static <T>ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(Coffee.MOD_ID, name),
                (builderOperator.apply(ComponentType.builder())).build());
    }

    public static void registerDataComponentTypes() {
        Coffee.LOGGER.info("Registering mod data component types for " + Coffee.MOD_ID);
    }
}
