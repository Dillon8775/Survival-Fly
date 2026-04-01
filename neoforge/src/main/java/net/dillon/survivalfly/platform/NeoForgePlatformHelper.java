package net.dillon.survivalfly.platform;

import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import java.io.File;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public File getConfigDir(String fileName) {
        return new File(FMLPaths.CONFIGDIR.get().resolve(fileName).toString());
    }

    @Override
    public boolean isSafeToSend(CustomPacketPayload packet) {
        Minecraft minecraft = Minecraft.getInstance();
        return minecraft.getConnection() != null && minecraft.getConnection().hasChannel(packet);
    }

    @Override
    public void sendToServer(CustomPacketPayload payload) {
        if (this.isSafeToSend(payload)) {
            ClientPacketDistributor.sendToServer(payload);
        }
    }

    @Override
    public String getPlatformName() {
        return "NeoForge";
    }
}