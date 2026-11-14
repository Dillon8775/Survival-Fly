package net.dillon.survivalfly.main;

import net.blay09.mods.balm.Balm;
import net.dillon.survivalfly.client.config.ConfigurationScreen;
import net.dillon.survivalfly.helper.ModConstants;
import net.dillon.survivalfly.packet.ServerPacketHandlers;
import net.dillon.survivalfly.packet.clientbound.ReceiveServerOptionsS2CPacket;
import net.dillon.survivalfly.packet.serverbound.RequestServerOptionsC2SPacket;
import net.dillon.survivalfly.packet.serverbound.UpdateFlightC2SPacket;
import net.dillon.survivalfly.packet.serverbound.UpdateFlightSpeedC2SPacket;
import net.dillon.survivalfly.packet.serverbound.UpdateServerOptionsC2SPacket;
import net.dillon.survivalfly.util.PlayerAbilitiesExtensionImpl;
import net.minecraft.server.level.ServerPlayer;

import java.util.*;

import static net.dillon.dillonlib.task.ClientTasks.openScreen;
import static net.dillon.dillonlib.task.CommonTasks.sendUpdateMessage;
import static net.dillon.survivalfly.helper.ModConstants.MOD_INFO;
import static net.dillon.survivalfly.helper.ModHelper.modEnabled;

/**
 * Common events for Survival Fly.
 */
public class CommonEvents {
    private static final Set<UUID> FLYING_PLAYERS = new HashSet<>();
    private static final Map<UUID, Float> FLIGHT_SPEED = new HashMap<>();

    public static void onPlayerClone(boolean isWasDeath, ServerPlayer original, ServerPlayer entity, float flyingSpeed) {
        if (!modEnabled()) {
            return;
        }

        if (isWasDeath) {
            if (original.getAbilities().mayfly) {
                FLYING_PLAYERS.add(entity.getUUID());
            }
            FLIGHT_SPEED.put(original.getUUID(), flyingSpeed);
        }
    }

    public static void onPlayerRespawn(ServerPlayer serverPlayer) {
        if (!modEnabled()) {
            return;
        }

        if (FLYING_PLAYERS.contains(serverPlayer.getUUID())) {
            serverPlayer.getAbilities().mayfly = true;
            FLYING_PLAYERS.remove(serverPlayer.getUUID());
        }
        if (FLIGHT_SPEED.containsKey(serverPlayer.getUUID())) {
            serverPlayer.getAbilities().setFlyingSpeed(FLIGHT_SPEED.getOrDefault(serverPlayer.getUUID(), ModConstants.DEFAULT_FLIGHT_SPEED));
            FLIGHT_SPEED.remove(serverPlayer.getUUID());
        }

        serverPlayer.onUpdateAbilities();
    }

    public static void onPlayerJoin(ServerPlayer serverPlayer) {
        if (!modEnabled()) {
            return;
        }

        sendUpdateMessage(MOD_INFO, serverPlayer);

        ((PlayerAbilitiesExtensionImpl)serverPlayer).setEverEnabledFlight(serverPlayer.getAbilities().mayfly);
    }

    /**
     * Registers all survival fly packets.
     */
    public static void registerPackets() {
        Balm.networking().allowClientAndServerOnly(ModConstants.MOD_ID);

        Balm.networking().registerServerboundPacket(
                UpdateFlightSpeedC2SPacket.PACKET_TYPE,
                UpdateFlightSpeedC2SPacket.class,
                UpdateFlightSpeedC2SPacket.CODEC,
                ServerPacketHandlers::handleUpdateFlightSpeed
        );

        Balm.networking().registerServerboundPacket(
                UpdateFlightC2SPacket.PACKET_TYPE,
                UpdateFlightC2SPacket.class,
                UpdateFlightC2SPacket.CODEC,
                ServerPacketHandlers::handleUpdateFlight
        );

        Balm.networking().registerServerboundPacket(
                RequestServerOptionsC2SPacket.PACKET_TYPE,
                RequestServerOptionsC2SPacket.class,
                RequestServerOptionsC2SPacket.CODEC,
                ServerPacketHandlers::handleRequestServerConfig
        );

        Balm.networking().registerServerboundPacket(
                UpdateServerOptionsC2SPacket.PACKET_TYPE,
                UpdateServerOptionsC2SPacket.class,
                UpdateServerOptionsC2SPacket.CODEC,
                ServerPacketHandlers::handleUpdateServerConfig
        );

        Balm.networking().registerClientboundPacket(
                ReceiveServerOptionsS2CPacket.PACKET_TYPE,
                ReceiveServerOptionsS2CPacket.class,
                ReceiveServerOptionsS2CPacket.CODEC,
                (player, server) -> {
                    openScreen(ConfigurationScreen.configScreen(server.to(), server.hasPermission()).generateScreen(null));
                }
        );
    }
}