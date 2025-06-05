package net.dillon.survivalfly.mixin;

import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.world.GameMode;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Fixes issue with rejoining world and flight being disabled.
 */
@Mixin(GameMode.class)
public class GameModeMixin {

    @Redirect(method = "setAbilities", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerAbilities;allowFlying:Z", opcode = Opcodes.PUTFIELD))
    private void redirectAllowFlying(PlayerAbilities abilities, boolean value) {
        if (shouldCancelElseBlock(value)) {
            return;
        }
        abilities.allowFlying = value;
    }

    @Redirect(method = "setAbilities", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerAbilities;flying:Z", opcode = Opcodes.PUTFIELD))
    private void redirectFlying(PlayerAbilities abilities, boolean value) {
        if (shouldCancelElseBlock(value)) {
            return;
        }
        abilities.flying = value;
    }

    @Unique
    private boolean shouldCancelElseBlock(boolean value) {
        GameMode self = (GameMode) (Object) this;
        return !value && self != GameMode.CREATIVE && self != GameMode.SPECTATOR;
    }
}