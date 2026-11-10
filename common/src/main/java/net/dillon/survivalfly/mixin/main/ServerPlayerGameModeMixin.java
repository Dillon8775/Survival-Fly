package net.dillon.survivalfly.mixin.main;

import net.dillon.survivalfly.util.PlayerAbilitiesExtension;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixin {
    @Shadow @Final
    protected ServerPlayer player;

    /**
     * Fixes flying ability inconsistency.
     * <p>Ex. if you switch from creative -> survival and your flight was previously enabled with {@code /flight}, flight remains on. Otherwise flight disables.</p>
     */
    @Inject(method = "changeGameModeForPlayer", at = @At("TAIL"))
    private void fixFlyingInconsistency(GameType gameMode, CallbackInfoReturnable<Boolean> cir) {
        if (this.player.gameMode.getGameModeForPlayer().isSurvival()) {
            if (!((PlayerAbilitiesExtension)this.player).hasEverEnabledFlight()) {
                this.player.getAbilities().mayfly = false;
                this.player.getAbilities().flying = false;
            }
        }
    }
}