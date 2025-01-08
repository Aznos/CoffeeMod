package com.aznos.coffee;

import com.aznos.coffee.block.entity.ModBlockEntities;
import com.aznos.coffee.components.ModDataComponentTypes;
import com.aznos.coffee.item.ModItemGroups;
import com.aznos.coffee.item.ModItems;
import net.fabricmc.api.ModInitializer;

import java.util.logging.Logger;

public class Coffee implements ModInitializer {
    public static final String MOD_ID = "coffee";
    public static final Logger LOGGER = Logger.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Coffee mod!");

        ModItems.registerModItems();
        ModItemGroups.registerItemGroups();
        ModBlockEntities.registerBlockEntities();
        ModDataComponentTypes.registerDataComponentTypes();
    }
}
