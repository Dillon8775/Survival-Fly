package net.dillon.survivalfly.mixin;

import net.dillon.survivalfly.SurvivalFly;
import net.dillon.survivalfly.keybind.ModKeybinds;
import net.dillon.survivalfly.payload.UpdateFlightSpeedC2SPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {
    @Shadow @Final
    private MinecraftClient client;

    /**
     * Allows the player to use {@code CTRL + SCROLL} on any gamemode to change flight speed.
     */
    @Redirect(method = "onMouseScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isSpectator()Z"))
    private boolean allowAnyGamemode(ClientPlayerEntity clientPlayer) {
        // ensure that player has permission to change flight speed
        if (clientPlayer.getAbilities().allowFlying) {
            if (!ModKeybinds.CHANGE_FLIGHT_SPEED.isPressed()) { // if ALT key isn't pressed OR player doesn't have flying abilities, fallback to vanilla logic
                return clientPlayer.isSpectator();
            }

            if (SurvivalFly.options().changeFlySpeedOnRule.nonSurvivalLikeGamemodes()) {
                return clientPlayer.isSpectator() || clientPlayer.isCreative();
            } else if (SurvivalFly.options().changeFlySpeedOnRule.anyGamemode()) {
                return true;
            }
        }
        // otherwise only work on spectator mode
        return clientPlayer.isSpectator();
    }

    /**
     * Tells the player their flight speed and caches the current flight speed, so that when the player changes their gamemode, their flight speed is not reset.
     */
    @Inject(method = "onMouseScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerAbilities;setFlySpeed(F)V", shift = At.Shift.AFTER))
    private void updateFlightSpeed(long window, double horizontal, double vertical, CallbackInfo ci) {
        float speed = this.client.player.getAbilities().getFlySpeed();
        ClientPlayNetworking.send(new UpdateFlightSpeedC2SPayload(speed));
        this.client.player.sendMessage(Text.translatable("survivalfly.current_flight_speed", SurvivalFly.decimalAsPercentage(speed)).formatted(Formatting.GREEN).append("%"), true);
    }
}