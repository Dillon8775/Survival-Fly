package net.dillon.survivalfly.helper;

import net.dillon.dillonlib.util.UpdateChecker;
import net.dillon.survivalfly.platform.SurvivalFlyPlatforms;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Constant values for Survival Fly.
 */
public class ModConstants {
    public static final String MOD_ID = "survivalfly";
    public static final Component VERSION = Component.literal(SurvivalFlyPlatforms.getPlatform().modVersion());
    public static final Logger LOGGER = LoggerFactory.getLogger("Survival Fly");
    public static final boolean HAS_UPDATE = UpdateChecker.hasUpdate(UpdateChecker.checkForUpdate(
            "survival-fly",
            SurvivalFlyPlatforms.getPlatform().modVersion()
    ));
    public static final Identifier LOGO = Identifier.fromNamespaceAndPath("survivalfly", "survivalfly");

    public static final float DEFAULT_FLIGHT_SPEED = 0.05F;
    public static final int DEFAULT_DAMAGE_TIME_TICKS = 400;
    public static final String EVER_ENABLED_FLIGHT_NAME = "EverEnabledFlight";
    public static final String WANT_TO_FLY_AGAIN = "WantToFlyAgain";
    public static final String DAMAGE_TIME_TICKS_NAME = "DamageTimeTicks";
    public static final String PLAYED_BROKEN_NAME = "PlayedBroken";
}