package net.dillon.survivalfly.platform;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.util.Tristate;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.loading.FMLPaths;

import java.io.File;

public class NeoForgePlatformHelper implements PlatformHelper {

    @Override
    public boolean hasPermissionOnPlatform(ServerPlayer player, String node, boolean fallback) {
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

    @Override
    public File getConfigDir(String fileName) {
        return new File(FMLPaths.CONFIGDIR.get().resolve(fileName).toString());
    }
}