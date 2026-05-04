package net.dillon.survivalfly.packet;

import net.dillon.survivalfly.command.FlightCommand;
import net.dillon.survivalfly.permission.Nodes;
import net.dillon.survivalfly.permission.PermissionUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.List;

/**
 * Handles server-packet related things.
 */
public class ServerPacketHandlers {

    /**
     * Handles updating the player's flight.
     */
    public static void handleUpdateFlight(Player player, UpdateFlightC2SPacket packet) {
        if (!(player instanceof ServerPlayer serverPlayer)) {
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
        float speed = payload.speed();

        player.getAbilities().setFlyingSpeed(speed);
        player.onUpdateAbilities();
    }
}