package net.dillon.survivalfly.permission;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.util.Tristate;
import net.minecraft.server.level.ServerPlayer;

/**
 * Safe-accessor class for {@link LuckPerms}.
 */
public class LuckPermsPermissions {

    /**
     * @return if the player can execute commands with {@code LuckPerms} (platform specific).
     */
    protected static boolean luckPermsPermission(ServerPlayer player, String node, boolean fallback) {
        LuckPerms lp = LuckPermsProvider.get();
        User user = lp.getUserManager().getUser(player.getUUID());

        if (user == null) {
            user = lp.getUserManager().loadUser(player.getUUID()).join();
        }
        if (user == null) {
            return fallback;
        }

        Tristate result = user.getCachedData().getPermissionData().checkPermission(node);
        return result == Tristate.UNDEFINED ? fallback : result.asBoolean();
    }
}