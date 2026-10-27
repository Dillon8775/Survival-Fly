package net.dillon.survivalfly.event;

import net.dillon.survivalfly.main.CommonEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class FabricCommonEvents {

    public static void registerCloningEvents() {
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            CommonEvents.onPlayerClone(
                    !alive,
                    oldPlayer,
                    newPlayer,
                    oldPlayer.getAbilities().getFlyingSpeed()
            );
        });
    }

    public static void registerRespawningEvents() {
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            CommonEvents.onPlayerRespawn(newPlayer);
        });
    }

    public static void registerServerConnectionEvents() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            CommonEvents.onPlayerJoin(handler.player);
        });
    }
}