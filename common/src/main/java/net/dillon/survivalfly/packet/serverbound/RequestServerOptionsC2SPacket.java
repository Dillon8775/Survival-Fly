package net.dillon.survivalfly.packet.serverbound;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import static net.dillon.survivalfly.helper.ModHelper.survivalFlyIdentifier;

/**
 * Requests the {@code server} config for Survival Fly.
 */
public record RequestServerOptionsC2SPacket() implements CustomPacketPayload {
    public static final Identifier ID = survivalFlyIdentifier("request_server_options");
    public static final CustomPacketPayload.Type<RequestServerOptionsC2SPacket> PACKET_TYPE = new CustomPacketPayload.Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, RequestServerOptionsC2SPacket> CODEC = StreamCodec.ofMember(
            (buf, packet) -> {},
            buf -> new RequestServerOptionsC2SPacket()
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }
}