package net.dillon.survivalfly;

import net.dillon.survivalfly.event.ClientEvents;
import net.fabricmc.api.ClientModInitializer;

public class SurvivalFlyClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientEvents.registerKeybinds();
    }
}