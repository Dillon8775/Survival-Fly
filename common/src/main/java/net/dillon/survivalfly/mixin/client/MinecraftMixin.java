package net.dillon.survivalfly.mixin.client;

import net.dillon.dillonlib.annotation.Dill;
import net.dillon.dillonlib.annotation.DillType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.dillon.survivalfly.helper.ModHelper.modEnabled;

@Dill(DillType.CLIENT)
@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow
    public LocalPlayer player;

    /**
     * Resets player's flight speed.
     */
    @Inject(method = "handleKeybinds", at = @At("TAIL"))
    private void resetFlightSpeed(CallbackInfo ci) {
        if (!modEnabled()) {
            return;
        }

        // Reset flight speed if key was pressed and player has flying abilities

    }
}