package net.dillon.survivalfly.screen;

import net.dillon.dillonlib.annotation.Dill;
import net.dillon.dillonlib.annotation.DillType;
import net.dillon.dillonlib.task.ClientTasks;
import net.dillon.dillonlib.util.KeybindScrollHelper;
import net.dillon.survivalfly.config.ConfigurationScreen;
import net.dillon.survivalfly.helper.ModConstants;
import net.dillon.survivalfly.keybind.ModKeyMappings;
import net.dillon.survivalfly.platform.SurvivalFlyPlatforms;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import net.minecraft.network.chat.Component;

import java.net.URI;
import java.util.List;

import static net.dillon.dillonlib.task.ClientTasks.openDebugEntriesScreen;
import static net.dillon.dillonlib.task.ClientTasks.openScreen;
import static net.dillon.survivalfly.helper.ModConstants.HAS_UPDATE;
import static net.dillon.survivalfly.helper.ModConstants.VERSION;

/**
 * The options screen for configurating the {@code /fly} command.
 */
@Dill(DillType.CLIENT)
public class MainMenuScreen extends OptionsSubScreen {

    public MainMenuScreen(Screen parent) {
        super(parent, Minecraft.getInstance().options, Component.translatable("survivalfly.menu.title"));
    }

    @Override
    protected void init() {
        super.init();

        this.list.addHeader(Component.translatable("survivalfly.menu.settings"));
        this.list.addSmall(
                List.of(
                        Button.builder(Component.translatable("survivalfly.menu.configure"), button -> ClientTasks.tryOpenYaclScreen(
                                () -> ConfigurationScreen.configScreen().generateScreen(this),
                                Component.translatable("survivalfly.title")
                        )).build(),

                        Button.builder(Component.translatable("survivalfly.menu.keybinds"), button -> {
                            KeybindScrollHelper.request(ModKeyMappings.SURVIVAL_FLY);
                            openScreen(new KeyBindsScreen(this, Minecraft.getInstance().options));
                        }).build()
                )
        );

        this.list.addHeader(Component.translatable("survivalfly.menu.resources_and_utilities"));
        this.list.addSmall(
                List.of(
                        Button.builder(Component.translatable("survivalfly.gui.ask_questions"), ConfirmLinkScreen.confirmLink(this, URI.create("https://discord.gg/vfqEAn4YFy"), false))
                                .build(),

                        Button.builder(Component.translatable("survivalfly.menu.debug_huds"), button -> openDebugEntriesScreen(this, "survivalfly"))
                                .build(),

                        Button.builder(Component.translatable("survivalfly.gui.report_bugs"), ConfirmLinkScreen.confirmLink(this, URI.create("https://github.com/Dillon8775/Survival-Fly/issues"), false))
                                .build()
                )
        );
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float deltaTicks) {
        super.extractRenderState(graphics, mouseX, mouseY, deltaTicks);

        ClientTasks.drawModInfo(
                graphics,
                this,
                VERSION,
                SurvivalFlyPlatforms.getPlatform().logoWidth().getWidthModifier(),
                ModConstants.LOGO,
                HAS_UPDATE
        );
    }

    @Override
    protected void addOptions() {
    }
}