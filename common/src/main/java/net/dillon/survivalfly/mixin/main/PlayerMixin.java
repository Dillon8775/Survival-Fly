package net.dillon.survivalfly.mixin.main;

import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.dillon.survivalfly.helper.ModHelper.modEnabled;
import static net.dillon.survivalfly.helper.ModHelper.options;

@Mixin(Player.class)
public class PlayerMixin {

    /**
     * Makes the player actually crouch in-place while flying, server-side.
     */
    @Inject(method = "getDesiredPose", at = @At("RETURN"), cancellable = true)
    private void allowCrouchPoseWhileFlying(CallbackInfoReturnable<Pose> cir) {
        if (!modEnabled()) {
            return;
        }

        Player player = (Player)(Object)this;
        if (options().crouchFlight && player.getAbilities().flying && player.isShiftKeyDown()) {
            cir.setReturnValue(Pose.CROUCHING);
        }
    }
}