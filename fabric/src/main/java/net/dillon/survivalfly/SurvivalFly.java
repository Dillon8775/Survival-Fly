package net.dillon.survivalfly;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.fabric.platform.runtime.FabricLoadContext;
import net.dillon.survivalfly.main.CommonMain;
import net.dillon.survivalfly.util.ModUtil;
import net.fabricmc.api.ModInitializer;

import static net.dillon.survivalfly.event.FabricCommonEvents.registerFabricCommands;

public class SurvivalFly implements ModInitializer {

	@Override
	public void onInitialize() {
		registerFabricCommands();

		Balm.initializeMod(ModUtil.MOD_ID, FabricLoadContext.INSTANCE, CommonMain::initialize);
	}
}