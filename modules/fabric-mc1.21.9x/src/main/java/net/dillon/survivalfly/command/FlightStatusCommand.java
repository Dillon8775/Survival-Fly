package net.dillon.survivalfly.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.dillon.survivalfly.main.SurvivalFly;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;

/**
 * A command to check the status of your flight ability.
 */
public class FlightStatusCommand {

    /**
     * Registers the {@code /flightstatus} command.
     */
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("flightstatus")
                        .requires(source -> source.hasPermissionLevel(SurvivalFly.options().permissionLevel.getId()))
                        .executes(
                                context -> execute(
                                        context,
                                        context.getSource().getPlayerOrThrow()
                                )
                        )
                        .then(
                                CommandManager.argument("target", EntityArgumentType.player())
                                        .executes(context -> execute(
                                                context,
                                                EntityArgumentType.getPlayer(context, "target")
                                        ))
                        )
        );
    }

    /**
     * Returns the feedback text based on if the command succeeded or not.
     */
    private static void sendFeedback(ServerCommandSource source, ServerPlayerEntity player, boolean success) {
        if (source.getEntity() == player) {
            if (success) {
                source.sendFeedback(() -> Text.translatable("survivalfly.flight_status", SurvivalFly.statusText(player, true)), true);
            } else {
                source.sendFeedback(() -> Text.translatable("survivalfly.flight_status.flying_gamemode", player.interactionManager.getGameMode().asString()), true);
            }
        } else {
            if (success) {
                source.sendFeedback(() -> Text.translatable("survivalfly.flight_status.other", player.getDisplayName(), SurvivalFly.statusText(player, true)), true);
            } else {
                source.sendFeedback(() -> Text.translatable("survivalfly.flight_status.flying_gamemode.other", player.getDisplayName(), player.interactionManager.getGameMode().asString()), true);
            }
        }
    }

    /**
     * Executes the command.
     */
    private static int execute(CommandContext<ServerCommandSource> context, ServerPlayerEntity target) {
        if (target.interactionManager.getGameMode().isCreative() || target.interactionManager.getGameMode() == GameMode.SPECTATOR) {
            sendFeedback(context.getSource(), target, false);
            return 0;
        } else {
            sendFeedback(context.getSource(), target, true);
            return 1;
        }
    }
}