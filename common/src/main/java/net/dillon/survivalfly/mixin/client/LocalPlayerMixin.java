package net.dillon.survivalfly.mixin.client;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.dillon.survivalfly.util.ModUtil.options;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    @Shadow
    private boolean crouching;

    /**
     * @return if the player is attempting to crouch flight.
     */
    @Unique
    private boolean isPlayerTryingToCrouchFlight(LocalPlayer player, boolean includeSprint) {
        return options().crouchFlight && player.getAbilities().flying && player.isShiftKeyDown() && (!includeSprint || Screen.hasControlDown());
    }

    /**
     * Cancels out holding shift and descending, allowing the player to crouch in place.
     */
    @Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V"))
    private void preventCrouchDescentWhileFlying(LocalPlayer player, Vec3 motion) {
        if (this.isPlayerTryingToCrouchFlight(player, true) && !player.input.jumping) {
            player.setDeltaMovement(player.getDeltaMovement());
            return;
        }
        player.setDeltaMovement(motion);
    }

    /**
     * Makes the player appear crouching in place while flying.
     */
    @Inject(method = "aiStep", at = @At(value = "FIELD", target = "Lnet/minecraft/client/player/LocalPlayer;crouching:Z", opcode = 181, shift = At.Shift.AFTER))
    private void showCrouchPoseWhileFlying(CallbackInfo ci) {
        LocalPlayer player = (LocalPlayer)(Object)this;
        if (this.isPlayerTryingToCrouchFlight(player, false)) {
            this.crouching = true;
        }
    }
}
