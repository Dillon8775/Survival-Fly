package net.dillon.survivalfly.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.dillon.survivalfly.option.ModOptions;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import static net.dillon.survivalfly.util.ModTexts.DISABLED_UPPERCASE;
import static net.dillon.survivalfly.util.ModTexts.ENABLED_UPPERCASE;
import static net.dillon.survivalfly.util.ModUtil.options;

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
                .requires(source -> source.hasPermission(Commands.LEVEL_ADMINS))
                .executes(
                        context -> {
                            options().flightExhaustion = !options().flightExhaustion;
                            ModOptions.saveConfig();
                            if (options().flightExhaustion) {
                                context.getSource().sendSystemMessage(FLIGHT_EXHAUSTION_ENABLED);
                                context.getSource().sendSystemMessage(FLIGHT_EXHAUSTION_DESC);
                            } else {
                                context.getSource().sendSystemMessage(FLIGHT_EXHAUSTION_DISABLED);
                            }
                            return 0;
                        });
    }
}