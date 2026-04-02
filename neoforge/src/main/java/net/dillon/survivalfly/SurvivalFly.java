package net.dillon.survivalfly;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.neoforge.NeoForgeLoadContext;
import net.dillon.survivalfly.main.Main;
import net.dillon.survivalfly.util.ModUtil;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(ModUtil.MOD_ID)
public final class SurvivalFly {

    public SurvivalFly(IEventBus modEventBus, ModContainer modContainer) {
        final var context = new NeoForgeLoadContext(modEventBus);
        Balm.initializeMod(ModUtil.MOD_ID, context, Main::initialize);
    }
}