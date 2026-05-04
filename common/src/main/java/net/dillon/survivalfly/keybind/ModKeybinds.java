package net.dillon.survivalfly.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import net.blay09.mods.balm.Balm;
import net.blay09.mods.kuma.api.*;
import net.dillon.survivalfly.packet.UpdateFlightC2SPacket;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

/**
 * Keybindings for the {@code SurvivalFly} mod.
 */
public class ModKeybinds {
    private static final KeyMapping.Category SURVIVAL_FLY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("survivalfly", "survival_fly"));

    public static final ManagedKeyMapping TOGGLE_FLIGHT = Kuma.createKeyMapping(Identifier.fromNamespaceAndPath("survivalfly", "toggle_flight"))
            .overrideCategory(SURVIVAL_FLY)
            .withDefault(InputBinding.key(InputConstants.KEY_F, KeyModifiers.of(KeyModifier.CONTROL, KeyModifier.ALT)))
            .handleWorldInput(event -> {
                Balm.networking().sendToServer(new UpdateFlightC2SPacket(!Minecraft.getInstance().player.getAbilities().mayfly));
                return true;
            })
            .build();

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