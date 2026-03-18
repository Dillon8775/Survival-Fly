package net.dillon.survivalfly.util;

import com.mojang.logging.LogUtils;
import net.dillon.survivalfly.option.ModOptions;
import net.dillon.survivalfly.packet.UpdateFlightSpeedC2SPayload;
import net.dillon.survivalfly.platform.MultiLoader;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.PermissionCheck;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;

/**
 * Utility class for the {@code Survival Fly} mod.
 */
public class ModUtil {
    public static final float DEFAULT_FLIGHT_SPEED = 0.05F;
    private static final Logger LOGGER = LogUtils.getLogger();

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
     * Sends the successfully initialized message.
     */
    public static void initializeSuccess() {
        info("Survival Fly version " + MultiLoader.PLATFORM.getModVersion() + " (for " + MultiLoader.PLATFORM.getPlatformName() + ") loaded successfully!");
    }

    /**
     * Sends a warning message to console.
     */
    public static void warn(String message) {
        LOGGER.warn(message);
    }

    /**
     * Handles flight speed changing.
     */
    public static void handleFlightSpeed(UpdateFlightSpeedC2SPayload payload, Player player) {
        float speed = payload.speed();

        player.getAbilities().setFlyingSpeed(speed);
        player.onUpdateAbilities();
    }

    /**
     * @return the permission level required to run any /flight commands.
     * @since 1.21.11
     */
    public static PermissionCheck getPermissionLevel(int level) {
        return level == 4 ? Commands.LEVEL_OWNERS :
                level == 3 ? Commands.LEVEL_ADMINS :
                        level == 2 ? Commands.LEVEL_GAMEMASTERS :
                                level == 1 ? Commands.LEVEL_MODERATORS : Commands.LEVEL_ALL;
    }

    /**
     * Returns enabled/disabled text.
     */
    public static Component statusText(ServerPlayer player, boolean isFirstLetterLowercase) {
        return isFirstLetterLowercase ? player.getAbilities().mayfly ? Component.translatable("survivalfly.enabled.lowercase").withStyle(ChatFormatting.GREEN) : Component.translatable("survivalfly.disabled.lowercase").withStyle(ChatFormatting.RED) : player.getAbilities().mayfly ? Component.translatable("survivalfly.enabled").withStyle(ChatFormatting.GREEN) : Component.translatable("survivalfly.disabled").withStyle(ChatFormatting.RED);
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