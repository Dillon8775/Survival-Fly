package net.dillon.survivalfly.event;

import net.dillon.survivalfly.SurvivalFly;
import net.dillon.survivalfly.keybind.ModKeybinds;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(modid = SurvivalFly.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(ModKeybinds.RESET_FLIGHT_SPEED);
        event.register(ModKeybinds.CHANGE_FLIGHT_SPEED);
    }
}