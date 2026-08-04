package net.dillon.survivalfly.option;

import net.blay09.mods.balm.platform.config.reflection.Comment;
import net.blay09.mods.balm.platform.config.reflection.Config;
import net.dillon.survivalfly.helper.ModHelper;

@Config(value = ModHelper.MOD_ID, type = "client")
public class ModClientOptions {
    @Comment("Sends warning messages to the player when joining servers without Survival Fly installed.")
    public boolean serverWarnings = true;
}