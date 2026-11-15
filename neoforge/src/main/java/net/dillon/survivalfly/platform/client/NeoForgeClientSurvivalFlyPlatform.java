package net.dillon.survivalfly.platform.client;

import net.dillon.survivalfly.packet.serverbound.UpdateFlightC2SPacket;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.neoforge.network.registration.NetworkRegistry;

public class NeoForgeClientSurvivalFlyPlatform extends ClientSurvivalFlyPlatform {

    @Override
    public boolean canSendPacket(LocalPlayer localPlayer) {
        return NetworkRegistry.hasChannel(localPlayer.connection, UpdateFlightC2SPacket.ID);
    }
}