package net.dillon.survivalfly.command;

import net.dillon.survivalfly.main.SurvivalFly;
import net.dillon.survivalfly.option.ModOptions;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * A command to check the status of your flight ability.
 */
@Mod.EventBusSubscriber(modid = SurvivalFly.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FlightStatusCommand {

    /**
     * Registers the {@code /flightstatus} command.
     */
    @SubscribeEvent
    public static void register(RegisterCommandsEvent dispatcher) {
        dispatcher.getDispatcher().register(
                Commands.literal("flightstatus")
                        .requires(source -> source.hasPermission(ModOptions.PERMISSION_LEVEL.get().getId()))
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
                        )
        );
    }

    /**
     * Returns the feedback text based on if the command succeeded or not.
     */
    private static void sendSuccess(CommandSourceStack source, ServerPlayer player, boolean success) {
        if (source.getEntity() == player) {
            if (success) {
                source.sendSuccess(() -> Component.translatable("survivalfly.flight_status", SurvivalFly.statusText(player, true)), true);
            } else {
                source.sendSuccess(() -> Component.translatable("survivalfly.flight_status.flying_gamemode", player.gameMode.getGameModeForPlayer().getName()), true);
            }
        } else {
            if (success) {
                source.sendSuccess(() -> Component.translatable("survivalfly.flight_status.other", player.getDisplayName(), SurvivalFly.statusText(player, true)), true);
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