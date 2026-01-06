package net.dillon.survivalfly.option;

import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

public enum PermissionLevel implements StringRepresentable {
    REGULAR(0, "regular", "survivalfly.options.permission_level.regular"),
    MODERATOR(1, "moderator", "survivalfly.options.permission_level.moderator"),
    GAMEMASTER(2, "gamemaster", "survivalfly.options.permission_level.gamemaster"),
    ADMIN(3, "admin", "survivalfly.options.permission_level.admin"),
    OWNER(4, "owner", "survivalfly.options.permission_level.owner");

    public static final Codec<PermissionLevel> Codec = StringRepresentable.fromEnum(PermissionLevel::values);
    private final int ordinal;
    private final String name;
    private final Component translationKey;

    PermissionLevel(final int ordinal, final String name, final String translationKey) {
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
}