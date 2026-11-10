package net.dillon.survivalfly.option;

import net.blay09.mods.balm.Balm;

import java.util.function.Consumer;

/**
 * Getters for all Survival Fly's option instances.
 */
public class OptionInstances {

    /**
     * Returns the options.
     */
    public static ModCommonOptions common() {
        return Balm.config().getActiveConfig(ModCommonOptions.class);
    }

    /**
     * Returns the client options.
     */
    public static ModClientOptions client() {
        return Balm.config().getActiveConfig(ModClientOptions.class);
    }

    /**
     * Updates common options.
     */
    public static void updateCommon(Consumer<ModCommonOptions> common) {
        Balm.config().updateLocalConfig(ModCommonOptions.class, common);
    }

    /**
     * Updates client options.
     */
    public static void updateClient(Consumer<ModClientOptions> client) {
        Balm.config().updateLocalConfig(ModClientOptions.class, client);
    }
}