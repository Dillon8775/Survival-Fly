package net.dillon.survivalfly.option;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.TranslatableOption;

/**
 * Defines under what rule the player can change their flight speed.
 */
public enum ChangeFlySpeedOnRule implements TranslatableOption, StringIdentifiable {
    NON_SURVIVAL_LIKE_GAMEMODES(0, "non_survival_like_gamemodes", "survivalfly.options.change_fly_speed_on_rule.non_survival_like_gamemodes"),
    ANY_GAMEMODE(1, "any_gamemode", "survivalfly.options.change_fly_speed_on_rule.any_gamemode"),
    SPECTATOR_MODE_ONLY(2, "spectator_only", "survivalfly.options.change_fly_speed_on_rule.spectator_mode_only");

    public static final Codec<ChangeFlySpeedOnRule> Codec = StringIdentifiable.createCodec(ChangeFlySpeedOnRule::values);
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

    public String getTranslationKey() {
        return this.translationKey;
    }

    public String asString() {
        return this.name;
    }
}