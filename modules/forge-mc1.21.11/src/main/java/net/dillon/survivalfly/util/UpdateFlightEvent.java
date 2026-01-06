package net.dillon.survivalfly.util;

import net.dillon.survivalfly.main.SurvivalFly;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

/**
 * Keeps the player's flight updated.
 */
@Mod.EventBusSubscriber(modid = SurvivalFly.MOD_ID)
public class UpdateFlightEvent {
    private static final Set<UUID> FLYING_PLAYERS = new HashSet<>();
    private static final Map<UUID, Float> FLIGHT_SPEED = new HashMap<>();

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
            player.getAbilities().setFlyingSpeed(FLIGHT_SPEED.getOrDefault(player.getUUID(), SurvivalFly.DEFAULT_FLIGHT_SPEED));
            FLIGHT_SPEED.remove(player.getUUID());
        }

        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.onUpdateAbilities();
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        ((PlayerAbilitiesExtension)player.getAbilities()).setEverEnabledFlight(player.getAbilities().mayfly);
    }
}