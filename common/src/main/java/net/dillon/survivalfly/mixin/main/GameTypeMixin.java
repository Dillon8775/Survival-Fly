package net.dillon.survivalfly.mixin.main;

import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Fixes issue with rejoining world and flight being disabled.
 */
@Mixin(GameType.class)
public class GameTypeMixin {

    @Redirect(method = "updatePlayerAbilities", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Abilities;mayfly:Z", opcode = 181))
    private void redirectAllowFlying(Abilities abilities, boolean value) {
        if (this.shouldCancelElseBlock(value)) {
            return;
        }
        abilities.mayfly = value;
    }

    @Redirect(method = "updatePlayerAbilities", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Abilities;flying:Z", opcode = 181))
    private void redirectFlying(Abilities abilities, boolean value) {
        if (this.shouldCancelElseBlock(value)) {
            return;
        }
        abilities.flying = value;
    }

    /**
     * @return {@code true} if gamemode doesn't equal {@code creative} or {@code spectator.}
     */
    @Unique
    private boolean shouldCancelElseBlock(boolean value) {
        GameType self = (GameType) (Object) this;
        return !value && self != GameType.CREATIVE && self != GameType.SPECTATOR;
    }
}