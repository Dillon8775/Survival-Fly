package net.dillon.survivalfly.option;

import com.mojang.serialization.Codec;
import net.minecraft.text.Text;
import net.minecraft.util.StringIdentifiable;

/**
 * Defines under what rule the player can change their flight speed.
 */
public enum ChangeFlySpeedOnRule implements StringIdentifiable {
    ANY_GAMEMODE("any_gamemode", "survivalfly.options.change_fly_speed_on_rule.any_gamemode"),
    NON_SURVIVAL_LIKE_GAMEMODES("non_survival_like_gamemodes", "survivalfly.options.change_fly_speed_on_rule.non_survival_like_gamemodes"),
    SPECTATOR_MODE_ONLY("spectator_only", "survivalfly.options.change_fly_speed_on_rule.spectator_mode_only");

    public static final Codec<ChangeFlySpeedOnRule> Codec = StringIdentifiable.createCodec(ChangeFlySpeedOnRule::values);
    private final String name;
    private final Text translationKey;

    ChangeFlySpeedOnRule(final String name, final String translationKey) {
        this.name = name;
        this.translationKey = Text.translatable(translationKey);
    }

    public boolean nonSurvivalLikeGamemodes() {
        return this == NON_SURVIVAL_LIKE_GAMEMODES;
    }

    public boolean anyGamemode() {
        return this == ANY_GAMEMODE;
    }

    public Text getText() {
        return this.translationKey;
    }

    public String asString() {
        return this.name;
    }
}