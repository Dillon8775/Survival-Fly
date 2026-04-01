package net.dillon.survivalfly.platform;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.io.File;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isSafeToSend(CustomPacketPayload packet) {
        return true;
    }

    @Override
    public File getConfigDir(String fileName) {
        return new File(FabricLoader.getInstance().getConfigDir().toFile(), fileName);
    }

    @Override
    public void sendToServer(CustomPacketPayload payload) {
        if (this.isSafeToSend(payload)) {
            ClientPlayNetworking.send(payload);
        }
    }
}
