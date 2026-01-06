package net.dillon.survivalfly.mixin;

import net.dillon.survivalfly.util.PlayerAbilitiesExtension;
import net.minecraft.entity.player.PlayerAbilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

/**
 * Creates a new boolean expression, which tracks if the player has ever enabled flight, which in return fixes inconsistency with switching between flying gamemodes and non-flying gamemodes.
 */
@Mixin(PlayerAbilities.class)
public class PlayerAbilitiesMixin implements PlayerAbilitiesExtension {
    @Unique
    private boolean everEnabledFlight = false;

    @Override
    public void setEverEnabledFlight(boolean value) {
        this.everEnabledFlight = value;
    }

    @Override
    public boolean hasEverEnabledFlight() {
        return this.everEnabledFlight;
    }
}