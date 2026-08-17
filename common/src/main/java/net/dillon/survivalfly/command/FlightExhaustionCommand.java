package net.dillon.survivalfly.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.dillon.dillonlib.util.SimplePermissions;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import static net.dillon.survivalfly.option.OptionInstances.common;
import static net.dillon.survivalfly.option.OptionInstances.updateCommon;
import static net.dillon.survivalfly.util.ModTexts.DISABLED_UPPERCASE;
import static net.dillon.survivalfly.util.ModTexts.ENABLED_UPPERCASE;

/**
 * The functionality for the {@code /flightexhaustion} command.
 */
public class FlightExhaustionCommand {
    private static final Component FLIGHT_EXHAUSTION_ENABLED = ENABLED_UPPERCASE.copy().append(Component.literal(" flight exhaustion.").withStyle(ChatFormatting.WHITE));
    private static final Component FLIGHT_EXHAUSTION_DISABLED = DISABLED_UPPERCASE.copy().append(Component.literal(" flight exhaustion.").withStyle(ChatFormatting.WHITE));
    private static final Component FLIGHT_EXHAUSTION_DESC = Component.literal("Players will get hungry if they are using flight, more hungry the higher their flight speed.");

    /**
     * @return Registers the flightexhaustion command.
     */
    public static LiteralArgumentBuilder<CommandSourceStack> getFlightExhaustionCommand() {
        return Commands.literal("flightexhaustion")
                .requires(SimplePermissions::admin)
                .executes(
                        context -> {
                            updateCommon(common -> {
                                common.flightExhaustion = !common.flightExhaustion;
                            });
                            if (common().flightExhaustion) {
                                context.getSource().sendSystemMessage(FLIGHT_EXHAUSTION_ENABLED);
                                context.getSource().sendSystemMessage(FLIGHT_EXHAUSTION_DESC);
                            } else {
                                context.getSource().sendSystemMessage(FLIGHT_EXHAUSTION_DISABLED);
                            }
                            return 0;
                        });
    }
}