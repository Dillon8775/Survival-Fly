package net.dillon.survivalfly.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import net.blay09.mods.balm.Balm;
import net.blay09.mods.kuma.api.InputBinding;
import net.blay09.mods.kuma.api.KeyModifiers;
import net.blay09.mods.kuma.api.Kuma;
import net.blay09.mods.kuma.api.ManagedKeyMapping;
import net.dillon.dillonlib.annotation.Dill;
import net.dillon.dillonlib.annotation.DillType;
import net.dillon.dillonlib.factory.ClientFactories;
import net.dillon.survivalfly.packet.serverbound.UpdateFlightSpeedC2SPacket;
import net.dillon.survivalfly.screen.MainMenuScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;

import static net.dillon.dillonlib.task.ClientTasks.openScreen;
import static net.dillon.survivalfly.helper.ModConstants.DEFAULT_FLIGHT_SPEED;
import static net.dillon.survivalfly.helper.ModHelper.survivalFlyIdentifier;

/**
 * Keybindings for the {@code SurvivalFly} mod.
 */
@Dill(DillType.CLIENT)
public class ModKeyMappings {
    public static final KeyMapping.Category SURVIVAL_FLY_KEY_CATEGORY = KeyMapping.Category.register(survivalFlyIdentifier("survival_fly"));

    /**
     * Initializes Survival Fly keybinds.
     */
    public static void initKeybinds() {
    }

    public static final ManagedKeyMapping OPEN_SURVIVAL_FLY_MAIN_MENU = Kuma.createKeyMapping(survivalFlyIdentifier("open_survival_fly_main_menu"))
            .overrideCategory(SURVIVAL_FLY_KEY_CATEGORY)
            .withDefault(new InputBinding(
                    InputConstants.Type.KEYBOARD.getOrCreate(InputConstants.KEY_S),
                    KeyModifiers.none().addCustomModifier(InputConstants.KEY_TAB)
            ))
            .handleWorldInput(event -> {
                openScreen(new MainMenuScreen(null));
                return true;
            })
            .build();

    public static final KeyMapping RESET_FLIGHT_SPEED = ClientFactories.createKeyMapping(
            "key.survivalfly.reset_flight_speed",
            InputConstants.Type.KEYBOARD,
            SURVIVAL_FLY_KEY_CATEGORY,
            InputConstants.KEY_B,
            player -> {
                if (player != null && player.getAbilities().mayfly) {
                    Balm.networking().sendToServer(new UpdateFlightSpeedC2SPacket(DEFAULT_FLIGHT_SPEED));
                    player.sendOverlayMessage(Component.translatable("survivalfly.reset_flight_speed").withStyle(ChatFormatting.GREEN));
                }
            }
    );

    public static final KeyMapping CHANGE_FLIGHT_SPEED = new KeyMapping(
            "key.survivalfly.change_flight_speed",
            InputConstants.Type.KEYBOARD,
            InputConstants.KEY_LALT,
            SURVIVAL_FLY_KEY_CATEGORY
    );
}