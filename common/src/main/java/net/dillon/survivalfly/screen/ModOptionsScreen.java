package net.dillon.survivalfly.screen;

import net.dillon.survivalfly.option.ModListOptions;
import net.dillon.survivalfly.platform.MultiLoader;
import net.dillon.survivalfly.util.ModUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.CommonColors;

import java.util.ArrayList;
import java.util.List;

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
                ModListOptions.FRIENDLY_FLIGHT.createButton(this.options),

                ModListOptions.MENU_BUTTON.createButton(this.options),
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
        ModUtil.debug("Flushed changes.");
        super.onClose();
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float deltaTicks) {
        super.extractRenderState(graphics, mouseX, mouseY, deltaTicks);
        graphics.centeredText(this.font, Component.literal(MultiLoader.getPlatform().getModVersion()), this.width - 20, this.height - 21, CommonColors.WHITE);
        graphics.blit(RenderPipelines.GUI_TEXTURED, Identifier.parse("survivalfly:textures/gui/sprites/survivalfly.png"), this.width - 50, this.height - 26, 0.0F, 0.0F, 18, 18, 18, 18);
    }

    @Override
    protected void addOptions() {
    }
}