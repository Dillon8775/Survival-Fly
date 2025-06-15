package net.dillon.survivalfly.screen;

import net.dillon.survivalfly.main.SurvivalFly;
import net.dillon.survivalfly.keybind.ModKeybinds;
import net.dillon.survivalfly.option.ModListOptions;
import net.dillon.survivalfly.option.ModOptions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

/**
 * The options screen for configurating the {@code /fly} command.
 */
@Environment(EnvType.CLIENT)
public class ModOptionsScreen extends GameOptionsScreen {

    public ModOptionsScreen(Screen parent) {
        super(parent, MinecraftClient.getInstance().options, Text.translatable(ModKeybinds.SURVIVAL_FLY));
    }

    /**
     * {@code Survival fly options.}
     */
    private static SimpleOption<?>[] options() {
        return new SimpleOption<?>[]{
                ModListOptions.SHOW_CONFIG_BUTTON
        };
    }

    @Override
    protected void init() {
        super.init();
        this.body.addSingleOptionEntry(ModListOptions.PERMISSION_LEVEL);
        this.body.addSingleOptionEntry(ModListOptions.CHANGE_FLY_SPEED_ON_RULE);
        this.body.addAll(options());
    }

    @Override
    public void close() {
        ModOptions.saveConfig();
        SurvivalFly.info("Flushed changes.");
        super.close();
    }

    @Override
    protected void addOptions() {
    }
}