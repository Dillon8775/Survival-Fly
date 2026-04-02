package net.dillon.survivalfly;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.EmptyLoadContext;
import net.dillon.survivalfly.main.Main;
import net.dillon.survivalfly.screen.ModOptionsScreen;
import net.dillon.survivalfly.util.ModUtil;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ModUtil.MOD_ID)
public final class SurvivalFly {

    public SurvivalFly(FMLJavaModLoadingContext context) {
        Balm.initializeMod(ModUtil.MOD_ID, EmptyLoadContext.INSTANCE, Main::initialize);
    }

    @Mod.EventBusSubscriber(modid = ModUtil.MOD_ID, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MinecraftForge.registerConfigScreen(ModOptionsScreen::new);
        }
    }
}