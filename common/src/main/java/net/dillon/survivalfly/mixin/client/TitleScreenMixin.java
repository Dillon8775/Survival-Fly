package net.dillon.survivalfly.mixin.client;

import net.dillon.survivalfly.platform.MultiLoader;
import net.dillon.survivalfly.util.ButtonUtil;
import net.minecraft.client.gui.components.SpriteIconButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.dillon.survivalfly.util.ModUtil.options;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {

    public TitleScreenMixin(Component title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        if (options().configButton.everywhere() || options().configButton.titleOnly()) {
            SpriteIconButton settingsButton = this.addRenderableWidget(ButtonUtil.initializeButton(this.minecraft, this));
            settingsButton.setPosition(this.width / 2 + 104, this.height / 4 + 156 + (MultiLoader.PLATFORM.getPlatformName().equals("NeoForge") ? 6 : 0));
        }
    }
}