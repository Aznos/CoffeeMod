package com.aznos.coffee.block.entity;

import com.aznos.coffee.Coffee;
import com.aznos.coffee.block.ModBlocks;
import com.aznos.coffee.block.entity.custom.BrewerBlockEntity;
import com.aznos.coffee.block.entity.custom.DryingRackBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<DryingRackBlockEntity> DRYING_RACK_BE = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(Coffee.MOD_ID, "drying_rack_be"),
            BlockEntityType.Builder.create(DryingRackBlockEntity::new, ModBlocks.DRYING_RACK).build(null)
    );

    public static final BlockEntityType<BrewerBlockEntity> BREWER_BE = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(Coffee.MOD_ID, "brewer_be"),
            BlockEntityType.Builder.create(BrewerBlockEntity::new, ModBlocks.BREWER).build(null)
    );

    public static void registerBlockEntities() {
        Coffee.LOGGER.info("Registering Block Entities for " + Coffee.MOD_ID);
    }
}
