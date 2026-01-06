package net.dillon.survivalfly.screen;

import net.dillon.survivalfly.main.SurvivalFly;
import net.dillon.survivalfly.option.ModListOptions;
import net.dillon.survivalfly.option.ModOptions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConfirmLinkScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * The options screen for configurating the {@code /fly} command.
 */
@Environment(EnvType.CLIENT)
public class ModOptionsScreen extends GameOptionsScreen {

    public ModOptionsScreen(Screen parent) {
        super(parent, MinecraftClient.getInstance().options, Text.translatable("survivalfly.title.options"));
    }

    @Override
    protected void init() {
        super.init();
        this.body.addSingleOptionEntry(ModListOptions.PERMISSION_LEVEL);
        this.body.addSingleOptionEntry(ModListOptions.CHANGE_FLY_SPEED_ON_RULE);

        List<ClickableWidget> options = new ArrayList<>(List.of(
                ModListOptions.CONFIG_BUTTON.createWidget(MinecraftClient.getInstance().options),
                ButtonWidget.builder(Text.translatable("survivalfly.gui.report_bugs"), ConfirmLinkScreen.opening(this, "https://github.com/Dillon8775/Survival-Fly/issues", false)).build()
        ));
        this.body.addAll(options);
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