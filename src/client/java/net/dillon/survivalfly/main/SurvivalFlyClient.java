package net.dillon.survivalfly.main;

import net.dillon.survivalfly.keybind.ModKeybinds;
import net.fabricmc.api.ClientModInitializer;

public class SurvivalFlyClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ModKeybinds.init();
		SurvivalFly.info("Initialized survival fly keybinds!");
	}
}