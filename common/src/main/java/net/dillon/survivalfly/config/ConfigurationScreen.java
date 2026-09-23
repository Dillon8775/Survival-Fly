package net.dillon.survivalfly.config;

import net.blay09.mods.balm.client.platform.config.screen.BalmConfigScreen;
import net.dillon.dillonlib.annotation.Dill;
import net.dillon.dillonlib.annotation.DillType;
import net.dillon.survivalfly.helper.ModConstants;
import net.minecraft.client.gui.screens.Screen;

@Dill(DillType.CLIENT)
public class ConfigurationScreen {

    public static BalmConfigScreen configScreen(Screen parent) {
        return BalmConfigScreen.forMod(parent, ModConstants.MOD_ID);
    }
}