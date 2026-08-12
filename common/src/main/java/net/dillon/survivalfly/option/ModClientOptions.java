package net.dillon.survivalfly.option;

import net.blay09.mods.balm.platform.config.reflection.Comment;
import net.blay09.mods.balm.platform.config.reflection.Config;
import net.dillon.survivalfly.helper.ModConstants;

@Config(value = ModConstants.MOD_ID, type = "client")
public class ModClientOptions {
    @Comment("Sends warning messages to the player when joining servers without Survival Fly installed.")
    public boolean serverWarnings = true;

    @Comment("Determines where to display the Survival Fly menu button.")
    public MenuButton menuButton = MenuButton.TITLE_ONLY;
}