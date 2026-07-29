package net.dillon.survivalfly.platform.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.dillon.survivalfly.packet.UpdateFlightC2SPacket;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.player.LocalPlayer;

public class FabricClientSurvivalFlyPlatform extends ClientSurvivalFlyPlatform {

    @Override
    public KeyMapping registerKeyMapping(String name, InputConstants.Type type, KeyMapping.Category category, int value) {
        return KeyMappingHelper.registerKeyMapping(new KeyMapping(name, value, category));
    }

    @Override
    public boolean canSendPacket(LocalPlayer localPlayer) {
        return ClientPlayNetworking.canSend(UpdateFlightC2SPacket.PACKET_TYPE);
    }
}