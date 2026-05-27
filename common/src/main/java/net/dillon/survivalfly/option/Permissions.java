package net.dillon.survivalfly.option;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum Permissions implements StringRepresentable {
    ANYONE(0, "§aAnyone"),
    MODERATOR(1, "§dModerator"),
    GAMEMASTER(2, "§5Gamemaster"),
    ADMIN(3, "§cAdmin");

    public static final Codec<Permissions> CODEC = StringRepresentable.fromEnum(Permissions::values);
    private final int ordinal;
    private final String name;

    Permissions(final int ordinal, final String name) {
        this.ordinal = ordinal;
        this.name = name;
    }

    public int getId() {
        return this.ordinal;
    }

    @Override
    public String getSerializedName() {
        return this.name;
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