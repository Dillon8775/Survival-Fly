package net.dillon.survivalfly.option;

import net.blay09.mods.balm.api.Balm;
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
                value -> {
                    Balm.getConfig().updateLocalConfig(ModOptions.class, config -> {
                        config.permissions = value;
                    });
                });
    }

    public static final OptionInstance<MenuButton> MENU_BUTTON = new OptionInstance<>(
            "survivalfly.options.menu_button",
            option -> {
                return switch (option) {
                    case EVERYWHERE -> Tooltip.create(Component.translatable("survivalfly.options.menu_button.everywhere.tooltip"));
                    case BOTTOM_LEFT -> Tooltip.create(Component.translatable("survivalfly.options.menu_button.bottom_left.tooltip"));
                    case BOTTOM_RIGHT -> Tooltip.create(Component.translatable("survivalfly.options.menu_button.bottom_right.tooltip"));
                    case TITLE_ONLY -> Tooltip.create(Component.translatable("survivalfly.options.menu_button.title_only.tooltip"));
                    case OFF -> Tooltip.create(Component.translatable("survivalfly.options.menu_button.off.tooltip"));
                };
            },
            (optionText, value) -> value.getText(),
            new OptionInstance.Enum<>(Arrays.asList(MenuButton.values()), MenuButton.Codec),
            options().menuButton,
            value -> {
                Balm.getConfig().updateLocalConfig(ModOptions.class, config -> {
                    config.menuButton = value;
                });
            });

    public static OptionInstance<Boolean> elytraFlight() {
        return OptionInstance.createBoolean("survivalfly.options.elytra_flight", OptionInstance.cachedConstantTooltip(
                Minecraft.getInstance().getCurrentServer() == null
                        ? Component.translatable("survivalfly.options.elytra_flight.tooltip")
                        : Component.translatable("survivalfly.option_disabled")
                ),
                ON_OFF_TEXT, options().elytraFlight, value -> {
                    Balm.getConfig().updateLocalConfig(ModOptions.class, config -> {
                        config.elytraFlight = value;
                    });
                });
    }

    public static OptionInstance<Boolean> flightExhaustion() {
        return OptionInstance.createBoolean("survivalfly.options.flight_exhaustion", OptionInstance.cachedConstantTooltip(
                Minecraft.getInstance().getCurrentServer() == null
                        ? Component.translatable("survivalfly.options.flight_exhaustion.tooltip")
                        : Component.translatable("survivalfly.option_disabled")
                ),
                ON_OFF_TEXT, options().flightExhaustion, value -> {
                    Balm.getConfig().updateLocalConfig(ModOptions.class, config -> {
                        config.flightExhaustion = value;
                    });
                });
    }

    public static OptionInstance<Boolean> crouchFlight() {
        return OptionInstance.createBoolean("survivalfly.options.crouch_flight", OptionInstance.cachedConstantTooltip(
                        Component.translatable("survivalfly.options.crouch_flight.tooltip")
                ),
                ON_OFF_TEXT, options().crouchFlight, value -> {
                    Balm.getConfig().updateLocalConfig(ModOptions.class, config -> {
                        config.crouchFlight = value;
                    });
                });
    }

    public static OptionInstance<Boolean> safeMode() {
        return OptionInstance.createBoolean("survivalfly.options.safe_mode", OptionInstance.cachedConstantTooltip(
                        Component.translatable("survivalfly.options.safe_mode.tooltip")
                ),
                ON_OFF_TEXT, options().safeMode, value -> {
                    Balm.getConfig().updateLocalConfig(ModOptions.class, config -> {
                        config.safeMode = value;
                    });
                });
    }

    public static final OptionInstance<FriendlyFlight> FRIENDLY_FLIGHT = new OptionInstance<>(
            "survivalfly.options.friendly_flight",
            option -> {
                return switch (option) {
                    case OFF -> Tooltip.create(Component.translatable("survivalfly.options.friendly_flight.tooltip"));
                    case PLAYERS_AND_MOBS -> Tooltip.create(Component.translatable("survivalfly.options.friendly_flight.players_and_mobs.tooltip"));
                    case PLAYERS_ONLY -> Tooltip.create(Component.translatable("survivalfly.options.friendly_flight.players_only.tooltip"));
                };
            },
            (optionText, value) -> value.getText(),
            new OptionInstance.Enum<>(Arrays.asList(FriendlyFlight.values()), FriendlyFlight.Codec),
            options().friendlyFlight,
            value -> {
                Balm.getConfig().updateLocalConfig(ModOptions.class, config -> {
                    config.friendlyFlight = value;
                });
            });
}