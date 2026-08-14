package net.dillon.survivalfly.option;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum FriendlyFlight implements StringRepresentable {
    OFF(0, "OFF", "off"),
    PLAYERS_AND_MOBS(1, "§bPlayers §r§f& §r§aMobs", "players_and_mobs"),
    PLAYERS_ONLY(2, "§aPlayers Only", "players_only");

    public static final Codec<FriendlyFlight> CODEC = StringRepresentable.fromEnum(FriendlyFlight::values);
    private final int ordinal;
    private final String name;
    private final String rawName;

    FriendlyFlight(final int ordinal, final String name, final String rawName) {
        this.ordinal = ordinal;
        this.name = name;
        this.rawName = rawName;
    }

    public boolean enabled() {
        return this != OFF;
    }

    public boolean mobsAllowed() {
        return this == PLAYERS_AND_MOBS;
    }

    public int getId() {
        return this.ordinal;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public String getRawName() {
        return this.rawName;
    }

    public static FriendlyFlight byName(String name) {
        for (FriendlyFlight friendlyFlight : values()) {
            if (friendlyFlight.getRawName().equalsIgnoreCase(name)) {
                return friendlyFlight;
            }
        }
        return null;
    }
}