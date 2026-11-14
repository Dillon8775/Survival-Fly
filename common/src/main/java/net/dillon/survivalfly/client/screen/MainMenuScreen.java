package net.dillon.survivalfly.client.screen;

import net.blay09.mods.balm.Balm;
import net.dillon.dillonlib.annotation.Dill;
import net.dillon.dillonlib.annotation.DillEnv;
import net.dillon.dillonlib.client.component.BasicImageButtonBuilder;
import net.dillon.dillonlib.client.component.DoneButtonBuilder;
import net.dillon.dillonlib.client.component.GridBuilder;
import net.dillon.dillonlib.client.screen.DillonLibScreen;
import net.dillon.dillonlib.client.util.KeybindScrollHelper;
import net.dillon.dillonlib.widespread.DillonsWidgets;
import net.dillon.survivalfly.client.config.ConfigurationScreen;
import net.dillon.survivalfly.client.keybind.ModKeyMappings;
import net.dillon.survivalfly.packet.serverbound.RequestServerOptionsC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import static net.dillon.dillonlib.client.component.GridBuilder.createRow;
import static net.dillon.dillonlib.task.ClientTasks.*;
import static net.dillon.dillonlib.widespread.DillonsWidgets.createMultiLineText;
import static net.dillon.dillonlib.widespread.DillonsWidgets.finalizeHeaderAndFooter;
import static net.dillon.survivalfly.helper.ModConstants.MOD_INFO;
import static net.dillon.survivalfly.option.OptionInstances.common;

@Dill(env = DillEnv.CLIENT)
public class MainMenuScreen extends DillonLibScreen {

    public MainMenuScreen(Screen parent) {
        super(parent, Component.translatable("survivalfly.title"));
    }

    @Override
    public void widgets() {
        AbstractWidget configure = Button.builder(Component.translatable("survivalfly.menu.configure"), button -> {
                    if (isOnServer()) {
                        Balm.networking().sendToServer(new RequestServerOptionsC2SPacket());
                    } else {
                        openScreen(ConfigurationScreen.configScreen(common(), true).generateScreen(this));
                    }
                }
        )
                .width(100)
                .build();

        AbstractWidget keybinds = Button.builder(Component.translatable("survivalfly.menu.keybinds"), button -> {
            KeybindScrollHelper.request(ModKeyMappings.SURVIVAL_FLY_KEY_CATEGORY);
            openScreen(new KeyBindsScreen(this, Minecraft.getInstance().options));
        })
                .width(100)
                .build();

        AbstractWidget debugHuds = Button.builder(
                        Component.translatable("survivalfly.menu.debug_huds"),
                        button -> openDebugEntriesScreen(this, "survivalfly")
                )
                .width(100)
                .build();

        ImageButton disclaimer = BasicImageButtonBuilder.builder(
                        new WidgetSprites(
                                Identifier.withDefaultNamespace("social_interactions/report_button"),
                                Identifier.withDefaultNamespace("social_interactions/report_button_disabled"),
                                Identifier.withDefaultNamespace("social_interactions/report_button_highlighted")
                        ),
                        button -> openScreen(new WarningScreen(this))
                )
                .tooltip(Component.translatable("survivalfly.disclaimer"))
                .build();

        GridBuilder builder = GridBuilder.create(this)
                .setRowSpacing(8)
                .setColumnSpacing(8);

        builder.fillRow(
                createMultiLineText(title),
                builder.alignHorizontallyCenter().paddingBottom(8)
        );

        builder.fillRow(
                createMultiLineText(Component.translatable("survivalfly.header.settings")),
                builder.alignHorizontallyCenter().paddingTop(8)
        );

        builder.getCurrentRow().addChild(configure);
        builder.getCurrentRow().addChild(keybinds);

        builder.fillRow(
                createMultiLineText(Component.translatable("survivalfly.header.utilities")),
                builder.alignHorizontallyCenter().paddingTop(8)
        );

        LinearLayout row = createRow();
        row.addChild(debugHuds);
        row.addChild(disclaimer);
        builder.fillRowAndCenter(row);

        builder.fillRow(
                DillonsWidgets.createVersionLabel(MOD_INFO, this),
                builder.alignHorizontallyCenter().paddingTop(8)
        );

        headerAndFooterLayout.addToFooter(
                DoneButtonBuilder.builder(this)
                        .width(175)
                        .build()
        );
        finalizeHeaderAndFooter(this, headerAndFooterLayout);

        builder.setVerticalAlignment(0.40F).buildAndAlign();
    }
}