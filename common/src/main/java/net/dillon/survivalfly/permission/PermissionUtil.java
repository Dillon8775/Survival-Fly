package net.dillon.survivalfly.permission;

import net.dillon.dillonlib.util.SimplePermissions;
import net.dillon.survivalfly.platform.SurvivalFlyPlatforms;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.PermissionLevel;

import static net.dillon.survivalfly.helper.ModHelper.modEnabled;
import static net.dillon.survivalfly.helper.ModHelper.options;

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
        if (!modEnabled()) {
            return false;
        }

        return hasPermissionToExecute(player, node, hasCurrentPermissionId(commandSourceStack));
    }

    /**
     * @return if the source has node/default permission, or is a non-player source like a command block.
     */
    public static boolean hasPermissionDefaultFallbackOrCommandSource(CommandSourceStack commandSourceStack, ServerPlayer player, Nodes node) {
        if (!modEnabled()) {
            return false;
        }

        return hasPermissionDefaultFallback(commandSourceStack, player, node) || commandSourceStack.getEntity() == null;
    }

    /**
     * @return if the user has the permissions to execute a command.
     */
    private static boolean hasPermissionToExecute(ServerPlayer player, Nodes node, boolean fallback) {
        if (!modEnabled()) {
            return false;
        }

        if (player == null) {
            return fallback;
        }

        return SurvivalFlyPlatforms.getPlatform().hasPermission(player, node.getNode(), fallback);
    }

    /**
     * @return if the player's permission level is above or equal to the default set permissions id.
     */
    private static boolean hasCurrentPermissionId(CommandSourceStack commandSourceStack) {
        if (!modEnabled()) {
            return false;
        }

        return hasPermissionLevel(commandSourceStack, getPermissionLevel(options().permissions.getOrdinal()));
    }

    /**
     * @return if the source has admin permissions, or is a non-player source like a command block.
     */
    public static boolean hasAdminPermissionsOrCommandSource(CommandSourceStack commandSourceStack) {
        if (!modEnabled()) {
            return false;
        }

        return SimplePermissions.admin(commandSourceStack) || SimplePermissions.notPlayer(commandSourceStack);
    }

    /**
     * @return if the player's permission level is equivalent to the passed in value.
     */
    private static boolean hasPermissionLevel(CommandSourceStack commandSourceStack, PermissionLevel level) {
        if (!modEnabled()) {
            return false;
        }

        return commandSourceStack.permissions().hasPermission(new Permission.HasCommandLevel(level));
    }
}