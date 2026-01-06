package net.dillon.survivalfly.screen;

import net.dillon.survivalfly.main.SurvivalFly;
import net.dillon.survivalfly.option.ModListOptions;
import net.dillon.survivalfly.option.ModOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * The options screen for configurating the {@code /fly} command.
 */
@OnlyIn(Dist.CLIENT)
public class ModOptionsScreen extends OptionsSubScreen {
    protected OptionsList list;

    public ModOptionsScreen(Screen parent) {
        super(parent, Minecraft.getInstance().options, Component.translatable("survivalfly.title.options"));
    }

    protected OptionInstance<?>[] options() {
        return new OptionInstance[]{
                ModListOptions.SHOW_CONFIG_BUTTON
        };
    }

    @Override
    protected void init() {
        super.init();
        this.list = new OptionsList(this.minecraft, this.width, this.height, 32, this.height - 32, 25);
        this.list.addBig(ModListOptions.PERMISSION_LEVEL);
        this.list.addBig(ModListOptions.CHANGE_FLY_SPEED_ON_RULE);
        this.list.addSmall(options());
        this.addWidget(this.list);

        this.addRenderableWidget(Button.builder(
                CommonComponents.GUI_DONE,
                button -> this.onClose()
        ).bounds(this.width / 2 - 100, this.height - 27, 200, 20).build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float deltaTicks) {
        this.renderBackground(graphics);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 15, 16777215);
        if (this.list != null) {
            this.list.render(graphics, mouseX, mouseY, deltaTicks);
        }
        super.render(graphics, mouseX, mouseY, deltaTicks);
    }

    @Override
    public void onClose() {
        ModOptions.SPEC.save();
        SurvivalFly.info("Flushed changes.");
        super.onClose();
    }
}