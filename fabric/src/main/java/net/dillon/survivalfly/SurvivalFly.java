package net.dillon.survivalfly;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.fabric.platform.runtime.FabricLoadContext;
import net.dillon.survivalfly.event.FabricCommonEvents;
import net.dillon.survivalfly.helper.ModConstants;
import net.dillon.survivalfly.main.CommonMain;
import net.fabricmc.api.ModInitializer;

public class SurvivalFly implements ModInitializer {

	@Override
	public void onInitialize() {
		FabricCommonEvents.registerCloningEvents();
		FabricCommonEvents.registerRespawningEvents();
		FabricCommonEvents.registerServerConnectionEvents();

		Balm.initializeMod(ModConstants.MOD_ID, FabricLoadContext.INSTANCE, CommonMain::initialize);
	}
}