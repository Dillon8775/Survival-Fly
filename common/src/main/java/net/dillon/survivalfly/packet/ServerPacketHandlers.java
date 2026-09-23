package net.dillon.survivalfly.packet;

import net.dillon.survivalfly.command.FlightCommand;
import net.dillon.survivalfly.helper.ModHelper;
import net.dillon.survivalfly.packet.serverbound.UpdateFlightC2SPacket;
import net.dillon.survivalfly.packet.serverbound.UpdateFlightSpeedC2SPacket;
import net.dillon.survivalfly.permission.Nodes;
import net.dillon.survivalfly.permission.PermissionUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.List;

import static net.dillon.survivalfly.helper.ModHelper.modEnabled;

/**
 * Handles server-packet related things.
 */
public class ServerPacketHandlers {

    /**
     * Handles updating the player's flight.
     */
    public static void handleUpdateFlight(Player player, UpdateFlightC2SPacket packet) {
        if (!modEnabled() || !(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        if (PermissionUtil.hasPermissionDefaultFallback(serverPlayer.createCommandSourceStack(), serverPlayer, Nodes.FLIGHT)) {
            FlightCommand.execute(serverPlayer.createCommandSourceStack(), List.of(serverPlayer), packet.flight());
        }
    }

    /**
     * Handles flight speed changing.
     */
    public static void handleUpdateFlightSpeed(Player player, UpdateFlightSpeedC2SPacket payload) {
        if (!modEnabled() || !(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        if (PermissionUtil.hasPermissionToChangeFlightSpeed(serverPlayer.createCommandSourceStack())) {
            float speed = payload.speed();

            serverPlayer.getAbilities().setFlyingSpeed(speed);
            serverPlayer.onUpdateAbilities();
            serverPlayer.sendOverlayMessage(Component.translatable("survivalfly.current_flight_speed", ModHelper.flyingSpeedAsDecimalString(player)).withStyle(ChatFormatting.GREEN));
        }
    }
}