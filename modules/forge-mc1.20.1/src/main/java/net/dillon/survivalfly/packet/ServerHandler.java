package net.dillon.survivalfly.packet;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

/**
 * Packet handling.
 */
public class ServerHandler {
    private static int packetId = 0;
    private static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder
            .named(ResourceLocation.parse("survivalfly:main"))
            .networkProtocolVersion(() -> "1")
            .clientAcceptedVersions((status) -> true)
            .serverAcceptedVersions((status) -> true)
            .simpleChannel();

    private static int id() {
        return packetId++;
    }

    /**
     * Registers the update flight speed payload.
     */
    public static void registerUpdateFlightSpeedC2SPayload() {
        INSTANCE.messageBuilder(UpdateFlightSpeedC2SPayload.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .encoder(UpdateFlightSpeedC2SPayload::encode)
                .decoder(UpdateFlightSpeedC2SPayload::new)
                .consumerMainThread(UpdateFlightSpeedC2SPayload::handle)
                .add();
    }

    /**
     * Sends the payload over to the server.
     */
    public static void sendToServer(Object msg) {
        INSTANCE.send(PacketDistributor.SERVER.noArg(), msg);
    }
}