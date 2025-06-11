package net.dillon.survivalfly;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.GameRules;

import java.util.Collection;
import java.util.List;

public class FlightSpeedCommand {
    private static final String SPEED_ARGUMENT_NAME = "speed (as percentage)";

    /**
     * Registers the {@code /flightcooldown} command.
     */
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("flightspeed")
                        .requires(source -> source.hasPermissionLevel(SurvivalFly.options().permissionLevel.getId()))
                        .then(
                                CommandManager.literal("set")
                                        .then(
                                                CommandManager.argument(SPEED_ARGUMENT_NAME, IntegerArgumentType.integer(0, 100))
                                                        .executes(context -> execute(
                                                                context,
                                                                List.of(context.getSource().getPlayerOrThrow()),
                                                                SurvivalFly.percentageAsDecimal(IntegerArgumentType.getInteger(context, SPEED_ARGUMENT_NAME))
                                                        ))
                                                        .then(
                                                                CommandManager.argument("target", EntityArgumentType.players())
                                                                        .executes(context -> execute(
                                                                                context,
                                                                                EntityArgumentType.getPlayers(context, "target"),
                                                                                SurvivalFly.percentageAsDecimal(IntegerArgumentType.getInteger(context, SPEED_ARGUMENT_NAME))
                                                                        ))
                                                        )
                                        )
                        )
                        .then(
                                CommandManager.literal("get")
                                        .executes(
                                                context -> getSpeed(
                                                        context,
                                                        context.getSource().getPlayerOrThrow()
                                                ))
                                        .then(
                                                CommandManager.argument("target", EntityArgumentType.player())
                                                        .executes(context -> getSpeed(
                                                                context,
                                                                EntityArgumentType.getPlayer(context, "target")
                                                        ))
                                        )
                        )
                        .then(
                                CommandManager.literal("reset")
                                        .executes(context -> execute(
                                                context,
                                                List.of(context.getSource().getPlayerOrThrow()),
                                                0.05F
                                        ))
                                        .then(
                                                CommandManager.argument("target", EntityArgumentType.players())
                                                        .executes(context -> execute(
                                                                context,
                                                                EntityArgumentType.getPlayers(context, "target"),
                                                                0.05F
                                                        ))
                                        )
                        )

        );
    }

    /**
     * Tells the players speed.
     */
    private static void tellSpeed(ServerCommandSource source, ServerPlayerEntity player, float speed) {
        if (source.getEntity() == player) {
            source.sendFeedback(() -> Text.translatable("survivalfly.flight_speed", speed).append("%."), true);
        } else {
            source.sendFeedback(() -> Text.translatable("survivalfly.flight_speed.other", player.getDisplayName(), speed).append("%."), true);
        }
    }

    /**
     * Gets the speed of each player.
     */
    private static int getSpeed(CommandContext<ServerCommandSource> context, ServerPlayerEntity target) {
        tellSpeed(context.getSource(), target, SurvivalFly.decimalAsPercentage(target.getAbilities().getFlySpeed()));
        return 0;
    }

    /**
     * Sends messages to chat based on the player's flight speed.
     */
    private static void sendFeedback(ServerCommandSource source, ServerPlayerEntity player, boolean success, float speed) {
        if (source.getEntity() == player) {
            if (success) {
                source.sendFeedback(() -> Text.translatable("survivalfly.flight_speed_changed.self", speed).append("%."), true);
            } else {
                source.sendFeedback(() -> Text.translatable("survivalfly.cannot_change_flight_speed.self"), true);
            }
        } else {
            if (source.getWorld().getGameRules().getBoolean(GameRules.SEND_COMMAND_FEEDBACK) && success) {
                player.sendMessage(Text.translatable("survivalfly.flight_speed_changed", speed).append("%."));
            }

            if (success) {
                source.sendFeedback(() -> Text.translatable("survivalfly.flight_speed_changed.other", player.getDisplayName(), speed).append("%."), true);
            } else {
                source.sendFeedback(() -> Text.translatable("survivalfly.cannot_change_flight_speed.other", player.getDisplayName(), player.interactionManager.getGameMode().asString()), true);
            }
        }
    }

    /**
     * Changes fly speed for player.
     */
    private static int execute(CommandContext<ServerCommandSource> context, Collection<ServerPlayerEntity> targets, float speed) {
        int i = 0;

        for (ServerPlayerEntity player : targets) {
            if (player.getAbilities().allowFlying) {
                player.getAbilities().setFlySpeed(speed);
                player.sendAbilitiesUpdate();
                sendFeedback(context.getSource(), player, true, SurvivalFly.decimalAsPercentage(speed));
                i++;
            } else {
                sendFeedback(context.getSource(), player, false, SurvivalFly.decimalAsPercentage(speed));
            }
        }

        return i;
    }
}