package net.dillon.survivalfly.packet.serverbound;

import com.google.gson.Gson;
import net.dillon.survivalfly.option.ModCommonOptions;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import static net.dillon.survivalfly.helper.ModHelper.survivalFlyIdentifier;

/**
 * Updates the {@code server} config.
 */
public record UpdateServerOptionsC2SPacket(String jsonOptions) implements CustomPacketPayload {
    public static final Identifier ID = survivalFlyIdentifier("update_server_options");
    public static final Type<UpdateServerOptionsC2SPacket> PACKET_TYPE = new Type<>(ID);
    private static final Gson GSON = new Gson();

    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateServerOptionsC2SPacket> CODEC = StreamCodec.ofMember(
            (buf, packet) -> packet.writeUtf(buf.jsonOptions()),
            buf -> new UpdateServerOptionsC2SPacket(buf.readUtf())
    );

    public static UpdateServerOptionsC2SPacket from(ModCommonOptions options) {
        return new UpdateServerOptionsC2SPacket(GSON.toJson(options));
    }

    public ModCommonOptions toOptions() {
        return GSON.fromJson(jsonOptions, ModCommonOptions.class);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }
}