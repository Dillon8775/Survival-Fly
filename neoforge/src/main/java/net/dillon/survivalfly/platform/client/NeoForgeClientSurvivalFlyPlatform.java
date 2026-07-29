package net.dillon.survivalfly.platform.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.dillon.survivalfly.packet.UpdateFlightC2SPacket;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.neoforge.network.registration.NetworkRegistry;

public class NeoForgeClientSurvivalFlyPlatform extends ClientSurvivalFlyPlatform {

    @Override
    public KeyMapping registerKeyMapping(String name, InputConstants.Type type, KeyMapping.Category category, int value) {
        return new KeyMapping(name, value, category);
    }

    @Override
    public boolean canSendPacket(LocalPlayer localPlayer) {
        return NetworkRegistry.hasChannel(localPlayer.connection, UpdateFlightC2SPacket.ID);
    }
}