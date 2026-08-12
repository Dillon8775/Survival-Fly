package net.dillon.survivalfly.option;

import net.blay09.mods.balm.Balm;

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
}