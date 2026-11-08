package net.dillon.survivalfly.client.screen;

import net.dillon.dillonlib.annotation.Dill;
import net.dillon.dillonlib.annotation.DillEnv;
import net.dillon.dillonlib.client.component.GridBuilder;
import net.dillon.dillonlib.client.component.TextureBuilder;
import net.dillon.dillonlib.client.screen.DillonLibScreen;
import net.dillon.survivalfly.helper.ModConstants;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;

import static net.dillon.dillonlib.task.ClientTasks.openScreen;
import static net.dillon.dillonlib.widespread.DillonsWidgets.*;
import static net.dillon.survivalfly.option.OptionInstances.client;
import static net.dillon.survivalfly.option.OptionInstances.updateClient;

@Dill(env = DillEnv.CLIENT)
public class WarningScreen extends DillonLibScreen {
    private final Screen parent;
    private AbstractWidget proceed;

    public WarningScreen(Screen parent) {
        super(Component.translatable("survivalfly"));
        this.parent = parent;
    }

    @Override
    public void onClose() {
        if (client().seenWarningMessage && parent != null) {
            openScreen(parent);
        }
    }

    @Override
    public void widgets() {
        GridBuilder builder = GridBuilder.create(this, 1);

        builder.fillRow(
                createMultiLineText(Component.translatable("survivalfly.warning")),
                builder.alignHorizontallyCenter().paddingBottom(80)
        );

        this.proceed = Button.builder(Component.translatable("dillonlib.proceed"), b -> {
                    updateClient(c -> {
                        if (!c.seenWarningMessage) {
                            openScreen(new TitleScreen());
                        } else {
                            this.onClose();
                        }
                        c.seenWarningMessage = true;
                    });
                })
                .build();
        builder.fillRowAndCenter(proceed);

        addTitleToHeader(this);
        finalizeHeaderAndFooter(this, headerAndFooterLayout);

        builder.buildAndCenter();
    }

    @Override
    protected void drawGraphics(GuiGraphicsExtractor graphics) {
        TextureBuilder.create(
                graphics,
                ModConstants.LOGO,
                proceed.getX() + 44,
                proceed.getY() - 70,
                64,
                64
        ).buildAndBlit(true);
    }
}