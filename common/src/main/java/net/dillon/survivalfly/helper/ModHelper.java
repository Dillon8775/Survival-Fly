package net.dillon.survivalfly.helper;

import com.mojang.brigadier.context.CommandContext;
import net.blay09.mods.balm.Balm;
import net.dillon.survivalfly.option.ModClientOptions;
import net.dillon.survivalfly.option.ModOptions;
import net.dillon.survivalfly.util.ModTexts;
import net.dillon.survivalfly.util.PlayerAbilitiesExtension;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for the {@code Survival Fly} mod.
 */
public class ModHelper {
    public static final String MOD_ID = "survivalfly";
    public static final Logger LOGGER = LoggerFactory.getLogger("Survival Fly");
    public static final float DEFAULT_FLIGHT_SPEED = 0.05F;
    public static final int DEFAULT_DAMAGE_TIME_TICKS = 400;
    public static final String EVER_ENABLED_FLIGHT_NAME = "EverEnabledFlight";
    public static final String WANT_TO_FLY_AGAIN = "WantToFlyAgain";
    public static final String DAMAGE_TIME_TICKS_NAME = "DamageTimeTicks";
    public static final String PLAYED_BROKEN_NAME = "PlayedBroken";

    /**
     * Returns the options.
     */
    public static ModOptions options() {
        return Balm.config().getActiveConfig(ModOptions.class);
    }

    /**
     * Returns the client options.
     */
    public static ModClientOptions coptions() {
        return Balm.config().getActiveConfig(ModClientOptions.class);
    }

    /**
     * Sends a message to console.
     */
    public static void info(String message) {
        LOGGER.info(message);
    }

    /**
     * Sends a warning message to console.
     */
    @Deprecated
    public static void warn(String message) {
        LOGGER.warn(message);
    }

    /**
     * Sends a {@code debug} message to the console.
     */
    public static void debug(String message) {
        LOGGER.debug(message);
    }

    /**
     * @return the current elytra stack from the player.

     */
    public static ItemStack getChestSlot(ServerPlayer player) {
        return player.getItemBySlot(EquipmentSlot.CHEST);
    }

    /**
     * Checks if any of the mod's features should function.
     */
    public static boolean modEnabled() {
        return options().enableMod;
    }

    /**
     * @return if the player has an elytra equipped.
     */
    public static boolean hasElytra(ServerPlayer player) {
        ItemStack item = getChestSlot(player);
        return !options().elytraFlight || (item.is(Items.ELYTRA) && item.getDamageValue() != item.getMaxDamage() - 1);
    }

    /**
     * @return if the player's flight abilities should be allowed.
     */
    public static boolean isFlyingAllowed(ServerPlayer player) {
        return ((PlayerAbilitiesExtension)player).flyingAllowed();
    }

    /**
     * Disables flight for player for 20 seconds.
     */
    public static void stopFlightForPlayer(ServerPlayer player, boolean attacker) {
        if (player.getAbilities().mayfly) {
            player.sendOverlayMessage(attacker ? ModTexts.COMBAT : ModTexts.DAMAGED);
            ((PlayerAbilitiesExtension) player).setWantToFlyAgain(true);
        }
        ((PlayerAbilitiesExtension) player).setDamageTicks(DEFAULT_DAMAGE_TIME_TICKS);
    }

    /**
     * @return an invalid player gamemode.
     */
    public static boolean isInvalidPlayerGameMode(ServerPlayer player) {
        return player.gameMode() == GameType.CREATIVE || player.gameMode() == GameType.SPECTATOR;
    }

    /**
     * Sends a message from source stack.
     */
    public static void sendSourceMessage(CommandContext<CommandSourceStack> source, Component text) {
        source.getSource().sendSystemMessage(text);
    }

    /**
     * Returns the flight speed in decimal form.
     */
    public static float percentageAsDecimal(float speed) {
        return (speed / 100.0F) * 0.2F;
    }

    /**
     * Returns the flight speed in percentage form.
     */
    public static int decimalAsPercentage(float speed) {
        return Math.round((speed / 0.2F) * 100);
    }
}