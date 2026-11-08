package net.dillon.survivalfly.main;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.core.BalmRegistrars;
import net.dillon.dillonlib.annotation.Dill;
import net.dillon.dillonlib.annotation.DillEnv;
import net.dillon.dillonlib.client.component.UpdatableSpriteButton;
import net.dillon.survivalfly.client.screen.MainMenuScreen;
import net.dillon.survivalfly.helper.ModConstants;
import net.dillon.survivalfly.option.ModClientOptions;
import net.minecraft.network.chat.Component;

import static net.dillon.dillonlib.task.ClientTasks.getScreen;
import static net.dillon.dillonlib.task.ClientTasks.openScreen;
import static net.dillon.survivalfly.client.debug.ModDebugScreenEntries.registerDebugEntries;

/**
 * The client entrypoint for Survival Fly.
 */
@Dill(env = DillEnv.CLIENT)
public class ClientMain {

    public static void cInitialize(BalmRegistrars balmRegistrars) {
        registerDebugEntries();

        Balm.config().registerConfig(ModClientOptions.class);
    }

    public static UpdatableSpriteButton createMenuButton() {
        return UpdatableSpriteButton.builder(
                        "Survival Fly Main Menu",
                        Component.translatable("survivalfly"),
                        ModConstants.LOGO,
                        (button) -> openScreen(new MainMenuScreen(getScreen()))
                )
                .tooltip(Component.translatable("survivalfly.title"))
                .build();
    }
}