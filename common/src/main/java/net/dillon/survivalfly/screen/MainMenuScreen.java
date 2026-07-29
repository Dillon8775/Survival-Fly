package net.dillon.survivalfly.screen;

import net.dillon.survivalfly.config.ConfigurationScreen;
import net.dillon.survivalfly.helper.ModHelper;
import net.dillon.survivalfly.platform.ModReferences;
import net.dillon.survivalfly.platform.SurvivalFlyPlatforms;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.toasts.SystemToast;
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
public class MainMenuScreen extends OptionsSubScreen {

    public MainMenuScreen(Screen parent) {
        super(parent, Minecraft.getInstance().options, Component.translatable("survivalfly.title.options"));
    }

    @Override
    protected void init() {
        super.init();
        List<AbstractWidget> options = new ArrayList<>(List.of(
                Button.builder(Component.translatable("survivalfly.gui.configure"), button -> {
                    if (!ModReferences.isModLoaded(ModReferences.YACL)) {
                        this.minecraft.gui.toastManager().addToast(new SystemToast(
                                SystemToast.SystemToastId.PERIODIC_NOTIFICATION,
                                Component.translatable("survivalfly.toast.title.yacl").withStyle(ChatFormatting.RED),
                                Component.translatable("survivalfly.toast.yacl")));
                    } else {
                        this.minecraft.gui.setScreen(ConfigurationScreen.configScreen().generateScreen(this));
                    }
                }).build(),

                Button.builder(Component.translatable("survivalfly.gui.ask_questions"), ConfirmLinkScreen.confirmLink(this, "https://discord.gg/vfqEAn4YFy", false)).build(),

                Button.builder(Component.translatable("survivalfly.gui.report_bugs"), ConfirmLinkScreen.confirmLink(this, "https://github.com/Dillon8775/Survival-Fly/issues", false)).build()
        ));

        this.list.addSmall(options);
    }

    @Override
    public void onClose() {
        ModHelper.debug("Flushed changes.");
        super.onClose();
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float deltaTicks) {
        super.extractRenderState(graphics, mouseX, mouseY, deltaTicks);
        int textWidth = this.width - 20;
        int textHeight = this.height - 21;
        int imageWidth = this.width - SurvivalFlyPlatforms.getPlatform().logoWidth().getWidthModifier();
        int imageHeight = this.height - 26;
        graphics.centeredText(this.font, SurvivalFlyPlatforms.getPlatform().modVersion(), textWidth, textHeight, CommonColors.WHITE);
        graphics.blit(RenderPipelines.GUI_TEXTURED, Identifier.fromNamespaceAndPath("survivalfly", "textures/gui/sprites/survivalfly.png"), imageWidth, imageHeight, 0.0F, 0.0F, 18, 18, 18, 18);
    }

    @Override
    protected void addOptions() {
    }
}