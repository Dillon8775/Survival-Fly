package net.dillon.survivalfly.mixin.client;

import net.dillon.dillonlib.annotation.Dill;
import net.dillon.dillonlib.annotation.DillType;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Input;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.dillon.survivalfly.helper.ModHelper.modEnabled;
import static net.dillon.survivalfly.option.OptionInstances.common;

@Dill(DillType.CLIENT)
@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    @Shadow
    private boolean crouching;

    /**
     * @return if the player is attempting to crouch flight.
     */
    @Unique
    private boolean isPlayerTryingToCrouchFlight(LocalPlayer player, boolean includeSprint) {
        return common().crouchFlight && player.getAbilities().flying && player.isShiftKeyDown() && (!includeSprint || player.input.keyPresses.sprint());
    }

    /**
     * Cancels out holding shift and descending, allowing the player to crouch in place.
     */
    @Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Input;shift()Z", ordinal = 2))
    private boolean survivalfly$preventCrouchDescentWhileFlying(Input input) {
        if (!modEnabled()) {
            return input.shift();
        }

        LocalPlayer player = (LocalPlayer)(Object)this;
        if (this.isPlayerTryingToCrouchFlight(player, true)) {
            return false;
        }
        return input.shift();
    }

    /**
     * Makes the player appear crouching in place while flying.
     */
    @Inject(method = "aiStep", at = @At(value = "FIELD", target = "Lnet/minecraft/client/player/LocalPlayer;crouching:Z", opcode = 181, shift = At.Shift.AFTER))
    private void survivalfly$showCrouchPoseWhileFlying(CallbackInfo ci) {
        if (!modEnabled()) {
            return;
        }

        LocalPlayer player = (LocalPlayer)(Object)this;
        if (this.isPlayerTryingToCrouchFlight(player, false)) {
            this.crouching = true;
        }
    }
}