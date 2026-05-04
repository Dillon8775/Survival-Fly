package net.dillon.survivalfly.mixin.main;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.dillon.survivalfly.util.ModUtil.options;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    public PlayerMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    /**
     * Makes the player actually crouch in-place while flying, server-side.
     */
    @Inject(method = "updatePlayerPose", at = @At("HEAD"), cancellable = true)
    private void allowCrouchPoseWhileFlying(CallbackInfo ci) {
        Player player = (Player)(Object)this;
        if (options().crouchFlight && player.getAbilities().flying && player.isShiftKeyDown()) {
            this.setPose(Pose.CROUCHING);
            ci.cancel();
        }
    }
}
