package net.dillon.survivalfly.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.dillon.survivalfly.main.SurvivalFly;
import net.dillon.survivalfly.util.PlayerAbilitiesExtension;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;
import net.minecraft.world.rule.GameRules;

import java.util.Collection;
import java.util.List;

import static net.dillon.survivalfly.main.SurvivalFly.getPermissionLevel;

/**
 * The functionality for the {@code /flight} command.
 */
public class FlightCommand {

    /**
     * Registers the flight command.
     */
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("flight")
                        .requires(CommandManager.requirePermissionLevel(getPermissionLevel(SurvivalFly.options().permissionLevel.getOrdinal())))
                        .executes(
                                context -> execute(
                                        context,
                                        List.of(context.getSource().getPlayerOrThrow()),
                                        !context.getSource().getPlayerOrThrow().getAbilities().allowFlying
                                )
                        )
                        .then(
                                CommandManager.argument("target", EntityArgumentType.player())
                                        .executes(
                                                context -> execute(
                                                        context,
                                                        List.of(context.getSource().getPlayerOrThrow()),
                                                        !context.getSource().getPlayerOrThrow().getAbilities().allowFlying
                                                )
                                        )
                        )
                        .then(
                                CommandManager.literal("enable")
                                        .executes(context -> execute(
                                                context,
                                                List.of(context.getSource().getPlayerOrThrow()),
                                                true
                                        ))
                                        .then(
                                                CommandManager.argument("target", EntityArgumentType.players())
                                                        .executes(
                                                                context -> execute(
                                                                        context,
                                                                        EntityArgumentType.getPlayers(context, "target"), true
                                                                )
                                                        )
                                        )
                        )
                        .then(
                                CommandManager.literal("disable")
                                        .executes(context -> execute(
                                                context,
                                                List.of(context.getSource().getPlayerOrThrow()),
                                                false
                                        ))
                                        .then(
                                                CommandManager.argument("target", EntityArgumentType.players())
                                                        .executes(
                                                                context -> execute(
                                                                        context, EntityArgumentType.getPlayers(context, "target"), false
                                                                )
                                                        )
                                        )
                        )
        );
    }

    /**
     * Sends messages to chat based on the player's flight ability.
     */
    private static void sendFeedback(ServerCommandSource source, ServerPlayerEntity player, boolean success) {
        if (source.getEntity() == player) {
            if (success) {
                source.sendFeedback(() -> Text.translatable("survivalfly.flight_changed.self", SurvivalFly.statusText(player, false)), true);
            } else {
                source.sendFeedback(() -> Text.translatable("survivalfly.cannot_change_flight.self", player.interactionManager.getGameMode().asString()), true);
            }
        } else {
            if (source.getWorld().getGameRules().getValue(GameRules.SEND_COMMAND_FEEDBACK) && success) {
                player.sendMessage(Text.translatable("survivalfly.flight_changed", SurvivalFly.statusText(player, true)));
            }

            if (success) {
                source.sendFeedback(() -> Text.translatable("survivalfly.flight_changed.other", SurvivalFly.statusText(player, false), player.getDisplayName()), true);
            } else {
                source.sendFeedback(() -> Text.translatable("survivalfly.cannot_change_flight.other", player.getDisplayName(), player.interactionManager.getGameMode().asString()), true);
            }
        }
    }

    /**
     * Enables/disables fly for certain players.
     */
    private static int execute(CommandContext<ServerCommandSource> context, Collection<ServerPlayerEntity> targets, boolean value) {
        int i = 0;

        for (ServerPlayerEntity player : targets) {
            if (!(player.interactionManager.getGameMode().isCreative() || player.interactionManager.getGameMode() == GameMode.SPECTATOR)) {
                if (player.getAbilities().allowFlying != value) {
                    player.getAbilities().allowFlying = value;
                    ((PlayerAbilitiesExtension)player.getAbilities()).setEverEnabledFlight(value);
                    if (player.getAbilities().flying && !player.getAbilities().allowFlying) {
                        player.getAbilities().flying = false;
                    }
                    player.sendAbilitiesUpdate();
                    sendFeedback(context.getSource(), player, true);
                    i++;
                } else {
                    if (context.getSource().getEntity() == player) {
                        context.getSource().sendFeedback(() -> Text.translatable("survivalfly.flight_is_the_same", SurvivalFly.statusText(player, true)), true);
                    } else {
                        context.getSource().sendFeedback(() -> Text.translatable("survivalfly.flight_is_the_same.other", player.getDisplayName(), SurvivalFly.statusText(player, true)), true);
                    }
                }
            } else {
                sendFeedback(context.getSource(), player, false);
            }
        }

        return i;
    }
}