package net.dillon.survivalfly;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameMode;
import net.minecraft.world.GameRules;

import java.util.Collection;
import java.util.Collections;

/**
 * The functionality for the {@code /fly} command.
 */
public class FlyCommand {

    /**
     * Registers the flight command.
     */
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("fly")
                        .requires(source -> source.hasPermissionLevel(SurvivalFly.options().permissionLevel.getId()))
                        .executes(
                                context -> execute(
                                        context, Collections.singleton(context.getSource().getPlayerOrThrow())
                                )
                        )
                        .then(
                                CommandManager.argument("target", EntityArgumentType.players())
                                        .executes(
                                                context -> execute(
                                                        context, EntityArgumentType.getPlayers(context, "target")
                                                )
                                        )
                        )
        );
    }

    /**
     * Sends messages to chat based on the player's flight ability.
     */
    private static void sendFeedback(ServerCommandSource source, ServerPlayerEntity player, boolean bl) {
        Text text = player.getAbilities().allowFlying ? Text.translatable("survivalfly.enabled").formatted(Formatting.GREEN) : Text.translatable("survivalfly.disabled").formatted(Formatting.RED);
        if (source.getEntity() == player) {
            if (!bl) {
                source.sendFeedback(() -> Text.translatable("survivalfly.flight_changed.self", text), true);
            } else {
                source.sendFeedback(() -> Text.translatable("survivalfly.cannot_change_flight.self", player.getGameMode().getId()), true);
            }
        } else {
            if (source.getWorld().getGameRules().getBoolean(GameRules.SEND_COMMAND_FEEDBACK) && !bl) {
                player.sendMessage(Text.translatable("survivalfly.flight_changed", text));
            }

            if (!bl) {
                source.sendFeedback(() -> Text.translatable("survivalfly.flight_changed.other", text, player.getDisplayName()), true);
            } else {
                source.sendFeedback(() -> Text.translatable("survivalfly.cannot_change_flight.other", player.getDisplayName(), player.getGameMode().getId()), true);
            }
        }
    }

    /**
     * Enables/disables fly for certain players.
     */
    private static int execute(CommandContext<ServerCommandSource> context, Collection<ServerPlayerEntity> targets) {
        int i = 0;

        for (ServerPlayerEntity player : targets) {
            if (!(player.getGameMode().isCreative() || player.getGameMode() == GameMode.SPECTATOR)) {
                player.getAbilities().allowFlying = !player.getAbilities().allowFlying;
                if (player.getAbilities().flying && !player.getAbilities().allowFlying) {
                    player.getAbilities().flying = false;
                }
                player.sendAbilitiesUpdate();
                sendFeedback(context.getSource(), player, false);
            } else {
                sendFeedback(context.getSource(), player, true);
            }
            i++;
        }

        return i;
    }
}