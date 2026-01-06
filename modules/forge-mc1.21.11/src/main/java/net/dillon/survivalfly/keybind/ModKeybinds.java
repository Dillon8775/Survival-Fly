package net.dillon.survivalfly.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import net.dillon.survivalfly.main.SurvivalFly;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

/**
 * Keybindings for the {@code SurvivalFly} mod.
 */
@Mod.EventBusSubscriber(modid = SurvivalFly.MOD_ID, value = Dist.CLIENT)
public class ModKeybinds {
    private static final KeyMapping.Category SURVIVAL_FLY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("survivalfly", "survival_fly"));

    public static final KeyMapping RESET_FLIGHT_SPEED = new KeyMapping(
            "survivalfly.reset_flight_speed",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_B,
            SURVIVAL_FLY
    );

    public static final KeyMapping CHANGE_FLIGHT_SPEED = new KeyMapping(
            "survivalfly.change_flight_speed",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_LEFT_ALT,
            SURVIVAL_FLY
    );

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(ModKeybinds.RESET_FLIGHT_SPEED);
        event.register(ModKeybinds.CHANGE_FLIGHT_SPEED);
    }
}