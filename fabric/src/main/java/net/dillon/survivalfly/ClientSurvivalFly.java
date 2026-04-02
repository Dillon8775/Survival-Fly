package net.dillon.survivalfly;

import net.dillon.survivalfly.event.FabricClientEvents;
import net.fabricmc.api.ClientModInitializer;

public class ClientSurvivalFly implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FabricClientEvents.registerKeybinds();
    }
}