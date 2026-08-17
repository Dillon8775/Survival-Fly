package net.dillon.survivalfly.debug;

import net.dillon.survivalfly.helper.ModHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.debug.DebugScreenDisplayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jspecify.annotations.Nullable;

import static net.dillon.dillonlib.task.ClientTasks.getMinecraft;

/**
 * Displays the player's flight speed.
 */
public class FlightSpeedHudEntry extends ModDebugEntry {

    @Override
    public void display(DebugScreenDisplayer lines, @Nullable Level serverOrClientLevel, @Nullable LevelChunk clientChunk, @Nullable LevelChunk serverChunk) {
        Minecraft minecraft = getMinecraft();
        LocalPlayer player = minecraft.player;
        if (minecraft.level == null || player == null) {
            return;
        }

        lines.addLine("Flight Speed: " + ModHelper.flyingSpeedAsDecimalString(player));
    }
}