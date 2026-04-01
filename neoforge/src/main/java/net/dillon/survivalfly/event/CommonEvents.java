package net.dillon.survivalfly.event;

import net.dillon.survivalfly.SurvivalFly;
import net.dillon.survivalfly.command.*;
import net.dillon.survivalfly.packet.UpdateFlightSpeedC2SPayload;
import net.dillon.survivalfly.util.ModUtil;
import net.dillon.survivalfly.util.PlayerAbilitiesExtension;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.*;

@EventBusSubscriber(modid = SurvivalFly.MOD_ID)
public class CommonEvents {
    private static final Set<UUID> FLYING_PLAYERS = new HashSet<>();
    private static final Map<UUID, Float> FLIGHT_SPEED = new HashMap<>();

    @SubscribeEvent
    public static void register(RegisterCommandsEvent dispatcher) {
        dispatcher.getDispatcher().register(
                SurvivalFlyCommand.getHelpCommand()
        );
        dispatcher.getDispatcher().register(
                FlightCommand.getFlightCommand()
        );
        dispatcher.getDispatcher().register(
                FlightStatusCommand.getFlightStatusCommand()
        );
        dispatcher.getDispatcher().register(
                FlightSpeedCommand.getFlightSpeedCommand()
        );
        dispatcher.getDispatcher().register(
                ElytraFlightCommand.getElytraFlightCommand()
        );
        dispatcher.getDispatcher().register(
                FlightExhaustionCommand.getFlightExhaustionCommand()
        );
        dispatcher.getDispatcher().register(
                FriendlyFlightCommand.getFriendlyFlightCommand()
        );
        dispatcher.getDispatcher().register(
                PermissionsCommand.getPermissionsCommand()
        );
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            if (event.getOriginal().getAbilities().mayfly) {
                FLYING_PLAYERS.add(event.getEntity().getUUID());
            }
            FLIGHT_SPEED.put(event.getOriginal().getUUID(), event.getOriginal().getAbilities().getFlyingSpeed());
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();

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

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        ((PlayerAbilitiesExtension)player).setEverEnabledFlight(player.getAbilities().mayfly);
    }

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1").optional();
        registrar.playToServer(
                UpdateFlightSpeedC2SPayload.PAYLOAD_ID,
                UpdateFlightSpeedC2SPayload.CODEC,
                CommonEvents::handle
        );
    }

    public static void handle(UpdateFlightSpeedC2SPayload payload, IPayloadContext context) {
        ModUtil.handleFlightSpeed(payload, context.player());
    }
}
