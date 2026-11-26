package net.dillon.survivalfly.command;

import com.mojang.brigadier.CommandDispatcher;
import net.dillon.survivalfly.option.FriendlyFlight;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandPermissionLevels;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.StringArgument;
import net.minecraft.commands.suggestions.SuggestionProviders;
import net.minecraft.network.chat.Component;

import static net.dillon.survivalfly.helper.ModHelper.sendSourceMessage;
import static net.dillon.survivalfly.option.OptionInstances.common;
import static net.dillon.survivalfly.option.OptionInstances.updateCommon;
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
     * Registers the {@code /friendlyflight} command.
     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("friendlyflight")
                        .requires(Commands.hasPermission(CommandPermissionLevels.LEVEL_ADMINS))
                        .then(
                                Commands.argument("type", StringArgument.word())
                                        .suggests(SuggestionProviders.serverOnly((server, context, builder) -> {
                                            for (FriendlyFlight friendlyFlight : FriendlyFlight.values()) {
                                                builder.suggest(friendlyFlight.getSerializedName());
                                            }
                                            return builder.buildFuture();
                                        }))
                                        .executes(context -> {
                                                    String input = StringArgument.getString(context, "type");

                                                    try {
                                                        FriendlyFlight friendlyFlight = FriendlyFlight.byName(input);
                                                        updateCommon(common -> {
                                                            common.friendlyFlight = friendlyFlight;
                                                        });
                                                        if (common().friendlyFlight.enabled()) {
                                                            sendSourceMessage(context, FRIENDLY_FLIGHT_ENABLED);
                                                            sendSourceMessage(context, FRIENDLY_FLIGHT_DESC);
                                                        } else {
                                                            sendSourceMessage(context, FRIENDLY_FLIGHT_DISABLED);
                                                        }
                                                        return friendlyFlight.getOrdinal();
                                                    } catch (NullPointerException | IllegalArgumentException o) {
                                                        context.getSource().sendFailure(Component.literal("Invalid type: " + input));
                                                        return 0;
                                                    }
                                                }
                                        )
                        )
        );
    }
}