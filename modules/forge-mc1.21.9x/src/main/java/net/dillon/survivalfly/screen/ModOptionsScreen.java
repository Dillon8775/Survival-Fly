package net.dillon.survivalfly.screen;

import net.dillon.survivalfly.main.SurvivalFly;
import net.dillon.survivalfly.option.ModListOptions;
import net.dillon.survivalfly.option.ModOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

/**
 * The options screen for configurating the {@code /fly} command.
 */
@OnlyIn(Dist.CLIENT)
public class ModOptionsScreen extends OptionsSubScreen {

    public ModOptionsScreen(Screen parent) {
        super(parent, Minecraft.getInstance().options, Component.translatable("survivalfly.title.options"));
    }

    @Override
    protected void init() {
        super.init();
        this.list.addBig(ModListOptions.PERMISSION_LEVEL);
        this.list.addBig(ModListOptions.CHANGE_FLY_SPEED_ON_RULE);

        List<AbstractWidget> options = new ArrayList<>(List.of(
                ModListOptions.SHOW_CONFIG_BUTTON.createButton(Minecraft.getInstance().options),
                Button.builder(Component.translatable("survivalfly.gui.report_bugs"), ConfirmLinkScreen.confirmLink(this, "https://github.com/Dillon8775/Survival-Fly/issues", false)).build()
        ));
        this.list.addSmall(options);
    }

    @Override
    public void onClose() {
        ModOptions.SPEC.save();
        SurvivalFly.info("Flushed changes.");
        super.onClose();
    }

    @Override
    protected void addOptions() {
    }
}