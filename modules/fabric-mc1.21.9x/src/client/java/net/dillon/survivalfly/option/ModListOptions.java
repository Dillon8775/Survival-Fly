package net.dillon.survivalfly.option;

import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

import java.util.Arrays;

import static net.dillon.survivalfly.main.SurvivalFly.options;

/**
 * The options displayed on the options screen.
 */
public class ModListOptions {

    public static final SimpleOption<PermissionLevel> PERMISSION_LEVEL = new SimpleOption<>(
            "survivalfly.options.permission_level",
            option -> {
                return switch (option) {
                    case REGULAR -> Tooltip.of(Text.translatable("survivalfly.options.permission_level.regular.tooltip"));
                    case MODERATOR -> Tooltip.of(Text.translatable("survivalfly.options.permission_level.moderator.tooltip"));
                    case GAMEMASTER -> Tooltip.of(Text.translatable("survivalfly.options.permission_level.gamemaster.tooltip"));
                    case ADMIN -> Tooltip.of(Text.translatable("survivalfly.options.permission_level.admin.tooltip"));
                    case OWNER -> Tooltip.of(Text.translatable("survivalfly.options.permission_level.owner.tooltip"));
                };
            },
            SimpleOption.enumValueText(),
            new SimpleOption.PotentialValuesBasedCallbacks<>(Arrays.asList(PermissionLevel.values()), PermissionLevel.Codec),
            options().permissionLevel,
            value -> options().permissionLevel = value);

    public static final SimpleOption<ChangeFlySpeedOnRule> CHANGE_FLY_SPEED_ON_RULE = new SimpleOption<>(
            "survivalfly.options.change_fly_speed_on_rule",
            option -> {
                return switch (option) {
                    case NON_SURVIVAL_LIKE_GAMEMODES -> Tooltip.of(Text.translatable("survivalfly.options.change_fly_speed_on_rule.non_survival_like_gamemodes.tooltip"));
                    case ANY_GAMEMODE -> Tooltip.of(Text.translatable("survivalfly.options.change_fly_speed_on_rule.any_gamemode.tooltip"));
                    case SPECTATOR_MODE_ONLY -> Tooltip.of(Text.translatable("survivalfly.options.change_fly_speed_on_rule.spectator_mode_only.tooltip"));
                };
            },
            SimpleOption.enumValueText(),
            new SimpleOption.PotentialValuesBasedCallbacks<>(Arrays.asList(ChangeFlySpeedOnRule.values()), ChangeFlySpeedOnRule.Codec),
            options().changeFlySpeedOnRule,
            value -> options().changeFlySpeedOnRule = value);

    public static final SimpleOption<ConfigButton> CONFIG_BUTTON = new SimpleOption<>(
            "survivalfly.options.config_button",
            option -> {
                return switch (option) {
                    case EVERYWHERE -> Tooltip.of(Text.translatable("survivalfly.options.config_button.everywhere.tooltip"));
                    case TITLE_ONLY -> Tooltip.of(Text.translatable("survivalfly.options.config_button.title_only.tooltip"));
                    case OFF -> Tooltip.of(Text.translatable("survivalfly.options.config_button.off.tooltip"));
                };
            },
            SimpleOption.enumValueText(),
            new SimpleOption.PotentialValuesBasedCallbacks<>(Arrays.asList(ConfigButton.values()), ConfigButton.Codec),
            options().configButton,
            value -> options().configButton = value);
}