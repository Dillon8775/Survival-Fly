package net.dillon.survivalfly.option;

import net.minecraft.util.StringRepresentable;

public enum MenuButton implements StringRepresentable {
    EVERYWHERE("everywhere", "survivalfly.options.menu_button.everywhere"),
    TITLE_ONLY("title_only", "survivalfly.options.menu_button.title_only"),
    OFF("off", "survivalfly.options.menu_button.off");

    private final String name;
    private final String translationKey;

    MenuButton(final String name, final String translationKey) {
        this.name = name;
        this.translationKey = translationKey;
    }

    public boolean enabled() {
        return this != OFF;
    }

    public boolean everywhere() {
        return this != OFF && this != TITLE_ONLY;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public String getTranslationKey() {
        return this.translationKey;
    }
}