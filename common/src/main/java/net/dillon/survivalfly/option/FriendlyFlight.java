package net.dillon.survivalfly.option;

import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

public enum FriendlyFlight implements StringRepresentable {
    OFF(0, "off", "survivalfly.options.friendly_flight.off"),
    PLAYERS_AND_MOBS(1, "players_and_mobs", "survivalfly.options.friendly_flight.players_and_mobs"),
    PLAYERS_ONLY(2, "players_only", "survivalfly.options.friendly_flight.players_only");

    public static final Codec<FriendlyFlight> Codec = StringRepresentable.fromEnum(FriendlyFlight::values);
    private final int ordinal;
    private final String name;
    private final Component translationKey;

    FriendlyFlight(final int ordinal, final String name, final String translationKey) {
        this.ordinal = ordinal;
        this.name = name;
        this.translationKey = Component.translatable(translationKey);
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

    public String getSerializedName() {
        return this.name;
    }

    public Component getText() {
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