package net.dillon.survivalfly;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
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
     * Registers the flight status command.
     */
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("flightstatus")
                        .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(0))
                        .executes(
                                context -> execute(
                                        context, context.getSource().getPlayerOrThrow()
                                )
                        )
                        .then(
                                CommandManager.argument("target", EntityArgumentType.player())
                                        .executes(context -> execute(
                                                context, EntityArgumentType.getPlayer(context, "target")
                                        ))
                        )
        );
    }

    /**
     * Executes the command.
     */
    private static int execute(CommandContext<ServerCommandSource> context, ServerPlayerEntity target) {
        if (target.interactionManager.getGameMode().isCreative() || target.interactionManager.getGameMode() == GameMode.SPECTATOR) {
            if (context.getSource().getEntity() == target) {
                context.getSource().sendFeedback(() -> Text.translatable("survivalfly.flight_status.flying_gamemode", target.interactionManager.getGameMode().asString()), true);
            } else {
                context.getSource().sendFeedback(() -> Text.translatable("survivalfly.flight_status.flying_gamemode.other", target.getDisplayName(), target.interactionManager.getGameMode().asString()), true);
            }
            return 0;
        } else {
            if (context.getSource().getEntity() == target) {
                context.getSource().sendFeedback(() -> Text.translatable("survivalfly.flight_status", SurvivalFly.statusText(target, true)), true);
            } else {
                context.getSource().sendFeedback(() -> Text.translatable("survivalfly.flight_status.other", target.getDisplayName(), SurvivalFly.statusText(target, true)), true);
            }
            return 1;
        }
    }
}