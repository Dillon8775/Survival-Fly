package net.dillon.survivalfly.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.dillon.survivalfly.option.ModOptions;
import net.dillon.survivalfly.permission.PermissionUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import static net.dillon.survivalfly.util.ModTexts.DISABLED_UPPERCASE;
import static net.dillon.survivalfly.util.ModTexts.ENABLED_UPPERCASE;
import static net.dillon.survivalfly.util.ModUtil.options;
import static net.dillon.survivalfly.util.ModUtil.sendSourceMessage;

/**
 * The functionality for the {@code /friendlyflight} command.
 */
public class FriendlyFlightCommand {
    private static final Component FRIENDLY_FLIGHT_ENABLED = ENABLED_UPPERCASE.copy().append(Component.literal(" friendly flight.").withStyle(ChatFormatting.WHITE));
    private static final Component FRIENDLY_FLIGHT_DISABLED = DISABLED_UPPERCASE.copy().append(Component.literal(" friendly flight.").withStyle(ChatFormatting.WHITE));
    private static final Component FRIENDLY_FLIGHT_DESC = Component.literal("If players take damage, their flight is disabled for 20 seconds.");

    /**
     * @return Registers the friendlyflight command.
     */
    public static LiteralArgumentBuilder<CommandSourceStack> getFriendlyFlightCommand() {
        return Commands.literal("friendlyflight")
                .requires(PermissionUtil::hasAdminPermissions)
                .executes(
                        context -> {
                            options().friendlyFlight = !options().friendlyFlight;
                            ModOptions.saveConfig();
                            if (options().friendlyFlight) {
                                sendSourceMessage(context, FRIENDLY_FLIGHT_ENABLED);
                                sendSourceMessage(context, FRIENDLY_FLIGHT_DESC);
                            } else {
                                sendSourceMessage(context, FRIENDLY_FLIGHT_DISABLED);
                            }
                            return 0;
                        });
    }
}