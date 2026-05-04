package net.dillon.survivalfly.option;

import net.blay09.mods.balm.api.config.reflection.Comment;
import net.blay09.mods.balm.api.config.reflection.Config;
import net.dillon.survivalfly.util.ModUtil;

@Config(ModUtil.MOD_ID)
public class ModOptions {
    @Comment("Default permission system for survival fly's commands (/flight, /flightstatus, and /flightspeed).\n# Allowed values: anyone, moderator, gamemaster, admin\n# Ignore this if you are using LuckPerms.")
    public Permissions permissions = Permissions.ANYONE;

    @Comment("Determines where to display the Survival Fly menu (or configuration) button.\n# Allowed values: everywhere, bottom_left, bottom_right, title_only, off")
    public MenuButton menuButton = MenuButton.EVERYWHERE;

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