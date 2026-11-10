package net.dillon.survivalfly.mixin.client;

import net.dillon.survivalfly.screen.WarningScreen;
import net.dillon.survivalfly.util.Overrides;
import net.minecraft.client.GameLoadCookie;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.dillon.survivalfly.option.OptionInstances.client;

@Mixin(Gui.class)
public class GuiMixin {

    /**
     * Boots into the warning screen.
     */
    @Inject(method = "buildInitialScreens", at = @At("RETURN"), cancellable = true)
    private void openSpeedrunnerModScreens(GameLoadCookie cookie, CallbackInfoReturnable<Runnable> cir) {
        Runnable vanillaFlow = cir.getReturnValue();
        cir.setReturnValue(() -> {
            if (!client().seenWarningMessage || !Overrides.seenWarningMessage()) {
                Minecraft.getInstance().gui.setScreen(new WarningScreen(null));
            } else {
                vanillaFlow.run();
            }
        });
    }
}