package net.dillon.survivalfly.main;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.core.BalmRegistrars;
import net.dillon.survivalfly.option.ModOptions;
import net.dillon.survivalfly.platform.MultiLoader;

import static net.dillon.survivalfly.event.CommonEvents.registerPackets;
import static net.dillon.survivalfly.util.ModUtil.info;

/**
 * The main entrypoint for Survival Fly.
 */
public class Main {

    public static void initialize(BalmRegistrars balmRegistrars) {
        registerPackets();

        Balm.getConfig().registerConfig(ModOptions.class);

        info("Survival Fly version " + MultiLoader.getPlatform().getModVersion() + " (for " + Balm.getPlatform() + ") loaded successfully!");
    }
}