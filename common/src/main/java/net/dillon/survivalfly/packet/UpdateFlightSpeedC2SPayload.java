package net.dillon.survivalfly.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

/**
 * The payload for sending {@code client to server} flight speed changes.
 */
public record UpdateFlightSpeedC2SPayload(float speed) implements CustomPacketPayload {
    public static final Identifier ID = Identifier.parse("survivalfly:update_flight_speed");
    public static final Type<UpdateFlightSpeedC2SPayload> PAYLOAD_ID = new Type<>(ID);
    public static final StreamCodec<ByteBuf, UpdateFlightSpeedC2SPayload> CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.FLOAT,
                    UpdateFlightSpeedC2SPayload::speed,
                    UpdateFlightSpeedC2SPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PAYLOAD_ID;
    }
}