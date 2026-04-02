package net.dillon.survivalfly.packet;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * The payload for sending {@code client to server} flight speed changes.
 */
public record UpdateFlightSpeedC2SPayload(float speed) implements CustomPacketPayload {
    private static final ResourceLocation ID = ResourceLocation.parse("survivalfly:update_flight_speed");
    public static final Type<UpdateFlightSpeedC2SPayload> PACKET_TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateFlightSpeedC2SPayload> CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.FLOAT,
                    UpdateFlightSpeedC2SPayload::speed,
                    UpdateFlightSpeedC2SPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }
}