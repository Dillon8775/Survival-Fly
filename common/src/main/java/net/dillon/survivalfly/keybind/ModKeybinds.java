package net.dillon.survivalfly.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.kuma.api.*;
import net.dillon.survivalfly.packet.UpdateFlightC2SPacket;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;

/**
 * Keybindings for the {@code SurvivalFly} mod.
 */
public class ModKeybinds {
    private static final String SURVIVAL_FLY = "survivalfly.title.options";

    public static final ManagedKeyMapping TOGGLE_FLIGHT = Kuma.createKeyMapping(ResourceLocation.fromNamespaceAndPath("survivalfly", "toggle_flight"))
            .overrideCategory(SURVIVAL_FLY)
            .withDefault(InputBinding.key(InputConstants.KEY_F, KeyModifiers.of(KeyModifier.CONTROL, KeyModifier.ALT)))
            .withFallbackDefault(InputBinding.key(InputConstants.UNKNOWN.getValue()))
            .handleWorldInput(event -> {
                Balm.getNetworking().sendToServer(new UpdateFlightC2SPacket(!Minecraft.getInstance().player.getAbilities().mayfly));
                return true;
            }).build();

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

    /**
     * Initializes Survival Fly keybinds.
     */
    public static void initKeybinds() {
    }
}