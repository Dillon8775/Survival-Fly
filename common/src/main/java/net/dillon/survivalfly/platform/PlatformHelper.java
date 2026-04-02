package net.dillon.survivalfly.platform;

import net.blay09.mods.balm.api.Balm;

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
        return Balm.getPlatform().equals("neoforge");
    }

    /**
     * Gets the config directory for the supported platform.
     */
    File getConfigDir(String fileName);
}