package net.dillon.survivalfly.mixin;

import net.dillon.survivalfly.SurvivalFly;
import net.dillon.survivalfly.keybind.ModKeybinds;
import net.minecraft.client.Mouse;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Mouse.class)
public class MouseMixin {

    /**
     * Allows the player to use {@code CTRL + SCROLL} on any gamemode to change flight speed.
     */
    @Redirect(method = "onMouseScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isSpectator()Z"))
    private boolean allowAnyGamemode(ClientPlayerEntity clientPlayer) {
        if (!ModKeybinds.CHANGE_FLIGHT_SPEED.isPressed()) { // if ALT key isn't pressed OR player doesn't have flying abilities, fallback to vanilla logic
            return clientPlayer.isSpectator();
        }

        if (SurvivalFly.options().changeFlySpeedOnRule.nonSurvivalLikeGamemodes()) {
            return clientPlayer.isSpectator() || clientPlayer.isCreative();
        } else if (SurvivalFly.options().changeFlySpeedOnRule.anyGamemode()) {
            return true;
        } else {
            return clientPlayer.isSpectator();
        }
    }
}