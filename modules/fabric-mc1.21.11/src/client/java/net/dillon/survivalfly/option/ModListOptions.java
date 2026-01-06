package net.dillon.survivalfly.option;

import net.dillon.survivalfly.main.SurvivalFly;
import net.dillon.survivalfly.util.ModTexts;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

import java.util.Arrays;

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
            (optionText, value) -> value.getText(),
            new SimpleOption.PotentialValuesBasedCallbacks<>(Arrays.asList(PermissionLevel.values()), PermissionLevel.Codec),
            SurvivalFly.options().permissionLevel,
            value -> SurvivalFly.options().permissionLevel = value);

    public static final SimpleOption<ChangeFlySpeedOnRule> CHANGE_FLY_SPEED_ON_RULE = new SimpleOption<>(
            "survivalfly.options.change_fly_speed_on_rule",
            option -> {
                return switch (option) {
                    case NON_SURVIVAL_LIKE_GAMEMODES -> Tooltip.of(Text.translatable("survivalfly.options.change_fly_speed_on_rule.non_survival_like_gamemodes.tooltip"));
                    case ANY_GAMEMODE -> Tooltip.of(Text.translatable("survivalfly.options.change_fly_speed_on_rule.any_gamemode.tooltip"));
                    case SPECTATOR_MODE_ONLY -> Tooltip.of(Text.translatable("survivalfly.options.change_fly_speed_on_rule.spectator_mode_only.tooltip"));
                };
            },
            (optionText, value) -> value.getText(),
            new SimpleOption.PotentialValuesBasedCallbacks<>(Arrays.asList(ChangeFlySpeedOnRule.values()), ChangeFlySpeedOnRule.Codec),
            SurvivalFly.options().changeFlySpeedOnRule,
            value -> SurvivalFly.options().changeFlySpeedOnRule = value);

    public static final SimpleOption<Boolean> SHOW_CONFIG_BUTTON = new SimpleOption<>("survivalfly.options.show_config_button", SimpleOption.constantTooltip(Text.translatable("survivalfly.options.show_config_button.tooltip")),
            (optionText, value) -> !value ? ModTexts.OFF : ModTexts.ON, SimpleOption.BOOLEAN, SurvivalFly.options().showConfigButton, value -> SurvivalFly.options().showConfigButton = value);
}