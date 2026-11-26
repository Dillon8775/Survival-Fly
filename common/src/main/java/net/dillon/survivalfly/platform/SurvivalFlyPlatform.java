package net.dillon.survivalfly.platform;

import com.mojang.brigadier.CommandDispatcher;
import net.blay09.mods.balm.Balm;
import net.dillon.dillonlib.platform.ModPlatform;
import net.dillon.dillonlib.platform.info.Platform;
import net.dillon.dillonlib.platform.info.Release;
import net.dillon.survivalfly.command.*;
import net.dillon.survivalfly.helper.ModConstants;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;

public class SurvivalFlyPlatform extends ModPlatform {

    @Override
    public void registerCommonCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandRegistryAccess) {
        SurvivalFlyCommand.register(dispatcher);
        FlightCommand.register(dispatcher);
        FlightStatusCommand.register(dispatcher);
        FlightSpeedCommand.register(dispatcher);
        ElytraFlightCommand.register(dispatcher);
        FlightExhaustionCommand.register(dispatcher);
        FriendlyFlightCommand.register(dispatcher);
        PermissionsCommand.register(dispatcher);
    }

    @Override
    public String modId() {
        return ModConstants.MOD_ID;
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