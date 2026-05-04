package net.dillon.survivalfly.event;

import net.dillon.survivalfly.keybind.ModKeybinds;
import net.dillon.survivalfly.packet.ClientPacketHandlers;
import net.dillon.survivalfly.packet.UpdateFlightC2SPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

@Environment(EnvType.CLIENT)
public class FabricClientEvents {

    public static void registerKeybinds() {
        ModKeybinds.initKeybinds();

        KeyMappingHelper.registerKeyMapping(ModKeybinds.CHANGE_FLIGHT_SPEED);
        KeyMappingHelper.registerKeyMapping(ModKeybinds.RESET_FLIGHT_SPEED);
    }

    public static void registerConnectionChecks() {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (!ClientPlayNetworking.canSend(UpdateFlightC2SPacket.PACKET_TYPE)) {
                ClientPacketHandlers.disconnectSafeMode(handler.getConnection(), client.player);
            }
        });
    }
}