package net.dillon.survivalfly.debug;

import net.minecraft.client.gui.components.debug.DebugEntryCategory;
import net.minecraft.client.gui.components.debug.DebugScreenEntry;

/**
 * An abstract Quality of Queso {@link DebugScreenEntry}.
 */
public abstract class ModDebugEntry implements DebugScreenEntry {

    /**
     * @return Quality of Queso debug entries should always be allowed.
     */
    @Override
    public boolean isAllowed(boolean reducedDebugInfo) {
        return true;
    }

    /**
     * @return always add the Survival Fly debug entry to the mod's category.
     */
    @Override
    public DebugEntryCategory category() {
        return ModDebugScreenEntries.SURVIVAL_FLY;
    }
}