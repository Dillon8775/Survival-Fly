package net.dillon.survivalfly.util;

import net.dillon.survivalfly.screen.ModOptionsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.SpriteIconButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.Identifier;

/**
 * Utility class.
 */
public class ButtonUtil {

    /**
     * Initializes the settings button.
     */
    public static SpriteIconButton initializeButton(Minecraft client, Screen parent) {
        return SpriteIconButton.builder(ModTexts.BLANK, (onPress) -> client.setScreen(new ModOptionsScreen(parent)), false)
                .width(20)
                .sprite(Identifier.fromNamespaceAndPath("survivalfly", "survivalfly"), 16, 16)
                .build();
    }
}