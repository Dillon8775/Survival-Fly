package net.dillon.survivalfly.event;

import net.dillon.survivalfly.util.ModUtil;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = ModUtil.MOD_ID)
public class NeoForgedCommonEvents {

    @SubscribeEvent
    public static void register(RegisterCommandsEvent dispatcher) {
        CommonEvents.registerCommands(dispatcher.getDispatcher(), dispatcher.getBuildContext());
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        CommonEvents.onPlayerClone(event.isWasDeath(), event.getOriginal(), event.getEntity(), event.getOriginal().getAbilities().getFlyingSpeed());
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        CommonEvents.onPlayerJoin(event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        CommonEvents.onPlayerJoin(event.getEntity());
    }
}
