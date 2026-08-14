package net.dillon.survivalfly.option;

import net.minecraft.util.StringRepresentable;

public enum Permissions implements StringRepresentable {
    ANYONE(0, "anyone", "survivalfly.options.permissions.anyone"),
    MODERATOR(1, "moderator", "survivalfly.options.permissions.moderator"),
    GAMEMASTER(2, "gamemaster", "survivalfly.options.permissions.gamemaster"),
    ADMIN(3, "admin", "survivalfly.options.permissions.admin");

    private final int ordinal;
    private final String name;
    private final String translationKey;

    Permissions(final int ordinal, final String name, final String translationKey) {
        this.ordinal = ordinal;
        this.name = name;
        this.translationKey = translationKey;
    }

    public int getOrdinal() {
        return this.ordinal;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public String getTranslationKey() {
        return this.translationKey;
    }

    public static Permissions byName(String name) {
        for (Permissions permission : values()) {
            if (permission.getSerializedName().equalsIgnoreCase(name)) {
                return permission;
            }
        }
        return null;
    }
}