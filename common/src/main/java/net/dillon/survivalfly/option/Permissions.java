package net.dillon.survivalfly.option;

import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

public enum Permissions implements StringRepresentable {
    ANYONE(0, "regular", "survivalfly.options.permissions.regular"),
    MODERATOR(1, "moderator", "survivalfly.options.permissions.moderator"),
    GAMEMASTER(2, "gamemaster", "survivalfly.options.permissions.gamemaster"),
    ADMIN(3, "admin", "survivalfly.options.permissions.admin");

    public static final Codec<Permissions> Codec = StringRepresentable.fromEnum(Permissions::values);
    private final int ordinal;
    private final String name;
    private final Component translationKey;

    Permissions(final int ordinal, final String name, final String translationKey) {
        this.ordinal = ordinal;
        this.name = name;
        this.translationKey = Component.translatable(translationKey);
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

    public static Permissions byName(String name) {
        for (Permissions permission : values()) {
            if (permission.getSerializedName().equalsIgnoreCase(name)) {
                return permission;
            }
        }
        return null;
    }
}