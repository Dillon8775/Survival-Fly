package net.dillon.survivalfly.helper;

import com.mojang.brigadier.context.CommandContext;
import net.dillon.survivalfly.util.ModTexts;
import net.dillon.survivalfly.util.PlayerAbilitiesExtension;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;

import static net.dillon.survivalfly.helper.ModConstants.DEFAULT_DAMAGE_TIME_TICKS;
import static net.dillon.survivalfly.helper.ModConstants.MOD_ID;
import static net.dillon.survivalfly.option.OptionInstances.common;

/**
 * Utility class for the {@code Survival Fly} mod.
 */
public class ModHelper {

    /**
     * Checks if any of the mod's features should function.
     */
    public static boolean modEnabled() {
        return common().enableMod;
    }

    /**
     * @return a {@code Survival Fly} identifier.
     */
    public static Identifier ofSurvivalFly(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    /**
     * @return the current elytra stack from the player.

     */
    public static ItemStack getChestSlot(ServerPlayer player) {
        return player.getItemBySlot(EquipmentSlot.CHEST);
    }

    /**
     * @return if the player has an elytra equipped.
     */
    public static boolean hasElytra(ServerPlayer player) {
        ItemStack item = getChestSlot(player);
        return !common().elytraFlight || (item.is(Items.ELYTRA) && item.getDamageValue() != item.getMaxDamage() - 1);
    }

    /**
     * @return if the player's flight abilities should be allowed.
     */
    public static boolean isFlyingAllowed(ServerPlayer player) {
        return ((PlayerAbilitiesExtension)player).flyingAllowed();
    }

    /**
     * @return the player's flight speed as a decimal string.
     */
    public static String flyingSpeedAsDecimalString(Player player) {
        int f = decimalAsPercentage(player.getAbilities().getFlyingSpeed());
        return f == 25 ? "Default" : f + "%";
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