package net.dillon.survivalfly.mixin;

import net.dillon.survivalfly.keybind.ModKeybinds;
import net.dillon.survivalfly.payload.UpdateFlightSpeedC2SPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.dillon.survivalfly.SurvivalFly.DEFAULT_FLIGHT_SPEED;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow
    public ClientPlayerEntity player;

    /**
     * Resets player's flight speed.
     */
    @Inject(method = "handleInputEvents", at = @At("TAIL"))
    private void resetFlightSpeed(CallbackInfo ci) {
        // Reset flight speed if key was pressed and player has flying abilities
        while (ModKeybinds.RESET_FLIGHT_SPEED.wasPressed() && this.player.getAbilities().allowFlying && this.player != null) {
            ClientPlayNetworking.send(new UpdateFlightSpeedC2SPayload(DEFAULT_FLIGHT_SPEED));
            this.player.sendMessage(Text.translatable("survivalfly.reset_flight_speed").formatted(Formatting.GREEN), true);
        }
    }
}