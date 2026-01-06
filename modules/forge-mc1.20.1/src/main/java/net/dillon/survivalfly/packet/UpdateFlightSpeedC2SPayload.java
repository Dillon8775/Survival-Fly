package net.dillon.survivalfly.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

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

    public void handle(Supplier<NetworkEvent.Context> context) {
        var player = context.get().getSender();
        float speed = this.speed();

        player.getAbilities().setFlyingSpeed(speed);
        player.onUpdateAbilities();
    }
}