package net.dillon.survivalfly.event;

import net.blay09.mods.balm.Balm;
import net.dillon.survivalfly.helper.ModHelper;
import net.dillon.survivalfly.packet.ServerPacketHandlers;
import net.dillon.survivalfly.packet.UpdateFlightC2SPacket;
import net.dillon.survivalfly.packet.UpdateFlightSpeedC2SPacket;
import net.dillon.survivalfly.util.PlayerAbilitiesExtension;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.*;

import static net.dillon.survivalfly.helper.ModHelper.modEnabled;

/**
 * Common-side events for Survival Fly.
 */
public class CommonEvents {
    private static final Set<UUID> FLYING_PLAYERS = new HashSet<>();
    private static final Map<UUID, Float> FLIGHT_SPEED = new HashMap<>();

    public static void onPlayerClone(boolean isWasDeath, Player original, Player entity, float flyingSpeed) {
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

    public static void onPlayerRespawn(Player player) {
        if (!modEnabled()) {
            return;
        }

        if (FLYING_PLAYERS.contains(player.getUUID())) {
            player.getAbilities().mayfly = true;
            FLYING_PLAYERS.remove(player.getUUID());
        }
        if (FLIGHT_SPEED.containsKey(player.getUUID())) {
            player.getAbilities().setFlyingSpeed(FLIGHT_SPEED.getOrDefault(player.getUUID(), ModHelper.DEFAULT_FLIGHT_SPEED));
            FLIGHT_SPEED.remove(player.getUUID());
        }

        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.onUpdateAbilities();
        }
    }

    public static void onPlayerJoin(Player player) {
        if (!modEnabled()) {
            return;
        }

        ((PlayerAbilitiesExtension)player).setEverEnabledFlight(player.getAbilities().mayfly);
    }

    /**
     * Registers all survival fly packets.
     */
    public static void registerPackets() {
        Balm.networking().allowClientAndServerOnly(ModHelper.MOD_ID);

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
    }
}