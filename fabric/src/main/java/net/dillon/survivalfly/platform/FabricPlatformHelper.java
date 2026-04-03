package net.dillon.survivalfly.platform;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.level.ServerPlayer;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class FabricPlatformHelper implements PlatformHelper {

    @Override
    public boolean hasPermissionOnPlatform(ServerPlayer player, String node, boolean fallback) {
        try {
            return Permissions.check(player.getUUID(), node, fallback).get();
        } catch (InterruptedException | ExecutionException e) {
            return fallback;
        }
    }

    @Override
    public File getConfigDir(String fileName) {
        return new File(FabricLoader.getInstance().getConfigDir().toFile(), fileName);
    }
}
