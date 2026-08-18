package net.dillon.survivalfly.debug;

import net.dillon.dillonlib.mixin.accessor.DebugScreenEntriesInvoker;
import net.minecraft.client.gui.components.debug.DebugEntryCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import static net.dillon.survivalfly.helper.ModHelper.survivalFlyIdentifier;

/**
 * All mod debug entries.
 */
public class ModDebugScreenEntries {
    public static final DebugEntryCategory SURVIVAL_FLY = new DebugEntryCategory(Component.translatable("survivalfly.title"), 5.0F);
    public static final Identifier FLIGHT_STATUS_GROUP = survivalFlyIdentifier("flight_status_group");

    /**
     * Registers all Survival Fly debug entries.
     */
    public static void registerDebugEntries() {
        DebugScreenEntriesInvoker.invokeRegister(survivalFlyIdentifier("flight_status"), new ModDebugEntryFlightStatus());
    }
}