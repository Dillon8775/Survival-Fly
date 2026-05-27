package net.dillon.survivalfly.option;

import net.blay09.mods.balm.platform.config.reflection.Comment;
import net.blay09.mods.balm.platform.config.reflection.Config;
import net.dillon.survivalfly.helper.ModHelper;

@Config(ModHelper.MOD_ID)
public class ModOptions {
    @Comment("Enables the Survival Fly mod.")
    public boolean enableMod = true;

    @Comment("Default permission system for survival fly's commands (/flight, /flightstatus, and /flightspeed).\n# Allowed values: anyone, moderator, gamemaster, admin\n# Ignore this if you are using LuckPerms.")
    public Permissions permissions = Permissions.ANYONE;

    @Comment("Requires the player to wear an elytra to use flight. The elytra's durability will decrement based on the player's flight speed.")
    public boolean elytraFlight = false;

    @Comment("Makes the player hungry when using flight.")
    public boolean flightExhaustion = false;

    @Comment("Allows crouching while flying without descending.")
    public boolean crouchFlight = false;

    @Comment("Prevents the player from using flight if they have taken any damage within 20 seconds.\n# Allowed values: off, players_and_mobs, players_only")
    public FriendlyFlight friendlyFlight = FriendlyFlight.OFF;

    @Comment("Prevents players from joining a server that doesn't have the mod installed.")
    public boolean safeMode = true;
}