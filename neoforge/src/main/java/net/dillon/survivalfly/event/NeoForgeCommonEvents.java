package net.dillon.survivalfly.event;

import net.dillon.survivalfly.helper.ModConstants;
import net.dillon.survivalfly.main.CommonEvents;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = ModConstants.MOD_ID)
public class NeoForgeCommonEvents {

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!(event.getOriginal() instanceof ServerPlayer oldServerPlayer) || !(event.getEntity() instanceof ServerPlayer newServerPlayer)) {
            return;
        }

        CommonEvents.onPlayerClone(event.isWasDeath(), oldServerPlayer, newServerPlayer, event.getOriginal().getAbilities().getFlyingSpeed());
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) {
            return;
        }

        CommonEvents.onPlayerRespawn(serverPlayer);
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) {
            return;
        }

        CommonEvents.onPlayerJoin(serverPlayer);
    }
}