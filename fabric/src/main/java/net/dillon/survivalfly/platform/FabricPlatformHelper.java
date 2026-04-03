package net.dillon.survivalfly.platform;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.level.ServerPlayer;

import java.io.File;

public class FabricPlatformHelper implements PlatformHelper {

    @Override
    public boolean hasPermissionOnPlatform(ServerPlayer player, String node, boolean fallback) {
        return Permissions.check(player, node, fallback);
    }

    @Override
    public File getConfigDir(String fileName) {
        return new File(FabricLoader.getInstance().getConfigDir().toFile(), fileName);
    }
}