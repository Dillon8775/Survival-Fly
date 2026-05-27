package net.dillon.survivalfly;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.neoforge.platform.runtime.NeoForgeLoadContext;
import net.dillon.survivalfly.helper.ModHelper;
import net.dillon.survivalfly.keybind.ModKeyMappings;
import net.dillon.survivalfly.main.ClientMain;
import net.dillon.survivalfly.screen.MainMenuScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = ModHelper.MOD_ID, dist = Dist.CLIENT)
public class ClientSurvivalFly {

    public ClientSurvivalFly(IEventBus modEventBus, ModContainer container) {
        ModKeyMappings.initKeybinds();

        container.registerExtensionPoint(
                IConfigScreenFactory.class,
                (mc, parent) -> new MainMenuScreen(parent)
        );

        final var context = new NeoForgeLoadContext(container, modEventBus);
        Balm.initializeMod(ModHelper.MOD_ID, context, ClientMain::cInitialize);
    }
}