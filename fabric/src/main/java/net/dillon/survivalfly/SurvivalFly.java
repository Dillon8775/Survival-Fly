package net.dillon.survivalfly;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.fabric.platform.runtime.FabricLoadContext;
import net.dillon.survivalfly.helper.ModConstants;
import net.dillon.survivalfly.main.CommonMain;
import net.fabricmc.api.ModInitializer;

public class SurvivalFly implements ModInitializer {

	@Override
	public void onInitialize() {
		Balm.initializeMod(ModConstants.MOD_ID, FabricLoadContext.INSTANCE, CommonMain::initialize);
	}
}