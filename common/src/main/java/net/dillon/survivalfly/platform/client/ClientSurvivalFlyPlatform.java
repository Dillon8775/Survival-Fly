package net.dillon.survivalfly.platform.client;

import net.dillon.dillonlib.client.component.PlatformMenuButton;
import net.dillon.dillonlib.platform.client.ClientModPlatform;
import net.dillon.survivalfly.helper.ModConstants;
import net.dillon.survivalfly.main.ClientMain;

import java.util.List;

public abstract class ClientSurvivalFlyPlatform extends ClientModPlatform {

    @Override
    public List<PlatformMenuButton> menuButtons() {
        return List.of(
                PlatformMenuButton.builder(ClientMain.createMenuButton()).build()
        );
    }

    @Override
    public String modId() {
        return ModConstants.MOD_ID;
    }
}