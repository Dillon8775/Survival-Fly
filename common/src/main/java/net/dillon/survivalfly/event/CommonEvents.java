package net.dillon.survivalfly.event;

import com.mojang.brigadier.CommandDispatcher;
import net.blay09.mods.balm.api.Balm;
import net.dillon.survivalfly.command.*;
import net.dillon.survivalfly.packet.UpdateFlightSpeedC2SPayload;
import net.dillon.survivalfly.util.ModUtil;
import net.dillon.survivalfly.util.PlayerAbilitiesExtension;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.*;

/**
 * Common-side events for Survival Fly.
 */
public class CommonEvents {
    private static final Set<UUID> FLYING_PLAYERS = new HashSet<>();
    private static final Map<UUID, Float> FLIGHT_SPEED = new HashMap<>();

    public static void onPlayerClone(boolean isWasDeath, Player original, Player entity, float flyingSpeed) {
        if (isWasDeath) {
            if (original.getAbilities().mayfly) {
                FLYING_PLAYERS.add(entity.getUUID());
            }
            FLIGHT_SPEED.put(original.getUUID(), flyingSpeed);
        }
    }

    public static void onPlayerRespawn(Player player) {
        if (FLYING_PLAYERS.contains(player.getUUID())) {
            player.getAbilities().mayfly = true;
            FLYING_PLAYERS.remove(player.getUUID());
        }
        if (FLIGHT_SPEED.containsKey(player.getUUID())) {
            player.getAbilities().setFlyingSpeed(FLIGHT_SPEED.getOrDefault(player.getUUID(), ModUtil.DEFAULT_FLIGHT_SPEED));
            FLIGHT_SPEED.remove(player.getUUID());
        }

        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.onUpdateAbilities();
        }
    }

    public static void onPlayerJoin(Player player) {
        ((PlayerAbilitiesExtension)player).setEverEnabledFlight(player.getAbilities().mayfly);
    }

    public static void registerPackets() {
        Balm.getNetworking().registerServerboundPacket(
                UpdateFlightSpeedC2SPayload.PACKET_TYPE,
                UpdateFlightSpeedC2SPayload.class,
                UpdateFlightSpeedC2SPayload.CODEC,
                ModUtil::handleFlightSpeed
        );
    }

    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandRegistryAccess) {
        dispatcher.register(SurvivalFlyCommand.getHelpCommand());
        dispatcher.register(FlightCommand.getFlightCommand());
        dispatcher.register(FlightStatusCommand.getFlightStatusCommand());
        dispatcher.register(FlightSpeedCommand.getFlightSpeedCommand());
        dispatcher.register(ElytraFlightCommand.getElytraFlightCommand());
        dispatcher.register(FlightExhaustionCommand.getFlightExhaustionCommand());
        dispatcher.register(FriendlyFlightCommand.getFriendlyFlightCommand());
        dispatcher.register(PermissionsCommand.getPermissionsCommand());
    }
}