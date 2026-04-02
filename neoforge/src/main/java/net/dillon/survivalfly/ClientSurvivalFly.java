package net.dillon.survivalfly;

import net.dillon.survivalfly.screen.ModOptionsScreen;
import net.dillon.survivalfly.util.ModUtil;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = ModUtil.MOD_ID, dist = Dist.CLIENT)
public class ClientSurvivalFly {

    /**
     * Registers client-side events for Survival Fly.
     */
    public ClientSurvivalFly(IEventBus modEventBus, ModContainer container) {
        container.registerExtensionPoint(
                IConfigScreenFactory.class,
                (mc, parent) -> new ModOptionsScreen(parent)
        );
    }
}