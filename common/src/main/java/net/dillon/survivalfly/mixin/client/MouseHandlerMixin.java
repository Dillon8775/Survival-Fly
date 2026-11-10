package net.dillon.survivalfly.mixin.client;

import net.blay09.mods.balm.Balm;
import net.dillon.dillonlib.annotation.Dill;
import net.dillon.dillonlib.annotation.DillType;
import net.dillon.survivalfly.keybind.ModKeyMappings;
import net.dillon.survivalfly.packet.serverbound.UpdateFlightSpeedC2SPacket;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.player.LocalPlayer;
import org.joml.Vector2i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static net.dillon.survivalfly.helper.ModHelper.modEnabled;

@Dill(DillType.CLIENT)
@Mixin(MouseHandler.class)
public class MouseHandlerMixin {

    /**
     * Allows the player to use {@code CTRL + SCROLL} on any gamemode to change flight speed.
     */
    @Redirect(method = "onScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isSpectator()Z"))
    private boolean allowAnyGameMode(LocalPlayer clientPlayer) {
        if (!modEnabled()) {
            return clientPlayer.isSpectator();
        }

        // ensure that player has permission to change flight speed
        if (clientPlayer.getAbilities().mayfly) {
            if (!ModKeyMappings.CHANGE_FLIGHT_SPEED.isDown()) { // if ALT key isn't pressed OR player doesn't have flying abilities, fallback to vanilla logic
                return clientPlayer.isSpectator();
            }

            return true;
        }

        // otherwise only work on spectator mode
        return clientPlayer.isSpectator();
    }

    /**
     * Tells the player their flight speed and caches the current flight speed, so that when the player changes their gamemode, their flight speed is not reset.
     */
    @Inject(method = "onScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Abilities;setFlyingSpeed(F)V", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILEXCEPTION, cancellable = true)
    private void updateFlightSpeed(long handle, double xoffset, double yoffset, CallbackInfo ci, boolean discreteScroll, double scrollSensitivity, double scaledXOffset, double scaledYOffset, Vector2i wheelXY, int wheel, float speed) {
        if (!modEnabled()) {
            return;
        }

        Balm.networking().sendToServer(new UpdateFlightSpeedC2SPacket(speed));
        ci.cancel();
    }
}