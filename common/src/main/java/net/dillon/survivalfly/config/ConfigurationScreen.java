package net.dillon.survivalfly.config;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.EnumControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.blay09.mods.balm.Balm;
import net.dillon.dillonlib.annotation.Dill;
import net.dillon.dillonlib.annotation.DillType;
import net.dillon.survivalfly.option.*;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import static net.dillon.survivalfly.option.OptionInstances.client;
import static net.dillon.survivalfly.option.OptionInstances.common;

/**
 * The main configuration screen for Survival Fly.
 */
@Dill(DillType.CLIENT)
public class ConfigurationScreen {

    public static YetAnotherConfigLib configScreen() {
        boolean onServer = Minecraft.getInstance().getCurrentServer() != null;

        return YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("survivalfly.title"))
                .category(
                        ConfigCategory.createBuilder()
                                .name(Component.translatable("survivalfly.options.main"))
                                .tooltip(Component.translatable("survivalfly.options.main.description"))
                                .option(
                                        Option.<Boolean>createBuilder()
                                                .name(Component.translatable("survivalfly.options.enable_mod"))
                                                .description(OptionDescription.of(Component.translatable("survivalfly.options.enable_mod.description")))
                                                .binding(true, () -> common().enableMod, v -> Balm.config().updateLocalConfig(ModCommonOptions.class, config -> config.enableMod = v))
                                                .controller(TickBoxControllerBuilder::create)
                                                .build()
                                )
                                .option(
                                        Option.<Boolean>createBuilder()
                                                .name(Component.translatable("survivalfly.options.crouch_flight"))
                                                .description(OptionDescription.of(Component.translatable("survivalfly.options.crouch_flight.description")))
                                                .binding(false, () -> common().crouchFlight, v -> Balm.config().updateLocalConfig(ModCommonOptions.class, config -> config.crouchFlight = v))
                                                .controller(BooleanControllerBuilder::create)
                                                .build()
                                )
                                .option(
                                        Option.<Boolean>createBuilder()
                                                .name(Component.translatable("survivalfly.options.safe_mode"))
                                                .description(OptionDescription.of(Component.translatable("survivalfly.options.safe_mode.description")))
                                                .binding(true, () -> common().safeMode, v -> Balm.config().updateLocalConfig(ModCommonOptions.class, config -> config.safeMode = v))
                                                .controller(TickBoxControllerBuilder::create)
                                                .build()
                                )
                                .build()
                )
                .category(
                        ConfigCategory.createBuilder()
                                .name(Component.translatable("survivalfly.options.client"))
                                .tooltip(Component.translatable("survivalfly.options.client.description"))
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
                                .option(
                                        Option.<Boolean>createBuilder()
                                                .name(Component.translatable("survivalfly.options.server_warnings"))
                                                .description(OptionDescription.of(Component.translatable("survivalfly.options.server_warnings.description")))
                                                .binding(true, () -> client().serverWarnings, v -> Balm.config().updateLocalConfig(ModClientOptions.class, config -> config.serverWarnings = v))
                                                .controller(BooleanControllerBuilder::create)
                                                .build()
                                )
                                .build()
                )
                .category(
                        ConfigCategory.createBuilder()
                                .name(Component.translatable("survivalfly.options.singleplayer"))
                                .tooltip(Component.translatable("survivalfly.options.singleplayer.description"))
                                .option(
                                        Option.<Permissions>createBuilder()
                                                .name(Component.translatable("survivalfly.options.permissions"))
                                                .description(OptionDescription.of(Component.translatable("survivalfly.options.permissions.description")))
                                                .binding(Permissions.ANYONE, () -> common().permissions, v -> Balm.config().updateLocalConfig(ModCommonOptions.class, config -> config.permissions = v))
                                                .controller(o -> EnumControllerBuilder.create(o)
                                                        .enumClass(Permissions.class)
                                                        .formatValue(v -> Component.translatable(v.getTranslationKey())))
                                                .available(!onServer)
                                                .build()
                                )
                                .option(
                                        Option.<FriendlyFlight>createBuilder()
                                                .name(Component.translatable("survivalfly.options.friendly_flight"))
                                                .description(OptionDescription.of(Component.translatable("survivalfly.options.friendly_flight.description")))
                                                .binding(FriendlyFlight.OFF, () -> common().friendlyFlight, v -> Balm.config().updateLocalConfig(ModCommonOptions.class, config -> config.friendlyFlight = v))
                                                .controller(o -> EnumControllerBuilder.create(o)
                                                        .enumClass(FriendlyFlight.class)
                                                        .formatValue(v -> Component.translatable(v.getTranslationKey())))
                                                .available(!onServer)
                                                .build()
                                )
                                .option(
                                        Option.<Boolean>createBuilder()
                                                .name(Component.translatable("survivalfly.options.elytra_flight"))
                                                .description(OptionDescription.of(Component.translatable("survivalfly.options.elytra_flight.description")))
                                                .binding(false, () -> common().elytraFlight, v -> Balm.config().updateLocalConfig(ModCommonOptions.class, config -> config.elytraFlight = v))
                                                .controller(BooleanControllerBuilder::create)
                                                .available(!onServer)
                                                .build()
                                )
                                .option(
                                        Option.<Boolean>createBuilder()
                                                .name(Component.translatable("survivalfly.options.flight_exhaustion"))
                                                .description(OptionDescription.of(Component.translatable("survivalfly.options.flight_exhaustion.description")))
                                                .binding(false, () -> common().flightExhaustion, v -> Balm.config().updateLocalConfig(ModCommonOptions.class, config -> config.flightExhaustion = v))
                                                .controller(BooleanControllerBuilder::create)
                                                .available(!onServer)
                                                .build()
                                )
                                .build()
                )
                .build();
    }
}