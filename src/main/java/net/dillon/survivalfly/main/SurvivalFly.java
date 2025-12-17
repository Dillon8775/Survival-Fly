package net.dillon.survivalfly.main;

import net.dillon.survivalfly.command.FlightCommand;
import net.dillon.survivalfly.command.FlightSpeedCommand;
import net.dillon.survivalfly.command.FlightStatusCommand;
import net.dillon.survivalfly.option.ModOptions;
import net.dillon.survivalfly.option.PermissionLevel;
import net.dillon.survivalfly.packet.UpdateFlightSpeedC2SPayload;
import net.dillon.survivalfly.util.PlayerAbilitiesExtension;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.command.permission.PermissionCheck;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main entrypoint for survival fly.
 */
public class SurvivalFly implements ModInitializer {
	private static final Logger LOGGER = LoggerFactory.getLogger("Survival Fly");
	public static final float DEFAULT_FLIGHT_SPEED = 0.05F;

	@Override
	public void onInitialize() {
		if (options().permissionLevel == null) { // fix odd bug
			warn("Permission level is somehow null, fixing.");
			options().permissionLevel = PermissionLevel.REGULAR;
			ModOptions.saveConfig();
		}
		ModOptions.loadConfig();

		registerPayloads();
		registerCommands();

		info("Initialized Survival Fly mod successfully!");
	}

	/**
	 * Registers the {@code client-to-server} flight speed change payload.
	 */
	private static void registerPayloads() {
		PayloadTypeRegistry.playC2S().register(
				UpdateFlightSpeedC2SPayload.PAYLOAD_ID,
				UpdateFlightSpeedC2SPayload.CODEC
		);

		ServerPlayNetworking.registerGlobalReceiver(
				UpdateFlightSpeedC2SPayload.PAYLOAD_ID,
				(payload, context) -> {
					var player = context.player();
					float speed = payload.speed();

					player.getAbilities().setFlySpeed(speed);
					player.sendAbilitiesUpdate();
				}
		);

		ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
			newPlayer.getAbilities().allowFlying = ((PlayerAbilitiesExtension)oldPlayer.getAbilities()).hasEverEnabledFlight();
            newPlayer.getAbilities().setFlySpeed(oldPlayer.getAbilities().getFlySpeed());
			newPlayer.sendAbilitiesUpdate();
		});

		ServerPlayConnectionEvents.JOIN.register((serverPlayNetworkHandler, packetSender, minecraftServer) -> {
			ServerPlayerEntity serverPlayer = serverPlayNetworkHandler.player;
			((PlayerAbilitiesExtension)serverPlayer.getAbilities()).setEverEnabledFlight(serverPlayer.getAbilities().allowFlying);
		});
	}

	/**
	 * @return the permission level required to run any /flight commands.
	 * @since 1.21.11
	 */
	public static PermissionCheck getPermissionLevel(int level) {
		return level == 4 ? CommandManager.OWNERS_CHECK :
				level == 3 ? CommandManager.ADMINS_CHECK :
						level == 2 ? CommandManager.GAMEMASTERS_CHECK :
								level == 1 ? CommandManager.MODERATORS_CHECK : CommandManager.ALWAYS_PASS_CHECK;
	}

	/**
	 * Registers all {@code Survival Fly} commands.
	 */
	private static void registerCommands() {
		CommandRegistrationCallback.EVENT.register((commandDispatcher, commandRegistryAccess, registrationEnvironment) -> {
			FlightCommand.register(commandDispatcher);
		});
		CommandRegistrationCallback.EVENT.register((commandDispatcher, commandRegistryAccess, registrationEnvironment) -> {
			FlightSpeedCommand.register(commandDispatcher);
		});
		CommandRegistrationCallback.EVENT.register((commandDispatcher, commandRegistryAccess, registrationEnvironment) -> {
			FlightStatusCommand.register(commandDispatcher);
		});
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

	/**
	 * Sends a warning message to console.
	 */
	public static void warn(String message) {
		LOGGER.warn(message);
	}

	/**
	 * Returns enabled/disabled text.
	 */
	public static Text statusText(ServerPlayerEntity player, boolean isFirstLetterLowercase) {
		return isFirstLetterLowercase ? player.getAbilities().allowFlying ? Text.translatable("survivalfly.enabled.lowercase").formatted(Formatting.GREEN) : Text.translatable("survivalfly.disabled.lowercase").formatted(Formatting.RED) : player.getAbilities().allowFlying ? Text.translatable("survivalfly.enabled").formatted(Formatting.GREEN) : Text.translatable("survivalfly.disabled").formatted(Formatting.RED);
	}

	/**
	 * Returns the flight speed in decimal form.
	 */
	public static float percentageAsDecimal(float speed) {
		return (speed / 100.0F) * 0.2F;
	}

	/**
	 * Returns the flight speed in percentage form.
	 */
	public static int decimalAsPercentage(float speed) {
		return Math.round((speed / 0.2F) * 100);
	}
}