package net.dillon.survivalfly.command;

import net.dillon.survivalfly.main.SurvivalFly;
import net.dillon.survivalfly.option.ModOptions;
import net.dillon.survivalfly.util.PlayerAbilitiesExtension;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;
import java.util.List;

/**
 * The functionality for the {@code /flight} command.
 */
@Mod.EventBusSubscriber(modid = SurvivalFly.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FlightCommand {

    /**
     * Registers the flight command.
     */
    @SubscribeEvent
    public static void register(RegisterCommandsEvent dispatcher) {
        dispatcher.getDispatcher().register(
                Commands.literal("flight")
                        .requires(source -> source.hasPermission(ModOptions.PERMISSION_LEVEL.get().getId()))
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
                        )
        );
    }

    /**
     * Sends messages to chat based on the player's flight ability.
     */
    private static void sendFeedback(CommandSourceStack source, ServerPlayer player, boolean success) {
        if (source.getEntity() == player) {
            if (success) {
                source.sendSuccess(() -> Component.translatable("survivalfly.flight_changed.self", SurvivalFly.statusText(player, false)), true);
            } else {
                source.sendSuccess(() -> Component.translatable("survivalfly.cannot_change_flight.self", player.gameMode.getGameModeForPlayer().getName()), true);
            }
        } else {
            if (source.getLevel().getGameRules().getBoolean(GameRules.RULE_SENDCOMMANDFEEDBACK) && success) {
                player.sendSystemMessage(Component.translatable("survivalfly.flight_changed", SurvivalFly.statusText(player, true)));
            }

            if (success) {
                source.sendSuccess(() -> Component.translatable("survivalfly.flight_changed.other", SurvivalFly.statusText(player, false), player.getDisplayName()), true);
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
                        context.sendSuccess(() -> Component.translatable("survivalfly.flight_is_the_same", SurvivalFly.statusText(player, true)), true);
                    } else {
                        context.sendSuccess(() -> Component.translatable("survivalfly.flight_is_the_same.other", player.getDisplayName(), SurvivalFly.statusText(player, true)), true);
                    }
                }
            } else {
                sendFeedback(context, player, false);
            }
        }

        return i;
    }
}