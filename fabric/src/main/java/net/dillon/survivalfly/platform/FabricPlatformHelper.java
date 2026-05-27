package net.dillon.survivalfly.platform;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.level.ServerPlayer;

public class FabricPlatformHelper implements PlatformHelper {

    @Override
    public boolean hasPermissionOnPlatform(ServerPlayer player, String node, boolean fallback) {
        return Permissions.check(player, node, fallback);
    }
}