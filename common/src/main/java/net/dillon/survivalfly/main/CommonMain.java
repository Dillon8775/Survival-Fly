package net.dillon.survivalfly.main;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.core.BalmRegistrars;
import net.dillon.survivalfly.option.ModClientOptions;
import net.dillon.survivalfly.option.ModOptions;
import net.dillon.survivalfly.platform.SurvivalFlyPlatforms;

import static net.dillon.survivalfly.event.CommonEvents.registerPackets;
import static net.dillon.survivalfly.helper.ModHelper.info;

/**
 * The main entrypoint for Survival Fly.
 */
public class CommonMain {

    public static void initialize(BalmRegistrars balmRegistrars) {
        registerPackets();

        Balm.config().registerConfig(ModOptions.class);
        Balm.config().registerConfig(ModClientOptions.class);

        info("Survival Fly version " + SurvivalFlyPlatforms.getPlatform().modVersion() + " (for " + Balm.platform().name() + ") loaded successfully!");
    }
}