package net.dillon.survivalfly.mixin.client;

import net.dillon.survivalfly.keybind.ModKeybinds;
import net.dillon.survivalfly.main.SurvivalFly;
import net.dillon.survivalfly.option.ModOptions;
import net.dillon.survivalfly.packet.ServerHandler;
import net.dillon.survivalfly.packet.UpdateFlightSpeedC2SPayload;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(MouseHandler.class)
public class MouseMixin {
    @Shadow @Final
    private Minecraft minecraft;

    /**
     * Allows the player to use {@code CTRL + SCROLL} on any gamemode to change flight speed.
     */
    @Redirect(method = "onScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isSpectator()Z"))
    private boolean allowAnyGamemode(LocalPlayer clientPlayer) {
        // ensure that player has permission to change flight speed
        if (clientPlayer.getAbilities().mayfly) {
            if (!ModKeybinds.CHANGE_FLIGHT_SPEED.isDown()) { // if ALT key isn't pressed OR player doesn't have flying abilities, fallback to vanilla logic
                return clientPlayer.isSpectator();
            }

            if (ModOptions.CHANGE_FLY_SPEED_ON_RULE.get().nonSurvivalLikeGamemodes()) {
                return clientPlayer.isSpectator() || clientPlayer.isCreative();
            } else if (ModOptions.CHANGE_FLY_SPEED_ON_RULE.get().anyGamemode()) {
                return true;
            }
        }
        // otherwise only work on spectator mode
        return clientPlayer.isSpectator();
    }

    /**
     * Tells the player their flight speed and caches the current flight speed, so that when the player changes their gamemode, their flight speed is not reset.
     */
    @Inject(method = "onScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Abilities;setFlyingSpeed(F)V", shift = At.Shift.AFTER))
    private void updateFlightSpeed(long window, double horizontal, double vertical, CallbackInfo ci) {
        float speed = this.minecraft.player.getAbilities().getFlyingSpeed();
        ServerHandler.sendToServer(new UpdateFlightSpeedC2SPayload(speed));
        this.minecraft.player.displayClientMessage(Component.translatable("survivalfly.current_flight_speed", SurvivalFly.decimalAsPercentage(speed)).withStyle(ChatFormatting.GREEN).append("%"), true);
    }
}