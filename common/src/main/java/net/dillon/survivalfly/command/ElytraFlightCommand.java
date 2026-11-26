package net.dillon.survivalfly.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandPermissionLevels;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import static net.dillon.survivalfly.option.OptionInstances.common;
import static net.dillon.survivalfly.option.OptionInstances.updateCommon;
import static net.dillon.survivalfly.util.ModTexts.DISABLED_UPPERCASE;
import static net.dillon.survivalfly.util.ModTexts.ENABLED_UPPERCASE;

/**
 * The functionality for the {@code /elytraflight} command.
 */
public class ElytraFlightCommand {
    private static final Component ELYTRA_FLIGHT_ENABLED = ENABLED_UPPERCASE.copy().append(Component.literal(" elytra flight.").withStyle(ChatFormatting.WHITE));
    private static final Component ELYTRA_FLIGHT_DISABLED = DISABLED_UPPERCASE.copy().append(Component.literal(" elytra flight.").withStyle(ChatFormatting.WHITE));
    private static final Component ELYTRA_FLIGHT_DESC = Component.literal("Players must be wearing an elytra to use /flight.");

    /**
     * Registers the elytraflight command.
     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("elytraflight")
                        .requires(Commands.hasPermission(CommandPermissionLevels.LEVEL_ADMINS))
                        .executes(
                                context -> {
                                    updateCommon(common -> {
                                        common.elytraFlight = !common.elytraFlight;
                                    });
                                    if (common().elytraFlight) {
                                        context.getSource().sendSystemMessage(ELYTRA_FLIGHT_ENABLED);
                                        context.getSource().sendSystemMessage(ELYTRA_FLIGHT_DESC);
                                    } else {
                                        context.getSource().sendSystemMessage(ELYTRA_FLIGHT_DISABLED);
                                    }
                                    return 0;
                                }
                        )
        );
    }
}