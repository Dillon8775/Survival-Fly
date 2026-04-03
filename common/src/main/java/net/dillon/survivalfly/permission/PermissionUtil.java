package net.dillon.survivalfly.permission;

import net.dillon.survivalfly.platform.MultiLoader;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.PermissionLevel;

import static net.dillon.survivalfly.util.ModUtil.options;

/**
 * Permission utility class for Survival Fly.
 */
public class PermissionUtil {

    /**
     * @return the permission level required to run any /flight commands.
     */
    public static PermissionLevel getPermissionLevel(int level) {
        return level == 3 ? PermissionLevel.ADMINS :
                level == 2 ? PermissionLevel.GAMEMASTERS :
                level == 1 ? PermissionLevel.MODERATORS : PermissionLevel.ALL;
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
     * @return if the player's permission level is above or equal to {@link PermissionLevel#ADMINS}.
     */
    public static boolean hasAdminPermissions(CommandSourceStack commandSourceStack) {
        return hasPermissionLevel(commandSourceStack, PermissionLevel.ADMINS);
    }

    /**
     * @return if the player's permission level is equivalent to the passed in value.
     */
    private static boolean hasPermissionLevel(CommandSourceStack commandSourceStack, PermissionLevel level) {
        return commandSourceStack.permissions().hasPermission(new Permission.HasCommandLevel(level));
    }
}