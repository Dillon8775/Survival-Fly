package net.dillon.survivalfly.packet.serverbound;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import static net.dillon.survivalfly.helper.ModHelper.survivalFlyIdentifier;

/**
 * Requests the {@code server} config for Survival Fly.
 */
public record RequestServerConfigC2SPacket() implements CustomPacketPayload {
    public static final Identifier ID = survivalFlyIdentifier("request_server_side_options_c2s");
    public static final CustomPacketPayload.Type<RequestServerConfigC2SPacket> PACKET_TYPE = new CustomPacketPayload.Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, RequestServerConfigC2SPacket> CODEC = StreamCodec.ofMember(
            (buf, packet) -> {},
            buf -> new RequestServerConfigC2SPacket()
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }
}