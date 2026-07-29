package net.dillon.survivalfly.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.dillon.dillonlib.util.SimplePermissions;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

/**
 * The functionality for the {@code /survivalfly} command.
 */
public class SurvivalFlyCommand {

    /**
     * @return Registers the survivalfly command.
     */
    public static LiteralArgumentBuilder<CommandSourceStack> getHelpCommand() {
        return Commands.literal("survivalfly")
                .requires(SimplePermissions::all)
                .executes(
                        context -> {
                            context.getSource().sendSystemMessage(getMessage());
                            return 0;
                        });
    }

    /**
     * @return A message on how to use all Survival Fly commands!
     */
    private static Component getMessage() {
        Component header = Component.literal("§f§l=== Survival Fly Commands ===");

        Component ownerOnlyCommands = Component.literal("§4§l=== Owner Only Commands ===");

        Component permissionsCommand = Component.literal("§4§l/permissions§r§f - Changes the permission level in which other player's can use §lSurvival Fly §r§fcommands.");

        Component operatorOnlyCommands = Component.literal("§c§l=== Operator Only Commands ===");

        Component flightExhaustionCommand = Component.literal("§c§l/flightexhaustion§r§f - Makes players hungry when flying, and lose more depending on their flight speed.");
        Component elytraFlightCommand = Component.literal("§c§l/elytraflight§r§f - Requires players to wear an elytra to use /flight. The elytra's durability is decremented depending on the player's flight speed.");
        Component friendlyFlightCommand = Component.literal("§c§l/friendlyflight§r§f - Immediately disables player flight when they take damage for 20 seconds.");

        Component commandsForEveryone = Component.literal("§a§l === Commands For Everyone ===");

        Component flightCommand = Component.literal("§6§l/flight§r§f - Enables/disables flight.");
        Component flightCommandUsages = Component.literal("     §bCommand Usage:\n     §r§o/flight {enable/disable} {player}§r\n     You can only change the flight of others if you are an operator.");

        Component flightStatusCommand = Component.literal("§6§l/flightstatus§r§f - Displays your flight status.");
        Component flightStatusCommandUsages = Component.literal("     §bCommand Usage: \n     §r§o/flightstatus {player}§r\n     You can only see the flight status of others if you are an operator.");

        Component flightSpeedCommand = Component.literal("§6§l/flightspeed§r§f - Changes your flight speed.\n§aYou can also use ALT + SCROLL to change your flight speed §o(only if you have the mod installed on your client)!");
        Component flightSpeedCommandUsages = Component.literal("     §bCommand Usage: \n     §r§o/flightspeed {set/get/reset} {player}§r\n     You can only change the flight speed of others if you are an operator.");

        return header.copy().append("\n\n")
                .append(ownerOnlyCommands)
                .append("\n").append(permissionsCommand)
                .append("\n\n").append(operatorOnlyCommands)
                .append("\n").append(flightExhaustionCommand)
                .append("\n").append(elytraFlightCommand)
                .append("\n").append(friendlyFlightCommand)
                .append("\n\n").append(commandsForEveryone)
                .append("\n\n").append(flightCommand)
                .append("\n").append(flightCommandUsages)
                .append("\n\n").append(flightStatusCommand)
                .append("\n").append(flightStatusCommandUsages)
                .append("\n\n").append(flightSpeedCommand)
                .append("\n").append(flightSpeedCommandUsages);
    }
}