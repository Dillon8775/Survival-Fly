package net.dillon.survivalfly.keybind;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;

/**
 * Keybindings for the {@code Survival Fly} mod.
 */
@Environment(EnvType.CLIENT)
public class ModKeybinds {
    public static final KeyBinding.Category SURVIVAL_FLY = KeyBinding.Category.create(Identifier.of("survivalfly", "survivalfly"));

    public static final KeyBinding RESET_FLIGHT_SPEED = KeyBindingHelper.registerKeyBinding(new KeyBinding("survivalfly.reset_flight_speed", InputUtil.GLFW_KEY_B, SURVIVAL_FLY));
    public static final KeyBinding CHANGE_FLIGHT_SPEED = KeyBindingHelper.registerKeyBinding(new KeyBinding("survivalfly.change_flight_speed", InputUtil.GLFW_KEY_LEFT_ALT, SURVIVAL_FLY));

    /**
     * Initialize mod keybinds.
     */
    public static void init() {}
}