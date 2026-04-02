package net.dillon.survivalfly;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.fabric.platform.runtime.FabricLoadContext;
import net.dillon.survivalfly.main.Main;
import net.dillon.survivalfly.util.ModUtil;
import net.fabricmc.api.ModInitializer;

public class SurvivalFly implements ModInitializer {

	@Override
	public void onInitialize() {
		Balm.initializeMod(ModUtil.MOD_ID, FabricLoadContext.INSTANCE, Main::initialize);
	}
}