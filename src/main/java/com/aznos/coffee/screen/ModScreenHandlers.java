package com.aznos.coffee.screen;

import com.aznos.coffee.Coffee;
import com.aznos.coffee.screen.custom.BrewerScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ModScreenHandlers {
    public static final ScreenHandlerType<BrewerScreenHandler> BREWER_SCREEN_HANDLER = Registry.register(
            Registries.SCREEN_HANDLER,
            Identifier.of(Coffee.MOD_ID, "brewer_screen_handler"),
            new ExtendedScreenHandlerType<>(BrewerScreenHandler::new, BlockPos.PACKET_CODEC)
    );

    public static void registerScreenHandlers() {
        Coffee.LOGGER.info("Registering screen handlers for " + Coffee.MOD_ID);
    }
}
