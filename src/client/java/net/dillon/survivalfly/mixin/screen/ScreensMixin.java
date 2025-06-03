package net.dillon.survivalfly.mixin.screen;

import net.dillon.survivalfly.screen.ModOptionsScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;

@Environment(EnvType.CLIENT)
@Mixin(value = {TitleScreen.class, GameMenuScreen.class})
public class ScreensMixin extends Screen {

    public ScreensMixin(Text title) {
        super(title);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_F) {
            this.client.setScreen(new ModOptionsScreen(this));
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}