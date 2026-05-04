package net.dillon.survivalfly.packet;

import net.blay09.mods.balm.Balm;
import net.dillon.survivalfly.option.ModOptions;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;

import static net.dillon.survivalfly.util.ModUtil.options;

/**
 * Handles client-side packet related things.
 */
public class ClientPacketHandlers {

    /**
     * Safely disconnects a player from a server if safe mode is on, and the mod is not installed server-side.
     */
    public static void disconnectSafeMode(Connection connection, LocalPlayer localPlayer) {
        boolean disconnected = false;
        if (options().safeMode) {
            connection.disconnect(Component.translatable("survivalfly.gui.not_installed"));
            disconnected = true;
        }
        if (!disconnected) {
            Balm.config().updateLocalConfig(ModOptions.class, options -> {
                options.crouchFlight = false;
            });
            if (localPlayer != null) {
                localPlayer.sendSystemMessage(Component.translatable("survivalfly.gui.crouch_flight_disabled"));
            }
        }
    }
}