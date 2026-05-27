package net.dillon.survivalfly.platform;

import net.blay09.mods.balm.Balm;
import net.minecraft.server.level.ServerPlayer;

public interface PlatformHelper {

    /**
     * @return the mod version.
     */
    default String getModVersion() {
        return "1.3.2";
    }

    /**
     * @return the version type.
     */
    default VersionType getVersionType() {
        return VersionType.PATCH;
    }

    /**
     * @return if {@code YACL} is loaded.
     */
    default boolean isYaclLoaded() {
        return Balm.platform().isModLoaded("yet_another_config_lib_v3");
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
}