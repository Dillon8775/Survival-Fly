package net.dillon.survivalfly.screen;

import net.dillon.dillonlib.annotation.Dill;
import net.dillon.dillonlib.annotation.DillType;
import net.dillon.dillonlib.screen.BasicDillonLibScreen;
import net.dillon.survivalfly.helper.ModConstants;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;

import static net.dillon.dillonlib.task.ClientTasks.drawSprite;
import static net.dillon.dillonlib.task.ClientTasks.openScreen;
import static net.dillon.survivalfly.option.OptionInstances.client;
import static net.dillon.survivalfly.option.OptionInstances.updateClient;

@Dill(DillType.CLIENT)
public class WarningScreen extends BasicDillonLibScreen {
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
        builder().heightCenter().apply();
        builder().heightDown(70).apply();
        builder().widthCenter().apply();
        builder().widthLeft(100).apply();

        this.proceed = this.addRenderableWidget(
                Button.builder(Component.translatable("dillonlib.proceed"), b -> {
                            updateClient(c -> {
                                if (!c.seenWarningMessage) {
                                    openScreen(new TitleScreen());
                                } else {
                                    this.onClose();
                                }
                                c.seenWarningMessage = true;
                            });
                        })
                        .bounds(builder().captureWidth(), builder().captureHeight(), 200, 20)
                        .build()
        );
    }

    @Override
    protected void drawGraphics(GuiGraphicsExtractor graphics) {
        builder().textTitleGraphicsHeight(graphics);

        builder().graphicsWidthCenter().apply();
        builder().graphicsWidthLeft(32).apply();
        builder().graphicsHeight(proceed).apply();
        builder().graphicsHeightUp(84).apply();

        drawSprite(
                graphics,
                ModConstants.LOGO,
                builder().captureGraphicsWidth(),
                builder().captureGraphicsHeight(),
                64,
                64
        );

        builder().graphicsHeightUp(64).apply();

        builder().textCenterAndGraphicsHeightDownWrapped(graphics, Component.translatable("survivalfly.warning")).apply();
    }
}