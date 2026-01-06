package net.dillon.survivalfly.mixin;

import net.dillon.survivalfly.util.ButtonUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.dillon.survivalfly.main.SurvivalFly.options;
import static net.dillon.survivalfly.main.SurvivalFlyClient.isFlashbackLoaded;

@Environment(EnvType.CLIENT)
@Mixin(GameMenuScreen.class)
public class GameMenuScreenMixin extends Screen {
    @Shadow
    @Final
    private boolean showMenu;

    public GameMenuScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        if (options().configButton.everywhere() && this.showMenu) {
            TextIconButtonWidget settingsButton = this.addDrawableChild(ButtonUtil.initializeButton(this.client, this));
            settingsButton.setPosition(this.width / 2 + 106, isFlashbackLoaded() ? this.height / 4 + 24 - 16 : this.height / 4 + 48 - 16);
        }
    }
}