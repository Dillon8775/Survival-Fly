package net.dillon.survivalfly.mixin;

import net.dillon.survivalfly.util.ButtonUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.dillon.survivalfly.main.SurvivalFly.options;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {

    public TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        if (options().configButton.everywhere() || options().configButton.titleOnly()) {
            TextIconButtonWidget settingsButton = this.addDrawableChild(ButtonUtil.initializeButton(this.client, this));
            settingsButton.setPosition(this.width / 2 + 104, this.height / 4 + 156);
        }
    }
}