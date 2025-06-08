package net.dillon.survivalfly.option;

import com.mojang.serialization.Codec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.TranslatableOption;

public enum PermissionLevel implements TranslatableOption, StringIdentifiable {
    REGULAR(0, "regular", "survivalfly.options.permission_level.regular"),
    MODERATOR(1, "moderator", "survivalfly.options.permission_level.moderator"),
    GAMEMASTER(2, "gamemaster", "survivalfly.options.permission_level.gamemaster"),
    ADMIN(3, "admin", "survivalfly.options.permission_level.admin"),
    OWNER(4, "owner", "survivalfly.options.permission_level.owner");

    public static final Codec<PermissionLevel> Codec = StringIdentifiable.createCodec(PermissionLevel::values);
    private final int ordinal;
    private final String name;
    private final String translationKey;

    PermissionLevel(final int ordinal, final String name, final String translationKey) {
        this.ordinal = ordinal;
        this.name = name;
        this.translationKey = translationKey;
    }

    public int getId() {
        return this.ordinal;
    }

    public String getTranslationKey() {
        return this.translationKey;
    }

    public String asString() {
        return this.name;
    }
}