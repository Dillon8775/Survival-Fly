package net.dillon.survivalfly.event;

import net.dillon.survivalfly.helper.ModHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = ModHelper.MOD_ID)
public class NeoForgedCommonEvents {

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        CommonEvents.onPlayerClone(event.isWasDeath(), event.getOriginal(), event.getEntity(), event.getOriginal().getAbilities().getFlyingSpeed());
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        CommonEvents.onPlayerRespawn(event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        CommonEvents.onPlayerJoin(event.getEntity());
    }
}
