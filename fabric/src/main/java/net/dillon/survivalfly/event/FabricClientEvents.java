package net.dillon.survivalfly.event;

import net.dillon.survivalfly.keybind.ModKeybinds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

@Environment(EnvType.CLIENT)
public class FabricClientEvents {

    public static void registerFabricKeybinds() {
        KeyBindingHelper.registerKeyBinding(ModKeybinds.CHANGE_FLIGHT_SPEED);
        KeyBindingHelper.registerKeyBinding(ModKeybinds.RESET_FLIGHT_SPEED);
    }
}