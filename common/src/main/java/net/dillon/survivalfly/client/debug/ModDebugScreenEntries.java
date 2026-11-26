package net.dillon.survivalfly.client.debug;

import net.dillon.dillonlib.annotation.Dill;
import net.dillon.dillonlib.annotation.DillEnv;
import net.minecraft.client.gui.components.debug.DebugEntryCategory;
import net.minecraft.client.gui.components.debug.DebugGroup;
import net.minecraft.client.gui.components.debug.DebugScreenEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;

import static net.dillon.dillonlib.client.component.DebugBuilder.basicDebugCategory;
import static net.dillon.dillonlib.factory.ClientFactories.createDebugEntryKey;
import static net.dillon.survivalfly.helper.ModHelper.survivalFlyIdentifier;

/**
 * All mod debug entries.
 */
@Dill(env = DillEnv.CLIENT)
public class ModDebugScreenEntries {
    public static final ResourceKey<DebugScreenEntry> FLIGHT_SPEED = key("flight_speed");

    protected static final DebugGroup MOD_GROUP = DebugGroup.Builder
            .titled(Component.translatable("survivalfly"))
            .build();

    protected static final DebugEntryCategory MOD_CATEGORY = basicDebugCategory(Component.translatable("survivalfly"));

    /**
     * @return a debug entry key.
     */
    private static ResourceKey<DebugScreenEntry> key(String name) {
        return createDebugEntryKey(survivalFlyIdentifier(name));
    }
}