package net.dillon.survivalfly.screen;

import net.dillon.survivalfly.option.ModListOptions;
import net.dillon.survivalfly.util.ModUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

import static net.dillon.survivalfly.option.ModOptions.saveConfig;

/**
 * The options screen for configurating the {@code /fly} command.
 */
public class ModOptionsScreen extends OptionsSubScreen {
    private final Options options = Minecraft.getInstance().options;

    public ModOptionsScreen(Screen parent) {
        super(parent, Minecraft.getInstance().options, Component.translatable("survivalfly.title.options"));
    }

    @Override
    protected void init() {
        super.init();

        List<AbstractWidget> options = new ArrayList<>(List.of(
                ModListOptions.permissions().createButton(this.options),
                ModListOptions.elytraFlight().createButton(this.options),

                ModListOptions.flightExhaustion().createButton(this.options),
                ModListOptions.friendlyFlight().createButton(this.options),

                ModListOptions.CONFIG_BUTTON.createButton(this.options),
                Button.builder(Component.translatable("survivalfly.gui.ask_questions"), ConfirmLinkScreen.confirmLink(this, "https://discord.gg/vfqEAn4YFy", false)).build(),

                Button.builder(Component.translatable("survivalfly.gui.report_bugs"), ConfirmLinkScreen.confirmLink(this, "https://github.com/Dillon8775/Survival-Fly/issues", false)).build()
        ));

        if (!(this.minecraft.getCurrentServer() == null)) {
            for (int i = 0; i < 4; i++) {
                options.get(i).active = false;
            }
        }

        this.list.addSmall(options);
    }

    @Override
    public void onClose() {
        saveConfig();
        ModUtil.debug("Flushed changes.");
        super.onClose();
    }

    @Override
    protected void addOptions() {
    }
}