package net.dillon.survivalfly.platform;

import com.mojang.brigadier.CommandDispatcher;
import net.blay09.mods.balm.Balm;
import net.dillon.dillonlib.core.DillonLibModReferences;
import net.dillon.dillonlib.platform.ModPlatform;
import net.dillon.dillonlib.platform.Platforms;
import net.dillon.dillonlib.platform.info.Platform;
import net.dillon.dillonlib.platform.info.Release;
import net.dillon.survivalfly.command.*;
import net.dillon.survivalfly.helper.ModConstants;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;

public abstract class SurvivalFlyPlatform extends ModPlatform {

    /**
     * @return If the player has permission to execute a command.
     */
    public boolean hasPermission(ServerPlayer player, String node, boolean fallback) {
        if (DillonLibModReferences.isModLoaded(ModReferences.LUCKPERMS)) {
            return this.hasPermissionOnPlatform(player, node, fallback);
        }
        return fallback;
    }

    /**
     * @return if the player can execute commands with {@code LuckPerms} (platform specific).
     */
    abstract boolean hasPermissionOnPlatform(ServerPlayer player, String node, boolean fallback);

    @Override
    public void registerCommonCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandRegistryAccess) {
        dispatcher.register(SurvivalFlyCommand.getHelpCommand());
        dispatcher.register(FlightCommand.getFlightCommand());
        dispatcher.register(FlightStatusCommand.getFlightStatusCommand());
        dispatcher.register(FlightSpeedCommand.getFlightSpeedCommand());
        dispatcher.register(ElytraFlightCommand.getElytraFlightCommand());
        dispatcher.register(FlightExhaustionCommand.getFlightExhaustionCommand());
        dispatcher.register(FriendlyFlightCommand.getFriendlyFlightCommand());
        dispatcher.register(PermissionsCommand.getPermissionsCommand());
    }

    @Override
    public String modId() {
        return ModConstants.MOD_ID;
    }

    @Override
    public String modVersion() {
        return Platforms.getCommonPlatform().commonModVersion(ModConstants.MOD_ID);
    }

    @Override
    public Release release() {
        return Release.STABLE;
    }

    @Override
    public Platform platform() {
        return Balm.platform().name().equals("fabric") ? Platform.FABRIC : Platform.NEOFORGE;
    }
}