package net.dillon.survivalfly.platform.client;

import net.dillon.dillonlib.platform.client.ClientModPlatform;
import net.dillon.dillonlib.platform.info.PlatformMenuButton;
import net.dillon.survivalfly.helper.ModConstants;
import net.dillon.survivalfly.main.ClientMain;

import java.util.List;

import static net.dillon.dillonlib.task.ClientTasks.getScreen;
import static net.dillon.survivalfly.option.OptionInstances.client;

public abstract class ClientSurvivalFlyPlatform extends ClientModPlatform {

    @Override
    public List<PlatformMenuButton> menuButtons() {
        return List.of(
                new PlatformMenuButton(
                        client().menuButton.enabled(),
                        client().menuButton.everywhere(),
                        ClientMain.menuButton(getScreen()),
                        spriteIconButton -> {})
        );
    }

    @Override
    public String modId() {
        return ModConstants.MOD_ID;
    }
}