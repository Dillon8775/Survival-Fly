package net.dillon.survivalfly.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.EnumControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.blay09.mods.balm.Balm;
import net.dillon.dillonlib.annotation.Dill;
import net.dillon.dillonlib.annotation.DillType;
import net.dillon.survivalfly.option.FriendlyFlight;
import net.dillon.survivalfly.option.ModOptions;
import net.dillon.survivalfly.option.Permissions;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import static net.dillon.survivalfly.helper.ModHelper.options;

/**
 * The main configuration screen for Survival Fly.
 */
@Dill(DillType.CLIENT)
public class ConfigurationScreen {

    public static YetAnotherConfigLib configScreen() {
        boolean onServer = Minecraft.getInstance().getCurrentServer() != null;

        return YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("survivalfly.options.title.surival_fly"))
                .category(
                        ConfigCategory.createBuilder()
                                .name(Component.translatable("survivalfly.options.title.surival_fly"))
                                .tooltip(Component.translatable("survivalfly.options.tooltip"))
                                .group(
                                        OptionGroup.createBuilder()
                                                .name(Component.translatable("survivalfly.options.general"))
                                                .description(OptionDescription.of(Component.translatable("survivalfly.options.general.description")))
                                                .option(
                                                        Option.<Boolean>createBuilder()
                                                                .name(Component.translatable("survivalfly.options.enable_mod"))
                                                                .description(OptionDescription.of(Component.translatable("survivalfly.options.enable_mod.description")))
                                                                .binding(true, () -> options().enableMod, v -> Balm.config().updateLocalConfig(ModOptions.class, config -> config.enableMod = v))
                                                                .controller(TickBoxControllerBuilder::create)
                                                                .build()
                                                )
                                                .option(
                                                        Option.<Permissions>createBuilder()
                                                                .name(Component.translatable("survivalfly.options.permissions"))
                                                                .description(OptionDescription.of(Component.translatable("survivalfly.options.permissions.description")))
                                                                .binding(Permissions.ANYONE, () -> options().permissions, v -> Balm.config().updateLocalConfig(ModOptions.class, config -> config.permissions = v))
                                                                .controller(o -> EnumControllerBuilder.create(o)
                                                                        .enumClass(Permissions.class)
                                                                        .formatValue(v -> Component.literal(v.getSerializedName())))
                                                                .available(!onServer)
                                                                .build()
                                                )
                                                .option(
                                                        Option.<Boolean>createBuilder()
                                                                .name(Component.translatable("survivalfly.options.crouch_flight"))
                                                                .description(OptionDescription.of(Component.translatable("survivalfly.options.crouch_flight.description")))
                                                                .binding(false, () -> options().crouchFlight, v -> Balm.config().updateLocalConfig(ModOptions.class, config -> config.crouchFlight = v))
                                                                .controller(BooleanControllerBuilder::create)
                                                                .build()
                                                )
                                                .option(
                                                        Option.<Boolean>createBuilder()
                                                                .name(Component.translatable("survivalfly.options.safe_mode"))
                                                                .description(OptionDescription.of(Component.translatable("survivalfly.options.safe_mode.description")))
                                                                .binding(true, () -> options().safeMode, v -> Balm.config().updateLocalConfig(ModOptions.class, config -> config.safeMode = v))
                                                                .controller(TickBoxControllerBuilder::create)
                                                                .build()
                                                )
                                                .build()
                                )
                                .group(
                                        OptionGroup.createBuilder()
                                                .name(Component.translatable("survivalfly.options.modes"))
                                                .description(OptionDescription.of(Component.translatable("survivalfly.options.modes.description")))
                                                .option(
                                                        Option.<FriendlyFlight>createBuilder()
                                                                .name(Component.translatable("survivalfly.options.friendly_flight"))
                                                                .description(OptionDescription.of(Component.translatable("survivalfly.options.friendly_flight.description")))
                                                                .binding(FriendlyFlight.OFF, () -> options().friendlyFlight, v -> Balm.config().updateLocalConfig(ModOptions.class, config -> config.friendlyFlight = v))
                                                                .controller(o -> EnumControllerBuilder.create(o)
                                                                        .enumClass(FriendlyFlight.class)
                                                                        .formatValue(v -> Component.literal(v.getSerializedName())))
                                                                .available(!onServer)
                                                                .build()
                                                )
                                                .option(
                                                        Option.<Boolean>createBuilder()
                                                                .name(Component.translatable("survivalfly.options.elytra_flight"))
                                                                .description(OptionDescription.of(Component.translatable("survivalfly.options.elytra_flight.description")))
                                                                .binding(false, () -> options().elytraFlight, v -> Balm.config().updateLocalConfig(ModOptions.class, config -> config.elytraFlight = v))
                                                                .controller(BooleanControllerBuilder::create)
                                                                .available(!onServer)
                                                                .build()
                                                )
                                                .option(
                                                        Option.<Boolean>createBuilder()
                                                                .name(Component.translatable("survivalfly.options.flight_exhaustion"))
                                                                .description(OptionDescription.of(Component.translatable("survivalfly.options.flight_exhaustion.description")))
                                                                .binding(false, () -> options().flightExhaustion, v -> Balm.config().updateLocalConfig(ModOptions.class, config -> config.flightExhaustion = v))
                                                                .controller(BooleanControllerBuilder::create)
                                                                .available(!onServer)
                                                                .build()
                                                )
                                                .collapsed(onServer)
                                                .build()
                                )
                                .build()
                )
                .build();
    }
}