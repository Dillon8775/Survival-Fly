package net.dillon.survivalfly.option;

import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

/**
 * Defines where the Survival Fly configuration button can appear.
 */
public enum MenuButton implements StringRepresentable {
    EVERYWHERE(0, "everywhere", "survivalfly.options.menu_button.everywhere"),
    BOTTOM_LEFT(1, "bottom_left", "survivalfly.options.menu_button.bottom_left"),
    BOTTOM_RIGHT(2, "bottom_right", "survivalfly.options.menu_button.bottom_right"),
    TITLE_ONLY(3, "title_only", "survivalfly.options.menu_button.title_only"),
    OFF(4, "off", "survivalfly.options.menu_button.off");

    public static final Codec<MenuButton> Codec = StringRepresentable.fromEnum(MenuButton::values);
    private final int ordinal;
    private final String name;
    private final Component translationKey;

    MenuButton(final int ordinal, final String name, final String translationKey) {
        this.ordinal = ordinal;
        this.name = name;
        this.translationKey = Component.translatable(translationKey);
    }

    public boolean everywhere() {
        return this != OFF && this != TITLE_ONLY;
    }

    public boolean titleOnly() {
        return this == TITLE_ONLY;
    }

    public boolean left() {
        return this == BOTTOM_LEFT;
    }

    public boolean right() {
        return this == BOTTOM_RIGHT;
    }

    public int getId() {
        return this.ordinal;
    }

    public String getSerializedName() {
        return this.name;
    }

    public Component getText() {
        return this.translationKey;
    }
}