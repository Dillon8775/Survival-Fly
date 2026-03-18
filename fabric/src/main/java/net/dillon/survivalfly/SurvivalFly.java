package net.dillon.survivalfly;

import net.dillon.survivalfly.event.CommonEvents;
import net.dillon.survivalfly.option.ModOptions;
import net.dillon.survivalfly.option.PermissionLevel;
import net.dillon.survivalfly.util.ModUtil;
import net.fabricmc.api.ModInitializer;

import static net.dillon.survivalfly.util.ModUtil.options;
import static net.dillon.survivalfly.util.ModUtil.warn;

/**
 * Main entrypoint for survival fly.
 */
public class SurvivalFly implements ModInitializer {

	@Override
	public void onInitialize() {
		if (options().permissionLevel == null) { // fix odd bug
			warn("Permission level is somehow null, fixing.");
			options().permissionLevel = PermissionLevel.REGULAR;
			ModOptions.saveConfig();
		}

		ModOptions.loadConfig();

		CommonEvents.registerCommands();
		CommonEvents.registerPayloads();

		ModUtil.initializeSuccess();
	}
}