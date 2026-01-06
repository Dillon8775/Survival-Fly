package net.dillon.survivalfly.option;

import net.dillon.survivalfly.util.ModTexts;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Arrays;

/**
 * The options displayed on the options screen.
 */
@OnlyIn(Dist.CLIENT)
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
            OptionInstance.forOptionEnum(),
            new OptionInstance.Enum<>(Arrays.asList(PermissionLevel.values()), PermissionLevel.Codec),
            ModOptions.PERMISSION_LEVEL.get(),
            ModOptions.PERMISSION_LEVEL::set);

    public static final OptionInstance<ChangeFlySpeedOnRule> CHANGE_FLY_SPEED_ON_RULE = new OptionInstance<>(
            "survivalfly.options.change_fly_speed_on_rule",
            option -> {
                return switch (option) {
                    case NON_SURVIVAL_LIKE_GAMEMODES -> Tooltip.create(Component.translatable("survivalfly.options.change_fly_speed_on_rule.non_survival_like_gamemodes.tooltip"));
                    case ANY_GAMEMODE -> Tooltip.create(Component.translatable("survivalfly.options.change_fly_speed_on_rule.any_gamemode.tooltip"));
                    case SPECTATOR_MODE_ONLY -> Tooltip.create(Component.translatable("survivalfly.options.change_fly_speed_on_rule.spectator_mode_only.tooltip"));
                };
            },
            OptionInstance.forOptionEnum(),
            new OptionInstance.Enum<>(Arrays.asList(ChangeFlySpeedOnRule.values()), ChangeFlySpeedOnRule.Codec),
            ModOptions.CHANGE_FLY_SPEED_ON_RULE.get(),
            ModOptions.CHANGE_FLY_SPEED_ON_RULE::set);

    public static final OptionInstance<Boolean> SHOW_CONFIG_BUTTON = OptionInstance.createBoolean("survivalfly.options.show_config_button", OptionInstance.cachedConstantTooltip(Component.translatable("survivalfly.options.show_config_button.tooltip")),
            ON_OFF_TEXT, ModOptions.SHOW_CONFIG_BUTTON.get(), ModOptions.SHOW_CONFIG_BUTTON::set);
}