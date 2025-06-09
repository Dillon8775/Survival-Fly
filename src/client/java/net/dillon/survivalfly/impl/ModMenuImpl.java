package net.dillon.survivalfly.impl;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.dillon.survivalfly.screen.ModOptionsScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * Implementation for Mod Menu.
 */
@Environment(EnvType.CLIENT)
public class ModMenuImpl implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ModOptionsScreen::new;
    }
}