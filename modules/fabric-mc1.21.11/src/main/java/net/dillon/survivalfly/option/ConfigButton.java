package net.dillon.survivalfly.option;

import com.mojang.serialization.Codec;
import net.minecraft.text.Text;
import net.minecraft.util.StringIdentifiable;

public enum ConfigButton implements StringIdentifiable {
    EVERYWHERE(0, "everywhere", "survivalfly.options.config_button.everywhere"),
    TITLE_ONLY(1, "title_only", "survivalfly.options.config_button.title_only"),
    OFF(2, "off", "survivalfly.options.config_button.off");

    public static final Codec<ConfigButton> Codec = StringIdentifiable.createCodec(ConfigButton::values);
    private final int ordinal;
    private final String name;
    private final Text translationKey;

    ConfigButton(final int ordinal, final String name, final String translationKey) {
        this.ordinal = ordinal;
        this.name = name;
        this.translationKey = Text.translatable(translationKey);
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

    public Text getText() {
        return this.translationKey;
    }

    public String asString() {
        return this.name;
    }
}