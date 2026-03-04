package net.dillon.survivalfly.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;

import static net.dillon.survivalfly.util.ModUtil.*;

/**
 * A command to check the status of your flight ability.
 */
public class FlightStatusCommand {

    /**
     * @return the {@code /flightstatus} command.
     */
    public static LiteralArgumentBuilder<CommandSourceStack> getFlightStatusCommand() {
        return Commands.literal("flightstatus")
                .requires(Commands.hasPermission(getPermissionLevel(options().permissionLevel.getId())))
                .executes(
                        context -> execute(
                                context.getSource(),
                                context.getSource().getPlayerOrException()
                        )
                )
                .then(
                        Commands.argument("target", EntityArgument.player())
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
                source.sendSuccess(() -> Component.translatable("survivalfly.flight_status", statusText(player, true)), true);
            } else {
                source.sendSuccess(() -> Component.translatable("survivalfly.flight_status.flying_gamemode", player.gameMode.getGameModeForPlayer().getName()), true);
            }
        } else {
            if (success) {
                source.sendSuccess(() -> Component.translatable("survivalfly.flight_status.other", player.getDisplayName(), statusText(player, true)), true);
            } else {
                source.sendSuccess(() -> Component.translatable("survivalfly.flight_status.flying_gamemode.other", player.getDisplayName(), player.gameMode.getGameModeForPlayer().getName()), true);
            }
        }
    }

    /**
     * Executes the command.
     */
    private static int execute(CommandSourceStack context, ServerPlayer target) {
        if (target.gameMode.getGameModeForPlayer().isCreative() || target.gameMode.getGameModeForPlayer() == GameType.SPECTATOR) {
            sendSuccess(context, target, false);
            return 0;
        } else {
            sendSuccess(context, target, true);
            return 1;
        }
    }
}