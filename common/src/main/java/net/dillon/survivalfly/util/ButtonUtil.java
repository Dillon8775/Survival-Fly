package net.dillon.survivalfly.util;

import net.dillon.survivalfly.platform.MultiLoader;
import net.dillon.survivalfly.screen.ModOptionsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.SpriteIconButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;

import static net.dillon.survivalfly.util.ModUtil.options;

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
                .sprite(ResourceLocation.fromNamespaceAndPath("survivalfly", "survivalfly"), 16, 16)
                .build();
    }

    /**
     * @return the configuration button X position.
     */
    public static int getConfigButtonX(int width) {
        if (options().configButton.left()) {
            return 8;
        } else if (options().configButton.right()) {
            return width - 28;
        } else {
            return width / 2 + 106;
        }
    }

    /**
     * @return the configuration button Y position.
     */
    public static int getConfigButtonY(int height) {
        if (options().configButton.left() || options().configButton.right()) {
            return height - 29;
        } else {
            return height / 4 + 48 - 16 + (MultiLoader.getPlatform().isNeoForged() ? -6 : 0);
        }
    }
}