package net.dillon.survivalfly.mixin.client;

import net.blay09.mods.balm.Balm;
import net.dillon.survivalfly.keybind.ModKeyMappings;
import net.dillon.survivalfly.packet.UpdateFlightSpeedC2SPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.dillon.survivalfly.helper.ModHelper.decimalAsPercentage;
import static net.dillon.survivalfly.helper.ModHelper.modEnabled;

@Mixin(MouseHandler.class)
public class MouseMixin {
    @Shadow @Final
    private Minecraft minecraft;

    /**
     * Allows the player to use {@code CTRL + SCROLL} on any gamemode to change flight speed.
     */
    @Redirect(method = "onScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isSpectator()Z"))
    private boolean allowAnyGamemode(LocalPlayer clientPlayer) {
        if (!modEnabled()) {
            return clientPlayer.isSpectator();
        }

        // ensure that player has permission to change flight speed
        if (clientPlayer.getAbilities().mayfly) {
            if (!ModKeyMappings.CHANGE_FLIGHT_SPEED.isDown()) { // if ALT key isn't pressed OR player doesn't have flying abilities, fallback to vanilla logic
                return clientPlayer.isSpectator();
            }

            return true;
        }
        // otherwise only work on spectator mode
        return clientPlayer.isSpectator();
    }

    /**
     * Tells the player their flight speed and caches the current flight speed, so that when the player changes their gamemode, their flight speed is not reset.
     */
    @Inject(method = "onScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Abilities;setFlyingSpeed(F)V", shift = At.Shift.AFTER))
    private void updateFlightSpeed(long window, double horizontal, double vertical, CallbackInfo ci) {
        if (!modEnabled()) {
            return;
        }

        float speed = this.minecraft.player.getAbilities().getFlyingSpeed();
        Balm.networking().sendToServer(new UpdateFlightSpeedC2SPacket(speed));
        this.minecraft.player.sendOverlayMessage(Component.translatable("survivalfly.current_flight_speed", decimalAsPercentage(speed)).withStyle(ChatFormatting.GREEN).append("%"));
    }
}