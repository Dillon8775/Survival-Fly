package net.dillon.survivalfly.option;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum FriendlyFlight implements StringRepresentable {
    OFF(0, "OFF"),
    PLAYERS_AND_MOBS(1, "§bPlayers §r§f& §r§aMobs"),
    PLAYERS_ONLY(2, "§aPlayers Only");

    public static final Codec<FriendlyFlight> CODEC = StringRepresentable.fromEnum(FriendlyFlight::values);
    private final int ordinal;
    private final String name;

    FriendlyFlight(final int ordinal, final String name) {
        this.ordinal = ordinal;
        this.name = name;
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

    public static FriendlyFlight byName(String name) {
        for (FriendlyFlight friendlyFlight : values()) {
            if (friendlyFlight.getSerializedName().equalsIgnoreCase(name)) {
                return friendlyFlight;
            }
        }
        return null;
    }
}