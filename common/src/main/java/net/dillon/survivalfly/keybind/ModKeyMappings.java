package net.dillon.survivalfly.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import net.blay09.mods.balm.Balm;
import net.blay09.mods.kuma.api.*;
import net.dillon.dillonlib.annotation.Dill;
import net.dillon.dillonlib.annotation.DillType;
import net.dillon.dillonlib.factory.ClientFactories;
import net.dillon.survivalfly.packet.UpdateFlightC2SPacket;
import net.dillon.survivalfly.packet.UpdateFlightSpeedC2SPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import static net.dillon.survivalfly.helper.ModConstants.DEFAULT_FLIGHT_SPEED;
import static net.dillon.survivalfly.helper.ModHelper.modEnabled;
import static net.dillon.survivalfly.helper.ModHelper.ofSurvivalFly;

/**
 * Keybindings for the {@code SurvivalFly} mod.
 */
@Dill(DillType.CLIENT)
public class ModKeyMappings {
    public static final KeyMapping.Category SURVIVAL_FLY = KeyMapping.Category.register(ofSurvivalFly("survival_fly"));

    /**
     * Initializes Survival Fly keybinds.
     */
    public static void initKeybinds() {
    }

    public static final ManagedKeyMapping TOGGLE_FLIGHT = Kuma.createKeyMapping(ofSurvivalFly("toggle_flight"))
            .overrideCategory(SURVIVAL_FLY)
            .withDefault(InputBinding.key(InputConstants.KEY_F, KeyModifiers.of(KeyModifier.CONTROL, KeyModifier.ALT)))
            .handleWorldInput(event -> {
                if (!modEnabled()) {
                    return false;
                }

                Balm.networking().sendToServer(new UpdateFlightC2SPacket(!Minecraft.getInstance().player.getAbilities().mayfly));
                return true;
            })
            .build();

    public static final KeyMapping RESET_FLIGHT_SPEED = ClientFactories.createKeyMapping(
            "survivalfly.reset_flight_speed",
            InputConstants.Type.KEYBOARD,
            SURVIVAL_FLY,
            InputConstants.KEY_B,
            player -> {
                if (player != null && player.getAbilities().mayfly) {
                    Balm.networking().sendToServer(new UpdateFlightSpeedC2SPacket(DEFAULT_FLIGHT_SPEED));
                    player.sendOverlayMessage(Component.translatable("survivalfly.reset_flight_speed").withStyle(ChatFormatting.GREEN));
                }
            }
    );

    public static final KeyMapping CHANGE_FLIGHT_SPEED = new KeyMapping(
            "survivalfly.change_flight_speed",
            InputConstants.Type.KEYBOARD,
            InputConstants.KEY_LALT,
            SURVIVAL_FLY
    );
}