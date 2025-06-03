package net.dillon.survivalfly.option;

import net.dillon.survivalfly.SurvivalFly;
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
            SimpleOption.enumValueText(),
            new SimpleOption.PotentialValuesBasedCallbacks<>(Arrays.asList(PermissionLevel.values()), PermissionLevel.Codec),
            SurvivalFly.options().permissionLevel,
            value -> SurvivalFly.options().permissionLevel = value);
}