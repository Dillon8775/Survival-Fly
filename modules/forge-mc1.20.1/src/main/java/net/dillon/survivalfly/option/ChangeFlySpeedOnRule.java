package net.dillon.survivalfly.option;

import com.mojang.serialization.Codec;
import net.minecraft.util.OptionEnum;
import net.minecraft.util.StringRepresentable;

/**
 * Defines under what rule the player can change their flight speed.
 */
public enum ChangeFlySpeedOnRule implements OptionEnum, StringRepresentable {
    ANY_GAMEMODE(0, "any_gamemode", "survivalfly.options.change_fly_speed_on_rule.any_gamemode"),
    NON_SURVIVAL_LIKE_GAMEMODES(1, "non_survival_like_gamemodes", "survivalfly.options.change_fly_speed_on_rule.non_survival_like_gamemodes"),
    SPECTATOR_MODE_ONLY(2, "spectator_only", "survivalfly.options.change_fly_speed_on_rule.spectator_mode_only");

    public static final Codec<ChangeFlySpeedOnRule> Codec = StringRepresentable.fromEnum(ChangeFlySpeedOnRule::values);
    private final int ordinal;
    private final String name;
    private final String translationKey;

    ChangeFlySpeedOnRule(final int ordinal, final String name, final String translationKey) {
        this.ordinal = ordinal;
        this.name = name;
        this.translationKey = translationKey;
    }

    public boolean nonSurvivalLikeGamemodes() {
        return this == NON_SURVIVAL_LIKE_GAMEMODES;
    }

    public boolean anyGamemode() {
        return this == ANY_GAMEMODE;
    }

    public boolean spectatorModeOnly() {
        return this == SPECTATOR_MODE_ONLY;
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