package net.dillon.survivalfly.platform;

import net.blay09.mods.balm.Balm;
import net.minecraft.server.level.ServerPlayer;

import java.io.File;

public interface PlatformHelper {

    /**
     * @return the mod version.
     */
    default String getModVersion() {
        return "1.3";
    }

    /**
     * @return if the platform is on NeoForged.
     */
    default boolean isNeoForged() {
        return Balm.platform().name().equals("neoforge");
    }

    /**
     * @return if {@code LuckPerms} is loaded.
     */
    default boolean isLuckPermsLoaded() {
        return Balm.platform().isModLoaded("luckperms");
    }

    /**
     * @return If the player has permission to execute a command.
     */
    default boolean hasPermission(ServerPlayer player, String node, boolean fallback) {
        if (this.isLuckPermsLoaded()) {
            return this.hasPermissionOnPlatform(player, node, fallback);
        }
        return fallback;
    }

    /**
     * @return if the player can execute commands with {@code LuckPerms} (platform specific).
     */
    boolean hasPermissionOnPlatform(ServerPlayer player, String node, boolean fallback);

    /**
     * Gets the config directory for the supported platform.
     */
    File getConfigDir(String fileName);
}