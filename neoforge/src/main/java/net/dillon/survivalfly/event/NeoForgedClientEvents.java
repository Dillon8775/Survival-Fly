package net.dillon.survivalfly.event;

import net.dillon.survivalfly.helper.ModConstants;
import net.dillon.survivalfly.keybind.ModKeyMappings;
import net.dillon.survivalfly.main.ClientEvents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(modid = ModConstants.MOD_ID, value = Dist.CLIENT)
public class NeoForgedClientEvents {

    @SubscribeEvent
    public static void registerNeoForgedKeybindings(RegisterKeyMappingsEvent event) {
        ModKeyMappings.initKeybinds();

        event.register(ModKeyMappings.CHANGE_FLIGHT_SPEED);
    }

    @SubscribeEvent
    public static void onClientLoggingIn(ClientPlayerNetworkEvent.LoggingIn event) {
        ClientEvents.onPlayerJoin(event.getConnection(), event.getPlayer());
    }
}