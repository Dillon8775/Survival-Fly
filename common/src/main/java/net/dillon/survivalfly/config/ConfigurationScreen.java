package net.dillon.survivalfly.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.EnumControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.blay09.mods.balm.Balm;
import net.dillon.dillonlib.annotation.Dill;
import net.dillon.dillonlib.annotation.DillType;
import net.dillon.dillonlib.util.Texts;
import net.dillon.survivalfly.option.*;
import net.minecraft.network.chat.Component;

import static net.dillon.dillonlib.task.ClientTasks.isOnServer;
import static net.dillon.survivalfly.option.OptionInstances.*;

/**
 * The main configuration screen for Survival Fly.
 */
@Dill(DillType.CLIENT)
public class ConfigurationScreen {

    public static YetAnotherConfigLib configScreen(ModCommonOptions serverConfig, boolean hasPermissionOnServer) {
        boolean onServer = isOnServer();
        boolean canModifyOnServer = !onServer || hasPermissionOnServer;

        Option<Boolean> serverWarnings = Option.<Boolean>createBuilder()
                .name(Component.translatable("survivalfly.options.server_warnings"))
                .description(OptionDescription.of(Component.translatable("survivalfly.options.server_warnings.description")))
                .binding(true, () -> client().serverWarnings, v -> Balm.config().updateLocalConfig(ModClientOptions.class, config -> config.serverWarnings = v))
                .controller(BooleanControllerBuilder::create)
                .build();

        return YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("survivalfly"))
                .category(
                        ConfigCategory.createBuilder()
                                .name(Component.translatable("survivalfly"))
                                .tooltip(Component.translatable("survivalfly.options.tooltip"))
                                .group(
                                        OptionGroup.createBuilder()
                                                .name(Component.translatable("survivalfly.options.universal"))
                                                .description(OptionDescription.of(Component.translatable("survivalfly.options.universal.description")))
                                                .option(
                                                        Option.<Boolean>createBuilder()
                                                                .name(Component.translatable("survivalfly.options.crouch_flight"))
                                                                .description(OptionDescription.of(Component.translatable("survivalfly.options.crouch_flight.description")))
                                                                .binding(false, () -> common().crouchFlight, v -> Balm.config().updateLocalConfig(ModCommonOptions.class, config -> config.crouchFlight = v))
                                                                .controller(BooleanControllerBuilder::create)
                                                                .build()
                                                )
                                                .build()
                                )
                                .group(
                                        OptionGroup.createBuilder()
                                                .name(
                                                        onServer
                                                                ? Component.translatable("survivalfly.options.server_options")
                                                                : Component.translatable("survivalfly.options.local_options")
                                                )
                                                .description(
                                                        OptionDescription.of(
                                                              onServer
                                                                      ? Component.translatable("survivalfly.options.server_options.description")
                                                                      : Component.translatable("survivalfly.options.local_options.description")
                                                        )
                                                )
                                                .option(
                                                        Option.<Boolean>createBuilder()
                                                                .name(Component.translatable("survivalfly.options.enable_mod"))
                                                                .description(
                                                                        OptionDescription.of(
                                                                                optionTooltip(Component.translatable("survivalfly.options.enable_mod.description"), canModifyOnServer)
                                                                        )
                                                                )
                                                                .available(canModifyOnServer)
                                                                .binding(true, () -> {
                                                                    if (onServer) {
                                                                        return serverConfig.enableMod;
                                                                    } else {
                                                                        return common().enableMod;
                                                                    }
                                                                }, v -> {
                                                                    if (onServer) {
                                                                        updateServerConfig(serverConfig, server -> server.enableMod = v);
                                                                    } else {
                                                                        Balm.config().updateLocalConfig(ModCommonOptions.class, config -> config.enableMod = v);
                                                                    }
                                                                })
                                                                .controller(TickBoxControllerBuilder::create)
                                                                .build()
                                                )
                                                .option(
                                                        Option.<Permissions>createBuilder()
                                                                .name(Component.translatable("survivalfly.options.permissions"))
                                                                .description(
                                                                        OptionDescription.of(
                                                                                optionTooltip(Component.translatable("survivalfly.options.permissions.description"), canModifyOnServer)
                                                                        )
                                                                )
                                                                .available(canModifyOnServer)
                                                                .binding(Permissions.ANYONE, () -> {
                                                                    if (onServer) {
                                                                        return serverConfig.permissions;
                                                                    } else {
                                                                        return common().permissions;
                                                                    }
                                                                }, v -> {
                                                                    if (onServer) {
                                                                        updateServerConfig(serverConfig, server -> server.permissions = v);
                                                                    } else {
                                                                        Balm.config().updateLocalConfig(ModCommonOptions.class, config -> config.permissions = v);
                                                                    }
                                                                })
                                                                .controller(o -> EnumControllerBuilder.create(o)
                                                                        .enumClass(Permissions.class)
                                                                        .formatValue(v -> !canModifyOnServer ? Texts.BLANK : Component.translatable(v.getTranslationKey())))
                                                                .build()
                                                )
                                                .option(
                                                        Option.<Boolean>createBuilder()
                                                                .name(Component.translatable("survivalfly.options.allow_players_to_change_flight_speed"))
                                                                .description(
                                                                        OptionDescription.of(
                                                                                optionTooltip(Component.translatable("survivalfly.options.allow_players_to_change_flight_speed.description"), canModifyOnServer)
                                                                        )
                                                                )
                                                                .available(canModifyOnServer)
                                                                .binding(true, () -> {
                                                                    if (onServer) {
                                                                        return serverConfig.allowPlayersToChangeFlightSpeed;
                                                                    } else {
                                                                        return common().allowPlayersToChangeFlightSpeed;
                                                                    }
                                                                }, v -> {
                                                                    if (onServer) {
                                                                        updateServerConfig(serverConfig, server -> server.allowPlayersToChangeFlightSpeed = v);
                                                                    } else {
                                                                        Balm.config().updateLocalConfig(ModCommonOptions.class, config -> config.allowPlayersToChangeFlightSpeed = v);
                                                                    }
                                                                })
                                                                .controller(BooleanControllerBuilder::create)
                                                                .build()
                                                )
                                                .option(
                                                        Option.<FriendlyFlight>createBuilder()
                                                                .name(Component.translatable("survivalfly.options.friendly_flight"))
                                                                .description(
                                                                        OptionDescription.of(
                                                                                optionTooltip(Component.translatable("survivalfly.options.friendly_flight.description"), canModifyOnServer)
                                                                        )
                                                                )
                                                                .available(canModifyOnServer)
                                                                .binding(FriendlyFlight.OFF, () -> {
                                                                    if (onServer) {
                                                                        return serverConfig.friendlyFlight;
                                                                    } else {
                                                                        return common().friendlyFlight;
                                                                    }
                                                                }, v -> {
                                                                    if (onServer) {
                                                                        updateServerConfig(serverConfig, server -> server.friendlyFlight = v);
                                                                    } else {
                                                                        Balm.config().updateLocalConfig(ModCommonOptions.class, config -> config.friendlyFlight = v);
                                                                    }
                                                                })
                                                                .controller(o -> EnumControllerBuilder.create(o)
                                                                        .enumClass(FriendlyFlight.class)
                                                                        .formatValue(v -> Component.translatable(v.getTranslationKey())))
                                                                .available(!onServer)
                                                                .build()
                                                )
                                                .option(
                                                        Option.<Boolean>createBuilder()
                                                                .name(Component.translatable("survivalfly.options.elytra_flight"))
                                                                .description(
                                                                        OptionDescription.of(
                                                                                optionTooltip(Component.translatable("survivalfly.options.elytra_flight.description"), canModifyOnServer)
                                                                        )
                                                                )
                                                                .available(canModifyOnServer)
                                                                .binding(false, () -> {
                                                                    if (onServer) {
                                                                        return serverConfig.elytraFlight;
                                                                    } else {
                                                                        return common().elytraFlight;
                                                                    }
                                                                }, v -> {
                                                                    if (onServer) {
                                                                        updateServerConfig(serverConfig, server -> server.elytraFlight = v);
                                                                    } else {
                                                                        Balm.config().updateLocalConfig(ModCommonOptions.class, config -> config.elytraFlight = v);
                                                                    }
                                                                })
                                                                .controller(BooleanControllerBuilder::create)
                                                                .available(!onServer)
                                                                .build()
                                                )
                                                .option(
                                                        Option.<Boolean>createBuilder()
                                                                .name(Component.translatable("survivalfly.options.flight_exhaustion"))
                                                                .description(
                                                                        OptionDescription.of(
                                                                                optionTooltip(Component.translatable("survivalfly.options.flight_exhaustion.description"), canModifyOnServer)
                                                                        )
                                                                )
                                                                .available(canModifyOnServer)
                                                                .binding(false, () -> {
                                                                    if (onServer) {
                                                                        return serverConfig.flightExhaustion;
                                                                    } else {
                                                                        return common().flightExhaustion;
                                                                    }
                                                                }, v -> {
                                                                    if (onServer) {
                                                                        updateServerConfig(serverConfig, server -> server.flightExhaustion = v);
                                                                    } else {
                                                                        Balm.config().updateLocalConfig(ModCommonOptions.class, config -> config.flightExhaustion = v);
                                                                    }
                                                                })
                                                                .controller(BooleanControllerBuilder::create)
                                                                .available(!onServer)
                                                                .build()
                                                )
                                                .build()
                                )
                                .group(
                                        OptionGroup.createBuilder()
                                                .name(Component.translatable("survivalfly.options.client"))
                                                .description(OptionDescription.of(Component.translatable("survivalfly.options.client.description")))
                                                .option(
                                                        Option.<Boolean>createBuilder()
                                                                .name(Component.translatable("survivalfly.options.safe_mode"))
                                                                .description(OptionDescription.of(Component.translatable("survivalfly.options.safe_mode.description")))
                                                                .binding(true, () -> client().safeMode, v -> Balm.config().updateLocalConfig(ModClientOptions.class, config -> config.safeMode = v))
                                                                .controller(TickBoxControllerBuilder::create)
                                                                .addListener((opt, event) -> {
                                                                    if (event == OptionEventListener.Event.STATE_CHANGE || event == OptionEventListener.Event.INITIAL) {
                                                                        serverWarnings.setAvailable(!opt.pendingValue());
                                                                    }
                                                                })
                                                                .build()
                                                )
                                                .option(
                                                        serverWarnings
                                                )
                                                .option(
                                                        Option.<MenuButton>createBuilder()
                                                                .name(Component.translatable("survivalfly.options.menu_button"))
                                                                .description(OptionDescription.of(Component.translatable("survivalfly.options.menu_button.description")))
                                                                .binding(MenuButton.TITLE_ONLY, () -> client().menuButton, v -> Balm.config().updateLocalConfig(ModClientOptions.class, config -> config.menuButton = v))
                                                                .controller(o -> EnumControllerBuilder.create(o)
                                                                        .enumClass(MenuButton.class)
                                                                        .formatValue(v -> Component.translatable(v.getTranslationKey())))
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();
    }

    /**
     * @return an option tooltip, if the player has permission to modify it.
     */
    private static Component optionTooltip(Component tooltip, boolean canModifyOnServer) {
        if (!canModifyOnServer) {
            return Component.translatable("survivalfly.options.no_permission.tooltip");
        }

        return Component.translatable(isOnServer() ? "survivalfly.server_option" : "survivalfly.client_option")
                .copy().append(tooltip);
    }
}