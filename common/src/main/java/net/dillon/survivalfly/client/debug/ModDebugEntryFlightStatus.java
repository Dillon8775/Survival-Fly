package net.dillon.survivalfly.client.debug;

import net.dillon.dillonlib.annotation.Dill;
import net.dillon.dillonlib.annotation.DillEnv;
import net.dillon.survivalfly.helper.ModHelper;
import net.dillon.survivalfly.util.PlayerAbilitiesExtensionImpl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.debug.DebugScreenDisplayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jspecify.annotations.Nullable;

import static net.dillon.dillonlib.task.ClientTasks.getMinecraft;
import static net.dillon.survivalfly.client.debug.ModDebugScreenEntries.FLIGHT_STATUS_GROUP;

/**
 * Displays the player's flight status.
 */
@Dill(env = DillEnv.CLIENT)
public class ModDebugEntryFlightStatus extends ModDebugEntry {

    @Override
    public void display(DebugScreenDisplayer lines, @Nullable Level serverOrClientLevel, @Nullable LevelChunk clientChunk, @Nullable LevelChunk serverChunk) {
        Minecraft minecraft = getMinecraft();
        LocalPlayer player = minecraft.player;
        if (minecraft.level == null || player == null) {
            return;
        }

        lines.addToGroup(FLIGHT_STATUS_GROUP, "Flight Speed: " + ModHelper.flyingSpeedAsDecimalString(player));

        if (!(serverOrClientLevel instanceof ServerLevel serverLevel)) {
            return;
        }

        ServerPlayer serverPlayer = serverLevel.getServer()
                .getPlayerList()
                .getPlayer(player.getUUID());

        if (serverPlayer == null) {
            return;
        }

        lines.addToGroup(FLIGHT_STATUS_GROUP, ((PlayerAbilitiesExtensionImpl)serverPlayer).flyingAllowed() ? "Able to Fly" : "Unable to Fly");
    }
}