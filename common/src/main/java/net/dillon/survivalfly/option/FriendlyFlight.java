package net.dillon.survivalfly.option;

import net.minecraft.util.StringRepresentable;

public enum FriendlyFlight implements StringRepresentable {
    OFF(0, "off", "survivalfly.options.friendly_flight.off"),
    PLAYERS_AND_MOBS(1, "players_and_mobs", "survivalfly.options.friendly_flight.players_and_mobs"),
    PLAYERS_ONLY(2, "players_only", "survivalfly.options.friendly_flight.players_only");

    private final int ordinal;
    private final String name;
    private final String translationKey;

    FriendlyFlight(final int ordinal, final String name, final String translationKey) {
        this.ordinal = ordinal;
        this.name = name;
        this.translationKey = translationKey;
    }

    public boolean enabled() {
        return this != OFF;
    }

    public boolean mobsAllowed() {
        return this == PLAYERS_AND_MOBS;
    }

    public int getOrdinal() {
        return this.ordinal;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public String getTranslationKey() {
        return this.translationKey;
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