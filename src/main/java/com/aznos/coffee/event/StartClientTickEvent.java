package com.aznos.coffee.event;

import com.aznos.coffee.block.entity.custom.DryingRackBlockEntity;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class StartClientTickEvent implements ClientTickEvents.StartTick {
    private final int dehyrationLevelForTick = 20 * 2; //2 seconds to gain +1 dehydration level
    private int tickTimer = 0;

    @Override
    public void onStartTick(MinecraftClient minecraftClient) {
        tickTimer++;

        if(tickTimer >= dehyrationLevelForTick) {
            DryingRackBlockEntity.tick();
            tickTimer = 0;
        }
    }
}
