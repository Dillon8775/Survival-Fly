package net.dillon.survivalfly.packet;

import net.blay09.mods.balm.Balm;
import net.dillon.dillonlib.util.SimplePermissions;
import net.dillon.survivalfly.command.FlightCommand;
import net.dillon.survivalfly.helper.ModConstants;
import net.dillon.survivalfly.helper.ModHelper;
import net.dillon.survivalfly.option.ModCommonOptions;
import net.dillon.survivalfly.packet.clientbound.SendServerOptionsS2CPacket;
import net.dillon.survivalfly.packet.serverbound.RequestServerConfigC2SPacket;
import net.dillon.survivalfly.packet.serverbound.UpdateFlightC2SPacket;
import net.dillon.survivalfly.packet.serverbound.UpdateFlightSpeedC2SPacket;
import net.dillon.survivalfly.packet.serverbound.UpdateServerOptionsC2SPacket;
import net.dillon.survivalfly.permission.Nodes;
import net.dillon.survivalfly.permission.PermissionUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.List;

import static net.dillon.survivalfly.helper.ModHelper.modEnabled;
import static net.dillon.survivalfly.option.OptionInstances.common;

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

    /**
     * Handles requesting the {@code server} config.
     */
    public static void handleRequestServerConfig(ServerPlayer player, RequestServerConfigC2SPacket packet) {
        ModCommonOptions serverOptions = common();
        Balm.networking().sendTo(player, SendServerOptionsS2CPacket.from(serverOptions, SimplePermissions.admin(player.createCommandSourceStack())));
        ModConstants.LOGGER.info("{} requested this server's config", player.getDisplayName().getString());
    }

    /**
     * Handles updating the {@code server} config.
     */
    public static void handleUpdateServerConfig(ServerPlayer player, UpdateServerOptionsC2SPacket packet) {
        ModCommonOptions received = packet.toOptions();

        String playerName = player.getDisplayName().getString();

        if (!SimplePermissions.admin(player.createCommandSourceStack())) {
            ModConstants.LOGGER.info("{} attempted to modify server config, but doesn't have permissions.", playerName);
            return;
        }

        Balm.config().updateLocalConfig(
                ModCommonOptions.class,
                config -> {
                    config.enableMod = received.enableMod;
                    config.permissions = received.permissions;
                    config.flightSpeedModification = received.flightSpeedModification;
                    config.elytraFlight = received.elytraFlight;
                    config.flightExhaustion = received.flightExhaustion;
                    config.crouchFlight = received.crouchFlight;
                    config.friendlyFlight = received.friendlyFlight;
                }
        );

        ModConstants.LOGGER.info("{} modified server option", playerName);
    }
}