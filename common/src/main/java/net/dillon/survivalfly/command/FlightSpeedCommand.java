package net.dillon.survivalfly.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.gamerules.GameRules;

import java.util.Collection;
import java.util.List;

import static net.dillon.survivalfly.util.ModUtil.*;

public class FlightSpeedCommand {
    private static final String SPEED_ARGUMENT_NAME = "speed (as percentage)";

    /**
     * @return the {@code /flightspeed} command.
     */
    public static LiteralArgumentBuilder<CommandSourceStack> getFlightSpeedCommand() {
        return Commands.literal("flightspeed")
                .requires(Commands.hasPermission(getPermissionLevel(options().permissionLevel.getId())))
                .then(
                        Commands.literal("set")
                                .then(
                                        Commands.argument(SPEED_ARGUMENT_NAME, IntegerArgumentType.integer(0, 100))
                                                .executes(context -> execute(
                                                        context.getSource(),
                                                        List.of(context.getSource().getPlayerOrException()),
                                                        percentageAsDecimal(IntegerArgumentType.getInteger(context, SPEED_ARGUMENT_NAME))
                                                ))
                                                .then(
                                                        Commands.argument("target", EntityArgument.players())
                                                                .executes(context -> execute(
                                                                        context.getSource(),
                                                                        EntityArgument.getPlayers(context, "target"),
                                                                        percentageAsDecimal(IntegerArgumentType.getInteger(context, SPEED_ARGUMENT_NAME))
                                                                ))
                                                )
                                )
                )
                .then(
                        Commands.literal("get")
                                .executes(
                                        context -> getSpeed(
                                                context.getSource(),
                                                context.getSource().getPlayerOrException()
                                        ))
                                .then(
                                        Commands.argument("target", EntityArgument.player())
                                                .executes(context -> getSpeed(
                                                        context.getSource(),
                                                        EntityArgument.getPlayer(context, "target")
                                                ))
                                )
                )
                .then(
                        Commands.literal("reset")
                                .executes(context -> execute(
                                        context.getSource(),
                                        List.of(context.getSource().getPlayerOrException()),
                                        DEFAULT_FLIGHT_SPEED
                                ))
                                .then(
                                        Commands.argument("target", EntityArgument.players())
                                                .executes(context -> execute(
                                                        context.getSource(),
                                                        EntityArgument.getPlayers(context, "target"),
                                                        DEFAULT_FLIGHT_SPEED
                                                ))
                                )
                );
    }

    /**
     * Tells the players speed.
     */
    private static void tellSpeed(CommandSourceStack source, ServerPlayer player, float speed) {
        if (source.getEntity() == player) {
            source.sendSuccess(() -> Component.translatable("survivalfly.flight_speed", speed).append("%."), true);
        } else {
            source.sendSuccess(() -> Component.translatable("survivalfly.flight_speed.other", player.getDisplayName(), speed).append("%."), true);
        }
    }

    /**
     * Gets the speed of each player.
     */
    private static int getSpeed(CommandSourceStack context, ServerPlayer target) {
        tellSpeed(context, target, decimalAsPercentage(target.getAbilities().getFlyingSpeed()));
        return 0;
    }

    /**
     * Sends messages to chat based on the player's flight speed.
     */
    private static void sendSuccess(CommandSourceStack source, ServerPlayer player, boolean success, float speed) {
        if (source.getEntity() == player) {
            if (success) {
                source.sendSuccess(() -> Component.translatable("survivalfly.flight_speed_changed.self", speed).append("%."), true);
            } else {
                source.sendSuccess(() -> Component.translatable("survivalfly.cannot_change_flight_speed.self"), true);
            }
        } else {
            if (source.getLevel().getGameRules().get(GameRules.SEND_COMMAND_FEEDBACK) && success) {
                player.sendSystemMessage(Component.translatable("survivalfly.flight_speed_changed", speed).append("%."));
            }

            if (success) {
                source.sendSuccess(() -> Component.translatable("survivalfly.flight_speed_changed.other", player.getDisplayName(), speed).append("%."), true);
            } else {
                source.sendSuccess(() -> Component.translatable("survivalfly.cannot_change_flight_speed.other", player.getDisplayName(), player.gameMode.getGameModeForPlayer().getName()), true);
            }
        }
    }

    /**
     * Changes fly speed for player.
     */
    private static int execute(CommandSourceStack context, Collection<ServerPlayer> targets, float speed) {
        int i = 0;

        for (ServerPlayer player : targets) {
            if (player.getAbilities().mayfly) {
                player.getAbilities().setFlyingSpeed(speed);
                player.onUpdateAbilities();
                sendSuccess(context, player, true, decimalAsPercentage(speed));
                i++;
            } else {
                sendSuccess(context, player, false, decimalAsPercentage(speed));
            }
        }

        return i;
    }
}