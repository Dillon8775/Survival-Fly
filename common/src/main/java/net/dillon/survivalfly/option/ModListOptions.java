package net.dillon.survivalfly.option;

import net.dillon.survivalfly.util.ModTexts;
import net.minecraft.client.Minecraft;
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

    public static OptionInstance<Permissions> permissions() {
        return new OptionInstance<>(
                "survivalfly.options.permissions",
                option -> {
                    if (Minecraft.getInstance().getCurrentServer() != null) {
                        return Tooltip.create(Component.translatable("survivalfly.option_disabled"));
                    }
                    return switch (option) {
                        case ANYONE -> Tooltip.create(Component.translatable("survivalfly.options.permissions.regular.tooltip"));
                        case MODERATOR -> Tooltip.create(Component.translatable("survivalfly.options.permissions.moderator.tooltip"));
                        case GAMEMASTER -> Tooltip.create(Component.translatable("survivalfly.options.permissions.gamemaster.tooltip"));
                        case ADMIN -> Tooltip.create(Component.translatable("survivalfly.options.permissions.admin.tooltip"));
                    };
                },
                (optionText, value) -> value.getText(),
                new OptionInstance.Enum<>(Arrays.asList(Permissions.values()), Permissions.Codec),
                options().permissions,
                value -> options().permissions = value);
    }

    public static final OptionInstance<ConfigButton> CONFIG_BUTTON = new OptionInstance<>(
            "survivalfly.options.config_button",
            option -> {
                return switch (option) {
                    case EVERYWHERE -> Tooltip.create(Component.translatable("survivalfly.options.config_button.everywhere.tooltip"));
                    case BOTTOM_LEFT -> Tooltip.create(Component.translatable("survivalfly.options.config_button.bottom_left.tooltip"));
                    case BOTTOM_RIGHT -> Tooltip.create(Component.translatable("survivalfly.options.config_button.bottom_right.tooltip"));
                    case TITLE_ONLY -> Tooltip.create(Component.translatable("survivalfly.options.config_button.title_only.tooltip"));
                    case OFF -> Tooltip.create(Component.translatable("survivalfly.options.config_button.off.tooltip"));
                };
            },
            (optionText, value) -> value.getText(),
            new OptionInstance.Enum<>(Arrays.asList(ConfigButton.values()), ConfigButton.Codec),
            options().configButton,
            value -> options().configButton = value);

    public static OptionInstance<Boolean> elytraFlight() {
        return OptionInstance.createBoolean("survivalfly.options.elytra_flight", OptionInstance.cachedConstantTooltip(
                Minecraft.getInstance().getCurrentServer() == null
                        ? Component.translatable("survivalfly.options.elytra_flight.tooltip")
                        : Component.translatable("survivalfly.option_disabled")
                ),
                ON_OFF_TEXT, options().elytraFlight, value -> options().elytraFlight = value);
    }

    public static OptionInstance<Boolean> flightExhaustion() {
        return OptionInstance.createBoolean("survivalfly.options.flight_exhaustion", OptionInstance.cachedConstantTooltip(
                Minecraft.getInstance().getCurrentServer() == null
                        ? Component.translatable("survivalfly.options.flight_exhaustion.tooltip")
                        : Component.translatable("survivalfly.option_disabled")
                ),
                ON_OFF_TEXT, options().flightExhaustion, value -> options().flightExhaustion = value);
    }

    public static OptionInstance<Boolean> friendlyFlight() {
        return OptionInstance.createBoolean("survivalfly.options.friendly_flight", OptionInstance.cachedConstantTooltip(
                Minecraft.getInstance().getCurrentServer() == null
                        ? Component.translatable("survivalfly.options.friendly_flight.tooltip")
                        : Component.translatable("survivalfly.option_disabled")
                ),
                ON_OFF_TEXT, options().friendlyFlight, value -> options().friendlyFlight = value);
    }
}