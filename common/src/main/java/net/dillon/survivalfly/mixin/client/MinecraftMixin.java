package net.dillon.survivalfly.mixin.client;

import net.blay09.mods.balm.Balm;
import net.dillon.survivalfly.keybind.ModKeyMappings;
import net.dillon.survivalfly.packet.UpdateFlightSpeedC2SPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.dillon.survivalfly.helper.ModHelper.DEFAULT_FLIGHT_SPEED;
import static net.dillon.survivalfly.helper.ModHelper.modEnabled;

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
        while (ModKeyMappings.RESET_FLIGHT_SPEED.consumeClick() && this.player.getAbilities().mayfly && this.player != null) {
            Balm.networking().sendToServer(new UpdateFlightSpeedC2SPacket(DEFAULT_FLIGHT_SPEED));
            this.player.sendOverlayMessage(Component.translatable("survivalfly.reset_flight_speed").withStyle(ChatFormatting.GREEN));
        }
    }
}