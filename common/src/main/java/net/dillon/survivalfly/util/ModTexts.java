package net.dillon.survivalfly.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

/**
 * Texts for {@code Survival Fly.}
 */
public class ModTexts {
    public static final Component BLANK = Component.literal("");
    public static final Component ON = Component.translatable("survivalfly.gui.on").withStyle(ChatFormatting.GREEN);
    public static final Component OFF = Component.translatable("survivalfly.gui.off").withStyle(ChatFormatting.RED);
}