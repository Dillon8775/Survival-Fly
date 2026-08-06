package net.dillon.survivalfly.event;

import net.dillon.dillonlib.task.CommonTasks;
import net.dillon.survivalfly.helper.ModHelper;
import net.dillon.survivalfly.packet.ClientPacketHandlers;
import net.dillon.survivalfly.platform.SurvivalFlyPlatforms;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;

/**
 * Client events for Survival Fly.
 */
public class ClientEvents {

    public static void onPlayerJoin(Connection connection, LocalPlayer player) {
        if (ModHelper.HAS_UPDATE) {
            CommonTasks.sendUpdateMessage(player,
                    Component.translatable("survivalfly.title.options"),
                    "https://modrinth.com/mod/survival-fly/versions",
                    TextColor.WHITE.getValue());
        }

        if (!SurvivalFlyPlatforms.getClientPlatform().canSendPacket(player)) {
            ClientPacketHandlers.disconnectSafeMode(connection, player);
        }
    }
}