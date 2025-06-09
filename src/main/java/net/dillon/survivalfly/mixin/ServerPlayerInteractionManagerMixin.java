package net.dillon.survivalfly.mixin;

import net.dillon.survivalfly.util.PlayerAbilitiesExtension;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {
    @Shadow @Final
    protected ServerPlayerEntity player;

    /**
     * Fixes flying ability inconsistency.
     */
    @Inject(method = "changeGameMode", at = @At("TAIL"))
    private void fixFlyingInconsistency(GameMode gameMode, CallbackInfoReturnable<Boolean> cir) {
        if (this.player.interactionManager.getGameMode().isSurvivalLike()) {
            if (!((PlayerAbilitiesExtension)this.player.getAbilities()).hasEverEnabledFlight()) {
                this.player.getAbilities().allowFlying = false;
                this.player.getAbilities().flying = false;
            }
        }
    }
}