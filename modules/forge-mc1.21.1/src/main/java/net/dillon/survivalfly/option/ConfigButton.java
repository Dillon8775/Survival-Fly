package net.dillon.survivalfly.option;

import com.mojang.serialization.Codec;
import net.minecraft.util.OptionEnum;
import net.minecraft.util.StringRepresentable;

/**
 * Defines where the Survival Fly configuration button can appear.
 */
public enum ConfigButton implements OptionEnum, StringRepresentable {
    EVERYWHERE(0, "everywhere", "survivalfly.options.config_button.everywhere"),
    TITLE_ONLY(1, "title_only", "survivalfly.options.config_button.title_only"),
    OFF(2, "off", "survivalfly.options.config_button.off");

    public static final Codec<ConfigButton> Codec = StringRepresentable.fromEnum(ConfigButton::values);
    private final int ordinal;
    private final String name;
    private final String translationKey;

    ConfigButton(final int ordinal, final String name, final String translationKey) {
        this.ordinal = ordinal;
        this.name = name;
        this.translationKey = translationKey;
    }

    public boolean everywhere() {
        return this == EVERYWHERE;
    }

    public boolean titleOnly() {
        return this == TITLE_ONLY;
    }

    public int getId() {
        return this.ordinal;
    }

    public String getSerializedName() {
        return this.name;
    }

    public String getKey() {
        return this.translationKey;
    }
}