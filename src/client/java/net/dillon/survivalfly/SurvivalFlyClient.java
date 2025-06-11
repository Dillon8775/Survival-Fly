package net.dillon.survivalfly;

import net.dillon.survivalfly.keybind.ModKeybinds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class SurvivalFlyClient implements ClientModInitializer {
	private static final String MOD_KEYBINDS = "survivalfly.title.options";
	public static final KeyBinding OPEN_CONFIG_SCREEN = KeyBindingHelper.registerKeyBinding(new KeyBinding("survivalfly.options.open_config_screen", InputUtil.GLFW_KEY_F, MOD_KEYBINDS));

	@Override
	public void onInitializeClient() {
		ModKeybinds.init();
		SurvivalFly.info("Initialized survival fly keybinds!");
	}
}