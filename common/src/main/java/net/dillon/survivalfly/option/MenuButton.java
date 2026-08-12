package net.dillon.survivalfly.option;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum MenuButton implements StringRepresentable {
    EVERYWHERE("Everywhere"),
    TITLE_ONLY("Title Only"),
    OFF("OFF");

    public static final Codec<MenuButton> CODEC = StringRepresentable.fromEnum(MenuButton::values);
    private final String name;

    MenuButton(final String name) {
        this.name = name;
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
}