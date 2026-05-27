package net.dillon.survivalfly.event;

import net.dillon.survivalfly.helper.ModHelper;
import net.dillon.survivalfly.keybind.ModKeyMappings;
import net.dillon.survivalfly.packet.ClientPacketHandlers;
import net.dillon.survivalfly.packet.UpdateFlightC2SPacket;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.network.registration.NetworkRegistry;

@EventBusSubscriber(modid = ModHelper.MOD_ID, value = Dist.CLIENT)
public class NeoForgedClientEvents {

    @SubscribeEvent
    public static void registerNeoForgedKeybindings(RegisterKeyMappingsEvent event) {
        ModKeyMappings.initKeybinds();

        event.register(ModKeyMappings.RESET_FLIGHT_SPEED);
        event.register(ModKeyMappings.CHANGE_FLIGHT_SPEED);
    }

    @SubscribeEvent
    public static void onClientLoggingIn(ClientPlayerNetworkEvent.LoggingIn event) {
        if (!NetworkRegistry.hasChannel(event.getPlayer().connection, UpdateFlightC2SPacket.ID)) {
            ClientPacketHandlers.disconnectSafeMode(event.getConnection(), event.getPlayer());
        }
    }
}