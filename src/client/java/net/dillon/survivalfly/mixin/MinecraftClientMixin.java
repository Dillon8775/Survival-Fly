package net.dillon.survivalfly.mixin;

import net.dillon.survivalfly.keybind.ModKeybinds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow
    public ClientPlayerEntity player;

    /**
     * Resets player's flight speed.
     */
    @Inject(method = "handleInputEvents", at = @At("TAIL"))
    private void resetFlightSpeed(CallbackInfo ci) {
        while (ModKeybinds.RESET_FLIGHT_SPEED.wasPressed() && this.player != null) {
            this.player.getAbilities().setFlySpeed(0.05F);
            this.player.sendMessage(Text.translatable("survivalfly.reset_flight_speed").formatted(Formatting.GREEN), true);
        }
    }
}