package net.dillon.survivalfly.event;

import net.dillon.survivalfly.util.ModUtil;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModUtil.MOD_ID)
public class ForgeCommonEvents {

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
        CommonEvents.onPlayerRespawn(event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        CommonEvents.onPlayerJoin(event.getEntity());
    }
}
