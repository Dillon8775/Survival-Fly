package net.dillon.survivalfly.main;

import net.blay09.mods.balm.core.BalmRegistrars;
import net.dillon.dillonlib.annotation.Dill;
import net.dillon.dillonlib.annotation.DillType;
import net.dillon.dillonlib.platform.info.UpdatableSpriteButton;
import net.dillon.dillonlib.task.ClientTasks;
import net.dillon.survivalfly.helper.ModConstants;
import net.dillon.survivalfly.screen.MainMenuScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.Map;

import static net.dillon.dillonlib.task.ClientTasks.openScreen;
import static net.dillon.survivalfly.debug.ModDebugScreenEntries.registerDebugEntries;

/**
 * The client entrypoint for Survival Fly.
 */
@Dill(DillType.CLIENT)
public class ClientMain {

    public static void cInitialize(BalmRegistrars balmRegistrars) {
        registerDebugEntries();
    }

    public static UpdatableSpriteButton menuButton(Screen parent) {
        return ClientTasks.createMenuButton(
                "Survival Fly Main Menu",
                ModConstants.LOGO,
                (button) -> openScreen(new MainMenuScreen(parent)),
                Map.of(
                        ModConstants.HAS_UPDATE,
                        Component.translatable("survivalfly.gui.update_available")
                ),
                Component.translatable("survivalfly.title.menu"),
                true);
    }
}