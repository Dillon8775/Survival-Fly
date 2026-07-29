package net.dillon.survivalfly.platform.client;

import net.dillon.dillonlib.platform.client.ClientModPlatform;
import net.dillon.survivalfly.helper.ModHelper;

public abstract class ClientSurvivalFlyPlatform extends ClientModPlatform {

    @Override
    public String modId() {
        return ModHelper.MOD_ID;
    }
}