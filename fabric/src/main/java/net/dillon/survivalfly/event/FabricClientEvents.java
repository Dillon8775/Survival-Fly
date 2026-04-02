package net.dillon.survivalfly.event;

import net.dillon.survivalfly.keybind.ModKeybinds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;

@Environment(EnvType.CLIENT)
public class FabricClientEvents {

    public static void registerKeybinds() {
        KeyMappingHelper.registerKeyMapping(ModKeybinds.CHANGE_FLIGHT_SPEED);
        KeyMappingHelper.registerKeyMapping(ModKeybinds.RESET_FLIGHT_SPEED);
    }
}