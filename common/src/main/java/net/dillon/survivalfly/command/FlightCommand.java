package net.dillon.survivalfly.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.dillon.survivalfly.util.PlayerAbilitiesExtension;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.gamerules.GameRules;

import java.util.Collection;
import java.util.List;

import static net.dillon.survivalfly.util.ModUtil.*;

/**
 * The functionality for the {@code /flight} command.
 */
public class FlightCommand {

    /**
     * @return Registers the flight command.
     */
    public static LiteralArgumentBuilder<CommandSourceStack> getFlightCommand() {
        return Commands.literal("flight")
                .requires(Commands.hasPermission(getPermissionLevel(options().permissionLevel.getId())))
                .executes(
                        context -> execute(
                                context.getSource(),
                                List.of(context.getSource().getPlayerOrException()),
                                !context.getSource().getPlayerOrException().getAbilities().mayfly
                        )
                )
                .then(
                        Commands.argument("target", EntityArgument.player())
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
    private static void sendFeedback(CommandSourceStack source, ServerPlayer player, boolean success) {
        if (source.getEntity() == player) {
            if (success) {
                source.sendSuccess(() -> Component.translatable("survivalfly.flight_changed.self", statusText(player, false)), true);
            } else {
                source.sendSuccess(() -> Component.translatable("survivalfly.cannot_change_flight.self", player.gameMode.getGameModeForPlayer().getName()), true);
            }
        } else {
            if (source.getLevel().getGameRules().get(GameRules.SEND_COMMAND_FEEDBACK) && success) {
                player.sendSystemMessage(Component.translatable("survivalfly.flight_changed", statusText(player, true)));
            }

            if (success) {
                source.sendSuccess(() -> Component.translatable("survivalfly.flight_changed.other", statusText(player, false), player.getDisplayName()), true);
            } else {
                source.sendSuccess(() -> Component.translatable("survivalfly.cannot_change_flight.other", player.getDisplayName(), player.gameMode.getGameModeForPlayer().getName()), true);
            }
        }
    }

    /**
     * Enables/disables fly for certain players.
     */
    private static int execute(CommandSourceStack context, Collection<ServerPlayer> targets, boolean value) {
        int i = 0;

        for (ServerPlayer player : targets) {
            if (!(player.gameMode.getGameModeForPlayer().isCreative() || player.gameMode.getGameModeForPlayer() == GameType.SPECTATOR)) {
                if (player.getAbilities().mayfly != value) {
                    player.getAbilities().mayfly = value;
                    ((PlayerAbilitiesExtension)player.getAbilities()).setEverEnabledFlight(value);
                    if (player.getAbilities().flying && !player.getAbilities().mayfly) {
                        player.getAbilities().flying = false;
                    }
                    player.onUpdateAbilities();
                    sendFeedback(context, player, true);
                    i++;
                } else {
                    if (context.getEntity() == player) {
                        context.sendSuccess(() -> Component.translatable("survivalfly.flight_is_the_same", statusText(player, true)), true);
                    } else {
                        context.sendSuccess(() -> Component.translatable("survivalfly.flight_is_the_same.other", player.getDisplayName(), statusText(player, true)), true);
                    }
                }
            } else {
                sendFeedback(context, player, false);
            }
        }

        return i;
    }
}