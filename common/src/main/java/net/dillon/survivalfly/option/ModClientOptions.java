package net.dillon.survivalfly.option;

import net.blay09.mods.balm.platform.config.reflection.Comment;
import net.blay09.mods.balm.platform.config.reflection.Config;
import net.dillon.survivalfly.helper.ModConstants;

@Config(value = ModConstants.MOD_ID, type = "client")
public class ModClientOptions {
    @Comment("Determines if the user needs to see the warning screen or not.")
    public boolean seenWarningMessage = false;

    @Comment("Sends warning messages to the player when joining servers without Survival Fly installed.")
    public boolean serverWarnings = true;

    @Comment("Prevents players from joining a server that doesn't have the mod installed.")
    public boolean safeMode = true;
}