package net.dillon.survivalfly;

import net.dillon.survivalfly.option.ModOptions;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main entrypoint for survival fly.
 */
public class SurvivalFly implements ModInitializer {
	public static final String MOD_ID = "survivalfly";
	private static final Logger LOGGER = LoggerFactory.getLogger("Survival Fly");

	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register(((commandDispatcher, commandRegistryAccess, registrationEnvironment) -> {
			FlyCommand.register(commandDispatcher);
		}));
		info("Initialized Survival Fly mod successfully!");
	}

	/**
	 * Returns the options.
	 */
	public static ModOptions options() {
		return ModOptions.OPTIONS;
	}

	/**
	 * Sends a message to console.
	 */
	public static void info(String message) {
		LOGGER.info(message);
	}
}