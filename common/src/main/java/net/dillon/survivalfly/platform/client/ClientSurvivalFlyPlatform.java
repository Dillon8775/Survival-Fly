package net.dillon.survivalfly.platform.client;

import net.dillon.dillonlib.client.component.PlatformMenuButton;
import net.dillon.dillonlib.platform.client.ClientModPlatform;
import net.dillon.survivalfly.client.debug.ModDebugEntryFlightSpeed;
import net.dillon.survivalfly.client.debug.ModDebugScreenEntries;
import net.dillon.survivalfly.helper.ModConstants;
import net.dillon.survivalfly.main.ClientMain;
import net.minecraft.client.gui.components.debug.DebugScreenEntry;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;

import java.util.List;

public abstract class ClientSurvivalFlyPlatform extends ClientModPlatform {

    @Override
    public abstract boolean canSendPacket(LocalPlayer localPlayer);

    @Override
    public void registerDebugEntries(WritableRegistry<DebugScreenEntry> registry) {
        Registry.register(registry, ModDebugScreenEntries.FLIGHT_SPEED, new ModDebugEntryFlightSpeed());
    }

    @Override
    public List<PlatformMenuButton> menuButtons() {
        return List.of(
                PlatformMenuButton.builder(ClientMain.createMenuButton()).build()
        );
    }

    @Override
    public String modId() {
        return ModConstants.MOD_ID;
    }
}