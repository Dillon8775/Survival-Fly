package net.dillon.survivalfly.main;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.core.BalmRegistrars;
import net.dillon.survivalfly.helper.ModConstants;
import net.dillon.survivalfly.option.ModClientOptions;
import net.dillon.survivalfly.option.ModCommonOptions;
import net.dillon.survivalfly.platform.SurvivalFlyPlatforms;

import static net.dillon.survivalfly.event.CommonEvents.registerPackets;

/**
 * The main entrypoint for Survival Fly.
 */
public class CommonMain {

    public static void initialize(BalmRegistrars balmRegistrars) {
        registerPackets();

        Balm.config().registerConfig(ModCommonOptions.class);
        Balm.config().registerConfig(ModClientOptions.class);

        ModConstants.LOGGER.info("Survival Fly version {} (for {}) loaded successfully!", SurvivalFlyPlatforms.getPlatform().modVersion(), Balm.platform().name());
    }
}