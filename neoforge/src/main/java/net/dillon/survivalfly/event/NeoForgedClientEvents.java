package net.dillon.survivalfly.event;

import net.dillon.survivalfly.keybind.ModKeybinds;
import net.dillon.survivalfly.packet.ClientPacketHandlers;
import net.dillon.survivalfly.packet.UpdateFlightC2SPacket;
import net.dillon.survivalfly.util.ModUtil;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.network.registration.NetworkRegistry;

import static net.dillon.survivalfly.util.ModUtil.options;

@EventBusSubscriber(modid = ModUtil.MOD_ID, value = Dist.CLIENT)
public class NeoForgedClientEvents {

    @SubscribeEvent
    public static void registerNeoForgedKeybindings(RegisterKeyMappingsEvent event) {
        ModKeybinds.initKeybinds();

        event.register(ModKeybinds.RESET_FLIGHT_SPEED);
        event.register(ModKeybinds.CHANGE_FLIGHT_SPEED);
    }

    @SubscribeEvent
    public static void onClientLoggingIn(ClientPlayerNetworkEvent.LoggingIn event) {
        if (!options().safeMode) {
            return;
        }

        if (!NetworkRegistry.hasChannel(event.getPlayer().connection, UpdateFlightC2SPacket.ID)) {
            ClientPacketHandlers.disconnectSafeMode(event.getConnection(), event.getPlayer());
        }
    }
}