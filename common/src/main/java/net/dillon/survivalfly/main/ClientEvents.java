package net.dillon.survivalfly.main;

import net.dillon.survivalfly.packet.ClientPacketHandlers;
import net.dillon.survivalfly.platform.SurvivalFlyPlatforms;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.Connection;

/**
 * Client events for Survival Fly.
 */
public class ClientEvents {

    public static void onPlayerJoin(Connection connection, LocalPlayer player) {
        if (!SurvivalFlyPlatforms.getClientPlatform().canSendPacket(player)) {
            ClientPacketHandlers.disconnectSafeMode(connection, player);
        }
    }
}