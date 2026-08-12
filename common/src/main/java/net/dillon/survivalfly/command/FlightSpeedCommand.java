package net.dillon.survivalfly.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.dillon.survivalfly.permission.Nodes;
import net.dillon.survivalfly.permission.PermissionUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.gamerules.GameRules;

import java.util.Collection;
import java.util.List;

import static net.dillon.survivalfly.helper.ModConstants.DEFAULT_FLIGHT_SPEED;
import static net.dillon.survivalfly.helper.ModHelper.*;
import static net.dillon.survivalfly.util.ModTexts.getPlayerName;

public class FlightSpeedCommand {
    private static final String SPEED_ARGUMENT_NAME = "speed (as percentage)";
    private static final Component CANNOT_CHANGE_FLIGHT_SPEED_SELF = Component.literal("Cannot change flight speed because you don't have flying abilities.");
    private static final Component NO_ELYTRA_FLIGHT_SPEED_SELF = Component.literal("Cannot change flight speed because you don't have an elytra equipped.");
    private static final Component FLIGHT_SPEED_NOT_ALLOWED_SELF = Component.literal("Cannot change flight speed because you have taken damage within the last 20 seconds.");

    /**
     * @return the {@code /flightspeed} command.
     */
    public static LiteralArgumentBuilder<CommandSourceStack> getFlightSpeedCommand() {
        return Commands.literal("flightspeed")
                .requires(commandSourceStack -> PermissionUtil.hasPermissionDefaultFallbackOrCommandSource(commandSourceStack, commandSourceStack.getPlayer(), Nodes.FLIGHT_SPEED))
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
                                                                .requires(PermissionUtil::hasAdminPermissionsOrCommandSource)
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
                                                .requires(PermissionUtil::hasAdminPermissionsOrCommandSource)
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
                                                .requires(PermissionUtil::hasAdminPermissionsOrCommandSource)
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
            source.sendSuccess(() -> getFlightSpeedSelf(speed), true);
        } else {
            source.sendSuccess(() -> getFlightSpeedOther(player, speed), true);
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
                source.sendSuccess(() -> flightSpeedChangedSelf(speed), true);
            } else if (!isFlyingAllowed(player)) {
                source.sendSuccess(() -> FLIGHT_SPEED_NOT_ALLOWED_SELF, true);
            } else if (!hasElytra(player)) {
                source.sendSuccess(() -> NO_ELYTRA_FLIGHT_SPEED_SELF, true);
            } else {
                source.sendSuccess(() -> CANNOT_CHANGE_FLIGHT_SPEED_SELF, true);
            }
        } else {
            if (source.getLevel().getGameRules().get(GameRules.SEND_COMMAND_FEEDBACK) && success) {
                player.sendSystemMessage(flightSpeedForceChanged(speed));
            }

            if (success) {
                source.sendSuccess(() -> flightSpeedChangedOther(player, speed), true);
            } else if (!isFlyingAllowed(player)) {
                source.sendSuccess(() -> flightSpeedNotAllowedOther(player), true);
            } else if (!hasElytra(player)) {
                source.sendSuccess(() -> noElytraFlightSpeedOther(player), true);
            } else {
                source.sendSuccess(() -> cannotChangeFlightSpeedOther(player), true);
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
                sendSuccess(context, player, hasElytra(player) && isFlyingAllowed(player), decimalAsPercentage(speed));
                i++;
            } else {
                sendSuccess(context, player, false, decimalAsPercentage(speed));
            }
        }

        return i;
    }

    private static Component getFlightSpeedSelf(float value) {
        return Component.literal("Your flight speed is ").append(value + "%.");
    }

    private static Component getFlightSpeedOther(ServerPlayer player, float value) {
        return getPlayerName(player).copy().append("'s flight speed is " + value + "%.");
    }

    private static Component flightSpeedChangedSelf(float value) {
        return Component.literal("Set flight speed to ").append(value + "%.");
    }

    private static Component flightSpeedForceChanged(float value) {
        return Component.literal("Your flight speed has been set to ").append(value + "%.");
    }

    private static Component flightSpeedChangedOther(ServerPlayer player, float value) {
        return Component.literal("Set ").append(getPlayerName(player)).append("'s flight speed to ").append(value + "%.");
    }

    private static Component cannotChangeFlightSpeedOther(ServerPlayer player) {
        return Component.literal("Cannot change flight speed for ").append(getPlayerName(player)).append(" because they do not have flying abilities.");
    }

    private static Component noElytraFlightSpeedOther(ServerPlayer player) {
        return Component.literal("Cannot change flight speed for ").append(getPlayerName(player)).append(" because they do not have an elytra equipped.");
    }

    private static Component flightSpeedNotAllowedOther(ServerPlayer player) {
        return Component.literal("Cannot change flight speed for ").append(getPlayerName(player)).append(" because they have taken damage within the last 20 seconds.");
    }
}