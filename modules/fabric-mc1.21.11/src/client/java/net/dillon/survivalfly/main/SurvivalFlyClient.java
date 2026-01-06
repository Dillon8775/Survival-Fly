package net.dillon.survivalfly.main;

import net.dillon.survivalfly.keybind.ModKeybinds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class SurvivalFlyClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ModKeybinds.init();
		SurvivalFly.info("Initialized survival fly keybinds!");
	}

    /**
     * Checks if the {@code Flashback mod} is loaded.
     */
    public static boolean isFlashbackLoaded() {
        return FabricLoader.getInstance().isModLoaded("flashback");
    }
}