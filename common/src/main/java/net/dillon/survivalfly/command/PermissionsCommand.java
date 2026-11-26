package net.dillon.survivalfly.command;

import com.mojang.brigadier.CommandDispatcher;
import net.dillon.survivalfly.option.Permissions;
import net.minecraft.commands.CommandPermissionLevels;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.StringArgument;
import net.minecraft.commands.suggestions.SuggestionProviders;
import net.minecraft.network.chat.Component;

import static net.dillon.survivalfly.option.OptionInstances.updateCommon;

/**
 * The functionality for the {@code /permissions} command.
 */
public class PermissionsCommand {

    /**
     * @return the permission command text to display.
     */
    private static Component changedPermission(Permissions permission) {
        Component perm;
        switch (permission) {
            case MODERATOR -> perm = Component.literal("Players that bypass spawn protection");
            case GAMEMASTER ->
                    perm = Component.literal("Players that have higher authority, such as permission to use command blocks, change gamemode, difficulty, etc.");
            case ADMIN -> perm = Component.literal("Only operators");
            default -> perm = Component.literal("Anyone");
        }
        return perm.copy().append(" can now use /flight.");
    }

    /**
     * Registers the {@code elytraflight} command.
     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("permissions")
                        .requires(Commands.hasPermission(CommandPermissionLevels.LEVEL_ADMINS))
                        .then(
                                Commands.literal("set")
                                        .then(
                                                Commands.argument("permission", StringArgument.word())
                                                        .suggests(SuggestionProviders.serverOnly((server, context, builder) -> {
                                                            for (Permissions permission : Permissions.values()) {
                                                                builder.suggest(permission.getSerializedName());
                                                            }
                                                            return builder.buildFuture();
                                                        }))
                                                        .executes(context -> {
                                                                    String input = StringArgument.getString(context, "permission");

                                                                    try {
                                                                        Permissions permission = Permissions.byName(input);
                                                                        updateCommon(common -> {
                                                                            common.permissions = permission;
                                                                        });
                                                                        context.getSource().sendSystemMessage(changedPermission(permission));
                                                                        return permission.getOrdinal();
                                                                    } catch (NullPointerException |
                                                                             IllegalArgumentException o) {
                                                                        context.getSource().sendFailure(Component.literal("Invalid permission: " + input));
                                                                        return 0;
                                                                    }
                                                                }
                                                        )
                                        )
                        )
        );
    }
}