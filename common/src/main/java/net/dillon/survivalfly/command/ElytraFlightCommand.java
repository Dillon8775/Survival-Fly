package net.dillon.survivalfly.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.blay09.mods.balm.api.Balm;
import net.dillon.survivalfly.option.ModOptions;
import net.dillon.survivalfly.permission.PermissionUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import static net.dillon.survivalfly.util.ModTexts.DISABLED_UPPERCASE;
import static net.dillon.survivalfly.util.ModTexts.ENABLED_UPPERCASE;
import static net.dillon.survivalfly.util.ModUtil.options;

/**
 * The functionality for the {@code /elytraflight} command.
 */
public class ElytraFlightCommand {
    private static final Component ELYTRA_FLIGHT_ENABLED = ENABLED_UPPERCASE.copy().append(Component.literal(" elytra flight.").withStyle(ChatFormatting.WHITE));
    private static final Component ELYTRA_FLIGHT_DISABLED = DISABLED_UPPERCASE.copy().append(Component.literal(" elytra flight.").withStyle(ChatFormatting.WHITE));
    private static final Component ELYTRA_FLIGHT_DESC = Component.literal("Players must be wearing an elytra to use /flight.");

    /**
     * @return Registers the elytraflight command.
     */
    public static LiteralArgumentBuilder<CommandSourceStack> getElytraFlightCommand() {
        return Commands.literal("elytraflight")
                .requires(PermissionUtil::hasAdminPermissions)
                .executes(
                        context -> {
                            Balm.getConfig().updateLocalConfig(ModOptions.class, config -> {
                                options().elytraFlight = !options().elytraFlight;
                            });
                            if (options().elytraFlight) {
                                context.getSource().sendSystemMessage(ELYTRA_FLIGHT_ENABLED);
                                context.getSource().sendSystemMessage(ELYTRA_FLIGHT_DESC);
                            } else {
                                context.getSource().sendSystemMessage(ELYTRA_FLIGHT_DISABLED);
                            }
                            return 0;
                        });
    }
}