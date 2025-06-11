package net.dillon.survivalfly.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/**
 * Texts for {@code Survival Fly.}
 */
@Environment(EnvType.CLIENT)
public class ModTexts {
    public static final Text BLANK = Text.literal("");
    public static final Text ON = Text.translatable("survivalfly.gui.on").formatted(Formatting.GREEN);
    public static final Text OFF = Text.translatable("survivalfly.gui.off").formatted(Formatting.RED);
}