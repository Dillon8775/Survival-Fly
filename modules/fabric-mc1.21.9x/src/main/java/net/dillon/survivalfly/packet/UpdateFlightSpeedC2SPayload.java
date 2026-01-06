package net.dillon.survivalfly.packet;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

/**
 * The payload for sending {@code client to server} flight speed changes.
 */
public record UpdateFlightSpeedC2SPayload(float speed) implements CustomPayload {
    public static final Identifier ID = Identifier.of("survivalfly:update_flight_speed");
    public static final CustomPayload.Id<UpdateFlightSpeedC2SPayload> PAYLOAD_ID = new CustomPayload.Id<>(ID);
    public static final PacketCodec<RegistryByteBuf, UpdateFlightSpeedC2SPayload> CODEC =
            PacketCodec.of(
                    (buf, payload) -> payload.writeFloat(buf.speed),       // writes float
                    buf -> new UpdateFlightSpeedC2SPayload(buf.readFloat())     // reads float
            );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return PAYLOAD_ID;
    }
}