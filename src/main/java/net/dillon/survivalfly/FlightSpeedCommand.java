package net.dillon.survivalfly;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
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

    /**
     * Registers the {@code /flightcooldown} command.
     */
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("flightspeed")
                        .requires(source -> source.hasPermissionLevel(SurvivalFly.options().permissionLevel.getId()))
                        .then(
                                CommandManager.argument("speed", FloatArgumentType.floatArg(0.0F, 0.2F))
                                        .executes(context -> execute(
                                                context,
                                                List.of(context.getSource().getPlayerOrThrow()),
                                                FloatArgumentType.getFloat(context, "speed")
                                        ))
                                        .then(
                                                CommandManager.argument("target", EntityArgumentType.players())
                                                        .executes(context -> execute(
                                                                context,
                                                                EntityArgumentType.getPlayers(context, "target"),
                                                                FloatArgumentType.getFloat(context, "speed")
                                                        ))
                                        )
                        )

        );
    }

    /**
     * Sends messages to chat based on the player's flight speed.
     */
    private static void sendFeedback(ServerCommandSource source, ServerPlayerEntity player, boolean success, float speed) {
        if (source.getEntity() == player) {
            if (success) {
                source.sendFeedback(() -> Text.translatable("survivalfly.flight_speed_changed.self", speed), true);
            } else {
                source.sendFeedback(() -> Text.translatable("survivalfly.cannot_change_flight_speed.self"), true);
            }
        } else {
            if (source.getWorld().getGameRules().getBoolean(GameRules.SEND_COMMAND_FEEDBACK) && success) {
                player.sendMessage(Text.translatable("survivalfly.flight_speed_changed", speed));
            }

            if (success) {
                source.sendFeedback(() -> Text.translatable("survivalfly.flight_speed_changed.other", player.getDisplayName(), speed), true);
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
                sendFeedback(context.getSource(), player, true, speed);
                i++;
            } else {
                sendFeedback(context.getSource(), player, false, speed);
            }
        }

        return i;
    }
}