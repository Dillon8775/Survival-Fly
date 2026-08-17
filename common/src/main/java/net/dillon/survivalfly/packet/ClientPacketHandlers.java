package net.dillon.survivalfly.packet;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;

import static net.dillon.survivalfly.option.OptionInstances.client;
import static net.dillon.survivalfly.option.OptionInstances.updateCommon;

/**
 * Handles client-side packet related things.
 */
public class ClientPacketHandlers {

    /**
     * Safely disconnects a player from a server if safe mode is on, and the mod is not installed server-side.
     */
    public static void disconnectSafeMode(Connection connection, LocalPlayer localPlayer) {
        boolean disconnected = false;

        if (!client().serverWarnings) {
            return;
        }

        if (client().safeMode) {
            connection.disconnect(Component.translatable("survivalfly.not_installed"));
            disconnected = true;
        }
        if (!disconnected) {
            updateCommon(common -> {
                common.crouchFlight = false;
            });
            if (localPlayer != null) {
                localPlayer.sendSystemMessage(Component.translatable("survivalfly.crouch_flight_disabled"));
            }
        }
    }
}