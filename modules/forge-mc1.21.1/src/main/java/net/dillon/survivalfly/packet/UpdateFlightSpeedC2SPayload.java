package net.dillon.survivalfly.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;

public record UpdateFlightSpeedC2SPayload(float speed) {

    /**
     * Decoding.
     */
    public UpdateFlightSpeedC2SPayload(FriendlyByteBuf buf) {
        this(buf.readFloat());
    }

    /**
     * Encoding.
     */
    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(this.speed);
    }

    public void handle(CustomPayloadEvent.Context context) {
        var player = context.getSender();
        float speed = this.speed();

        player.getAbilities().setFlyingSpeed(speed);
        player.onUpdateAbilities();
    }
}