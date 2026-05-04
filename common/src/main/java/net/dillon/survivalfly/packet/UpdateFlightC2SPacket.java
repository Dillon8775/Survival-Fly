package net.dillon.survivalfly.packet;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

/**
 * The packet for updating {@code client to server} player flight.
 */
public record UpdateFlightC2SPacket(boolean flight) implements CustomPacketPayload {
    public static final Identifier ID = Identifier.parse("survivalfly:update_flight");
    public static final Type<UpdateFlightC2SPacket> PACKET_TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateFlightC2SPacket> CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.BOOL,
                    UpdateFlightC2SPacket::flight,
                    UpdateFlightC2SPacket::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }
}