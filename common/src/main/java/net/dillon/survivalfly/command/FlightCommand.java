package net.dillon.survivalfly.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.dillon.survivalfly.util.PlayerAbilitiesExtension;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.gamerules.GameRules;

import java.util.Collection;
import java.util.List;

import static net.dillon.survivalfly.util.ModTexts.*;
import static net.dillon.survivalfly.util.ModUtil.*;

/**
 * The functionality for the {@code /flight} command.
 */
public class FlightCommand {
    private static final Component NO_ELYTRA_FLIGHT_SELF = Component.literal("Cannot enable flight because you do not have an elytra equipped.");
    private static final Component FLIGHT_NOT_ALLOWED_SELF = Component.literal("You have taken damage within the last 20 seconds, cannot enable flight!");

    /**
     * @return Registers the flight command.
     */
    public static LiteralArgumentBuilder<CommandSourceStack> getFlightCommand() {
        return Commands.literal("flight")
                .requires(Commands.hasPermission(getPermissionLevel(options().permissions.getId())))
                .executes(
                        context -> execute(
                                context.getSource(),
                                List.of(context.getSource().getPlayerOrException()),
                                !context.getSource().getPlayerOrException().getAbilities().mayfly
                        )
                )
                .then(
                        Commands.argument("target", EntityArgument.player())
                                .requires(Commands.hasPermission(Commands.LEVEL_ADMINS))
                                .executes(
                                        context -> execute(
                                                context.getSource(),
                                                List.of(context.getSource().getPlayerOrException()),
                                                !context.getSource().getPlayerOrException().getAbilities().mayfly
                                        )
                                )
                )
                .then(
                        Commands.literal("enable")
                                .executes(context -> execute(
                                        context.getSource(),
                                        List.of(context.getSource().getPlayerOrException()),
                                        true
                                ))
                                .then(
                                        Commands.argument("target", EntityArgument.players())
                                                .requires(Commands.hasPermission(Commands.LEVEL_ADMINS))
                                                .executes(
                                                        context -> execute(
                                                                context.getSource(),
                                                                EntityArgument.getPlayers(context, "target"), true
                                                        )
                                                )
                                )
                )
                .then(
                        Commands.literal("disable")
                                .executes(context -> execute(
                                        context.getSource(),
                                        List.of(context.getSource().getPlayerOrException()),
                                        false
                                ))
                                .then(
                                        Commands.argument("target", EntityArgument.players())
                                                .requires(Commands.hasPermission(Commands.LEVEL_ADMINS))
                                                .executes(
                                                        context -> execute(
                                                                context.getSource(), EntityArgument.getPlayers(context, "target"), false
                                                        )
                                                )
                                )
                );
    }

    /**
     * Sends messages to chat based on the player's flight ability.
     */
    private static void sendFeedback(CommandSourceStack source, ServerPlayer player, boolean success, boolean value) {
        if (source.getEntity() == player) {
            if (success) {
                source.sendSuccess(() -> flightChangedSelf(value), true);
            } else if (!isFlyingAllowed(player)) {
                source.sendSuccess(() -> FLIGHT_NOT_ALLOWED_SELF, true);
                ((PlayerAbilitiesExtension)player).setWantToFlyAgain(true);
            } else if (!hasElytra(player)) {
                source.sendSuccess(() -> NO_ELYTRA_FLIGHT_SELF, true);
            } else {
                source.sendSuccess(() -> cannotChangeFlightSelf(player), true);
            }
        } else {
            if (source.getLevel().getGameRules().get(GameRules.SEND_COMMAND_FEEDBACK) && success) {
                player.sendSystemMessage(flightForceChanged(value));
            }

            if (success) {
                source.sendSuccess(() -> flightChangedOther(player, value), true);
            } else if (!isFlyingAllowed(player)) {
                source.sendSuccess(() -> flightNotAllowedOther(player), true);
            } else if (!hasElytra(player)) {
                source.sendSuccess(() -> noElytraFlightOther(player), true);
            } else {
                source.sendSuccess(() -> cannotChangeFlightOther(player), true);
            }
        }
    }

    /**
     * Enables/disables fly for certain players.
     */
    private static int execute(CommandSourceStack source, Collection<ServerPlayer> targets, boolean value) {
        int i = 0;

        for (ServerPlayer player : targets) {
            if (!(player.gameMode.getGameModeForPlayer().isCreative() || player.gameMode.getGameModeForPlayer() == GameType.SPECTATOR)) {
                if (player.getAbilities().mayfly != value) {
                    player.getAbilities().mayfly = value;
                    ((PlayerAbilitiesExtension)player).setEverEnabledFlight(value);
                    if (player.getAbilities().flying && !player.getAbilities().mayfly) {
                        player.getAbilities().flying = false;
                    }
                    player.onUpdateAbilities();
                    sendFeedback(source, player, hasElytra(player) && isFlyingAllowed(player), value);
                    i++;
                } else {
                    if (source.getEntity() == player) {
                        source.sendSuccess(() -> flightSameSelf(value), true);
                    } else {
                        source.sendSuccess(() -> flightSameOther(player, value), true);
                    }
                }
            } else {
                sendFeedback(source, player, false, value);
            }
        }

        return i;
    }

    private static Component flightChangedSelf(boolean value) {
        return blUppercase(value).copy().append(Component.literal(" flight.").withStyle(ChatFormatting.WHITE));
    }

    private static Component flightChangedOther(ServerPlayer player, boolean value) {
        return blUppercase(value).copy().append(Component.literal(" flight for ").copy().withStyle(ChatFormatting.WHITE).append(getPlayerName(player)));
    }

    private static Component flightForceChanged(boolean value) {
        return Component.literal("Your flight has been ").copy().append(blLowercase(value)).copy().append(Component.literal(".").withStyle(ChatFormatting.WHITE));
    }

    private static Component flightSameSelf(boolean value) {
        return Component.literal("Your flight is already ").copy().append(blLowercase(value).copy().append(Component.literal(".").withStyle(ChatFormatting.WHITE)));
    }

    private static Component flightSameOther(ServerPlayer player, boolean value) {
        return getPlayerName(player).copy().append("'s flight is already ").append(blLowercase(value).copy().append(Component.literal(".").withStyle(ChatFormatting.WHITE)));
    }

    private static Component cannotChangeFlightSelf(ServerPlayer player) {
        return Component.literal("Cannot change flight ability because your gamemode is ").append(getGameMode(player)).append(".");
    }

    private static Component cannotChangeFlightOther(ServerPlayer player) {
        return Component.literal("Couldn't change flight ability for ").append(getPlayerName(player)).append(" because their gamemode is " + getGameMode(player) + ".");
    }

    private static Component noElytraFlightOther(ServerPlayer player) {
        return Component.literal("Couldn't enable flight for ").append(getPlayerName(player)).append(" because they do not have an elytra equipped.");
    }

    private static Component flightNotAllowedOther(ServerPlayer player) {
        return Component.literal("Couldn't enable flight for ").append(getPlayerName(player)).append(" because they have taken damage within the last 20 seconds.");
    }
}