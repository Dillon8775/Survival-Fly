package net.dillon.survivalfly;

import net.dillon.survivalfly.option.ModOptions;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
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
		ModOptions.loadConfig();
		CommandRegistrationCallback.EVENT.register((commandDispatcher, commandRegistryAccess, registrationEnvironment) -> {
			FlightCommand.register(commandDispatcher);
		});
		CommandRegistrationCallback.EVENT.register((commandDispatcher, commandRegistryAccess, registrationEnvironment) -> {
			FlightStatusCommand.register(commandDispatcher);
		});
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

	public static Text lowercaseText(ServerPlayerEntity player) {
		return player.getAbilities().allowFlying ? Text.translatable("survivalfly.enabled.lowercase").formatted(Formatting.GREEN) : Text.translatable("survivalfly.disabled.lowercase").formatted(Formatting.RED);
	}
}