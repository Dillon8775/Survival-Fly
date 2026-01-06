package net.dillon.survivalfly.main;

import com.mojang.logging.LogUtils;
import net.dillon.survivalfly.option.ModOptions;
import net.dillon.survivalfly.packet.ServerHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(SurvivalFly.MOD_ID)
public final class SurvivalFly {
    public static final String MOD_ID = "survivalfly";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final float DEFAULT_FLIGHT_SPEED = 0.05F;

    public SurvivalFly(FMLJavaModLoadingContext context) {
        var modBusGroup = context.getModBusGroup();

        context.registerConfig(ModConfig.Type.COMMON, ModOptions.SPEC);

        FMLCommonSetupEvent.getBus(modBusGroup).addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(ServerHandler::registerUpdateFlightSpeedC2SPayload);
    }

    /**
     * Sends a message to console.
     */
    public static void info(String message) {
        LOGGER.info(message);
    }

    /**
     * Sends a warning message to console.
     */
    public static void warn(String message) {
        LOGGER.warn(message);
    }

    /**
     * Returns enabled/disabled text.
     */
    public static Component statusText(ServerPlayer player, boolean isFirstLetterLowercase) {
        return isFirstLetterLowercase ? player.getAbilities().mayfly ? Component.translatable("survivalfly.enabled.lowercase").withStyle(ChatFormatting.GREEN) : Component.translatable("survivalfly.disabled.lowercase").withStyle(ChatFormatting.RED) : player.getAbilities().mayfly ? Component.translatable("survivalfly.enabled").withStyle(ChatFormatting.GREEN) : Component.translatable("survivalfly.disabled").withStyle(ChatFormatting.RED);
    }

    /**
     * Returns the flight speed in decimal form.
     */
    public static float percentageAsDecimal(float speed) {
        return (speed / 100.0F) * 0.2F;
    }

    /**
     * Returns the flight speed in percentage form.
     */
    public static int decimalAsPercentage(float speed) {
        return Math.round((speed / 0.2F) * 100);
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }
}