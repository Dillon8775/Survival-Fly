package net.dillon.survivalfly;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.fabric.platform.runtime.FabricLoadContext;
import net.dillon.survivalfly.helper.ModConstants;
import net.dillon.survivalfly.main.ServerMain;
import net.fabricmc.api.DedicatedServerModInitializer;

public class ServerSurvivalFly implements DedicatedServerModInitializer {

    @Override
    public void onInitializeServer() {
        Balm.initializeMod(ModConstants.MOD_ID, FabricLoadContext.INSTANCE, ServerMain::sInitialize);
    }
}