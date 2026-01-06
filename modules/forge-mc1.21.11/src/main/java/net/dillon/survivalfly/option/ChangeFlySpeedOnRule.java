package net.dillon.survivalfly.option;

import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

/**
 * Defines under what rule the player can change their flight speed.
 */
public enum ChangeFlySpeedOnRule implements StringRepresentable {
    ANY_GAMEMODE("any_gamemode", "survivalfly.options.change_fly_speed_on_rule.any_gamemode"),
    NON_SURVIVAL_LIKE_GAMEMODES("non_survival_like_gamemodes", "survivalfly.options.change_fly_speed_on_rule.non_survival_like_gamemodes"),
    SPECTATOR_MODE_ONLY("spectator_only", "survivalfly.options.change_fly_speed_on_rule.spectator_mode_only");

    public static final Codec<ChangeFlySpeedOnRule> Codec = StringRepresentable.fromEnum(ChangeFlySpeedOnRule::values);
    private final String name;
    private final Component translationKey;

    ChangeFlySpeedOnRule(final String name, final String translationKey) {
        this.name = name;
        this.translationKey = Component.translatable(translationKey);
    }

    public boolean nonSurvivalLikeGamemodes() {
        return this == NON_SURVIVAL_LIKE_GAMEMODES;
    }

    public boolean anyGamemode() {
        return this == ANY_GAMEMODE;
    }

    public String getSerializedName() {
        return this.name;
    }

    public Component getText() {
        return this.translationKey;
    }
}