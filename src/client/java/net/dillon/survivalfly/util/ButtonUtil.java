package net.dillon.survivalfly.util;

import net.dillon.survivalfly.screen.ModOptionsScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.util.Identifier;

/**
 * Utility class.
 */
@Environment(EnvType.CLIENT)
public class ButtonUtil {

    /**
     * Initializes the settings button.
     */
    public static TextIconButtonWidget initializeButton(MinecraftClient client, Screen parent) {
        return TextIconButtonWidget.builder(ModTexts.BLANK, (onPress) -> client.setScreen(new ModOptionsScreen(parent)), false)
                .width(20)
                .texture(Identifier.of("survivalfly", "survivalfly"), 16, 16)
                .build();
    }
}