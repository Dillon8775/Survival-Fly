package net.dillon.survivalfly.platform;

import net.dillon.dillonlib.platform.PlatformLoader;
import net.dillon.dillonlib.platform.client.ClientModPlatform;
import net.dillon.survivalfly.helper.ModHelper;

public class SurvivalFlyPlatforms {
    private static final SurvivalFlyPlatform PLATFORM = PlatformLoader.load(SurvivalFlyPlatform.class, ModHelper.MOD_ID);
    private static final ClientModPlatform CLIENT_PLATFORM = PlatformLoader.load(ClientModPlatform.class, ModHelper.MOD_ID);

    public static SurvivalFlyPlatform getPlatform() {
        return PLATFORM;
    }

    public static ClientModPlatform getClientPlatform() {
        return CLIENT_PLATFORM;
    }
}