package net.dillon.survivalfly.option;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModOptions {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.EnumValue<PermissionLevel> PERMISSION_LEVEL;
    public static final ForgeConfigSpec.EnumValue<ChangeFlySpeedOnRule> CHANGE_FLY_SPEED_ON_RULE;
    public static final ForgeConfigSpec.BooleanValue SHOW_CONFIG_BUTTON;

    static {
        BUILDER.push("Config for Survival Fly");

        PERMISSION_LEVEL = BUILDER
                .comment("Required permission level for users to use the survival flight command.")
                .defineEnum("permissionLevel", PermissionLevel.REGULAR);
        CHANGE_FLY_SPEED_ON_RULE = BUILDER
                .comment("The gamemodes on which the player can change their flight speed.")
                .defineEnum("changeFlySpeedOnRule", ChangeFlySpeedOnRule.ANY_GAMEMODE);
        SHOW_CONFIG_BUTTON = BUILDER
                .comment("Display the Survival Fly configuration button on the pause screen.")
                .define("showConfigButton", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}