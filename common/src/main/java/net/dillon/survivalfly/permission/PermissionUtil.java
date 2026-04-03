package net.dillon.survivalfly.permission;

import net.dillon.survivalfly.platform.MultiLoader;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

import static net.dillon.survivalfly.util.ModUtil.options;

/**
 * Permission utility class for Survival Fly.
 */
public class PermissionUtil {

    /**
     * @return the permission level required to run any /flight commands.
     */
    public static int getPermissionLevel(int level) {
        return level == 3 ? Commands.LEVEL_ADMINS :
                level == 2 ? Commands.LEVEL_GAMEMASTERS :
                level == 1 ? Commands.LEVEL_MODERATORS : Commands.LEVEL_ALL;
    }

    /**
     * @return if the player has permission to execute a command, with the default fallback.
     */
    public static boolean hasPermissionDefaultFallback(CommandSourceStack commandSourceStack, ServerPlayer player, Nodes node) {
        return hasPermissionToExecute(player, node, hasCurrentPermissionId(commandSourceStack));
    }

    /**
     * @return if the user has the permissions to execute a command.
     */
    private static boolean hasPermissionToExecute(ServerPlayer player, Nodes node, boolean fallback) {
        if (player == null) {
            return fallback;
        }

        return MultiLoader.getPlatform().hasPermission(player, node.getNode(), fallback);
    }

    /**
     * @return if the player's permission level is above or equal to the default set permissions id.
     */
    private static boolean hasCurrentPermissionId(CommandSourceStack commandSourceStack) {
        return hasPermissionLevel(commandSourceStack, getPermissionLevel(options().permissions.getId()));
    }

    /**
     * @return if the player's permission level is above or equal to {@link Commands#LEVEL_ADMINS}.
     */
    public static boolean hasAdminPermissions(CommandSourceStack commandSourceStack) {
        return hasPermissionLevel(commandSourceStack, Commands.LEVEL_ADMINS);
    }

    /**
     * @return if the player's permission level is equivalent to the passed in value.
     */
    private static boolean hasPermissionLevel(CommandSourceStack commandSourceStack, int level) {
        return commandSourceStack.hasPermission(level);
    }
}