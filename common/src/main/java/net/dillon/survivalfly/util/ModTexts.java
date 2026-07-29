package net.dillon.survivalfly.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

/**
 * Texts for {@code Survival Fly.}
 */
public class ModTexts {
    public static final Component ENABLED_UPPERCASE = Component.literal("Enabled").withStyle(ChatFormatting.GREEN);
    public static final Component ENABLED_LOWERCASE = Component.literal("enabled").withStyle(ChatFormatting.GREEN);
    public static final Component DISABLED_UPPERCASE = Component.literal("Disabled").withStyle(ChatFormatting.RED);
    public static final Component DISABLED_LOWERCASE = Component.literal("disabled").withStyle(ChatFormatting.RED);

    public static final Component DAMAGED = Component.literal("Flight disabled, you took damage!").withStyle(ChatFormatting.RED);
    public static final Component COMBAT = Component.literal("Flight disabled, you started combat!").withStyle(ChatFormatting.RED);
    public static final Component FLY_AGAIN = Component.literal("You may fly again.").withStyle(ChatFormatting.GREEN);

    /**
     * @return the player's name in {@link Component} form.
     */
    public static Component getPlayerName(ServerPlayer player) {
        return player.getDisplayName();
    }

    /**
     * @return the player's gamemode in string form.
     */
    public static String getGameMode(ServerPlayer player) {
        return player.gameMode.getGameModeForPlayer().getName();
    }

    /**
     * @return enabled/disabled text in uppercase form.
     */
    public static Component blUppercase(boolean op) {
        return op ? ENABLED_UPPERCASE : DISABLED_UPPERCASE;
    }

    /**
     * @return enabled/disabled text in lowercase form.
     */
    public static Component blLowercase(boolean op) {
        return op ? ENABLED_LOWERCASE : DISABLED_LOWERCASE;
    }
}