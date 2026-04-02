package net.dillon.survivalfly.mixin.client;

import net.blay09.mods.balm.Balm;
import net.dillon.survivalfly.keybind.ModKeybinds;
import net.dillon.survivalfly.packet.UpdateFlightSpeedC2SPayload;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.dillon.survivalfly.util.ModUtil.DEFAULT_FLIGHT_SPEED;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow
    public LocalPlayer player;

    /**
     * Resets player's flight speed.
     */
    @Inject(method = "handleKeybinds", at = @At("TAIL"))
    private void resetFlightSpeed(CallbackInfo ci) {
        // Reset flight speed if key was pressed and player has flying abilities
        while (ModKeybinds.RESET_FLIGHT_SPEED.consumeClick() && this.player.getAbilities().mayfly && this.player != null) {
            Balm.networking().sendToServer(new UpdateFlightSpeedC2SPayload(DEFAULT_FLIGHT_SPEED));
            this.player.sendOverlayMessage(Component.translatable("survivalfly.reset_flight_speed").withStyle(ChatFormatting.GREEN));
        }
    }
}