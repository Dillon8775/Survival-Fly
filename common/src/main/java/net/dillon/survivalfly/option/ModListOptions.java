package net.dillon.survivalfly.option;

import net.dillon.survivalfly.util.ModTexts;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;

import java.util.Arrays;

import static net.dillon.survivalfly.util.ModUtil.options;

/**
 * The options displayed on the options screen.
 */
public class ModListOptions {
    public static final OptionInstance.CaptionBasedToString<Boolean> ON_OFF_TEXT = (p_231544_, p_231545_) -> p_231545_
            ? ModTexts.ON
            : ModTexts.OFF;

    public static final OptionInstance<PermissionLevel> PERMISSION_LEVEL = new OptionInstance<>(
            "survivalfly.options.permission_level",
            option -> {
                return switch (option) {
                    case REGULAR -> Tooltip.create(Component.translatable("survivalfly.options.permission_level.regular.tooltip"));
                    case MODERATOR -> Tooltip.create(Component.translatable("survivalfly.options.permission_level.moderator.tooltip"));
                    case GAMEMASTER -> Tooltip.create(Component.translatable("survivalfly.options.permission_level.gamemaster.tooltip"));
                    case ADMIN -> Tooltip.create(Component.translatable("survivalfly.options.permission_level.admin.tooltip"));
                    case OWNER -> Tooltip.create(Component.translatable("survivalfly.options.permission_level.owner.tooltip"));
                };
            },
            (optionText, value) -> value.getText(),
            new OptionInstance.Enum<>(Arrays.asList(PermissionLevel.values()), PermissionLevel.Codec),
            options().permissionLevel,
            value -> options().permissionLevel = value);

    public static final OptionInstance<ChangeFlySpeedOnRule> CHANGE_FLY_SPEED_ON_RULE = new OptionInstance<>(
            "survivalfly.options.change_fly_speed_on_rule",
            option -> {
                return switch (option) {
                    case NON_SURVIVAL_LIKE_GAMEMODES -> Tooltip.create(Component.translatable("survivalfly.options.change_fly_speed_on_rule.non_survival_like_gamemodes.tooltip"));
                    case ANY_GAMEMODE -> Tooltip.create(Component.translatable("survivalfly.options.change_fly_speed_on_rule.any_gamemode.tooltip"));
                    case SPECTATOR_MODE_ONLY -> Tooltip.create(Component.translatable("survivalfly.options.change_fly_speed_on_rule.spectator_mode_only.tooltip"));
                };
            },
            (optionText, value) -> value.getText(),
            new OptionInstance.Enum<>(Arrays.asList(ChangeFlySpeedOnRule.values()), ChangeFlySpeedOnRule.Codec),
            options().changeFlySpeedOnRule,
            value -> options().changeFlySpeedOnRule = value);

    public static final OptionInstance<ConfigButton> CONFIG_BUTTON = new OptionInstance<>(
            "survivalfly.options.config_button",
            option -> {
                return switch (option) {
                    case EVERYWHERE -> Tooltip.create(Component.translatable("survivalfly.options.config_button.everywhere.tooltip"));
                    case TITLE_ONLY -> Tooltip.create(Component.translatable("survivalfly.options.config_button.title_only.tooltip"));
                    case OFF -> Tooltip.create(Component.translatable("survivalfly.options.config_button.off.tooltip"));
                };
            },
            (optionText, value) -> value.getText(),
            new OptionInstance.Enum<>(Arrays.asList(ConfigButton.values()), ConfigButton.Codec),
            options().configButton,
            value -> options().configButton = value);
}