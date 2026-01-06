package net.dillon.survivalfly.util;

import net.dillon.survivalfly.screen.ModOptionsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Utility class.
 */
@OnlyIn(Dist.CLIENT)
public class ButtonUtil {

    /**
     * Initializes the settings button.
     */
    public static Button initializeButton(Minecraft client, Screen parent, int width, int height) {
        return new ImageButton(width, height, 20, 20, 0, 0, 20, ResourceLocation.parse("survivalfly:textures/gui/survivalfly.png"), 20, 40, (p_280835_) -> {
            client.setScreen(new ModOptionsScreen(parent));
        }, ModTexts.BLANK);
    }
}