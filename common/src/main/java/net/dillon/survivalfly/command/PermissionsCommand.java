package net.dillon.survivalfly.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.blay09.mods.balm.Balm;
import net.dillon.dillonlib.util.SimplePermissions;
import net.dillon.survivalfly.option.ModOptions;
import net.dillon.survivalfly.option.Permissions;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

/**
 * The functionality for the {@code /permissions} command.
 */
public class PermissionsCommand {

    /**
     * @return Registers the elytraflight command.
     */
    public static LiteralArgumentBuilder<CommandSourceStack> getPermissionsCommand() {
        return Commands.literal("permissions")
                .requires(SimplePermissions::admin)
                .then(
                        Commands.literal("set")
                                .then(Commands.argument("permission", StringArgumentType.word())
                                        .suggests((context, builder) -> {
                                            for (Permissions permission : Permissions.values()) {
                                                builder.suggest(permission.getSerializedName());
                                            }
                                            return builder.buildFuture();
                                        }).executes(context -> {
                                            String input = StringArgumentType.getString(context, "permission");

                                            try {
                                                Permissions permission = Permissions.byName(input);
                                                Balm.config().updateLocalConfig(ModOptions.class, config -> {
                                                    config.permissions = permission;
                                                });
                                                context.getSource().sendSystemMessage(changedPermission(permission));
                                                return permission.getId();
                                            } catch (NullPointerException | IllegalArgumentException o) {
                                                context.getSource().sendFailure(Component.literal("Invalid permission: " + input));
                                                return 0;
                                            }
                                        })
                                ));
    }

    /**
     * @return the permission command text to display.
     */
    private static Component changedPermission(Permissions permission) {
        Component perm;
        switch (permission) {
            case MODERATOR -> perm = Component.literal("Players that bypass spawn protection");
            case GAMEMASTER -> perm = Component.literal("Players that have higher authority, such as permission to use command blocks, change gamemode, difficulty, etc.");
            case ADMIN -> perm = Component.literal("Only operators");
            default -> perm = Component.literal("Anyone");
        }
        return perm.copy().append(" can now use /flight.");
    }
}