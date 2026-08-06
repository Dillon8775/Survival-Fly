package net.dillon.survivalfly.event;

import net.dillon.survivalfly.keybind.ModKeyMappings;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

@Environment(EnvType.CLIENT)
public class FabricClientEvents {

    public static void registerKeybinds() {
        ModKeyMappings.initKeybinds();

        KeyMappingHelper.registerKeyMapping(ModKeyMappings.CHANGE_FLIGHT_SPEED);
    }

    public static void registerConnectionChecks() {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            ClientEvents.onPlayerJoin(handler.getConnection(), client.player);
        });
    }
}