package net.dillon.survivalfly.platform;

import net.dillon.dillonlib.platform.ModPlatform;
import net.dillon.dillonlib.platform.PlatformLoader;
import net.dillon.dillonlib.platform.client.ClientModPlatform;
import net.dillon.survivalfly.helper.ModConstants;

public class SurvivalFlyPlatforms {
    private static final ModPlatform PLATFORM = PlatformLoader.load(ModPlatform.class, ModConstants.MOD_ID);
    private static final ClientModPlatform CLIENT_PLATFORM = PlatformLoader.load(ClientModPlatform.class, ModConstants.MOD_ID);

    public static ModPlatform getPlatform() {
        return PLATFORM;
    }

    public static ClientModPlatform getClientPlatform() {
        return CLIENT_PLATFORM;
    }
}