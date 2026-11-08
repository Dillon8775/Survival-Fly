package net.dillon.survivalfly.platform.client;

import net.dillon.survivalfly.packet.serverbound.UpdateFlightC2SPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.player.LocalPlayer;

public class FabricClientSurvivalFlyPlatform extends ClientSurvivalFlyPlatform {

    @Override
    public boolean canSendPacket(LocalPlayer localPlayer) {
        return ClientPlayNetworking.canSend(UpdateFlightC2SPacket.PACKET_TYPE);
    }
}