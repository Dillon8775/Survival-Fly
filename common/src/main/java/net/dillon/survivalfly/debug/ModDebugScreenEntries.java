package net.dillon.survivalfly.debug;

import net.dillon.dillonlib.mixin.accessor.DebugScreenEntriesInvoker;
import net.minecraft.client.gui.components.debug.DebugEntryCategory;
import net.minecraft.network.chat.Component;

import static net.dillon.survivalfly.helper.ModHelper.ofSurvivalFly;

/**
 * All mod debug entries.
 */
public class ModDebugScreenEntries {
    public static final DebugEntryCategory SURVIVAL_FLY = new DebugEntryCategory(Component.translatable("survivalfly.title"), 5.0F);

    /**
     * Registers all Survival Fly debug entries.
     */
    public static void registerDebugEntries() {
        DebugScreenEntriesInvoker.invokeRegister(ofSurvivalFly("flight_speed"), new FlightSpeedHudEntry());
    }
}