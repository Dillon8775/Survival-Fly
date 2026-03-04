package net.dillon.survivalfly.event;

import net.dillon.survivalfly.command.FlightCommand;
import net.dillon.survivalfly.command.FlightSpeedCommand;
import net.dillon.survivalfly.command.FlightStatusCommand;
import net.dillon.survivalfly.packet.UpdateFlightSpeedC2SPayload;
import net.dillon.survivalfly.util.PlayerAbilitiesExtension;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;

import static net.dillon.survivalfly.util.ModUtil.handleFlightSpeed;

public class CommonEvents {

    /**
     * Registers the {@code client-to-server} flight speed change payload.
     */
    public static void registerPayloads() {
        PayloadTypeRegistry.serverboundPlay().register(
                UpdateFlightSpeedC2SPayload.PAYLOAD_ID,
                UpdateFlightSpeedC2SPayload.CODEC
        );

        ServerPlayNetworking.registerGlobalReceiver(
                UpdateFlightSpeedC2SPayload.PAYLOAD_ID,
                (payload, context) -> {
                    handleFlightSpeed(payload, context.player());
                }
        );

        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            newPlayer.getAbilities().mayfly = ((PlayerAbilitiesExtension)oldPlayer.getAbilities()).hasEverEnabledFlight();
            newPlayer.getAbilities().setFlyingSpeed(oldPlayer.getAbilities().getFlyingSpeed());
            newPlayer.onUpdateAbilities();
        });

        ServerPlayConnectionEvents.JOIN.register((serverPlayNetworkHandler, packetSender, minecraftServer) -> {
            ServerPlayer serverPlayer = serverPlayNetworkHandler.player;
            ((PlayerAbilitiesExtension)serverPlayer.getAbilities()).setEverEnabledFlight(serverPlayer.getAbilities().flying);
        });
    }

    /**
     * Registers all {@code Survival Fly} commands.
     */
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((commandDispatcher, commandRegistryAccess, registrationEnvironment) -> {
            commandDispatcher.register(FlightCommand.getFlightCommand());
        });
        CommandRegistrationCallback.EVENT.register((commandDispatcher, commandRegistryAccess, registrationEnvironment) -> {
            commandDispatcher.register(FlightSpeedCommand.getFlightSpeedCommand());
        });
        CommandRegistrationCallback.EVENT.register((commandDispatcher, commandRegistryAccess, registrationEnvironment) -> {
            commandDispatcher.register(FlightStatusCommand.getFlightStatusCommand());
        });
    }
}