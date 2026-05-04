package net.dillon.survivalfly.event;

import net.dillon.survivalfly.keybind.ModKeybinds;
import net.dillon.survivalfly.util.ModUtil;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModUtil.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ForgeClientEvents {

    @SubscribeEvent
    public static void registerForgeKeybindings(RegisterKeyMappingsEvent event) {
        ModKeybinds.initKeybinds();

        event.register(ModKeybinds.RESET_FLIGHT_SPEED);
        event.register(ModKeybinds.CHANGE_FLIGHT_SPEED);
    }
}