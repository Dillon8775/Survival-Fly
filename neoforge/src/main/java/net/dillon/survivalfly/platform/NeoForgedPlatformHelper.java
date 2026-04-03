package net.dillon.survivalfly.platform;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.loading.FMLPaths;

import java.io.File;

public class NeoForgedPlatformHelper implements PlatformHelper {

    /**
     * Due to a NeoForged 1.21.1 bug with LuckPerms, we must return fallback because LuckPerms will always kick the player.
     */
    @Override
    public boolean hasPermissionOnPlatform(ServerPlayer player, String node, boolean fallback) {
        return fallback;
    }

    @Override
    public File getConfigDir(String fileName) {
        return new File(FMLPaths.CONFIGDIR.get().resolve(fileName).toString());
    }
}