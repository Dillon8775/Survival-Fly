package net.dillon.survivalfly.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;

import static net.dillon.survivalfly.util.ModTexts.*;
import static net.dillon.survivalfly.util.ModUtil.*;

/**
 * A command to check the status of your flight ability.
 */
public class FlightStatusCommand {
    private static final Component NO_ELYTRA_FLIGHT_STATUS_SELF = Component.literal("You don't have flying abilities because you don't have an elytra equipped.");
    private static final Component FLIGHT_STATUS_NOT_ALLOWED_SELF = Component.literal("You don't have flying abilities because you have taken damage within the last 20 seconds.");

    /**
     * @return the {@code /flightstatus} command.
     */
    public static LiteralArgumentBuilder<CommandSourceStack> getFlightStatusCommand() {
        return Commands.literal("flightstatus")
                .requires(source -> source.hasPermission(options().permissions.getId()))
                .executes(
                        context -> execute(
                                context.getSource(),
                                context.getSource().getPlayerOrException()
                        )
                )
                .then(
                        Commands.argument("target", EntityArgument.player())
                                .requires(source -> source.hasPermission(Commands.LEVEL_ADMINS))
                                .executes(context -> execute(
                                        context.getSource(),
                                        EntityArgument.getPlayer(context, "target")
                                ))
                );
    }

    /**
     * Returns the feedback text based on if the command succeeded or not.
     */
    private static void sendSuccess(CommandSourceStack source, ServerPlayer player, boolean success) {
        if (source.getEntity() == player) {
            if (success) {
                if (!isFlyingAllowed(player)) {
                    source.sendSuccess(() -> FLIGHT_STATUS_NOT_ALLOWED_SELF, true);
                } else if (!hasElytra(player)) {
                    source.sendSuccess(() -> NO_ELYTRA_FLIGHT_STATUS_SELF, true);
                } else {
                    source.sendSuccess(() -> getFlightStatusSelf(player.getAbilities().mayfly), true);
                }
            } else {
                source.sendSuccess(() -> noFlightStatusGameModeSelf(player), true);
            }
        } else {
            if (success) {
                if (!isFlyingAllowed(player)) {
                    source.sendSuccess(() -> noFlightStatusOther(player), true);
                } else if (!hasElytra(player)) {
                    source.sendSuccess(() -> noElytraFlightStatusOther(player), true);
                } else {
                    source.sendSuccess(() -> getFlightStatusOther(player, player.getAbilities().mayfly), true);
                }
            } else {
                source.sendSuccess(() -> noFlightStatusGameModeOther(player), true);
            }
        }
    }

    /**
     * Executes the command.
     */
    private static int execute(CommandSourceStack source, ServerPlayer target) {
        if (target.gameMode.getGameModeForPlayer().isCreative() || target.gameMode.getGameModeForPlayer() == GameType.SPECTATOR) {
            sendSuccess(source, target, false);
            return 0;
        } else {
            sendSuccess(source, target, true);
            return 1;
        }
    }

    private static Component getFlightStatusSelf(boolean value) {
        return Component.literal("Your flight is currently ").copy().append(blLowercase(value)).copy().append(Component.literal(".").withStyle(ChatFormatting.WHITE));
    }

    private static Component getFlightStatusOther(ServerPlayer player, boolean value) {
        return getPlayerName(player).copy().append(" flight is currently ").copy().append(blLowercase(value)).copy().append(Component.literal(".").withStyle(ChatFormatting.WHITE));
    }

    private static Component noFlightStatusOther(ServerPlayer player) {
        return getPlayerName(player).copy().append(" doesn't have flying abilities because they have taken damage within the last 20 seconds.");
    }

    private static Component noFlightStatusGameModeSelf(ServerPlayer player) {
        return Component.literal("You have flying abilities because your gamemode is ").append(getGameMode(player)).append(".");
    }

    private static Component noFlightStatusGameModeOther(ServerPlayer player) {
        return getPlayerName(player).copy().append(" has flying abilities because their gamemode is " + getGameMode(player) + ".");
    }

    private static Component noElytraFlightStatusOther(ServerPlayer player) {
        return getPlayerName(player).copy().append(" doesn't have flying abilities because they do not have an elytra equipped.");
    }
}