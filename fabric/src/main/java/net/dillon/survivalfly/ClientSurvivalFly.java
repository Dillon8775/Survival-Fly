package net.dillon.survivalfly;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.fabric.platform.runtime.FabricLoadContext;
import net.dillon.survivalfly.event.FabricClientEvents;
import net.dillon.survivalfly.helper.ModConstants;
import net.dillon.survivalfly.main.ClientMain;
import net.fabricmc.api.ClientModInitializer;

public class ClientSurvivalFly implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FabricClientEvents.registerKeybinds();
        FabricClientEvents.registerConnectionChecks();

        Balm.initializeMod(ModConstants.MOD_ID, FabricLoadContext.INSTANCE, ClientMain::cInitialize);
    }
}