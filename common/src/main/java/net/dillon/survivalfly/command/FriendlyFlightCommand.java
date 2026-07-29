package net.dillon.survivalfly.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.blay09.mods.balm.Balm;
import net.dillon.dillonlib.util.SimplePermissions;
import net.dillon.survivalfly.option.FriendlyFlight;
import net.dillon.survivalfly.option.ModOptions;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import static net.dillon.survivalfly.helper.ModHelper.options;
import static net.dillon.survivalfly.helper.ModHelper.sendSourceMessage;
import static net.dillon.survivalfly.util.ModTexts.DISABLED_UPPERCASE;
import static net.dillon.survivalfly.util.ModTexts.ENABLED_UPPERCASE;

/**
 * The functionality for the {@code /friendlyflight} command.
 */
public class FriendlyFlightCommand {
    private static final Component FRIENDLY_FLIGHT_ENABLED = ENABLED_UPPERCASE.copy().append(Component.literal(" friendly flight.").withStyle(ChatFormatting.WHITE));
    private static final Component FRIENDLY_FLIGHT_DISABLED = DISABLED_UPPERCASE.copy().append(Component.literal(" friendly flight.").withStyle(ChatFormatting.WHITE));
    private static final Component FRIENDLY_FLIGHT_DESC = Component.literal("If players take damage, their flight is disabled for 20 seconds.");

    /**
     * @return Registers the friendlyflight command.
     */
    public static LiteralArgumentBuilder<CommandSourceStack> getFriendlyFlightCommand() {
        return Commands.literal("friendlyflight")
                .requires(SimplePermissions::admin)
                .then(
                        Commands.argument("type", StringArgumentType.word())
                                .suggests((context, builder) -> {
                                    for (FriendlyFlight friendlyFlight : FriendlyFlight.values()) {
                                        builder.suggest(friendlyFlight.getSerializedName());
                                    }
                                    return builder.buildFuture();
                                }).executes(context -> {
                                    String input = StringArgumentType.getString(context, "type");

                                    try {
                                        FriendlyFlight friendlyFlight = FriendlyFlight.byName(input);
                                        Balm.config().updateLocalConfig(ModOptions.class, config -> {
                                            config.friendlyFlight = friendlyFlight;
                                        });
                                        if (options().friendlyFlight.enabled()) {
                                            sendSourceMessage(context, FRIENDLY_FLIGHT_ENABLED);
                                            sendSourceMessage(context, FRIENDLY_FLIGHT_DESC);
                                        } else {
                                            sendSourceMessage(context, FRIENDLY_FLIGHT_DISABLED);
                                        }
                                        return friendlyFlight.getId();
                                    } catch (NullPointerException | IllegalArgumentException o) {
                                        context.getSource().sendFailure(Component.literal("Invalid type: " + input));
                                        return 0;
                                    }
                                }));
    }
}