package net.dillon.survivalfly.mixin.client;

import net.dillon.survivalfly.option.ModOptions;
import net.dillon.survivalfly.util.ButtonUtil;
import net.minecraft.client.gui.components.SpriteIconButton;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(PauseScreen.class)
public class GameMenuScreenMixin extends Screen {
    @Shadow @Final
    private boolean showPauseMenu;

    public GameMenuScreenMixin(Component title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        if (ModOptions.SHOW_CONFIG_BUTTON.get() && this.showPauseMenu) {
            SpriteIconButton settingsButton = this.addRenderableWidget(ButtonUtil.initializeButton(this.minecraft, this));
            settingsButton.setPosition(this.width / 2 + 106, this.height / 4 + 48 - 16);
        }
    }
}