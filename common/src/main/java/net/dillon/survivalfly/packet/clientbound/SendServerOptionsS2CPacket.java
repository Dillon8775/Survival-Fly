package net.dillon.survivalfly.packet.clientbound;

import com.google.gson.Gson;
import net.dillon.survivalfly.option.ModCommonOptions;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import static net.dillon.survivalfly.helper.ModHelper.survivalFlyIdentifier;

/**
 * Sends the server config to the {@code client}.
 */
public record SendServerOptionsS2CPacket(String jsonOptions, boolean hasPermission) implements CustomPacketPayload {
    public static final Identifier ID = survivalFlyIdentifier("send_server_options");
    public static final CustomPacketPayload.Type<SendServerOptionsS2CPacket> PACKET_TYPE = new CustomPacketPayload.Type<>(ID);
    public static final Gson GSON = new Gson();

    public static final StreamCodec<RegistryFriendlyByteBuf, SendServerOptionsS2CPacket> CODEC = StreamCodec.ofMember(
        (buf, packet) -> {
            packet.writeUtf(buf.jsonOptions());
            packet.writeBoolean(buf.hasPermission());
        },
        buf -> new SendServerOptionsS2CPacket(
                buf.readUtf(),
                buf.readBoolean()
        )
    );

    public static SendServerOptionsS2CPacket from(ModCommonOptions options, boolean hasPermission) {
        return new SendServerOptionsS2CPacket(GSON.toJson(options), hasPermission);
    }

    public ModCommonOptions to() {
        return GSON.fromJson(this.jsonOptions, ModCommonOptions.class);
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }
}