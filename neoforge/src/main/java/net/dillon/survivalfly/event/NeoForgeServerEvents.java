package net.dillon.survivalfly.event;

import net.dillon.survivalfly.helper.ModConstants;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = ModConstants.MOD_ID, value = Dist.DEDICATED_SERVER)
public class NeoForgeServerEvents {
}